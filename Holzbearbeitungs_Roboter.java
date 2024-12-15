
/**
 * Beschreiben Sie hier die Klasse Holzbearbeitungs_Roboter.
 * 
 * @author Rafael Estermann
 * @version 15.12.2024
 */
public class Holzbearbeitungs_Roboter extends Roboter
{
    public Holzbearbeitungs_Roboter(String name){
        super(name);
    }
    
    @Override
    public void produziereProdukt(Produkt produkt) {     
    
        //Simulierung Bearbeitungszeit
         try {
             if (produkt instanceof Standardtuer) {              
                    Thread.sleep(166);  //= 10 Minuten -> laut Aufgabenstellung 600.000ms -> jedoch nicht möglich, da sonst inkonsistent mit 1s = 1h Kriterium. 10m = 166ms
                    System.out.println("Holz Roboter hat Standardtür Prozessschritt abgeschlossen");
                } else {
                    Thread.sleep(500); //= 30 Minuten -> laut Aufgabenstellung 1.800.000ms -> jedoch nicht möglich, da sonst inkonsistent mit 1s = 1h Kriterium. 30m = 500ms
                    System.out.println("Holz Roboter hat Premiumtür Prozessschritt abgeschlossen");
                }
                
                //Einleiten nächster Produktionsschritt (aufgrund nicht-implementierung anderer Roboter, kommt hier keiner mehr).
                produkt.naechsteProduktionsStation();   
                
        } catch (InterruptedException e) {
            e.printStackTrace();
        }       
    }
}
