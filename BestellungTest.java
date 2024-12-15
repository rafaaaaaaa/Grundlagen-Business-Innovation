import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Klasse BestellungTest
 *
 * @author Rafael Estermann
 * @version 15.12.2024
 */
public class BestellungTest {
    String nameTestClasse = "BestellungTest"; // Name der Testklasse

    /**
     * Anweisungen vor jedem Testlauf
     */
    @BeforeEach
    public void setUp() {
        System.out.println("Testlauf " + nameTestClasse + " Start");
        System.out.println();
    }

    /**
     * Anweisungen nach jedem Testlauf
     */
    @AfterEach
    public void tearDown() {
        System.out.println();
        System.out.println("Testlauf " + nameTestClasse + " Ende");
        System.out.println("------------------------");
    }

    @Test
    /**
     * Testet die korrekte Initialisierung einer Bestellung
     */
    public void testeBestellung() {
        Bestellung testBestellung = new Bestellung(5, 7, 2);

        assertEquals(5, testBestellung.gibAnzahlStandardTueren(), "Anzahl der Standardtüren ist inkorrekt.");
        assertEquals(7, testBestellung.gibAnzahlPremiumTueren(), "Anzahl der Premiumtüren ist inkorrekt.");
        assertEquals(2, testBestellung.gibBestellungsNr(), "Bestellnummer ist inkorrekt.");
        assertFalse(testBestellung.gibBestellBestaetigung(), "Bestellung sollte initial nicht bestätigt sein.");
        assertEquals(-1, testBestellung.gibBeschaffungsZeit(), "Initiale Beschaffungszeit sollte -1 sein.");
        assertEquals(-1, testBestellung.gibLieferzeit(), "Initiale Lieferzeit sollte -1 sein.");
    }

    @Test
    /**
     * Testet bestellungBestaetigen()
     */
    public void testeBestellungBestaetigen() {
        Bestellung testBestellung = new Bestellung(5, 7, 2);

        assertFalse(testBestellung.gibBestellBestaetigung(), "Bestellung sollte initial nicht bestätigt sein.");
        testBestellung.bestellungBestaetigen();
        assertTrue(testBestellung.gibBestellBestaetigung(), "Bestellung sollte nach der Bestätigung als bestätigt markiert sein.");
    }

    @Test
    /**
     * Testet setzeBeschaffungsZeit()
     */
    public void testeSetzeBeschaffungsZeit() {
        Bestellung testBestellung = new Bestellung(5, 7, 2);

        assertEquals(-1, testBestellung.gibBeschaffungsZeit(), "Initiale Beschaffungszeit sollte -1 sein.");
        testBestellung.setzeBeschaffungsZeit(2);
        assertEquals(2, testBestellung.gibBeschaffungsZeit(), "Beschaffungszeit wurde nicht korrekt gesetzt.");
    }

    @Test
    /**
     * Testet setzeLieferzeit()
     */
    public void testeSetzeLieferzeit() {
        Bestellung testBestellung = new Bestellung(5, 7, 2);

        assertEquals(-1, testBestellung.gibLieferzeit(), "Initiale Lieferzeit sollte -1 sein.");
        testBestellung.setzeLieferzeit(2);
        assertEquals(2, testBestellung.gibLieferzeit(), "Lieferzeit wurde nicht korrekt gesetzt.");
    }

    @Test
    /**
     * Testet die Fehlerbehandlung bei ungültigen Bestellmengen
     */
    public void testeFehlerbehandlung() {
        assertThrows(IllegalArgumentException.class, () -> new Bestellung(-1, 5, 1), "Negative Standardtüren sollten nicht erlaubt sein.");
        assertThrows(IllegalArgumentException.class, () -> new Bestellung(5, -1, 2), "Negative Premiumtüren sollten nicht erlaubt sein.");
        assertThrows(IllegalArgumentException.class, () -> new Bestellung(0, 0, 3), "Eine Bestellung ohne Produkte sollte nicht erlaubt sein.");
        assertThrows(IllegalArgumentException.class, () -> new Bestellung(11_000, 5, 4), "Zu viele Standardtüren sollten nicht erlaubt sein.");
        assertThrows(IllegalArgumentException.class, () -> new Bestellung(5, 12_000, 5), "Zu viele Premiumtüren sollten nicht erlaubt sein.");
    }

    @Test
    /**
     * Testet setzeAlleProdukteProduziert()
     */
    public void testSetzeAlleProdukteProduziert() {
        Bestellung bestellung = new Bestellung(5, 3, 12345);

        bestellung.setzeAlleProdukteProduziert();
        assertTrue(bestellung.gibAlleProdukteProduziert(), "Status 'alleProdukteProduziert' sollte true sein.");
    }

    @Test
    /**
     * Testet gibAlleProdukteProduziert()
     */
    public void testGibAlleProdukteProduziert() {
        Bestellung bestellung = new Bestellung(2, 1, 67890);

        assertFalse(bestellung.gibAlleProdukteProduziert(), "Status 'alleProdukteProduziert' sollte initial false sein.");
        bestellung.setzeAlleProdukteProduziert();
        assertTrue(bestellung.gibAlleProdukteProduziert(), "Status 'alleProdukteProduziert' sollte nach dem Setzen true sein.");
    }
}
