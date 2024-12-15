import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HolzbearbeitungsRoboterTest {

    @Test
    public void testStandardtuerProductionTime() {
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
    public void testPremiumtuerProductionTime() {
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
