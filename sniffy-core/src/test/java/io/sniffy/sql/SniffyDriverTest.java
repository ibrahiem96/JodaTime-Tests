package io.sniffy.sql;

import io.sniffy.BaseTest;
import io.sniffy.Constants;
import io.sniffy.Sniffy;
import io.sniffy.Spy;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.sql.*;
import java.util.Enumeration;
import java.util.Properties;

import static java.net.InetAddress.getLoopbackAddress;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;

public class SniffyDriverTest extends BaseTest {

    @Test
    public void testRegisterDriver() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        boolean found = false;
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            if (driver instanceof SniffyDriver) found = true;
        }
        assertTrue(found);
    }

    @Test
    public void testGetDriver() throws ClassNotFoundException, SQLException {
        Driver driver = DriverManager.getDriver("sniffy:jdbc:h2:mem:");
        assertNotNull(driver);
    }

    @Test
    public void testGetMockConnection() throws ClassNotFoundException, SQLException {
        try (Connection connection = DriverManager.getConnection("sniffy:jdbc:h2:mem:", "sa", "sa")) {
            assertNotNull(connection);
            assertTrue(Proxy.isProxyClass(connection.getClass()));
        }
    }

    @Test
    public void testGetMockConnectionMakesTcp() throws ClassNotFoundException, SQLException {
        TestDriver testDriver = TestDriver.getSpy();

        final InetSocketAddress loopbackAddress = new InetSocketAddress(getLoopbackAddress(), 4242);

        try {
            doAnswer(invocation -> {
                Sniffy.logSocket(1, loopbackAddress, 100, 2, 3);
                return invocation.callRealMethod();
            }).when(testDriver).connect(any(), any());

            try (Connection connection = DriverManager.getConnection("sniffy:jdbc:h2spy:mem:", "sa", "sa")) {
                assertNotNull(connection);
                assertTrue(Proxy.isProxyClass(connection.getClass()));
            }
        } finally {
            Mockito.reset(testDriver);
        }

        try (Connection connection = DriverManager.getConnection("sniffy:jdbc:h2spy:mem:", "sa", "sa")) {
            assertNotNull(connection);
            assertTrue(Proxy.isProxyClass(connection.getClass()));
        }

    }

    @Test
    public void testGetMockConnectionRaisesException() throws ClassNotFoundException, SQLException {
        try {
            DriverManager.getConnection("sniffy:unknown:jdbc:url");
            fail();
        } catch (SQLException e) {
            assertNotNull(e);
        } catch (Exception e) {
            assertNull(e);
        }
    }

    @Test
    public void testGetPropertyInfo() throws ClassNotFoundException, SQLException {
        Driver driver = DriverManager.getDriver("sniffy:jdbc:h2:mem:");
        DriverPropertyInfo[] propertyInfo = driver.getPropertyInfo("jdbc:h2:~/test", new Properties());
        assertNotNull(propertyInfo);
    }

    @Test
    public void testJdbcComplaint() throws ClassNotFoundException, SQLException {
        Driver driver = DriverManager.getDriver("sniffy:jdbc:h2:mem:");
        assertTrue(driver.jdbcCompliant());
    }

    @Test
    public void testGetParentLoggerThrowsException() throws ClassNotFoundException, SQLException {
        Driver driver = DriverManager.getDriver("sniffy:jdbc:h2:mem:");
        try {
            driver.getParentLogger();
            fail("getParentLogger() should have thrown an exception");
        } catch (SQLFeatureNotSupportedException e) {
            assertNotNull(e);
        }
    }

    @Test
    public void testExecuteStatement() throws ClassNotFoundException, SQLException {
        Spy spy = Sniffy.spy();
        executeStatement();
        assertEquals(1, spy.executedStatements());
        spy.verifyAtMostOnce().reset().verifyNever();
    }

    @Test
    public void testExecuteIncorrectStatement() throws ClassNotFoundException, SQLException {
        Spy spy = Sniffy.spy();
        try (Connection connection = DriverManager.getConnection("sniffy:jdbc:h2:mem:", "sa", "sa");
             Statement statement = connection.createStatement()) {
            try {
                statement.execute("this is an incorrect SQL query");
            } catch (SQLException e) {
                assertNotNull(e);
            }
        }
        assertEquals(1, spy.executedStatements());
        spy.verifyAtMostOnce().reset().verifyNever();
    }

    @Test
    public void testVersion() throws ClassNotFoundException, SQLException {
        Driver driver = DriverManager.getDriver("sniffy:jdbc:h2:mem:");
        assertEquals(Constants.MAJOR_VERSION, driver.getMajorVersion());
        assertEquals(Constants.MINOR_VERSION, driver.getMinorVersion());
    }

    @Test
    public void testGetParentLogger() throws ClassNotFoundException, SQLException {
        Driver driver = DriverManager.getDriver("sniffy:jdbc:h2:mem:");
        try {
            driver.getParentLogger();
            fail();
        } catch (SQLFeatureNotSupportedException e) {
            assertNotNull(e);
        }
    }

    @Test
    public void testExecuteQueryStatement() throws ClassNotFoundException, SQLException {
        Spy spy = Sniffy.spy();
        try (Connection connection = DriverManager.getConnection("sniffy:jdbc:h2:mem:", "sa", "sa");
             Statement statement = connection.createStatement()) {
            statement.executeQuery("SELECT 1 FROM DUAL");
        }
        assertEquals(1, spy.executedStatements());
        spy.verifyAtMostOnce().reset().verifyNever();
    }

    @Test
    public void testExecutePreparedStatement() throws ClassNotFoundException, SQLException {
        Spy spy = Sniffy.spy();
        try (Connection connection = DriverManager.getConnection("sniffy:jdbc:h2:mem:", "sa", "sa");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT 1 FROM DUAL")) {
            preparedStatement.execute();
        }
        assertEquals(1, spy.executedStatements());
        spy.verifyAtMostOnce().reset().verifyNever();
    }

    @Test
    public void testExecuteQueryPreparedStatement() throws ClassNotFoundException, SQLException {
        Spy spy = Sniffy.spy();
        try (Connection connection = DriverManager.getConnection("sniffy:jdbc:h2:mem:", "sa", "sa");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT 1 FROM DUAL")) {
            preparedStatement.executeQuery();
        }
        assertEquals(1, spy.executedStatements());
        spy.verifyAtMostOnce().reset().verifyNever();
    }

    @Test
    public void testExecuteStatementThrowsException() throws ClassNotFoundException, SQLException {
        Spy spy = Sniffy.spy();
        try (Connection connection = DriverManager.getConnection("sniffy:jdbc:h2:mem:", "sa", "sa");
             Statement statement = connection.createStatement()) {
            statement.execute("SELECT 1 FROM DUAL_HUAL");
        } catch (Exception e) {
            assertFalse(InvocationTargetException.class.isAssignableFrom(e.getClass()));
            assertTrue(SQLException.class.isAssignableFrom(e.getClass()));
        }
        assertEquals(1, spy.executedStatements());
    }

    @Test
    public void getConnectionFromStatement() throws SQLException {
        try (Connection connection = DriverManager.getConnection("sniffy:jdbc:h2:mem:", "sa", "sa");
             Statement statement = connection.createStatement()) {
            assertEquals(connection, statement.getConnection());
        }
    }

    /**
     * This method is used in {@link #testCallStatement()} - do NOT remove it
     * @param arg any integer parameter
     * @return parameter multiplied by 2
     */
    @SuppressWarnings("unused")
    public static int timesTwo(int arg) {
        return arg * 2;
    }

    @Test
    public void testCallStatement() throws ClassNotFoundException, SQLException {
        try (Connection connection = DriverManager.getConnection("sniffy:jdbc:h2:mem:", "sa", "sa")) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("CREATE ALIAS IF NOT EXISTS TIMES_TWO FOR \"io.sniffy.MockDriverTest.timesTwo\"");
            }

            Spy spy = Sniffy.spy();
            try (CallableStatement callableStatement = connection.prepareCall("CALL TIMES_TWO(?)")) {
                callableStatement.setInt(1, 1);
                callableStatement.execute();
            }
            assertEquals(1, spy.executedStatements());
            spy.
                    verifyAtMostOnce().
                    reset().
                    verifyNever();
        }
    }

}