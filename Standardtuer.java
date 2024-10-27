public class Standardtuer extends Produkt
{
    //Initialisierung der Werte laut Aufgabestellung
    private static final int HOLZEINHEITEN = 2;
    private static final int SCHRAUBEN = 10;
    private static final int FARBEINHEITEN = 2;
    private static final int KARTONEINHEITEN = 1;
    private static final int PRODUKTIONSZEIT = 10;
    
    //Getter-Methoden sind laut Klassendiagramm vorgegeben, machen aber keinen Sinn. Mit Premiumtuer.HOLZEINHEITEN könnte direkt auf die statische Variable zugegriffen werden. Wir entschieden uns trotzdem dafür, da das Musterlösung-Klassendiagramm dies vorgibt.
    public int gibHolzeinheiten()
    {
        return HOLZEINHEITEN;
    }
    
    public int gibSchrauben()
    {
        return SCHRAUBEN;
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
