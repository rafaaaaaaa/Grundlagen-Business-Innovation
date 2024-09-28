import java.util.ArrayList;

public class Fabrik
{
    private ArrayList<Bestellung> bestellungen = new ArrayList<Bestellung>();
   
    public void bestellungAufgeben(int standardTueren, int premiumTueren)
    {
        if(standardTueren < 1 || premiumTueren < 1)
        {
            System.out.println("Fehlerhafte Anzahl Türen ausgegeben. Bestellung wird nicht aufgegeben");
            return;
        }
        
        Bestellung neueBestellung = new Bestellung(standardTueren, premiumTueren);
        bestellungen.add(neueBestellung);        
    }
   
    public void bestellungAusgeben()
    {
        for (Bestellung bestellung : bestellungen) 
        {
            System.out.println(bestellung.gibBestellungsNr());
            // anderes Zeug ausgeben
        }      
    }
}