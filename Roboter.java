import java.util.LinkedList;

/**
 * Beschreiben Sie hier die Klasse Roboter.
 * 
 * @author Rafael Estermann
 * @version 15.12.2024
 */
public abstract class Roboter extends Thread
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private int x;
    private LinkedList<Produkt> warteschlange;
    private String name;
    private int produktionsZeit;
    private boolean lastProducedIsPremium = false;

    public Roboter(String name)
    {
        //Listen instanzieren
        warteschlange = new LinkedList<Produkt>();    
        this.name = name;
        lastProducedIsPremium = false; // Default Konfiguration der Maschine Standard
    }
    
    @Override
    public void run() {
        while (true) {
                // Prüfen, ob eine neues Produkt eingetroffen ist
                if (!warteschlange.isEmpty()) {
                    System.out.println(warteschlange.size() + " Produkt(e) in " + gibName() + " Warteschlange");
                    
                    // Das nächste Produkt produzieren aus der Liste der zu verarbeitenden Bestellungen entnehmen (wieder: First-in-First-out)
                    Produkt zuProduzierendesProdukt = warteschlange.removeFirst();   
                    
                    //Falls Maschine umgerüstet werden muss (+ 1s)
                    if(zuProduzierendesProdukt instanceof Standardtuer && lastProducedIsPremium || zuProduzierendesProdukt instanceof Premiumtuer && !lastProducedIsPremium) {
                        try
                        {
                            System.out.println("PRODUKTIONSINFO: " + gibName() + " muss umgerüstet werden.");
                            Thread.sleep(1000);
                        }
                        catch (InterruptedException ie)
                        {
                            ie.printStackTrace();
                        }                      
                    }
                    
                    lastProducedIsPremium = (zuProduzierendesProdukt instanceof Premiumtuer);
                    
                    produziereProdukt(zuProduzierendesProdukt);                    
                }
            
                // Den Thread eine kurze Zeit (0.1s) schlafen lassen, bis zur nächsten Überprüfung
                try {
                    Thread.sleep(1000); 
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                    // Hier könnte man den Interrupt-Status wiederherstellen oder Aufräumarbeiten ausführen
                    Thread.currentThread().interrupt();
                }
            }
    }
    
    public void fuegeProduktHinzu(Produkt produkt)
    {
        warteschlange.add(produkt);
    }
    
    public void setzeProduktionsZeit(int zeit){
        produktionsZeit = zeit;
    }
    
    //wird nicht auf Basisklasse implementiert, da jeder Roboter eigenn Namen hat
    public String gibName(){
        return name;
    }
    
    //wird nicht auf Basisklasse implementiert, da jeder Roboter eigene Produktionslogik hat
    public abstract void produziereProdukt(Produkt produkt);
}
