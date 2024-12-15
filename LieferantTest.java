import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Die Testklasse für die Klasse Lieferant.
 * 
 * @author Rafael Estermann
 * @version 15.12.2024
 */
public class LieferantTest {

    String nameTestClasse = "LieferantTest"; // Name der Testklasse

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
     * Testet, ob die Methode wareBestellen() erfolgreich eine Bestellung bearbeitet.
     */
    @Test
    public void testWareBestellenErfolgreich() {
        // Arrange
        Lieferant lieferant = new Lieferant();

        // Act
        boolean result = lieferant.wareBestellen(2, 4, 50, 3, 2);

        // Assert
        assertTrue(result, "Die Bestellung sollte erfolgreich bearbeitet werden.");
        System.out.println("Test der Methode wareBestellen() erfolgreich.");
    }

    /**
     * Testet, ob die Methode wareBestellen() eine Lieferzeit von 48 Sekunden einhält.
     */
    @Test
    public void testWareBestellenMit48SekundenLieferzeit() {
        // Arrange
        Lieferant lieferant = new Lieferant();
        int tolerance = 500; // Toleranz in Millisekunden (+/- 0.5 Sekunden)
        long expectedTime = 48000; // 48 Sekunden in Millisekunden

        // Act
        long startTime = System.currentTimeMillis();
        boolean result = lieferant.wareBestellen(10, 20, 30, 40, 50);
        long elapsedTime = System.currentTimeMillis() - startTime;

        // Assert
        assertTrue(result, "Die Bestellung sollte erfolgreich sein.");
        assertTrue(
            elapsedTime >= expectedTime - tolerance && elapsedTime <= expectedTime + tolerance,
            "Die Lieferzeit sollte im Bereich von " + (expectedTime - tolerance) + " ms bis " +
            (expectedTime + tolerance) + " ms liegen. Gemessene Zeit: " + elapsedTime + " ms."
        );
    }
}
