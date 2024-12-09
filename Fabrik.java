import java.util.ArrayList;

/**
 * Klasse Fabrik beinhaltet die Methoden der Fabrik zur Verwaltung und Bearbeitung von Bestellungen.
 * Diese Klasse enthält Methoden zur Bestellungsverwaltung und zur Berechnung von Produktions- und Lieferzeiten.
 * 
 * @author Rafael Estermann
 * @version 23.11.2024
 */
public class Fabrik 
{
    // Die Liste bestellungen enthält alle Bestellungen, die in der Fabrik aufgegeben wurden.
    private ArrayList<Bestellung> bestellungen;

    // Die Variable bestellungsNr hält die aktuelle Bestellnummer und wird für jede neue Bestellung erhöht.
    private int bestellungsNr;

    // Das Lager-Objekt verwaltet die Lagerbestände und Bereitstellungszeiten für Bestellungen.
    private Lager lager;
    
    /**
     * Konstruktor der Klasse Fabrik
     * Initialisiert die globalen Variablen und erstellt ein neues Lager-Objekt.
     */
    public Fabrik() 
    {
        bestellungen = new ArrayList<Bestellung>();
        lager = new Lager();
        bestellungsNr = 0; // Initialisiert die Bestellnummer zu Beginn auf 0.
    }

    /**
     * Mit dieser Methode wird eine Bestellung aufgegeben. Die Bestellmenge für Standard- und Premiumtüren
     * wird angegeben und daraufhin eine Bestellung erstellt, die Beschaffungs- und Lieferzeiten berechnet
     * und gegebenenfalls das Lager auffüllt.
     * 
     * @param standardTueren Anzahl der zu bestellenden Standardtüren
     * @param premiumTueren Anzahl der zu bestellenden Premiumtüren
     */
    public void bestellungAufgeben(int standardTueren, int premiumTueren) 
    {
        //Prüfung, ob Lagerbestand "niedrig" (= min. 1 Material weniger als 20%) ist
        if(lager.istUnterMinimalbestand()) {
           //Falls unter minimal Bestand, wird das Lager wieder komplett aufgefüllt
           lager.lagerAuffuellen();
        }
        
        // Überprüft auf gültige Bestellmengen
        if (standardTueren < 0 || premiumTueren < 0) { 
            System.out.println("\nUngültige Bestellmenge. Kann nicht negativ sein.");
        } else if (standardTueren == 0 && premiumTueren == 0) { 
            System.out.println("\nDie Bestellung muss mindestens ein Produkt enthalten.");
        } else if (standardTueren > 10_000 || premiumTueren > 10_000) { 
            System.out.println("\nBestellmenge ist zu gross. Maximal 10 Tausend pro Artikel.");
        } else {
        
            // Erhöht die Bestellnummer für die neue Bestellung
            bestellungsNr = bestellungsNr + 1;  
        
            // Erstellt eine neue Bestellung mit der aktuellen Bestellnummer und Türmengen
            Bestellung neueBestellung = new Bestellung(standardTueren, premiumTueren, bestellungsNr); 
            
            // Berechnet und setzt die Beschaffungszeit für die Bestellung basierend auf dem Lagerbestand
            int beschaffungsZeit = lager.gibBeschaffungszeit(neueBestellung);
            neueBestellung.setzeBeschaffungsZeit(beschaffungsZeit);    
            
            if(beschaffungsZeit == 2)  {
              lagerAuffuellen();  //wenn mehr als 20% von allen Materialien vorhanden sind im Lager (siehe Prüfung am Anfang der Methode), aber die Bestellung trotzdem mehr Material erfordert, wird ebenfalls nachbestellt. 
            }
            
            // Berechnet und setzt die gesamte Lieferzeit der Bestellung
            float lieferzeit = berechneProduktionszeit(neueBestellung) + beschaffungsZeit + 1;
            neueBestellung.setzeLieferzeit(lieferzeit);
             
            // Fügt die neue Bestellung zur Liste der Bestellungen hinzu
            bestellungen.add(neueBestellung);   
            
            // Bestätigt die Bestellung
            bestellungBestaetigen(neueBestellung);
        }
    }
    
    /**
     * Ruft Lager.lagerAuffuellen auf (füllt also wieder auf die Maximum Kapazität auf)
     */
    public void lagerAuffuellen()
    {
        lager.lagerAuffuellen();
    }

    /**
     * Gibt alle Bestellungen aus, die derzeit in der Fabrik bestehen.
     */
    public void bestellungAusgeben() 
    {
        System.out.println("\nIn der Fabrik gibt es gerade folgende Bestellungen.");
        for (Bestellung bestellung : bestellungen) {
            System.out.println("Bestellung Nummer " + bestellung.gibBestellungsNr()
                    + " Standardtüren: " + bestellung.gibAnzahlStandardTueren()
                    + " Premiumtüren: " + bestellung.gibAnzahlPremiumTueren()
                    + " Beschaffungszeit: " + bestellung.gibBeschaffungsZeit()
                    + " Lieferzeit: " + bestellung.gibLieferzeit()
                    + " Bestellbestätigung: " + bestellung.gibBestellBestaetigung());
        }
    }
    
    /**
     * Gibt die Liste aller Bestellungen zurück. Diese Methode wird für Unit-Tests verwendet.
     * 
     * @return Die ArrayList aller Bestellungen.
     */
    public ArrayList<Bestellung> gibBestellungen()
    {
        return this.bestellungen;
    }
    
    /**
     * Berechnet die Produktionszeit für eine gegebene Bestellung, basierend auf der Anzahl der Standard-
     * und Premiumtüren und deren jeweiliger Produktionszeit.
     * 
     * @param bestellung Die Bestellung, für die die Produktionszeit berechnet wird.
     * @return anzahlTageProduktionszeit Die gesamte Produktionszeit in Tagen.
     */
    private float berechneProduktionszeit(Bestellung bestellung)
    {
        int anzahlMinutenProduktionsZeit = (bestellung.gibAnzahlStandardTueren() * Standardtuer.gibProduktionszeit()) 
                + (bestellung.gibAnzahlPremiumTueren() * Premiumtuer.gibProduktionszeit());     
        float anzahlTageProduktionszeit = (float)anzahlMinutenProduktionsZeit / 1440;    
        return anzahlTageProduktionszeit;
    }
    
    /**
     * Bestätigt die Bestellung und gibt die Bestellbestätigung aus
     * 
     * @param bestellung Die Bestellung, die bestätigt werden muss
        */
    private void bestellungBestaetigen(Bestellung bestellung) {
        bestellung.bestellungBestaetigen();
        System.out.println("\nBestellung " + bestellung.gibBestellungsNr() + " bestätigt. Die Lieferzeit beträgt " + bestellung.gibLieferzeit() + " Tage");
    }
}
