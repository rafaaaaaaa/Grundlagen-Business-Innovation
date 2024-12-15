import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class RoboterTest.
 *
 * @author  Rafael Estermann
 * @version 15.12.2024
 */
public class RoboterTest
{    
    @Test
    public void testFuegeProduktHinzu() {
    // Arrange
    Roboter roboter = new Holzbearbeitungs_Roboter("Holzbot");
    Produkt produkt = new Standardtuer();

    // Act
    roboter.fuegeProduktHinzu(produkt);

    // Assert
    assertEquals(1, roboter.gibWarteschlange().size(), "Die Warteschlange sollte ein Produkt enthalten.");
    assertEquals(produkt, roboter.gibWarteschlange().getFirst(), "Das erste Produkt in der Warteschlange sollte das hinzugefügte sein.");
    }
    
    @Test
    public  void testWarteschlangeAbarbeiten() throws InterruptedException {
    // Arrange
    Roboter roboter = new Holzbearbeitungs_Roboter("Holzbot");
    Produkt produkt1 = new Standardtuer();
    Produkt produkt2 = new Premiumtuer();
    roboter.fuegeProduktHinzu(produkt1);
    roboter.fuegeProduktHinzu(produkt2);

    // Act
    roboter.start();
    Thread.sleep(2000); // Warten, damit der Roboter Zeit hat, die Produkte abzuarbeiten
    roboter.interrupt(); // Den Thread sauber beenden

    // Assert
    assertTrue(roboter.gibWarteschlange().isEmpty(), "Die Warteschlange sollte leer sein, nachdem sie abgearbeitet wurde.");
    }
    
    @Test
    public void testUmschaltzeit() throws InterruptedException {
    // Arrange
    Holzbearbeitungs_Roboter roboter = new Holzbearbeitungs_Roboter("Holzbot");
    Produkt produkt1 = new Premiumtuer();
    Produkt produkt2 = new Standardtuer();
    roboter.fuegeProduktHinzu(produkt1);
    roboter.fuegeProduktHinzu(produkt2);

    // Act
    roboter.start();
    long startTime = System.currentTimeMillis();
    Thread.sleep(3000); // Genug Zeit, um beide Produkte zu verarbeiten
    roboter.interrupt();

    // Assert
    long elapsedTime = System.currentTimeMillis() - startTime;
    long expectedTime = 1000 + 500 + 166 + 1000; // 1s bis Loop gestartet + Produktionszeit Premium + Standard + Umschaltzeit
    long tolerance = 500; // Zulässige Abweichung

    assertTrue(Math.abs(elapsedTime - expectedTime) <= tolerance,
            "Die Zeitdifferenz sollte der erwarteten Umschaltzeit entsprechen.");
    }
    
    @Test
    public void testKeineUmschaltzeit() throws InterruptedException {
    // Arrange
    Holzbearbeitungs_Roboter roboter = new Holzbearbeitungs_Roboter("Holzbot");
    Produkt produkt1 = new Premiumtuer();
    Produkt produkt2 = new Premiumtuer();
    roboter.fuegeProduktHinzu(produkt1);
    roboter.fuegeProduktHinzu(produkt2);

    // Act
    roboter.start();
    long startTime = System.currentTimeMillis();
    Thread.sleep(2000); // Genug Zeit, um beide Produkte zu verarbeiten
    roboter.interrupt();

    // Assert
    long elapsedTime = System.currentTimeMillis() - startTime;
    long expectedTime = 1000 + 500 + 500; // 1s bis Loop gestartet + Produktionszeit Premium + Premium
    long tolerance = 500; // Zulässige Abweichung

    assertTrue(Math.abs(elapsedTime - expectedTime) <= tolerance,
            "Die Zeitdifferenz sollte keine Umschaltzeit enthalten.");
    }
    
    @Test
    public void testGibName() {
        // Arrange
        Roboter roboter = new Holzbearbeitungs_Roboter("Holzbot");
    
        // Act
        String name = roboter.gibName();
    
        // Assert
        assertEquals("Holzbot", name, "Die Methode gibName() sollte den korrekten Namen zurückgeben.");
    }
    
     @Test
    public void testSetzeProduktionszeitWithValidValues() {
        // Arrange
        Roboter roboter = new Roboter("TestBot");

        // Act
        roboter.setzeProduktionsZeit(500);
        int produktionsZeit = roboter.gibProduktionszeit();

        // Assert
        assertEquals(500, produktionsZeit, "Die Produktionszeit sollte korrekt auf 500 gesetzt werden.");
    }

    @Test
    public void testSetzeProduktionszeitThrowsExceptionForNegativeValues() {
        // Arrange
        Roboter roboter = new Roboter("TestBot");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> roboter.setzeProduktionsZeit(-1),
            "Negative Produktionszeiten sollten nicht erlaubt sein."
        );
        assertEquals("Produktionszeit darf nicht negativ sein.", exception.getMessage());
    }

}
