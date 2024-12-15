
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Klasse ProduktTest
 *
 * @author Rafael Estermann
 * @version 15.12.2024
 */
public class ProduktTest {
    String nameTestClasse = "ProduktTest"; // Name der Testklasse

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
     * Testet die Initialisierung und den Getter
     */
    public void testeProduktGetter() {

        Produkt testProdukt = new Produkt();
        assertEquals(testProdukt.aktuellerZustand(), 0);

        System.out.println("Test Produkt Getter und Initialisierung des Zustandes funktionieren.");

    }

    @Test
    /**
     * Testet den Setter
     */
    public void testeProduktSetter() {

        Produkt testProdukt = new Produkt();
        testProdukt.zustandAendern(2);
        assertEquals(testProdukt.aktuellerZustand(), 2);

        System.out.println("Test Produkt Setter funktioniert.");

    }
    
    @Test
    public void testRoboterInProduktionsablaufHinzufuegen() {
    // Arrange
    Produkt produkt = new Produkt();
    Roboter roboter = new Holzbearbeitungs_Roboter("Holzbot");

    // Act
    produkt.setzteProduktionsAblauf(roboter);

    // Assert
    assertTrue(produkt.gibProduktionsAblauf().contains(roboter), 
            "Der Roboter sollte in der Produktionsablauf-Liste enthalten sein.");
    assertEquals(1, produkt.gibProduktionsAblauf().size(),
            "Die Produktionsablauf-Liste sollte genau einen Eintrag enthalten.");
    }
    
    @Test
    public void testZustandAendernNachLetzterProduktionsStation() {
    // Arrange
    Produkt produkt = new Produkt();
    Roboter roboter1 = new Holzbearbeitungs_Roboter("Holzbot");
    Roboter roboter2 = new Holzbearbeitungs_Roboter("Schraubenbot");

    // Produktionsstationen hinzufügen
    produkt.setzteProduktionsAblauf(roboter1);
    produkt.setzteProduktionsAblauf(roboter2);

    // Simuliere Abarbeitung der Produktionsstationen
    produkt.naechsteProduktionsStation(); // Entfernt roboter1
    produkt.naechsteProduktionsStation(); // Entfernt roboter2

    // Act
    produkt.naechsteProduktionsStation(); // Keine weiteren Roboter, Zustand sollte 2 sein

    // Assert
    assertEquals(2, produkt.aktuellerZustand(),
            "Der Zustand sollte 2 sein, nachdem die letzte Produktionsstation abgearbeitet wurde.");
}


}
