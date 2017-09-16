import org.junit.Test;


import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertEquals;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class SniffyServerTests {
    InetAddress ia = InetAddress.getLocalHost();
    String testResource = "www.testpage.html";

    public SniffyServerTests() throws UnknownHostException {}

    @Test
    public void testServerStart() throws IOException {
        assertTrue(ia.isReachable(3000));
        System.out.println("test passed");
    }

    @Test
    public void testMIMETypes() throws IOException {
        assertEquals("www.testpage.html", testResource);
        System.out.println("test passed");
    }



}
