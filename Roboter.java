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

    public Roboter(String name)
    {
        //Listen instanzieren
        warteschlange = new LinkedList<Produkt>();    
        this.name = name;
    }
    
    @Override
    public void run() {
        while (true) {
                // Prüfen, ob eine neues Produkt eingetroffen ist
                if (!warteschlange.isEmpty()) {
                    System.out.println(warteschlange.size() + " Produkt(e) in Warteschlange");
                    
                    // Das nächste Produkt produzieren aus der Liste der zu verarbeitenden Bestellungen entnehmen
                    Produkt zuProduzierendesProdukt = warteschlange.removeFirst();                     
                    produziereProdukt(zuProduzierendesProdukt);                    
                }
            
                // Den Thread eine kurze Zeit (0.1s) schlafen lassen, bis zur nächsten Überprüfung
                try {
                    Thread.sleep(100); 
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
    public String gibNamen(Produkt produkt){
        return name;
    }
    
    //wird nicht auf Basisklasse implementiert, da jeder Roboter eigene Produktionslogik hat
    public abstract void produziereProdukt(Produkt produkt);
}
