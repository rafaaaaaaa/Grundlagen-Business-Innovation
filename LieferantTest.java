import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class LieferantTest.
 *
 * @author  Rafael Estermann
 * @version 23.11.2024
 */
public class LieferantTest
{
     @Test
    public void testWareBestellenErfolgreich() {
        Lieferant lieferant = new Lieferant();
        boolean result = lieferant.wareBestellen(10, 5, 3, 2, 1);
        assertTrue(result, "Die Bestellung sollte erfolgreich sein, wenn alle Werte >= 0 sind.");
    }

    @Test
    public void testWareBestellenFehlgeschlagenNegativerWert() {
        Lieferant lieferant = new Lieferant();
        boolean result = lieferant.wareBestellen(-1, 5, 3, 2, 1);
        assertFalse(result, "Die Bestellung sollte fehlschlagen, wenn ein Wert negativ ist.");
    }

    @Test
    public void testWareBestellenAlleNull() {
        Lieferant lieferant = new Lieferant();
        boolean result = lieferant.wareBestellen(0, 0, 0, 0, 0);
        assertFalse(result, "Die Bestellung sollte erfolgreich sein, wenn alle Werte 0 sind.");
    }
}
