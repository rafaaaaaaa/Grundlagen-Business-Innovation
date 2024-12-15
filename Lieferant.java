/**
 * Diese Klasse wird verwendet, um die Lieferzeiten des Lieferanten und die
 * Bestellung über diesen zu simulieren.
 *
 * @author Rafael Estermann
 * @version 15.12.2024
 */
public class Lieferant extends Thread {

    /**
     * Konstruktor der Klasse Lieferant.
     * Erstellt eine Instanz des Lieferanten.
     */
    public Lieferant() {
    }

    /**
     * Simuliert die Bestellung beim Lieferanten und fügt eine Lieferzeit von
     * zwei Tagen (simuliert als 48 Sekunden) hinzu.
     * 
     * @param holzEinheiten   Anzahl der bestellten Holzeinheiten
     * @param schrauben       Anzahl der bestellten Schrauben
     * @param farbEinheiten   Anzahl der bestellten Farbeinheiten
     * @param kartonEinheiten Anzahl der bestellten Kartoneinheiten
     * @param glasEinheiten   Anzahl der bestellten Glaseinheiten
     * @return true, wenn die Bestellung erfolgreich abgeschlossen wurde, false bei Unterbrechung
     */
    public boolean wareBestellen(int holzEinheiten, int schrauben, int farbEinheiten, int kartonEinheiten, int glasEinheiten) {
        System.out.println("Bestellung aufgegeben. Lieferung dauert 2 Tage (48 Sekunden).");

        try {
            // Zeitbeschleunigung: 1 Stunde = 1 Sekunde. 2 Tage = 48 Stunden = 48 Sekunden = 48000 ms.
            Thread.sleep(48000); 
        } catch (InterruptedException e) {
            System.out.println("Lieferzeit wurde unterbrochen.");
            return false;
        }

        System.out.println("Bestellung geliefert: Die Teile sind im Lager angekommen.");
        return true;
    }
}
