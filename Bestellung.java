import java.util.ArrayList;

/**
 * Die Klasse Bestellung enthält die Details zu einer Bestellung.
 * Sie verwaltet die bestellten Produkte, die Bestellbestätigung, die Beschaffungszeit und die Lieferzeit.
 *
 * @author Rafael Estermann
 * @version 15.12.2024
 */
public class Bestellung {
    // Liste der bestellten Produkte
    private ArrayList<Produkt> bestellteProdukte;

    // Gibt an, ob die Bestellung bestätigt wurde
    private boolean bestellBestaetigung;

    // Beschaffungszeit in Tagen (-1: Initialwert)
    private int beschaffungsZeit;

    // Eindeutige Nummer der Bestellung
    private int bestellungsNr;

    // Anzahl der Standardtüren in der Bestellung
    private int anzahlStandardTueren;

    // Anzahl der Premiumtüren in der Bestellung
    private int anzahlPremiumTueren;

    // Lieferzeit in Tagen
    private float lieferZeit;

    // Gibt an, ob alle Produkte der Bestellung produziert wurden
    private boolean alleProdukteProduziert;

    /**
     * Konstruktor der Klasse Bestellung.
     * Initialisiert die Bestellung und überprüft die Bestellmenge.
     *
     * @param anzahlStandardTueren Anzahl der bestellten Standardtüren
     * @param anzahlPremiumTueren  Anzahl der bestellten Premiumtüren
     * @param bestellungsNr        Eindeutige Bestellnummer
     */
    public Bestellung(int anzahlStandardTueren, int anzahlPremiumTueren, int bestellungsNr) {
        this.bestellungsNr = bestellungsNr;
        this.beschaffungsZeit = -1;
        this.lieferZeit = -1;
        this.bestellteProdukte = new ArrayList<>();
        this.bestellBestaetigung = false;
        this.alleProdukteProduziert = false;

        if (anzahlStandardTueren < 0 || anzahlPremiumTueren < 0) {
            throw new IllegalArgumentException("Ungültige Bestellmenge. Kann nicht negativ sein.");
        } else if (anzahlStandardTueren == 0 && anzahlPremiumTueren == 0) {
            throw new IllegalArgumentException("Die Bestellung muss mindestens ein Produkt enthalten.");
        } else if (anzahlStandardTueren > 10_000 || anzahlPremiumTueren > 10_000) {
            throw new IllegalArgumentException("Bestellmenge ist zu groß. Maximal 10.000 pro Artikel.");
        } else {
            this.anzahlStandardTueren = anzahlStandardTueren;
            this.anzahlPremiumTueren = anzahlPremiumTueren;
            fuelleBestellteprodukte(anzahlStandardTueren, anzahlPremiumTueren);
        }
    }

    /**
     * Füllt die Liste der bestellten Produkte basierend auf der Anzahl von Standard- und Premiumtüren.
     *
     * @param anzahlStandardTueren Anzahl der bestellten Standardtüren
     * @param anzahlPremiumTueren  Anzahl der bestellten Premiumtüren
     */
    private void fuelleBestellteprodukte(int anzahlStandardTueren, int anzahlPremiumTueren) {
        for (int i = 0; i < anzahlStandardTueren; i++) {
            bestellteProdukte.add(new Standardtuer());
        }
        for (int i = 0; i < anzahlPremiumTueren; i++) {
            bestellteProdukte.add(new Premiumtuer());
        }
    }

    /**
     * Gibt den Bestellstatus zurück.
     *
     * @return true, wenn die Bestellung bestätigt wurde, andernfalls false.
     */
    public boolean gibBestellBestaetigung() {
        return bestellBestaetigung;
    }

    /**
     * Gibt die Beschaffungszeit in Tagen zurück.
     *
     * @return Die Beschaffungszeit der Bestellung.
     */
    public int gibBeschaffungsZeit() {
        return beschaffungsZeit;
    }

    /**
     * Setzt die Beschaffungszeit in Tagen.
     *
     * @param beschaffungsZeit Die neue Beschaffungszeit.
     */
    public void setzeBeschaffungsZeit(int beschaffungsZeit) {
        this.beschaffungsZeit = beschaffungsZeit;
    }

    /**
     * Gibt die Bestellnummer zurück.
     *
     * @return Die eindeutige Bestellnummer.
     */
    public int gibBestellungsNr() {
        return bestellungsNr;
    }

    /**
     * Gibt die Anzahl der bestellten Standardtüren zurück.
     *
     * @return Die Anzahl der Standardtüren.
     */
    public int gibAnzahlStandardTueren() {
        return anzahlStandardTueren;
    }

    /**
     * Gibt die Anzahl der bestellten Premiumtüren zurück.
     *
     * @return Die Anzahl der Premiumtüren.
     */
    public int gibAnzahlPremiumTueren() {
        return anzahlPremiumTueren;
    }

    /**
     * Bestätigt die Bestellung.
     */
    public void bestellungBestaetigen() {
        this.bestellBestaetigung = true;
    }

    /**
     * Gibt die Liste der bestellten Produkte zurück.
     *
     * @return Die Liste der Produkte.
     */
    public ArrayList<Produkt> gibBestellteProdukte() {
        return bestellteProdukte;
    }

    /**
     * Setzt die Lieferzeit für die Bestellung.
     *
     * @param lieferZeit Die neue Lieferzeit.
     */
    public void setzeLieferzeit(float lieferZeit) {
        this.lieferZeit = lieferZeit;
    }

    /**
     * Gibt die Lieferzeit der Bestellung zurück.
     *
     * @return Die Lieferzeit in Tagen.
     */
    public float gibLieferzeit() {
        return lieferZeit;
    }

    /**
     * Markiert, dass alle Produkte der Bestellung produziert wurden.
     */
    public void setzeAlleProdukteProduziert() {
        alleProdukteProduziert = true;
    }

    /**
     * Gibt zurück, ob alle Produkte der Bestellung produziert wurden.
     *
     * @return true, wenn alle Produkte produziert wurden, andernfalls false.
     */
    public boolean gibAlleProdukteProduziert() {
        return alleProdukteProduziert;
    }
}
