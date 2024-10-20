import java.util.ArrayList;

public class Bestellung
{
    private ArrayList<Produkt> bestellteProdukte = new ArrayList<Produkt>();
    private boolean bestellBestaetigung;
    private int beschaffungsZeit;
    private int anzahlStandardTueren;
    private int anzahlPremiumTueren;
    private int bestellungsNr;
    
    public Bestellung(int bestellungsNr, int anzahlStandardTueren, int anzahlPremiumTueren)
    {  
       this.bestellungsNr = bestellungsNr;
       this.anzahlStandardTueren = anzahlStandardTueren;
       this.anzahlPremiumTueren = anzahlPremiumTueren;
       fügeProduktZuBestellung(anzahlStandardTueren, Standardtuer.class);
       fügeProduktZuBestellung(anzahlStandardTueren, Premiumtuer.class);
       bestellBestaetigung = true;
    }
    
    public void bestellungBestaetigen(){
        bestellBestaetigung = true;
    }
    
    public boolean gibBestellBestaetigung()
    {
        return bestellBestaetigung;
    }
    
    public void setzeBeschaffungsZeit(int zeit) 
    {
        if(zeit < 0)
        {
            System.out.println("Fehlerhafte Beschaffungszeit Angabe. Beschaffungszeit wurde nicht angepasst");
            return;
        }
        
        beschaffungsZeit = zeit;
    }
    
    public int gibBeschaffungsZeit()
    {
        return beschaffungsZeit;
    }
    
    public int gibBestellungsNr()
    {
        return bestellungsNr;
    }
    
    public int gibAnzahlStandardTueren() {
        return anzahlStandardTueren;
    }
    
    public int gibAnzahlPremiumTueren(){
        return anzahlPremiumTueren;
    }   
    
    private <T extends Produkt> void fügeProduktZuBestellung(int anzahl, Class<T> produktTyp) {
        for (int i = 0; i < anzahl; i++) {
            try {
                T newInstance = produktTyp.getDeclaredConstructor().newInstance();
                bestellteProdukte.add(newInstance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
