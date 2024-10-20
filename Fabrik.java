import java.util.ArrayList;

public class Fabrik
{
    private ArrayList<Bestellung> bestellungen = new ArrayList<Bestellung>();
    private int bestellungsNr = 0;
    
    public void main(){
        bestellungAufgeben(2, 4);
        bestellungAufgeben(1, 0);
        bestellungAusgeben();
    }
    
   
    public void bestellungAufgeben(int standardTueren, int premiumTueren)
    {
        if(standardTueren < 1 && premiumTueren < 1)
        {
            System.out.println("Fehlerhafte Anzahl Türen ausgegeben. Bestellung wird nicht aufgegeben");
            return;
        }
        
        bestellungsNr = bestellungsNr + 1;       
        Bestellung neueBestellung = new Bestellung(bestellungsNr, standardTueren, premiumTueren); 
        neueBestellung.setzeBeschaffungsZeit(1440); //in Minuten (1440min = 1 Tag)
        bestellungen.add(neueBestellung);        
    }
   
    public void bestellungAusgeben()
    {   
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
         System.out.println(resultOutput);
    }
}