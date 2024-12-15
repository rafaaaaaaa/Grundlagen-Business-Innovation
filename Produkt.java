import java.util.LinkedList;

/**
 * Die Klasse Produkt ist eine Superklasse für die Klassen Premiumtuer und Standardtuer.
 * Sie repräsentiert den Zustand eines Produkts sowie dessen Produktionsablauf.
 *
 * @author Rafael Estermann
 * @version 15.12.2024
 */
public class Produkt {

    /**
     * Variable zustand gibt den Zustand des bestellten Produktes an.
     * Mögliche Zustände:
     * 0: Bestellt
     * 1: In Produktion
     * 2: Bereit für Auslieferung
     * 3: Ausgeliefert
     */
    private int zustand; // Standardwert 0
    private LinkedList<Roboter> produktionsAblauf;

    /**
     * Konstruktor der Klasse Produkt.
     * Initialisiert den Zustand des Produkts und den Produktionsablauf.
     */
    public Produkt() {
        produktionsAblauf = new LinkedList<>();
        this.zustand = 0;
    }

    /**
     * Gibt den aktuellen Zustand des Produkts zurück.
     *
     * @return Der aktuelle Zustand des Produkts (0, 1, 2 oder 3).
     */
    public int aktuellerZustand() {
        return this.zustand;
    }

    /**
     * Setzt den Zustand des Produkts auf einen neuen Wert.
     *
     * @param zustand Der neue Zustand des Produkts.
     */
    public void zustandAendern(int zustand) {
        this.zustand = zustand;
    }

    /**
     * Fügt einen Roboter zum Produktionsablauf des Produkts hinzu.
     *
     * @param roboter Der Roboter, der zum Produktionsablauf hinzugefügt wird.
     */
    public void setzteProduktionsAblauf(Roboter roboter) {
        produktionsAblauf.add(roboter);
    }

    /**
     * Gibt den Produktionsablauf des Produkts zurück.
     * Diese Methode wird hauptsächlich für Unit-Tests verwendet.
     *
     * @return Eine Liste von Robotern, die Teil des Produktionsablaufs sind.
     */
    public LinkedList<Roboter> gibProduktionsAblauf() {
        return produktionsAblauf;
    }

    /**
     * Führt den nächsten Produktionsschritt für das Produkt aus.
     * Wenn der Produktionsablauf abgeschlossen ist, wird der Zustand des Produkts auf 2 gesetzt.
     */
    public void naechsteProduktionsStation() {
        // Wenn der Produktionsablauf abgeschlossen ist, setze den Zustand auf 2 (bereit zur Auslieferung)
        if (produktionsAblauf.isEmpty()) {
            zustandAendern(2);
            return;
        }

        // Entferne den nächsten Roboter aus der Liste und füge das Produkt hinzu
        Roboter naechsterSchrittRoboter = produktionsAblauf.removeFirst();
        naechsterSchrittRoboter.fuegeProduktHinzu(this);
    }
}
