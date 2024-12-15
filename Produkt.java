import java.util.LinkedList;

/**
 * Klasse Produkt ist eine Superklasse für die Klassen Premiumtuer und
 * Standardtuer
 *
 * @author Rafael Estermann
 * @version 15.12.2024
 */
public class Produkt {
    /**
     * Variable zustand gibt den Zustand des bestellten Produktes an
     * Mögliche Zustände:
     * 0: Bestellt
     * 1: In Produktion
     * 2: Bereit für Auslieferung
     * 3: Ausgeliefert
     */
    private int zustand; // default 0, sonst 1, 2, oder 3
    private LinkedList<Roboter> produktionsAblauf;
    /**
     * Konstruktor
     */
    public Produkt() {
        produktionsAblauf = new LinkedList<Roboter>();
        this.zustand = 0; 
    }

    /**
     * Aktueller Zustand des Produkts wird ausgegeben
     *
     * @return zustand Zustand des Produkt wird ausgegeben
     */
    public int aktuellerZustand() {
        return this.zustand;
    }

    /**
     * Zustand des Produkts wird geändert
     *
     * @param zustand : neuer Zustand des Produkt
     */
    public void zustandAendern(int zustand) {
        this.zustand = zustand;
    }
    
    public void setzteProduktionsAblauf(Roboter roboter){
        produktionsAblauf.add(roboter);
    } 
    
    //wird nur für Unit Tests benötigt
    public LinkedList<Roboter> gibProduktionsAblauf()
    {
       return produktionsAblauf;
    } 
    
    public void naechsteProduktionsStation() {
        //Logik: wenn nichts mehr auf dem Produktionsablauf-Plan ansteht, ist das Produkt fertig produziert
        if(produktionsAblauf.isEmpty()){
           zustandAendern(2);
           return;
        }
        
        //Ein Produktionsablauf-Schritt wird entfernt, nachdem er abgearbeitet wurde.
        Roboter naechsterSchrittRoboter = produktionsAblauf.removeFirst();
        naechsterSchrittRoboter.fuegeProduktHinzu(this); 
    }
}
