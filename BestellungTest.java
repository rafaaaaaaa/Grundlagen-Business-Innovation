
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows; //optional

/**
 * Klasse BestellungTest
 *
 * @author Alex Marchese
 * @version 04.12.2024
 */
public class BestellungTest {
    String nameTestClasse = "BestellungTest"; // Name der Testklasse

    /**
     * Konstruktor von BestellungTest
     */
    public BestellungTest() {
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
     * Testet die korrekte Initialisierung einer Bestellung
     */
    public void testeBestellung() {

        // Instanzierung einer Bestellung
        Bestellung testBestellung = new Bestellung(5, 7, 2);

        assertEquals(testBestellung.gibAnzahlStandardTueren(), 5);
        assertEquals(testBestellung.gibAnzahlPremiumTueren(), 7);
        assertEquals(testBestellung.gibBestellungsNr(), 2);

        // Testen von automatisch initialisierten Werten
        assertEquals(testBestellung.gibBestellBestaetigung(), false);
        assertEquals(testBestellung.gibBeschaffungsZeit(), -1);
        assertEquals(testBestellung.gibLieferzeit(), -1);

        System.out.println(
                "Test Bestellung mit Variableneingabe erfolgreich. Initialisierung mit Selbstdefinierten Variablen und Standardwerten funktioniert.");

    }

    @Test
    /**
     * Testet bestellungBestaetigen()
     */
    public void testeBestellungBestaetigen() {

        // Instanzierung einer Bestellung
        Bestellung testBestellung = new Bestellung(5, 7, 2);

        assertEquals(testBestellung.gibBestellBestaetigung(), false);
        testBestellung.bestellungBestaetigen();
        assertEquals(testBestellung.gibBestellBestaetigung(), true);

        System.out.println(
                "Test Methode bestellungBestaetigen erfolgreich.");

    }

    @Test
    /**
     * Testet setzeBeschaffungsZeit()
     */
    public void testeSetzeBeschaffungsZeit() {

        // Instanzierung einer Bestellung
        Bestellung testBestellung = new Bestellung(5, 7, 2);

        assertEquals(testBestellung.gibBeschaffungsZeit(), -1);
        testBestellung.setzeBeschaffungsZeit(2);
        assertEquals(testBestellung.gibBeschaffungsZeit(), 2);

        System.out.println("Test Setter setzeBeschaffungsZeit erfolgreich.");

    }
    
    @Test
    /**
     * Testet setzeLieferzeit()
     */
    public void testeSetzeLieferzeit() {

        // Instanzierung einer Bestellung
        Bestellung testBestellung = new Bestellung(5, 7, 2);

        assertEquals(testBestellung.gibLieferzeit(), -1);
        testBestellung.setzeLieferzeit(2);
        assertEquals(testBestellung.gibLieferzeit(), 2);

        System.out.println("Test Setter setzeLieferzeit erfolgreich.");

    }

    @Test // Optional
    /**
     * Test der Fehlerbehandlung (der Exceptions)
     */
    public void testeFehlerbehandlung() {

        // Optional -> wir haben Exceptions nicht zusammen gesehen

        // Negativwerte
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Bestellung(-1, 5, 1); // Standardtür Negativwert
        });
        assert (exception.getMessage().contains("Ungültige Bestellmenge. Kann nicht negativ sein."));

        exception = assertThrows(IllegalArgumentException.class, () -> {
            new Bestellung(5, -1, 2); // Premiumtür Negativwert
        });
        assert (exception.getMessage().contains("Ungültige Bestellmenge. Kann nicht negativ sein."));

        // Beide Werte von Türen Null
        exception = assertThrows(IllegalArgumentException.class, () -> {
            new Bestellung(0, 0, 3);
        });
        assert (exception.getMessage().contains("Die Bestellung muss mindestens ein Produkt enthalten."));

        // Zu hohe Bestellmenge
        exception = assertThrows(IllegalArgumentException.class, () -> {
            new Bestellung(11_000, 5, 4); // Standardtür hohe Bestellmenge
        });
        assert (exception.getMessage().contains("Bestellmenge ist zu gross. Maximal 10 Tausend pro Artikel."));

        exception = assertThrows(IllegalArgumentException.class, () -> {
            new Bestellung(5, 12_000, 5); // Premiumtür hohe Bestellmenge
        });
        assert (exception.getMessage().contains("Bestellmenge ist zu gross. Maximal 10 Tausend pro Artikel."));

    }
    
    @Test
    public void testSetzeAlleProdukteProduziert() {
    // Arrange
    Bestellung bestellung = new Bestellung(5, 3, 12345); // Beispiel mit 5 Standardtüren und 3 Premiumtüren

    // Act
    bestellung.setzeAlleProdukteProduziert();

    // Assert
    assertTrue(bestellung.gibAlleProdukteProduziert(), 
            "Der Status 'alleProdukteProduziert' sollte nach dem Aufruf von setzeAlleProdukteProduziert() auf true gesetzt sein.");
}

@Test
public void testGibAlleProdukteProduziert() {
    // Arrange
    Bestellung bestellung = new Bestellung(2, 1, 67890); // Beispiel mit 2 Standardtüren und 1 Premiumtür

    // Act & Assert
    // Anfangsstatus überprüfen
    assertFalse(bestellung.gibAlleProdukteProduziert(), 
            "Der Status 'alleProdukteProduziert' sollte standardmäßig false sein.");

    // Status setzen und überprüfen
    bestellung.setzeAlleProdukteProduziert();
    assertTrue(bestellung.gibAlleProdukteProduziert(), 
            "Der Status 'alleProdukteProduziert' sollte true sein, nachdem setzeAlleProdukteProduziert() aufgerufen wurde.");
}



}
