/**
 * Diese Klasse wird verwendet, um die Lieferzeiten des Lieferanten und die
 * Bestellung über diesen zu simulieren.
 *
 * @author Rafael Estermann
 * @version 14.12l.2024
 */
public class Lieferant extends Thread {

    /**
     * Konstruktor der Klasse Lieferant
     */
    public Lieferant() {
    }

    /**
     * Mit dieser Methode wird die Bestellung bei dem Lieferanten simuliert
     * und eine Lieferzeit von zwei Tagen (simuliert als 48 Sekunden) hinzugefügt.
     * 
     * @param holzEinheiten   Anzahl bestellter Holzeinheiten
     * @param schrauben       Anzahl bestellter Schrauben
     * @param farbEinheiten   Anzahl bestellter Farbeinheiten
     * @param kartonEinheiten Anzahl bestellter Kartoneinheiten
     * @param glasEinheiten   Anzahl bestellter Glaseinheiten
     * @return true, wenn die Bestellung erfolgreich ist
     */
    public boolean wareBestellen(int holzEinheiten, int schrauben, int farbEinheiten, int kartonEinheiten, int glasEinheiten) {
        System.out.println("Bestellung aufgegeben. Lieferung dauert 2 Tage (48 Sekunden).");

        try {
            // Zeit beschleunigen: 1 Stunde = 1 Sekunde. 2 Tage = 48 Stunden = 48 Sekunden = 48000 Ms.
            Thread.sleep(48000); 
        } catch (InterruptedException e) {
            System.out.println("Lieferzeit wurde unterbrochen.");
            return false;
        }

        System.out.println("Bestellung geliefert: Die Teile sind im Lager angekommen.");
        return true;
}
}
