

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class Verpackungs_RoboterTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class Verpackungs_RoboterTest
{    
    @Test
    public void testNameAssignment() {
        String testname = "Verpackung Roboter";
        Montage_Roboter roboter = new Montage_Roboter(testname);
        assertEquals(testname, roboter.gibName(), "Der Name des Roboter sollte eigentlich " + testname + " sein.");
    }
}
