
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Klasse FabrikTest
 *
 * @author Rafael Estermann
 * @version 23.11.2024
 */
public class FabrikTest {
    String nameTestClasse = "FabrikTest"; // Name der Testklasse
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out; // Save the original System.out

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
        
        System.setOut(new PrintStream(outputStream)); // Redirect System.out
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

        System.out.println("Test Bestellung mit erlaubten Werten. Produkte wurden bestellt");

    }
    
    
    @Test
    /**
     * Testet, ob bei einer riesigen Bestellung, die Beschaffungszeit und Lieferzeit korrekt berechnet wird.
     */
    public void testeBestellungAufgaben__WhenBestandZuNiederig_BerechneZeiten() {

        // Instanzierung einer Fabrik
        Fabrik testFabrik = new Fabrik();
        testFabrik.bestellungAufgeben(10000, 10000);
                
        int expectedBeschaffungszeit = 2;
        int expectedStandardLieferzeit = 1;
        float expectedProduktionszeit = (float)(10000 * Standardtuer.gibProduktionszeit() + 10000 * Premiumtuer.gibProduktionszeit()) / 1440;
        float expectedLieferzeit = expectedProduktionszeit + expectedStandardLieferzeit + expectedBeschaffungszeit;

        assertEquals(testFabrik.gibBestellungen().get(0).gibBeschaffungsZeit(), expectedBeschaffungszeit);
        assertEquals(testFabrik.gibBestellungen().get(0).gibLieferzeit(), expectedLieferzeit);

        System.out.println("Beschaffungszeit und Lieferzeit wurden korrekt berechnet");

    }
    
     @Test
    /**
     * Testet, ob bei einer kleinen Bestellung, die Beschaffungszeit und Lieferzeit korrekt berechnet wird.
     */
    public void testeBestellungAufgaben__WhenBestandZuGenügend_BerechneZeiten() {

        // Instanzierung einer Fabrik
        Fabrik testFabrik = new Fabrik();
        testFabrik.bestellungAufgeben(1, 1);
                
        int expectedBeschaffungszeit = 0;
        int expectedStandardLieferzeit = 1;
        float expectedProduktionszeit = (float)(1 * Standardtuer.gibProduktionszeit() + 1 * Premiumtuer.gibProduktionszeit()) / 1440;
        float expectedLieferzeit = expectedProduktionszeit + expectedStandardLieferzeit + expectedBeschaffungszeit;

        assertEquals(testFabrik.gibBestellungen().get(0).gibBeschaffungsZeit(), expectedBeschaffungszeit);
        assertEquals(testFabrik.gibBestellungen().get(0).gibLieferzeit(), expectedLieferzeit);

        System.out.println("Beschaffungszeit und Lieferzeit wurden korrekt berechnet");

    }
    
    @Test
    /**
     * Testet, ob beim Eingang einer Bestellung automatisch geprüft wird, ob der Lagerbestand "minimal" ist
     */
    public void testeLagerbestandAuffuellen_WhenBestandMinimial_AuffuellenBeiBestellungEingang() {

        // Instanzierung einer Fabrik
        Fabrik testFabrik = new Fabrik();
        testFabrik.bestellungAufgeben(1, 1);
        
        // Lese den Output
        String consoleOutput = outputStream.toString();

        // Verify the console output contains the expected text
        assertTrue(consoleOutput.contains("Lieferung erfolgreich"));              
      
    }
    
        @Test
    /**
     * Testet, ob beim Eingang einer Bestellung automatisch geprüft wird, ob der Lagerbestand "minimal" ist
     */
    public void testeLagerbestandAuffuellen_WhenBestandNichtMinimial_NichtAuffuellenBeiBestellungEingang() {

        // Instanzierung einer Fabrik
        Fabrik testFabrik = new Fabrik();
        testFabrik.lagerAuffuellen();
        outputStream.reset();
        testFabrik.bestellungAufgeben(1, 1);
        
        // Lese den Output
        String consoleOutput = outputStream.toString();

        // Verify the console output contains the expected text
        assertFalse(consoleOutput.contains("Lieferung erfolgreich"));              
      
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
