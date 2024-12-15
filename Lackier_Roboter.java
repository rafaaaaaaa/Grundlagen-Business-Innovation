
/**
 * Beschreiben Sie hier die Klasse Holzbearbeitungs_Roboter.
 * 
 * @author Rafael Estermann
 * @version 15.12.2024
 */
public class Lackier_Roboter extends Roboter
{
    public Lackier_Roboter(String name){
        super(name);
    }
    
    @Override
    public void produziereProdukt(Produkt produkt) {     
     //nicht zu implementieren laut Aufgabenstellung       
    }   
   
    @Override
    public String gibNamen(Produkt produkt){
        return "Lackier Roboter";
    }
}
