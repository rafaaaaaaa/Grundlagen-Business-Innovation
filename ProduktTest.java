import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Die Testklasse für die Klasse Produkt.
 *
 * @author Rafael Estermann
 * @version 15.12.2024
 */
public class ProduktTest {

    String nameTestClasse = "ProduktTest"; // Name der Testklasse

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
     * Testet die Initialisierung und den Getter für den Zustand eines Produkts.
     */
    @Test
    public void testeProduktGetter() {
        // Arrange
        Produkt testProdukt = new Produkt();

        // Assert
        assertEquals(0, testProdukt.aktuellerZustand(),
                "Der Zustand des Produkts sollte nach der Initialisierung 0 sein.");
    }

    /**
     * Testet den Setter für den Zustand eines Produkts.
     */
    @Test
    public void testeProduktSetter() {
        // Arrange
        Produkt testProdukt = new Produkt();

        // Act
        testProdukt.zustandAendern(2);

        // Assert
        assertEquals(2, testProdukt.aktuellerZustand(),
                "Der Zustand des Produkts sollte nach dem Setzen auf 2 geändert sein.");
    }

    /**
     * Testet, ob ein Roboter korrekt in den Produktionsablauf des Produkts
     * hinzugefügt wird.
     */
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

    /**
     * Testet, ob der Zustand eines Produkts nach Abschluss aller Produktionsstationen
     * korrekt auf 2 gesetzt wird.
     */
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
