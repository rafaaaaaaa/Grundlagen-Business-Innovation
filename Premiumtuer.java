public class Premiumtuer extends Produkt
{
    //Initialisierung der Werte laut Aufgabestellung
    private static final int HOLZEINHEITEN = 4;
    private static final int SCHRAUBEN = 5;
    private static final int GLASEINHEITEN = 5;
    private static final int FARBEINHEITEN = 1;
    private static final int KARTONEINHEITEN = 5;
    private static final int PRODUKTIONSZEIT = 30;
    
    //Getter-Methoden sind laut Klassendiagramm vorgegeben, machen aber keinen Sinn. Mit Premiumtuer.HOLZEINHEITEN könnte direkt auf die statische Variable zugegriffen werden. Wir entschieden uns trotzdem dafür, da das Musterlösung-Klassendiagramm dies vorgibt.
    public int gibHolzeinheiten()
    {
        return HOLZEINHEITEN;
    }
    
    public int gibSchrauben()
    {
        return SCHRAUBEN;
    }
    
    public int gibGlaseinheiten()
    {
        return GLASEINHEITEN;
    }
    
    public int gibFarbeinheiten()
    {
        return FARBEINHEITEN;
    }
    
    public int gibKartoneinheiten()
    {
        return KARTONEINHEITEN;
    }
    
    public int gibProduktionszeit()
    {
        return PRODUKTIONSZEIT;
    }   
}
