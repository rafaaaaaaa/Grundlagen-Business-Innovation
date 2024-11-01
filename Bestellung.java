import java.util.ArrayList;

public class Bestellung
{
    private ArrayList<Produkt> bestellteProdukte = new ArrayList<Produkt>();
    private boolean bestellBestaetigung;
    private int beschaffungsZeit;
    private int anzahlStandardTueren;
    private int anzahlPremiumTueren;
    private int bestellungsNr;
    private float lieferZeit;
    
    public Bestellung(int bestellungsNr, int anzahlStandardTueren, int anzahlPremiumTueren)
    {  
       //initialisieren einiger Klassenvariablen anhand Parameter -> Beschaffungszeit wird initial auf 0 gesetzt (für spätere Aufgaben wird dies anders gelöst).
       this.bestellungsNr = bestellungsNr;
       this.anzahlStandardTueren = anzahlStandardTueren;
       this.anzahlPremiumTueren = anzahlPremiumTueren;
       this.beschaffungsZeit = 0;
       
       //Erstellung der Produkte -> generische Lösung, da Code für das Erstellen der Standard- und Premiumtüren sonst repetitiv wäre
       fügeProduktZuBestellung(anzahlStandardTueren, Standardtuer.class);
       fügeProduktZuBestellung(anzahlStandardTueren, Premiumtuer.class);
       
       //beim erstellen des Objekts ist die Bestellung noch nicht bestätigt.
       bestellBestaetigung = false;
    }
    
    public void bestellungBestaetigen()
    {
        bestellBestaetigung = true;
    }
    
    public boolean gibBestellBestaetigung()
    {
        return bestellBestaetigung;
    }
        
    public void setzeBeschaffungsZeit(int zeit) 
    {
        //Eine Beschaffungszeit von 0 wäre theoretisch möglich (falls an Lager). Negative Beschaffungszeiten sind unmöglich. Dies wird an den User zurückgespielt über eine Ausgabe. Auf eine Exception wird verzichtet, da das Programm weiterlaufen kann.
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
    
    public ArrayList<Produkt> liefereBestellteProdukte() 
    {
        return bestellteProdukte;
    }
    
    public void setzeLieferzeit(float lieferZeit)
    {
        this.lieferZeit = lieferZeit;
    }
    
    public float gibLieferzeit()
    {
        return lieferZeit;
    }
    
    private <T extends Produkt> void fügeProduktZuBestellung(int anzahl, Class<T> produktTyp) {
        //iterieren durch die Anzahl Produkte die zu erstellen sind. Die Klasse die zu instanzieren ist wird über <T> Produkttyp generisch mitgegeben
        for (int i = 0; i < anzahl; i++) {
            try {
                T newInstance = produktTyp.getDeclaredConstructor().newInstance();
                bestellteProdukte.add(newInstance);
            } catch (Exception e) {
                //falls eine fehlerhafte Klasse als Parameter mitgegeben wird (nicht Premiumtüre und auch nicht Standardtüre), dann muss ergibt sich ein Fehler und das ganze wird im Stacktrace ausgegeben.
                e.printStackTrace();
            }
        }
    }
}
