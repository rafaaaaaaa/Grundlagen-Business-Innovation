import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Die Testklasse für die Klasse Roboter.
 * 
 * @author Rafael Estermann
 * @version 15.12.2024
 */
public class RoboterTest {

    String nameTestClasse = "RoboterTest"; // Name der Testklasse

    /**
     * Anweisungen vor jedem Testlauf.
     */
    @BeforeEach
    public void setUp() {
        System.out.println("Testlauf " + nameTestClasse + " Start");
        System.out.println();
    }

    /**
     * Anweisungen nach jedem Testlauf.
     */
    @AfterEach
    public void tearDown() {
        System.out.println();
        System.out.println("Testlauf " + nameTestClasse + " Ende");
        System.out.println("------------------------");
    }

    /**
     * Testet, ob ein Produkt korrekt zur Warteschlange hinzugefügt wird.
     */
    @Test
    public void testFuegeProduktHinzu() {
        // Arrange
        Roboter roboter = new Holzbearbeitungs_Roboter("Holzbot");
        Produkt produkt = new Standardtuer();

        // Act
        roboter.fuegeProduktHinzu(produkt);

        // Assert
        assertEquals(1, roboter.gibWarteschlange().size(), "Die Warteschlange sollte ein Produkt enthalten.");
        assertEquals(produkt, roboter.gibWarteschlange().getFirst(),
                "Das erste Produkt in der Warteschlange sollte das hinzugefügte sein.");
    }

    /**
     * Testet, ob die Warteschlange mit der Zeit abgearbeitet wird.
     */
    @Test
    public void testWarteschlangeAbarbeiten() throws InterruptedException {
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
        assertTrue(roboter.gibWarteschlange().isEmpty(),
                "Die Warteschlange sollte leer sein, nachdem sie abgearbeitet wurde.");
    }

    /**
     * Testet, ob die Umschaltzeit korrekt berücksichtigt wird.
     */
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

    /**
     * Testet, dass keine Umschaltzeit für gleichartige Produkte anfällt.
     */
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

    /**
     * Testet, ob der Name des Roboters korrekt zurückgegeben wird.
     */
    @Test
    public void testGibName() {
        // Arrange
        Roboter roboter = new Holzbearbeitungs_Roboter("Holzbot");

        // Act
        String name = roboter.gibName();

        // Assert
        assertEquals("Holzbot", name, "Die Methode gibName() sollte den korrekten Namen zurückgeben.");
    }

    /**
     * Testet, ob die Produktionszeit korrekt gesetzt wird.
     */
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

    /**
     * Testet, ob eine Ausnahme bei negativer Produktionszeit geworfen wird.
     */
    @Test
    public void testSetzeProduktionszeitThrowsExceptionForNegativeValues() {
        // Arrange
        Roboter roboter = new Roboter("TestBot");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> roboter.setzeProduktionsZeit(-1), "Negative Produktionszeiten sollten nicht erlaubt sein.");
        assertEquals("Produktionszeit darf nicht negativ sein.", exception.getMessage());
    }
}
