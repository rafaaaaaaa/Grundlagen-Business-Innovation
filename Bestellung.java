import java.util.ArrayList;
import java.util.Random;

public class Bestellung
{
    private ArrayList<Produkt> bestellteProdukte = new ArrayList<Produkt>();
    private boolean bestellBestaetigung;
    private int beschaffungsZeit;
    private int anzahlStandardTueren;
    private int anzahlPremiumTueren;
    private int bestellungsNr;
    
    public Bestellung(int anzahlStandardTueren, int anzahlPremiumTueren)
    {     
       bestellBestaetigung = false;
       bestellungsNr = generiereBestellungsNr();
       this.anzahlStandardTueren = anzahlStandardTueren;
       this.anzahlPremiumTueren = anzahlPremiumTueren;
       fügeProduktZuBestellung(anzahlStandardTueren, Standardtuer.class);
       fügeProduktZuBestellung(anzahlStandardTueren, Premiumtuer.class);
    }
    
    public void bestellungBestaetigen(){
        bestellBestaetigung = true;
    }
    
    public boolean gibBestellBestaetigung()
    {
        return bestellBestaetigung;
    }
    
    public void setzeBeschaffungszeit(int zeit) 
    {
        if(zeit < 0)
        {
            System.out.println("Fehlerhafte Beschaffungszeit Angabe. Zeit wurde nicht angepasst");
            return;
        }
        
        beschaffungsZeit = zeit;
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
    
    private int generiereBestellungsNr(){
        Random random = new Random();
        int bestellungsNr = 100000 + random.nextInt(900000);
        return bestellungsNr;
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
