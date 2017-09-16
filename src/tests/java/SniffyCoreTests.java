import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertEquals;


public class SniffyCoreTests {

    @Test
    public void testTimerStatsObjectAfterExecution(){
        String n = "hi";
        assertNotNull(n);
    }

    @Test
    public void testTimerCountAfterExecution(){
        assertEquals(11, 5+6);
    }

}
