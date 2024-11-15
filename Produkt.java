
/**
 * Klasse Produkt ist eine Superklasse für die Klassen Premiumtuer und Standardtuer
 *
 * @author Rafael Estermann
 * @version 23.11.2024
 */
public class Produkt 
{
    /** Variable zustand gibt den Zustand des bestellten Produktes an
    * Mögliche Zustände: 
    * 0: Bestellt
    * 1: In Produktion 
    * 2: Bereit für Auslieferung 
    * 3: Ausgeliefert
    */
    private int zustand; // default 0, sonst 1, 2, oder 3

    /**
     * Konstruktor
     */
    public Produkt() 
    {
         this.zustand = 0; 
    }

    /**
     * Aktueller Zustand des Produkts wird ausgegeben
     *
     * @return zustand Zustand des Produkt wird ausgegeben
     */
    public int aktuellerZustand() 
    {
        return this.zustand;
    }

    /**
     * Zustand des Produkts wird geändert
     *
     * @param  zustand : neuer Zustand des Produkt
     */
    public void zustandAendern(int zustand) 
    {
        this.zustand = zustand;
    }
}
