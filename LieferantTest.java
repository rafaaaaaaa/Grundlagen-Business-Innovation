

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class LieferantTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
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
        assertTrue(result, "Die Bestellung sollte erfolgreich sein, wenn alle Werte 0 sind.");
    }
}
