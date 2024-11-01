import java.util.ArrayList;

public class Fabrik
{
    private Lager lager;
    private ArrayList<Bestellung> bestellungen;
    private int bestellungsNr;
    
    public static void main(String[] args){
      //Diese Methode wird laut Klassendiagramm verlangt. Damit Java sie als "Main Methode" erkennt, muss diese genau diese Signatur haben (+ static sein). 
    }
    
    
    public Fabrik()
    {
        lager = new Lager();
        bestellungen = new ArrayList<Bestellung>();
        bestellungsNr = 0;
    }
   
    public void bestellungAufgeben(int standardTueren, int premiumTueren)
    {
        //als Erstes wird geprüft, ob die Bestellung von der Anzahl Türen her valide ist
        try
        {
            pruefeParameter(standardTueren, premiumTueren);
        }
        catch(IllegalArgumentException ex) 
        {
            //falls invalide Parameter mitgegeben werden, so wird dies dem User mitgeteilt. Die Bestellung wird aber nicht aufgegeben. Ziel ist es hier, keinen Programmabbruch beizuführen, weswegen die Exception gecatched und gehandelt wird.
            System.out.println("Bestellung wurde nicht aufgegeben, aufgrund invalider Parameter.");
            return;
        }
        
        //hochzählen der Bestellnummer um Eins
        bestellungsNr = bestellungsNr + 1;  
        
        //erstellen der neuen Bestellung
        Bestellung neueBestellung = new Bestellung(bestellungsNr, standardTueren, premiumTueren); 
        
        //berechnen und setzen der Beschaffungszeit auf Bestellung 
        int beschaffungsZeit = lager.gibBeschaffungszeit(neueBestellung);
        neueBestellung.setzeBeschaffungsZeit(beschaffungsZeit);    
        
        //fülle Lager auf, wenn Bestände niedrig sind (= Beschaffungszeit != 0 -> es hat zu wenig im Lager)
        if(beschaffungsZeit != 0) 
        {
            lagerAuffuellen(); 
        }
        else 
        {
            //produziere = ändere Zustand der Produkte von "bestellt" zu "in Produktion".
        }
        
        //berechnen und setzen der Lieferzeit auf Bestellung
        int lieferzeit = berechneProduktionszeit(neueBestellung) + beschaffungsZeit + 1;
        neueBestellung.setzeLieferzeit(lieferzeit);
        
        //bestätige die Bestellung, nachdem sie bestellt wurde. //todo -> abklären ob hier richtig?
        neueBestellung.bestellungBestaetigen();
        
        //hinzufügen der Bestellung zur ArrayListe
        bestellungen.add(neueBestellung);        
    }
   
    public void bestellungAusgeben()
    {   
        //für jede Bestellung wird die Bestellnummer, Anzahl Standard- und Premiumtüren, dessen Bestellbestätigung sowie die Beschaffungszeit an einen String gehängt.
        String resultOutput = "";
        for (Bestellung bestellung : bestellungen) 
        {
            resultOutput += "Bestellnummer: " + bestellung.gibBestellungsNr() + "\n";
            resultOutput += "Anzahl Standardtüren: " + bestellung.gibAnzahlStandardTueren() + "\n";
            resultOutput += "Anzahl Premiumtüren: " + bestellung.gibAnzahlPremiumTueren() + "\n";
            resultOutput += "Ist Bestellung bestätigt: ";
            
            if(bestellung.gibBestellBestaetigung()) 
            {
                resultOutput += "Ja\n";
            }
            else 
            {
                 resultOutput += "Nein\n";
            }
            resultOutput += "Beschaffungszeit: " + bestellung.gibBeschaffungsZeit() + "\n";  
        }          
        
        //Ausgabe des Strings an den User
        System.out.println(resultOutput);
    }
    
    public void lagerAuffuellen()
    {
        lager.lagerAuffuellen();
    }
    
    private int berechneProduktionszeit(Bestellung bestellung)
    {
        return (bestellung.gibAnzahlStandardTueren() * Standardtuer.PRODUKTIONSZEIT) + (bestellung.gibAnzahlPremiumTueren() * Premiumtuer.PRODUKTIONSZEIT);
    }
    
    private void pruefeParameter(int standardTueren, int premiumTueren) 
    {
        
        //Negative Bestellanzahlen sind ungültig. Entsprechend wird dies nicht zugelassen.
        if(standardTueren < 0 || premiumTueren < 0)
        {
            throw new IllegalArgumentException("Negative Anzahl Türen angegeben. Bestellung wird nicht aufgegeben");
        }
        
        //Komplett leere Bestellanzahlen sind ungültig. Entsprechend wird dies nicht zugelassen.
        if(standardTueren == 0 && premiumTueren == 0)
        {
            throw new IllegalArgumentException("Null Anzahl Türen angegeben. Bestellung wird nicht aufgegeben");
        }        
    }
}