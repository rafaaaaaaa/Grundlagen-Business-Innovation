
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Klasse PremiumtuerTest
 *
 * @author Rafael Esterman
 * @version 23.11.2024
 */
public class PremiumtuerTest {
    String nameTestClasse = "PremiumtuerTest"; // Name der Testklasse

    /**
     * Konstruktor von PremiumtuerTest
     */
    public PremiumtuerTest() {
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
     * Testet die Getters
     */
    public void testePremiumtuerGetters() {
        assertEquals(Premiumtuer.gibHolzeinheiten(), 4);
        assertEquals(Premiumtuer.gibSchrauben(), 5);
        assertEquals(Premiumtuer.gibGlaseinheiten(), 5);
        assertEquals(Premiumtuer.gibFarbeinheiten(), 1);
        assertEquals(Premiumtuer.gibKartoneinheiten(), 5);
        assertEquals(Premiumtuer.gibProduktionszeit(), 30);

        System.out.println("Getters von Premiumtür erfolgreich");
    }

    @Test
    /**
     * Testet die Initialisierung und den Getter von der Oberklasse Produkt
     */
    public void testeProduktGetter() {

        Premiumtuer testPremiumtuer = new Premiumtuer();
        assertEquals(testPremiumtuer.aktuellerZustand(), 0);

        System.out.println("Test Produkt Getter und Initialisierung des Zustandes funktionieren.");

    }

    @Test
    /**
     * Testet den Setter von der Oberklasse Produkt
     */
    public void testeProduktSetter() {

        Premiumtuer testPremiumtuer = new Premiumtuer();
        testPremiumtuer.zustandAendern(2);
        assertEquals(testPremiumtuer.aktuellerZustand(), 2);

        System.out.println("Test Produkt Setter funktioniert.");

    }

}
