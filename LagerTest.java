import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

/**
 * Die Testklasse für die Klasse Lager.
 *
 * @author Rafael Estermann
 * @version 15.12.2024
 */
public class LagerTest {
    String nameTestClasse = "LagerTest"; // Name der Testklasse

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
     * Testet die Methode gibBeschaffungsZeit().
     */
    @Test
    public void testeGibBeschaffungsZeit() {
        // Arrange
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Lager testLager = new Lager(executorService);
        Bestellung testBestellung1 = new Bestellung(5, 7, 2); // Genügende Materialien auf Lager
        Bestellung testBestellung2 = new Bestellung(5, 21, 3); // Ungenügende Materialien auf Lager

        // Act & Assert
        assertEquals(0, testLager.gibBeschaffungsZeit(testBestellung1),
            "Die Beschaffungszeit sollte 0 Tage betragen, wenn genügend Materialien vorhanden sind.");
        assertEquals(2, testLager.gibBeschaffungsZeit(testBestellung2),
            "Die Beschaffungszeit sollte 2 Tage betragen, wenn Materialien nachbestellt werden müssen.");
    }

    /**
     * Testet die Methode lagerAuffuellen() auf korrekte Ausführungszeit (48 Sekunden).
     */
    @Test
    public void testLagerAuffuellenTakes48Seconds() {
        // Arrange
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Lager testLager = new Lager(executorService);
        long expectedTime = 48000; // 48 Sekunden in Millisekunden
        long tolerance = 500; // Toleranz in Millisekunden (+/- 0.5 Sekunden)

        // Act
        long startTime = System.currentTimeMillis();
        testLager.lagerAuffuellen();
        long elapsedTime = System.currentTimeMillis() - startTime;

        // Assert
        assertTrue(elapsedTime >= expectedTime - tolerance && elapsedTime <= expectedTime + tolerance,
            "Die Methode lagerAuffuellen() sollte ungefähr 48 Sekunden dauern. Gemessene Zeit: " + elapsedTime + " ms.");
    }

    /**
     * Testet die Methode wareLiefern() darauf, ob der Lagerbestand korrekt reduziert wird.
     */
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

        // Materialien pro Tür
        int holzProStandardtuer = Standardtuer.gibHolzeinheiten();
        int schraubenProStandardtuer = Standardtuer.gibSchrauben();
        int farbeProStandardtuer = Standardtuer.gibFarbeinheiten();
        int kartonProStandardtuer = Standardtuer.gibKartoneinheiten();

        int holzProPremiumtuer = Premiumtuer.gibHolzeinheiten();
        int schraubenProPremiumtuer = Premiumtuer.gibSchrauben();
        int farbeProPremiumtuer = Premiumtuer.gibFarbeinheiten();
        int kartonProPremiumtuer = Premiumtuer.gibKartoneinheiten();
        int glasProPremiumtuer = Premiumtuer.gibGlaseinheiten();

        // Erwartete Lagerbestände nach Lieferung
        int erwarteteHolzeinheiten = 1000 - 2 * holzProStandardtuer - holzProPremiumtuer;
        int erwarteteSchrauben = 5000 - 2 * schraubenProStandardtuer - schraubenProPremiumtuer;
        int erwarteteFarbeeinheiten = 1000 - 2 * farbeProStandardtuer - farbeProPremiumtuer;
        int erwarteteKartoneinheiten = 1000 - 2 * kartonProStandardtuer - kartonProPremiumtuer;
        int erwarteteGlaseinheiten = 100 - glasProPremiumtuer;

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
