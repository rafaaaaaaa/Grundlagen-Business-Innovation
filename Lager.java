import java.util.concurrent.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Die Klasse Lager implementiert die Funktionalität eines Lagers,
 * in dem die für die Produktion benötigten Materialien gelagert werden.
 *
 * @author Rafael Estermann
 * @version 15.12.2024
 */
public class Lager {

    private static final int MAXHOLZEINHEITEN = 1000;
    private static final int MAXSCHRAUBEN = 5000;
    private static final int MAXFARBEEINHEITEN = 1000;
    private static final int MAXKARTONEINHEITEN = 1000;
    private static final int MAXGLASEINHEITEN = 100;

    private int vorhandeneHolzeinheiten;
    private int vorhandeneSchrauben;
    private int vorhandeneFarbeeinheiten;
    private int vorhandeneKartoneinheiten;
    private int vorhandeneGlaseinheiten;

    private ExecutorService executorService;
    private Lieferant lieferant;

    /**
     * Konstruktor der Klasse Lager.
     * 
     * @param executorService Der ExecutorService für asynchrone Aufgaben
     */
    public Lager(ExecutorService executorService) {
        this.vorhandeneHolzeinheiten = MAXHOLZEINHEITEN;
        this.vorhandeneSchrauben = MAXSCHRAUBEN;
        this.vorhandeneFarbeeinheiten = MAXFARBEEINHEITEN;
        this.vorhandeneKartoneinheiten = MAXKARTONEINHEITEN;
        this.vorhandeneGlaseinheiten = MAXGLASEINHEITEN;

        this.executorService = executorService;
        this.lieferant = new Lieferant();
    }

    /**
     * Berechnet die Beschaffungszeit basierend auf dem Materialbedarf.
     * 
     * @param kundenBestellung Die Kundenbestellung mit den benötigten Produkten
     * @return Die Beschaffungszeit in Tagen (0 oder 2 Tage)
     */
    public int gibBeschaffungsZeit(Bestellung kundenBestellung) {
        int benoetigteHolzeinheiten = 0;
        int benoetigteSchrauben = 0;
        int benoetigteFarbeeinheiten = 0;
        int benoetigteKartoneinheiten = 0;
        int benoetigteGlaseinheiten = 0;

        // Berechnung der benötigten Materialien
        for (Produkt produkt : kundenBestellung.gibBestellteProdukte()) {
            if (produkt instanceof Standardtuer) {
                benoetigteHolzeinheiten += Standardtuer.gibHolzeinheiten();
                benoetigteSchrauben += Standardtuer.gibSchrauben();
                benoetigteFarbeeinheiten += Standardtuer.gibFarbeinheiten();
                benoetigteKartoneinheiten += Standardtuer.gibKartoneinheiten();
            } else if (produkt instanceof Premiumtuer) {
                benoetigteHolzeinheiten += Premiumtuer.gibHolzeinheiten();
                benoetigteSchrauben += Premiumtuer.gibSchrauben();
                benoetigteFarbeeinheiten += Premiumtuer.gibFarbeinheiten();
                benoetigteKartoneinheiten += Premiumtuer.gibKartoneinheiten();
                benoetigteGlaseinheiten += Premiumtuer.gibGlaseinheiten();
            }
        }

        // Überprüfung des Materialbestands
        if (benoetigteHolzeinheiten <= vorhandeneHolzeinheiten &&
                benoetigteSchrauben <= vorhandeneSchrauben &&
                benoetigteFarbeeinheiten <= vorhandeneFarbeeinheiten &&
                benoetigteKartoneinheiten <= vorhandeneKartoneinheiten &&
                benoetigteGlaseinheiten <= vorhandeneGlaseinheiten) {
            return 0; // Keine zusätzliche Zeit erforderlich
        }
        return 2; // Zwei Tage für Nachbestellung
    }

    /**
     * Liefert die benötigten Materialien für eine Bestellung aus dem Lager.
     * 
     * @param kundenBestellung Die Kundenbestellung, für die Materialien geliefert werden
     */
    public void wareLiefern(Bestellung kundenBestellung) {
        int benoetigteHolzeinheiten = 0;
        int benoetigteSchrauben = 0;
        int benoetigteFarbeeinheiten = 0;
        int benoetigteKartoneinheiten = 0;
        int benoetigteGlaseinheiten = 0;

        // Berechnung der zu reduzierenden Materialien
        for (Produkt produkt : kundenBestellung.gibBestellteProdukte()) {
            if (produkt instanceof Standardtuer) {
                benoetigteHolzeinheiten += Standardtuer.gibHolzeinheiten();
                benoetigteSchrauben += Standardtuer.gibSchrauben();
                benoetigteFarbeeinheiten += Standardtuer.gibFarbeinheiten();
                benoetigteKartoneinheiten += Standardtuer.gibKartoneinheiten();
            } else if (produkt instanceof Premiumtuer) {
                benoetigteHolzeinheiten += Premiumtuer.gibHolzeinheiten();
                benoetigteSchrauben += Premiumtuer.gibSchrauben();
                benoetigteFarbeeinheiten += Premiumtuer.gibFarbeinheiten();
                benoetigteKartoneinheiten += Premiumtuer.gibKartoneinheiten();
                benoetigteGlaseinheiten += Premiumtuer.gibGlaseinheiten();
            }
        }

        // Reduzierung des Lagerbestands
        vorhandeneHolzeinheiten -= benoetigteHolzeinheiten;
        vorhandeneSchrauben -= benoetigteSchrauben;
        vorhandeneFarbeeinheiten -= benoetigteFarbeeinheiten;
        vorhandeneKartoneinheiten -= benoetigteKartoneinheiten;
        vorhandeneGlaseinheiten -= benoetigteGlaseinheiten;

        System.out.println("Ware für Produktion der Bestellung " + kundenBestellung.gibBestellungsNr() + " geliefert.");
    }

    
    public Map<String, Integer> gibLagerBestand() {
        Map<String, Integer> lagerBestand = new HashMap<>();
        lagerBestand.put("Holzeinheiten", vorhandeneHolzeinheiten);
        lagerBestand.put("Schrauben", vorhandeneSchrauben);
        lagerBestand.put("Farbeinheiten", vorhandeneFarbeeinheiten);
        lagerBestand.put("Kartoneinheiten", vorhandeneKartoneinheiten);
        lagerBestand.put("Glaseinheiten", vorhandeneGlaseinheiten);
        return lagerBestand;
}

    
    public Map<String, Integer> gibMaxLagerBestand() {
        Map<String, Integer> maxBestand = new HashMap<>();
        maxBestand.put("Holzeinheiten", MAXHOLZEINHEITEN);
        maxBestand.put("Schrauben", MAXSCHRAUBEN);
        maxBestand.put("Farbeinheiten", MAXFARBEEINHEITEN);
        maxBestand.put("Kartoneinheiten", MAXKARTONEINHEITEN);
        maxBestand.put("Glaseinheiten", MAXGLASEINHEITEN);
        return maxBestand;
    }

    /**
     * Füllt das Lager wieder vollständig auf.
     */
    public void lagerAuffuellen() {
        int zuBestellendeHolzeinheiten = MAXHOLZEINHEITEN - vorhandeneHolzeinheiten;
        int zuBestellendeSchrauben = MAXSCHRAUBEN - vorhandeneSchrauben;
        int zuBestellendeFarbeeinheiten = MAXFARBEEINHEITEN - vorhandeneFarbeeinheiten;
        int zuBestellendeKartoneinheiten = MAXKARTONEINHEITEN - vorhandeneKartoneinheiten;
        int zuBestellendeGlaseinheiten = MAXGLASEINHEITEN - vorhandeneGlaseinheiten;

        Callable<Boolean> bestellungTask = () -> lieferant.wareBestellen(
                zuBestellendeHolzeinheiten,
                zuBestellendeSchrauben,
                zuBestellendeFarbeeinheiten,
                zuBestellendeKartoneinheiten,
                zuBestellendeGlaseinheiten);

        Future<Boolean> future = executorService.submit(bestellungTask);

        try {
            boolean ergebnis = future.get(); // Blockiert, bis das Ergebnis verfügbar ist
            if (ergebnis) {
                vorhandeneHolzeinheiten = MAXHOLZEINHEITEN;
                vorhandeneSchrauben = MAXSCHRAUBEN;
                vorhandeneFarbeeinheiten = MAXFARBEEINHEITEN;
                vorhandeneKartoneinheiten = MAXKARTONEINHEITEN;
                vorhandeneGlaseinheiten = MAXGLASEINHEITEN;
                System.out.println("Lager wurde aufgefüllt!");
            } else {
                System.out.println("Auffüllen des Lagers fehlgeschlagen!");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gibt den aktuellen Lagerbestand aus.
     */
    public void lagerBestandAusgeben() {
        System.out.println("Lagerbestand:");
        System.out.println("Holzeinheiten: " + vorhandeneHolzeinheiten);
        System.out.println("Schrauben: " + vorhandeneSchrauben);
        System.out.println("Farbeeinheiten: " + vorhandeneFarbeeinheiten);
        System.out.println("Kartoneinheiten: " + vorhandeneKartoneinheiten);
        System.out.println("Glaseinheiten: " + vorhandeneGlaseinheiten);
    }

    // Getter für Unit-Tests
    public int gibVorhandeneHolzeinheiten() { return vorhandeneHolzeinheiten; }
    public int gibVorhandeneSchrauben() { return vorhandeneSchrauben; }
    public int gibVorhandeneFarbeeinheiten() { return vorhandeneFarbeeinheiten; }
    public int gibVorhandeneKartoneinheiten() { return vorhandeneKartoneinheiten; }
    public int gibVorhandeneGlaseinheiten() { return vorhandeneGlaseinheiten; }
}
