import java.util.concurrent.*;

/**
 * Die Klasse Lager implementiert die Funktionalität eines Lagers,
 * in dem die für die Produktion benötigten Materialien gelagert werden.
 *
 * @author Rafael Estermann
 * @version 04.12.2024
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
     * Konstruktor der Klasse Lager
     */
    public Lager(ExecutorService executorService) {

        this.vorhandeneHolzeinheiten = MAXHOLZEINHEITEN;
        this.vorhandeneSchrauben = MAXSCHRAUBEN;
        this.vorhandeneFarbeeinheiten = MAXFARBEEINHEITEN;
        this.vorhandeneKartoneinheiten = MAXKARTONEINHEITEN;
        this.vorhandeneGlaseinheiten = MAXGLASEINHEITEN;
        
        // ExecutorService für asynchrone Aufgaben
        this.executorService = executorService;
        lieferant = new Lieferant();
    }

    /**
     * Methode gibBeschaffungsZeit liefert die Beschaffungszeit in Tagen:
     * 0 Tage, wenn alle Materialien vorhanden sind und
     * 2 Tage, wenn diese nachbestellt werden müssen
     *
     * @param Bestellung: eine Kundenbestellung mit allen bestellten Produkten im
     *                    Array of Produkt
     * @return: Beschaffungszeit in Tagen
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

        // Wenn die benötigten Matierialien für die Bestellung alle vorhanden sind...
        if (benoetigteHolzeinheiten <= this.vorhandeneHolzeinheiten &&
                benoetigteSchrauben <= this.vorhandeneSchrauben &&
                benoetigteFarbeeinheiten <= this.vorhandeneFarbeeinheiten &&
                benoetigteKartoneinheiten <= this.vorhandeneKartoneinheiten &&
                benoetigteGlaseinheiten <= this.vorhandeneGlaseinheiten) {
            return 0; // .. keine 2 Tage zusätzlich
        }

        return 2; // .. sonst 2 Tage für Nachbestellung der Materialien beim Lieferanten
    }
    
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
        
        //Reduzierung des Lagerbestandes
        vorhandeneHolzeinheiten -= benoetigteHolzeinheiten;
        vorhandeneSchrauben -= benoetigteSchrauben;
        vorhandeneFarbeeinheiten -= benoetigteFarbeeinheiten;
        vorhandeneKartoneinheiten -= benoetigteKartoneinheiten;
        vorhandeneGlaseinheiten -= benoetigteGlaseinheiten; 
        
        lagerBestandAusgeben();
    }

    /**
     * Methode lagerAuffuellen bestellt alle Materialien nach, so dass
     * das Lager wieder voll ist.
     * 
     */
    public void lagerAuffuellen() {

        int zuBestellendeHolzeinheiten = MAXHOLZEINHEITEN - vorhandeneHolzeinheiten;
        int zuBestellendeSchrauben = MAXSCHRAUBEN - vorhandeneSchrauben;
        int zuBestellendeFarbeeinheiten = MAXFARBEEINHEITEN - vorhandeneFarbeeinheiten;
        int zuBestellendeKartoneinheiten = MAXKARTONEINHEITEN - vorhandeneKartoneinheiten;
        int zuBestellendeGlaseinheiten = MAXGLASEINHEITEN - vorhandeneGlaseinheiten;

        // Callable für wareBestellen-Methode erstellen, die einen Boolean zurückgibt
        Callable<Boolean> bestellungTask = () -> lieferant.wareBestellen(zuBestellendeHolzeinheiten, zuBestellendeSchrauben, zuBestellendeFarbeeinheiten, zuBestellendeKartoneinheiten,zuBestellendeGlaseinheiten);

        // Task ausführen und Future erhalten
        Future<Boolean> future = executorService.submit(bestellungTask);

        try {
            // Warten auf das Ergebnis (true oder false)
            boolean ergebnis = future.get(); // Ruft das Ergebnis ab (blockiert, bis das Ergebnis verfügbar ist)
            if (ergebnis) {
                vorhandeneHolzeinheiten = MAXHOLZEINHEITEN;
                vorhandeneSchrauben = MAXSCHRAUBEN;
                vorhandeneFarbeeinheiten = MAXFARBEEINHEITEN;
                vorhandeneKartoneinheiten = MAXKARTONEINHEITEN;
                vorhandeneGlaseinheiten = MAXGLASEINHEITEN;
                System.out.println("Lager wurde wieder aufgefüllt!");
            }
            
            else {
                System.out.println("Ware konnte nicht nachbestellt werden!");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
           
    }

    /**
     * Methode gibt den Zustand der Materialien auf Lager an
     */
    public void lagerBestandAusgeben() {
        System.out.println("Lagerbestand:");
        System.out.println("Vorhandene Holzeinheiten: " + vorhandeneHolzeinheiten +
                " Vorhandene Schrauben: " + vorhandeneSchrauben +
                " Vorhandene Farbeeinheiten: " + vorhandeneFarbeeinheiten +
                " Vorhandene Kartoneinheiten: " + vorhandeneKartoneinheiten +
                " Vorhandene Glaseinheiten: " + vorhandeneGlaseinheiten + "\n\n");
    }
}
