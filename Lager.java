/**
 * Klasse Lager beinhaltet die Methoden des Lager zur Verwaltung und Auffüllung von Lagerbeständen.
 * Sie enthält Funktionen zur Berechnung der Beschaffungszeit und zur Überprüfung der Materialverfügbarkeit.
 * 
 * @author Rafael Estermann
 * @version 23.11.2024
 */
public class Lager 
{
    // Maximale Kapazitäten für jede Art von Material
    private static final int MAXHOLZEINHEITEN = 1000;
    private static final int MAXSCHRAUBEN = 5000;
    private static final int MAXFARBEINHEITEN = 1000;
    private static final int MAXKARTONEINHEITEN = 1000;
    private static final int MAXGLASEINHEITEN = 100;
    
    // Aktueller Lagerbestand für jede Art von Material
    private int vorhandeneHolzeinheiten = 0;
    private int vorhandenSchrauben = 0;
    private int vorhandeneFarbeinheiten = 0;
    private int vorhandeneKartoneinheiten = 0;
    private int vorhandeneGlaseinheiten = 0;

    // Lieferant wird initialisiert, um die Nachbestellung von Materialien zu ermöglichen
    private Lieferant lieferant = new Lieferant();

    /**
     * Berechnet die Beschaffungszeit basierend auf der Kundenbestellung.
     * Falls der aktuelle Lagerbestand ausreichend ist, wird eine Beschaffungszeit von 0 zurückgegeben.
     * Ansonsten wird eine Beschaffungszeit von 2 Tagen für das Auffüllen angesetzt.
     * 
     * @param kundenBestellung Die Bestellung des Kunden, basierend auf welcher der Materialbedarf berechnet wird.
     * @return Die benötigte Beschaffungszeit in Tagen. 0 bedeutet, dass ausreichend Material im Lager vorhanden ist.
     */
    public int gibBeschaffungszeit(Bestellung kundenBestellung) {
        int totalHolz = 0;
        int totalSchrauben = 0;
        int totalFarbe = 0;
        int totalKarton = 0;
        int totalGlas = 0;

        // Berechnet den Materialbedarf basierend auf den bestellten Produkten
        for (Produkt produkt : kundenBestellung.liefereBestellteProdukte())
        {
            if (produkt instanceof Standardtuer) 
            {
                Standardtuer tuer = (Standardtuer) produkt;
                totalHolz += tuer.gibHolzeinheiten();
                totalSchrauben += tuer.gibSchrauben();
                totalFarbe += tuer.gibFarbeinheiten();
                totalKarton += tuer.gibKartoneinheiten();
            } 
            else if (produkt instanceof Premiumtuer) 
            {
                Premiumtuer tuer = (Premiumtuer) produkt;
                totalHolz += tuer.gibHolzeinheiten();
                totalSchrauben += tuer.gibSchrauben();
                totalFarbe += tuer.gibFarbeinheiten();
                totalKarton += tuer.gibKartoneinheiten();
                totalGlas += tuer.gibGlaseinheiten();
            }
        }

        // Überprüft, ob der aktuelle Lagerbestand für die Bestellung ausreicht
        if (totalHolz <= vorhandeneHolzeinheiten &&
            totalSchrauben <= vorhandenSchrauben &&
            totalFarbe <= vorhandeneFarbeinheiten &&
            totalKarton <= vorhandeneKartoneinheiten &&
            totalGlas <= vorhandeneGlaseinheiten) 
        {
            return 0; // Genügend Bestand vorhanden
        } else
        {
            return 2; // Bestand muss aufgefüllt werden
        }
    }

    /**
     * Füllt den Lagerbestand durch eine Bestellung beim Lieferanten auf, wenn der Bestand zu niedrig ist.
     * Bestellt die Differenz zur maximalen Kapazität für jedes Material.
     * Falls die Lieferung erfolgreich ist, wird der Bestand auf die maximale Kapazität aufgefüllt.
     */
    public void lagerAuffuellen() 
    {
        int holzNachbestellen = MAXHOLZEINHEITEN - vorhandeneHolzeinheiten;
        int schraubenNachbestellen = MAXSCHRAUBEN - vorhandenSchrauben;
        int farbeNachbestellen = MAXFARBEINHEITEN - vorhandeneFarbeinheiten;
        int kartonNachbestellen = MAXKARTONEINHEITEN - vorhandeneKartoneinheiten;
        int glasNachbestellen = MAXGLASEINHEITEN - vorhandeneGlaseinheiten;

        // Bestellt das benötigte Material beim Lieferanten
        boolean lieferungErfolgreich = lieferant.wareBestellen(holzNachbestellen, schraubenNachbestellen, farbeNachbestellen, kartonNachbestellen, glasNachbestellen);

        // Aktualisiert den Lagerbestand auf maximale Kapazität, falls die Lieferung erfolgreich war
        if (lieferungErfolgreich)
        {
            vorhandeneHolzeinheiten = MAXHOLZEINHEITEN;
            vorhandenSchrauben = MAXSCHRAUBEN;
            vorhandeneFarbeinheiten = MAXFARBEINHEITEN;
            vorhandeneKartoneinheiten = MAXKARTONEINHEITEN;
            vorhandeneGlaseinheiten = MAXGLASEINHEITEN;
        } 
        else 
        {
            System.out.println("Lieferung fehlgeschlagen!");
        }
    }  

    /**
     * Gibt den aktuellen Lagerbestand der verschiedenen Materialien auf der Konsole aus.
     */
    public void lagerBestandAusgeben()
    {
        System.out.println("Lagerbestand:");
        System.out.println("Holzeinheiten: " + vorhandeneHolzeinheiten);
        System.out.println("Schrauben: " + vorhandenSchrauben);
        System.out.println("Farbeinheiten: " + vorhandeneFarbeinheiten);
        System.out.println("Kartoneinheiten: " + vorhandeneKartoneinheiten);
        System.out.println("Glaseinheiten: " + vorhandeneGlaseinheiten);
    }

}
