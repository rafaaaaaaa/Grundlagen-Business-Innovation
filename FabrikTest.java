
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Klasse FabrikTest
 *
 * @author Alex Marchese
 * @version 04.12.2024
 */
public class FabrikTest {
    String nameTestClasse = "FabrikTest"; // Name der Testklasse

    /**
     * Konstruktor von FabrikTest
     */
    public FabrikTest() {
    }

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
     * Testet die Aufgabe einer Bestellung mit zugelassenen Werten
     */
    public void testeBestellung() {

        // Instanzierung einer Fabrik
        Fabrik testFabrik = new Fabrik();
        testFabrik.bestellungAufgeben(2, 5);

        Bestellung ersteBestellung = testFabrik.gibBestellungen().get(0);

        // Überprüfung, dass die Arraylist die Produkte enthält. Es müssen genau 7 sein
        assertEquals(ersteBestellung.gibBestellteProdukte().size(), 7);

        // Kontrolle, dass es genau 2 Standartüren und 5 Premiumtüren sind
        int anzahlStandardTueren = 0;
        int anzahlPremiumtueren = 0;

        for (Object produkt : ersteBestellung.gibBestellteProdukte()) {
            if (produkt instanceof Standardtuer) {
                anzahlStandardTueren++;
            } else if (produkt instanceof Premiumtuer) {
                anzahlPremiumtueren++;
            }
        }

        assertEquals(2, anzahlStandardTueren);
        assertEquals(5, anzahlPremiumtueren);

        System.out.println(
                "Test Bestellung mit erlaubten Werten. Produkte wurden bestellt");

    }

    @Test
    /**
     * Testet, dass bei der Eingabe von unzulässigen Werten, keine Bestellung
     * aufgegeben wird
     */
    public void testeBestellungFalsch() {

        // Instanzierung einer Fabrik
        Fabrik testFabrik = new Fabrik();
        // Beide Werte von Türen Null
        testFabrik.bestellungAufgeben(0, 0);
        // Zu hohe Bestellmenge
        testFabrik.bestellungAufgeben(15_000, 0);
        // Ein Negativwert
        testFabrik.bestellungAufgeben(-5, 0);

        // Kontrolle, dass keine Bestellung durchgegangen ist
        assertEquals(testFabrik.gibBestellungen().size(), 0);

        System.out.println(
                "Test Bestellung mit unerlaubten Argumenten. Nichts wurde bestellt");

    }

    @Test
    /**
     * Testet, dass die in Abgabe 2 neu implementierten Befehle korrket
     * funktionieren
     */
    public void testeBestellungAufgeben() {

        // Instanzierung einer Fabrik
        Fabrik testFabrik = new Fabrik();

        /// Genügende Materialien auf Lager (bei 2 StandardT und 5 PremiumT der Fall)

        testFabrik.bestellungAufgeben(2, 5);

        Bestellung ersteBestellung = testFabrik.gibBestellungen().get(0);

        // Kontrolle der Beschaffungszeit
        assertEquals(testFabrik.gibLager().gibBeschaffungsZeit(ersteBestellung), 0);

        // Kontrolle, dass das Lager nicht aufgefüllt wird
        assertEquals(testFabrik.gibLagerAuffuellungen(), 0);

        // Kontrolle der Lieferzeit
        assertEquals(Math.round(ersteBestellung.gibLieferzeit() * 100) / 100f, 1.12f); // 0 + (10 * 2 + 30 * 5) / (60 *
                                                                                       // 24) + 1 -> runden auf 2
                                                                                       // Kommastellen: 1.12
        // Kontrolle, dass Bestellung bestätigt wird
        assertTrue(ersteBestellung.gibBestellBestaetigung());

        /// Ungenügende Materialien auf Lager (bei 21 PremiumT der Fall) -> bei
        /// den Glaseinheiten: 100 (auf Lager) - 5 * 21 = -5 => nachbestellen.
        /// N.B. eingentlich werden bei der ersten Bestellung schon 25 Glaseinheiten
        /// verwendet. Da noch nicht produziert wird (Abgabe 3), werden sie auch nicht
        /// abgezogen

        testFabrik.bestellungAufgeben(0, 21);

        Bestellung zweiteBestellung = testFabrik.gibBestellungen().get(1);

        // Kontrolle der Beschaffungszeit
        assertEquals(testFabrik.gibLager().gibBeschaffungsZeit(zweiteBestellung), 2);

        // Kontrolle, dass das Lager aufgefüllt wird
        assertEquals(testFabrik.gibLagerAuffuellungen(), 1);

        // Kontrolle der Lieferzeit
        assertEquals(Math.round(zweiteBestellung.gibLieferzeit() * 100) / 100f, 3.44f); // 2 + (30 * 21) / (60 *
                                                                                        // 24) + 1 -> runden auf 2
                                                                                        // Kommastellen: 3.44
        // Kontrolle, dass Bestellung bestätigt wird
        assertTrue(zweiteBestellung.gibBestellBestaetigung());
        
        System.out.println(
                "Test für Abgabe 2 angepasste Methode bestellungAufgeben()");
    }
}
