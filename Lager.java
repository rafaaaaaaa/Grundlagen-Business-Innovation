public class Lager
{
    // Maximale Kapazitäten für jede Art von Material
    private int maxHolzeinheiten = 1000;
    private int maxSchrauben = 5000;
    private int maxFarbeinheiten = 1000;
    private int maxKartoneinheiten = 1000;
    private int maxGlaseinheiten = 100;

    // Aktueller Lagerbestand für jede Art von Material
    private int vorhandeneHolzeinheiten= 0;
    private int vorhandenSchrauben= 0;
    private int vorhandeneFarbeinheiten= 0;
    private int vorhandeneKartoneinheiten= 0;
    private int vorhandeneGlaseinheiten= 0;

    // Lieferant wird initialisiert
    private Lieferant lieferant = new Lieferant();

    // Berechnet die Beschaffungszeit basierend auf der Kundenbestellung
    public int gibBeschaffungszeit(Bestellung kundenBestellung) {
        int totalHolz = 0;
        int totalSchrauben = 0;
        int totalFarbe = 0;
        int totalKarton = 0;
        int totalGlas = 0;

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

    // Füllt den Lagerbestand durch eine Bestellung beim Lieferanten auf
    public void lagerAuffuellen() 
    {
        int holzNachbestellen = maxHolzeinheiten - vorhandeneHolzeinheiten;
        int schraubenNachbestellen = maxSchrauben - vorhandenSchrauben;
        int farbeNachbestellen = maxFarbeinheiten - vorhandeneFarbeinheiten;
        int kartonNachbestellen = maxKartoneinheiten - vorhandeneKartoneinheiten;
        int glasNachbestellen = maxGlaseinheiten - vorhandeneGlaseinheiten;

        boolean lieferungErfolgreich = lieferant.wareBestellen(holzNachbestellen, schraubenNachbestellen, farbeNachbestellen, kartonNachbestellen, glasNachbestellen);

        if (lieferungErfolgreich)
        {
            vorhandeneHolzeinheiten = maxHolzeinheiten;
            vorhandenSchrauben = maxSchrauben;
            vorhandeneFarbeinheiten = maxFarbeinheiten;
            vorhandeneKartoneinheiten = maxKartoneinheiten;
            vorhandeneGlaseinheiten = maxGlaseinheiten;
        } 
        else 
        {
            System.out.println("Lieferung fehlgeschlagen!");
        }
    }

    // Gibt den aktuellen Lagerbestand auf der Konsole aus
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
