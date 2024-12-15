import java.util.LinkedList;

/**
 * Diese Klasse repräsentiert einen Roboter, der Produkte aus einer Warteschlange verarbeitet.
 * Die spezifische Produktionslogik wird in den Unterklassen implementiert.
 * 
 * @author Rafael Estermann
 * @version 15.12.2024
 */
public class Roboter extends Thread {

    private LinkedList<Produkt> warteschlange;
    private String name;
    private int produktionsZeit;
    private boolean lastProducedIsPremium = false;

    /**
     * Konstruktor für die Klasse Roboter.
     * 
     * @param name Der Name des Roboters
     */
    public Roboter(String name) {
        // Liste initialisieren
        warteschlange = new LinkedList<Produkt>();    
        this.name = name;
        lastProducedIsPremium = false; // Standardmäßig produziert der Roboter keine Premiumprodukte
    }

    /**
     * Führt den Produktionsprozess durch.
     * Diese Methode wird kontinuierlich ausgeführt, solange der Thread läuft.
     */
    @Override
    public void run() {
        while (true) {
            // Prüfen, ob Produkte in der Warteschlange vorhanden sind
            if (!warteschlange.isEmpty()) {
                System.out.println(warteschlange.size() + " Produkt(e) in " + gibName() + " Warteschlange");

                // Nächstes Produkt entnehmen (FIFO)
                Produkt zuProduzierendesProdukt = warteschlange.removeFirst();

                // Falls Maschine umgerüstet werden muss, 1 Sekunde Verzögerung hinzufügen
                if ((zuProduzierendesProdukt instanceof Standardtuer && lastProducedIsPremium) ||
                    (zuProduzierendesProdukt instanceof Premiumtuer && !lastProducedIsPremium)) {
                    try {
                        System.out.println("PRODUKTIONSINFO: " + gibName() + " muss umgerüstet werden.");
                        Thread.sleep(1000);
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                }

                // Letztes produziertes Produkt speichern
                lastProducedIsPremium = (zuProduzierendesProdukt instanceof Premiumtuer);

                // Produktionslogik des Produkts ausführen
                produziereProdukt(zuProduzierendesProdukt);
            }

            // Thread kurz schlafen lassen, bevor die nächste Überprüfung startet
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
                Thread.currentThread().interrupt(); // Interrupt-Status wiederherstellen
            }
        }
    }

    /**
     * Fügt ein Produkt zur Warteschlange hinzu.
     * 
     * @param produkt Das Produkt, das zur Warteschlange hinzugefügt werden soll
     */
    public void fuegeProduktHinzu(Produkt produkt) {
        warteschlange.add(produkt);
    }

    /**
     * Setzt die Produktionszeit des Roboters.
     * 
     * @param zeit Die Produktionszeit in Millisekunden
     * @throws IllegalArgumentException Wenn die Produktionszeit negativ ist
     */
    public void setzeProduktionsZeit(int zeit) {
        if (zeit < 0) {
            throw new IllegalArgumentException("Produktionszeit darf nicht negativ sein.");
        }
        this.produktionsZeit = zeit;
    }

    /**
     * Gibt den Namen des Roboters zurück.
     * 
     * @return Der Name des Roboters
     */
    public String gibName() {
        return name;
    }

    /**
     * Gibt die Warteschlange der Produkte zurück.
     * Diese Methode wird nur für Unit-Tests benötigt.
     * 
     * @return Die Warteschlange der Produkte
     */
    public LinkedList<Produkt> gibWarteschlange() {
        return warteschlange;
    }

    /**
     * Gibt die aktuelle Produktionszeit des Roboters zurück.
     * Diese Methode wird nur für Unit-Tests benötigt.
     * 
     * @return Die Produktionszeit in Millisekunden
     */
    public int gibProduktionszeit() {
        return produktionsZeit;
    }

    /**
     * Führt den Produktionsprozess für ein Produkt durch.
     * Diese Methode wird in den Unterklassen mit spezifischer Logik überschrieben.
     * 
     * @param produkt Das zu produzierende Produkt
     */
    public void produziereProdukt(Produkt produkt) {
        // Wird in den Unterklassen implementiert
    }
}
