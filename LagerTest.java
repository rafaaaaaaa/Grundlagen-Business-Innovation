import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;

/**
 * Klasse LagerTest
 *
 * @author Alex Marchese
 * @version 04.12.2024
 */
public class LagerTest {
    String nameTestClasse = "LagerTest"; // Name der Testklasse

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
     * Testet gibBeschaffungsZeit()
     */
    public void testeGibBeschaffungsZeit() {

        
        // Instanzierung eines Lagers und einer Bestellung
        ExecutorService executorService =  Executors.newSingleThreadExecutor();
        Lager testLager = new Lager(executorService);
        Bestellung testBestellung1 = new Bestellung(5, 7, 2); // Genügende Materialien auf Lager
        Bestellung testBestellung2 = new Bestellung(5, 21, 3); // Ungenügende Materialien auf Lager

        assertEquals(testLager.gibBeschaffungsZeit(testBestellung1), 0);
        assertEquals(testLager.gibBeschaffungsZeit(testBestellung2), 2);

        System.out.println(
                "Test Methode gibBeschaffungsZeit erfolgreich.");

    }
    
      @Test
    public void testLagerAuffuellenTakes48Seconds() {
        // Assuming you have an instance of Lager (or the class containing lagerAuffuellen)
        ExecutorService executorService =  Executors.newSingleThreadExecutor();
        Lager testLager = new Lager(executorService);
        
        // Record the start time
        long startTime = System.currentTimeMillis();

        // Call the lagerAuffuellen method (this is the method you want to test)
        testLager.lagerAuffuellen();

        // Record the end time
        long endTime = System.currentTimeMillis();

        // Calculate the difference in time
        long elapsedTime = endTime - startTime;

        // Since 48 seconds are simulated as 48 milliseconds, check if it's within the expected range
        assertTrue(elapsedTime >= 48000 - 100 && elapsedTime <= 48000 + 100,
                "Lagerauffuellen took " + elapsedTime + " milliseconds, expected around 48 milliseconds");
    }  
    
      @Test
    public void testWareLiefernReduziertLagerbestand() {
        // Arrange
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Lager lager = new Lager(executorService);

        // Erstelle eine Bestellung mit 2 Standardtüren und 1 Premiumtür
        ArrayList<Produkt> produkte = new ArrayList<>();
        produkte.add(new Standardtuer());
        produkte.add(new Standardtuer());
        produkte.add(new Premiumtuer());

        Bestellung bestellung = new Bestellung(2, 1, 12345) {
            @Override
            public ArrayList<Produkt> gibBestellteProdukte() {
                return produkte;
            }
        };

        // Initiale Lagerbestände
        int initialHolzeinheiten = 1000;
        int initialSchrauben = 5000;
        int initialFarbeeinheiten = 1000;
        int initialKartoneinheiten = 1000;
        int initialGlaseinheiten = 100;

        // Materialien pro Tür (Annahmen)
        int holzProStandardtuer = Standardtuer.gibHolzeinheiten();
        int schraubenProStandardtuer = Standardtuer.gibSchrauben();
        int farbeProStandardtuer = Standardtuer.gibFarbeinheiten();
        int kartonProStandardtuer = Standardtuer.gibKartoneinheiten();

        int holzProPremiumtuer = Premiumtuer.gibHolzeinheiten();
        int schraubenProPremiumtuer = Premiumtuer.gibSchrauben();
        int farbeProPremiumtuer = Premiumtuer.gibFarbeinheiten();
        int kartonProPremiumtuer = Premiumtuer.gibKartoneinheiten();
        int glasProPremiumtuer = Premiumtuer.gibGlaseinheiten();

        int erwarteteHolzeinheiten = initialHolzeinheiten 
            - 2 * holzProStandardtuer - holzProPremiumtuer;
        int erwarteteSchrauben = initialSchrauben 
            - 2 * schraubenProStandardtuer - schraubenProPremiumtuer;
        int erwarteteFarbeeinheiten = initialFarbeeinheiten 
            - 2 * farbeProStandardtuer - farbeProPremiumtuer;
        int erwarteteKartoneinheiten = initialKartoneinheiten 
            - 2 * kartonProStandardtuer - kartonProPremiumtuer;
        int erwarteteGlaseinheiten = initialGlaseinheiten - glasProPremiumtuer;

        // Act
        lager.wareLiefern(bestellung);

        // Assert
        assertEquals(erwarteteHolzeinheiten, lager.gibVorhandeneHolzeinheiten(), 
            "Die Holzeinheiten sollten korrekt reduziert werden.");
        assertEquals(erwarteteSchrauben, lager.gibVorhandeneSchrauben(), 
            "Die Schrauben sollten korrekt reduziert werden.");
        assertEquals(erwarteteFarbeeinheiten, lager.gibVorhandeneFarbeeinheiten(), 
            "Die Farbeeinheiten sollten korrekt reduziert werden.");
        assertEquals(erwarteteKartoneinheiten, lager.gibVorhandeneKartoneinheiten(), 
            "Die Kartoneinheiten sollten korrekt reduziert werden.");
        assertEquals(erwarteteGlaseinheiten, lager.gibVorhandeneGlaseinheiten(), 
            "Die Glaseinheiten sollten korrekt reduziert werden.");

        // Beende den ExecutorService
        executorService.shutdown();
    }

}
