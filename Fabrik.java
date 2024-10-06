import java.util.ArrayList;

public class Fabrik
{
    private ArrayList<Bestellung> bestellungen = new ArrayList<Bestellung>();
    
    public void main(){
        bestellungAufgeben(2, 4);
        bestellungAufgeben(1, 0);
        bestellungAusgeben();
    }
    
   
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
        System.out.println("Anzahl Premiumtüren = 1");
        for (Bestellung bestellung : bestellungen) 
        {
            
            // anderes Zeug ausgeben
        }  
        
    }
}