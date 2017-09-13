package io.sniffy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.lang.ref.WeakReference;

import static org.junit.jupiter.api.Assertions.*;

public class SniffyCoreTests {

    Sniffy s = new Sniffy();
    Spy spy = new Spy();

    @Test
    public void testTimerStatsObjectAfterExecution(){
        s.logSqlTime("WHERE", 10);
        assertNotNull(s.getGlobalSqlStats());
    }

    @Test
    public void testTimerCountAfterExecution(){
        s.logSqlTime("WHERE", 10);
        assertNotEquals(11, s.getGlobalSqlStats());
    }

    @Test
    public void testSpyObject(){
        assertNotNull(s.getSpy(spy));
    }



}
