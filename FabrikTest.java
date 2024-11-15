
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Klasse FabrikTest
 *
 * @author Alex Marchese
 * @version 06.11.2024
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
        assertEquals(ersteBestellung.liefereBestellteProdukte().size(), 7);

        // Kontrolle, dass es genau 2 Standartüren und 5 Premiumtüren sind
        int anzahlStandardTueren = 0;
        int anzahlPremiumtueren = 0;

        for (Object produkt : ersteBestellung.liefereBestellteProdukte()) {
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
     * Testet, dass bei der Eingabe von unzulässigen Werten, keine Bestellung aufgegeben wird
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
}
