

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class Holzbearbeitungs_RoboterTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class Holzbearbeitungs_RoboterTest
{    
    @Test
    public void testNameAssignment() {
        String testname = "Holzbearbeitung Roboter";
        Montage_Roboter roboter = new Montage_Roboter(testname);
        assertEquals(testname, roboter.gibName(), "Der Name des Roboter sollte eigentlich " + testname + " sein.");
    }
    
    @Test
    void testStandardtuerProductionTime() {
        // Arrange
        Holzbearbeitungs_Roboter roboter = new Holzbearbeitungs_Roboter("Holzbot");
        Produkt standardtuer = new Standardtuer(); // Assuming Standardtuer is a subclass of Produkt
        long expectedTime = 166; // Expected sleep time in ms
        long tolerance = 50; // Allowable deviation in ms

        // Act
        long startTime = System.currentTimeMillis();
        roboter.produziereProdukt(standardtuer);
        long endTime = System.currentTimeMillis();

        // Assert
        long elapsedTime = endTime - startTime;
        assertTrue(elapsedTime >= expectedTime - tolerance && elapsedTime <= expectedTime + tolerance,
                "Elapsed time for Standardtuer production is outside the expected range: " + elapsedTime + " ms");
    }

    @Test
    void testPremiumtuerProductionTime() {
        // Arrange
        Holzbearbeitungs_Roboter roboter = new Holzbearbeitungs_Roboter("Holzbot");
        Produkt premiumtuer = new Premiumtuer(); // Assuming Premiumtuer is a subclass of Produkt
        long expectedTime = 500; // Expected sleep time in ms
        long tolerance = 50; // Allowable deviation in ms

        // Act
        long startTime = System.currentTimeMillis();
        roboter.produziereProdukt(premiumtuer);
        long endTime = System.currentTimeMillis();

        // Assert
        long elapsedTime = endTime - startTime;
        assertTrue(elapsedTime >= expectedTime - tolerance && elapsedTime <= expectedTime + tolerance,
                "Elapsed time for Premiumtuer production is outside the expected range: " + elapsedTime + " ms");
    }
}
