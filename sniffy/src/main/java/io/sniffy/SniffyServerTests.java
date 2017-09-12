package io.sniffy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class SniffyServerTests {
    SniffyAgent sa = new SniffyAgent();
    InetAddress ia = InetAddress.getLocalHost();

    public SniffyServerTests() throws UnknownHostException {}

    @Test
    public void testServerStart() throws IOException {
        sa.startServer(5555);
        assertTrue(ia.isReachable(3000));
    }

    @Test
    public void testMIMETypes() throws IOException {
        // add tests for MIME types
    }



}
