import java.util.ArrayList;

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
       this.anzahlStandardTueren = anzahlStandardTueren;
       this.anzahlPremiumTueren = anzahlPremiumTueren;
       
       for(int i = 0; i < anzahlStandardTueren; i++) 
       {
           Standardtuer neueStandardTuer = new Standardtuer();
           bestellteProdukte.add(neueStandardTuer);
       }
       
       for(int i = 0; i < anzahlPremiumTueren; i++) 
       {
           Premiumtuer neuePremiumTuer = new Premiumtuer();
           bestellteProdukte.add(neuePremiumTuer);
       }       
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
}
