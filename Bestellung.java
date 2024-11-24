import java.util.ArrayList;

/**
 * Klasse Bestellung beinhaltet die bestellten Produkte und deren Eigenschaften,
 * wie z.B. Bestellnummer, Bestätigung, Beschaffungszeit und Lieferzeit.
 * Diese Klasse verwaltet auch die Anzahl und Art der bestellten Produkte.
 * 
 * @author Rafael Estermann
 * @version 23.11.2024
 */
public class Bestellung 
{
    // Die Liste bestellteProdukte enthält alle Produkte, die in dieser Bestellung enthalten sind.
    private ArrayList<Produkt> bestellteProdukte;

    // Die bestellBestaetigung gibt an, ob eine Bestellung bestätigt wurde oder nicht (boolean).
    private boolean bestellBestaetigung;

    // Die Beschaffungszeit gibt an, wie lange die Lieferzeit (in Tagen) für die Produkte ist.
    // -1 ist der Initialisierungswert, bevor eine konkrete Zeit gesetzt wird.
    private int beschaffungsZeit;

    // Jede Bestellung erhält eine eindeutige Bestellnummer.
    private int bestellungsNr;  

    // Anzahl der bestellten Standardtüren in dieser Bestellung.
    private int anzahlStandardTueren;  

    // Anzahl der bestellten Premiumtüren in dieser Bestellung.
    private int anzahlPremiumTueren;

    // Die Lieferzeit der Bestellung, die die gesamte Zeit bis zur Lieferung angibt.
    private float lieferZeit;

    /**
     * Konstruktor für die Klasse Bestellung.
     * Hier werden alle globalen Variablen initialisiert und die Produkte zur Bestellliste hinzugefügt.
     * 
     * @param anzahlStandardTueren Anzahl der bestellten Standardtüren
     * @param anzahlPremiumTueren Anzahl der bestellten Premiumtüren
     * @param bestellungsNr Zugeordnete Bestellnummer
     */
    public Bestellung(int anzahlStandardTueren, int anzahlPremiumTueren, int bestellungsNr) 
    {
       // initialise instance variables
        this.bestellungsNr = bestellungsNr;
        this.beschaffungsZeit = -1; //Standart geht man von genügend Bestand aus.
        this.bestellteProdukte = new ArrayList<Produkt>();
        this.bestellBestaetigung = false; // this is optional
        this.lieferZeit = 1; //Initialisierung Lieferzeit (wird anschliessend überschrieben)
        
        if (anzahlStandardTueren < 0 || anzahlPremiumTueren < 0) {
            throw new IllegalArgumentException("Ungültige Bestellmenge. Kann nicht negativ sein.");
            // System.out.println("Ungültige Bestellmenge. Kann nicht negativ sein.");
        } else if (anzahlStandardTueren == 0 && anzahlPremiumTueren == 0) {
            throw new IllegalArgumentException("Die Bestellung muss mindestens ein Produkt enthalten.");
            // System.out.println("Die Bestellung muss mindestens ein Produkt enthalten.");
        } else if (anzahlStandardTueren > 10_000 || anzahlPremiumTueren > 10_000) {
            throw new IllegalArgumentException("Bestellmenge ist zu gross. Maximal 10 Tausend pro Artikel.");
            // System.out.println("Bestellmenge ist zu gross. Maximal 10 Tausend pro Artikel.");
        } else {
            this.anzahlStandardTueren = anzahlStandardTueren;
            this.anzahlPremiumTueren = anzahlPremiumTueren;
            fuelleBestellteprodukte(anzahlStandardTueren, anzahlPremiumTueren);
        }

    }

    /**
     * Füllt die Liste der bestellten Produkte mit der angegebenen Anzahl an Standard- und Premiumtüren.
     * 
     * @param anzahlStandardTueren Anzahl der bestellten Standardtüren
     * @param anzahlPremiumTueren Anzahl der bestellten Premiumtüren
     */ 
    private void fuelleBestellteprodukte(int anzahlStandardTueren, int anzahlPremiumTueren) 
    {
        int standardTueren = 0;
        int premiumTueren = 0;

        // Fügt die bestellte Anzahl an Standardtüren zur Produktliste hinzu
        while (standardTueren < anzahlStandardTueren) {
            bestellteProdukte.add(new Standardtuer());
            standardTueren++;
        }

        // Fügt die bestellte Anzahl an Premiumtüren zur Produktliste hinzu
        while (premiumTueren < anzahlPremiumTueren) {
            bestellteProdukte.add(new Premiumtuer());
            premiumTueren++;
        }
    }

    /**
     * Gibt an, ob die Bestellung bestätigt wurde.
     * 
     * @return true, wenn die Bestellung bestätigt ist, andernfalls false
     */
    public boolean gibBestellBestaetigung() 
    {
        return this.bestellBestaetigung;
    }

    /**
     * Gibt die Beschaffungszeit für die Bestellung zurück.
     * 
     * @return die Beschaffungszeit in Tagen
     */
    public int gibBeschaffungsZeit() 
    {
        return this.beschaffungsZeit;
    }

    /**
     * Setzt die Lieferzeit der Bestellung.
     * 
     * @param lieferZeit Die zu setzende Lieferzeit in Tagen
     */
    public void setzeLieferzeit(float lieferZeit)
    {
        this.lieferZeit = lieferZeit;
    }
    
    /**
     * Gibt die Lieferzeit der Bestellung zurück.
     * 
     * @return die Lieferzeit in Tagen
     */
    public float gibLieferzeit()
    {
        return lieferZeit;
    }

    /**
     * Setzt die Beschaffungszeit für die Bestellung.
     * 
     * @param beschaffungsZeit Die zu setzende Beschaffungszeit in Tagen
     */
    public void setzeBeschaffungsZeit(int beschaffungsZeit) 
    {
        this.beschaffungsZeit = beschaffungsZeit;
    }
    
    /**
     * Gibt eine Liste der bestellten Produkte zurück.
     * 
     * @return eine ArrayList der bestellten Produkte
     */
    public ArrayList<Produkt> liefereBestellteProdukte() 
    {
        return bestellteProdukte;
    }

    /**
     * Gibt die Bestellnummer der Bestellung zurück.
     * 
     * @return die Bestellnummer
     */
    public int gibBestellungsNr() 
    {
        return this.bestellungsNr;
    }

    /**
     * Gibt die Anzahl der bestellten Standardtüren zurück.
     * 
     * @return die Anzahl der Standardtüren
     */
    public int gibAnzahlStandardTueren() 
    {
        return this.anzahlStandardTueren;
    }

    /**
     * Gibt die Anzahl der bestellten Premiumtüren zurück.
     * 
     * @return die Anzahl der Premiumtüren
     */
    public int gibAnzahlPremiumTueren() 
    {
        return this.anzahlPremiumTueren;
    }

    /**
     * Bestätigt die Bestellung, indem der Bestellstatus auf "bestätigt" gesetzt wird.
     */
    public void bestellungBestaetigen() 
    {
        this.bestellBestaetigung = true;
    } 
}
