import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * Klasse Fabrik beinhaltet die Methoden der Fabrik
 *
 * @author Alex Marchese
 * @version 04.12.2024
 */
public class Fabrik {
    // Die Liste bestellteProdukte enthält alle Produkte, welche bestellt worden
    // sind
    private ArrayList<Bestellung> bestellungen;
    private int bestellungsNr;
    private Lager lager;
    private Produktions_Manager produktionsManager;
    private ExecutorService executorService;
    private int lagerAuffuellungen; // Gibt an wie häufig das Lager aufgefüllt wurde (wird für die Unittests
                                    // benötigt)

    /**
     * Konstruktor der Klasse
     * Hier werden alle globale Variablen initialisiert
     */
    public Fabrik() {
        bestellungen = new ArrayList<Bestellung>();
        bestellungsNr = 0; // optional
        executorService =  Executors.newSingleThreadExecutor();
        lager = new Lager(executorService);
        produktionsManager = new Produktions_Manager(this, lager);

        lagerAuffuellungen = 0;
        
        //Starten des Produktionsmanagers
        produktionsManager.start();  // <-- This actually launches the thread
    }

    /**
     * Mit dieser Methode wird eine Bestellung aufgegeben
     * 
     * @param standardTueren Anzahl zu bestellender Standardtüren
     * @param premiumTueren  Anzahl zu bestellender Premiumtüren
     */
    public void bestellungAufgeben(int standardTueren, int premiumTueren) {

        // Fehlerbehandlung
        if (standardTueren < 0 || premiumTueren < 0) { // Sobald einer der Werte negativ ist
            System.out.println("\nUngültige Bestellmenge. Kann nicht negativ sein.");
        } else if (standardTueren == 0 && premiumTueren == 0) { // Wenn beide Werte Null sind
            System.out.println("\nDie Bestellung muss mindestens ein Produkt enthalten.");
        } else if (standardTueren > 10_000 || premiumTueren > 10_000) { // Sobald einer der Werte mehr als 10k ist
            System.out.println("\nBestellmenge ist zu gross. Maximal 10 Tausend pro Artikel.");
        } else {

            bestellungsNr++; // can be done also with the other ways (+= 1 or bestellungsNr = bestellungsNr + 1)
            Bestellung bestellung = new Bestellung(standardTueren, premiumTueren, bestellungsNr);

            // Berechnung und Setzen der Beschaffungszeit
            int beschaffungsZeitMaterialien = lager.gibBeschaffungsZeit(bestellung);
            bestellung.setzeBeschaffungsZeit(beschaffungsZeitMaterialien);

            // Der Lagerbestand wird aufgefüllt, sobald der Bestand für einen Auftrag nicht
            // mehr ausreicht. Konkret: wenn die Beschaffungszeit > 0 ist.
            // Andere Ansätze sind auch zugelassen, solange sie begründet und damit
            // nachvollziehnar sind
            if (beschaffungsZeitMaterialien > 0) {
                this.lagerAuffuellen();
            }

            // Brechnung und Setzen der Lieferzeit. Lieferzeit = beschaffungsZeitMaterialien
            // + totale Produktionszeit + Standardlieferzeit (1 Tag)
            bestellung.setzeLieferzeit(bestellung.gibBeschaffungsZeit() +
                    (Standardtuer.gibProduktionszeit() * standardTueren
                            + Premiumtuer.gibProduktionszeit() * premiumTueren) / (60f * 24)
                    + 1); // 60f, da sonst die Lösung keinen Dezimalwert enthält. Nicht 60.0 da sonst
                          // double (man müsste es dann zu double konvertieren)

            // Bestellung bestätigen
            bestellung.bestellungBestaetigen();
            System.out.println("Bestellung aufgegeben");

            // Bestellung wird hinzugefügt
            bestellungen.add(bestellung);
            
            // Zu Verarbeitende Bestellung zu Produktion hinzufügen
            produktionsManager.fuegeZuVerarbeitendeBestellungHinzu(bestellung);
        }
    }  

    /**
     * Mit dieser Methode werden alle Bestellungen ausgegeben
     * 
     */
    public void bestellungenAusgeben() {
        System.out.println("\nIn der Fabrik gibt es gerade folgende Bestellungen.");
        for (Bestellung bestellung : bestellungen) {
            System.out.println("Bestellung Nummer " + bestellung.gibBestellungsNr()
                    + " Standardtüren: " + bestellung.gibAnzahlStandardTueren()
                    + " Premiumtüren: " + bestellung.gibAnzahlPremiumTueren()
                    + " Beschaffungszeit: " + Math.round(bestellung.gibBeschaffungsZeit()) + " Tage "
                    + " Lieferzeit: " + Math.round(bestellung.gibLieferzeit()) + " Tage "
                    + " Bestellbestätigung: " + bestellung.gibBestellBestaetigung());
        }
    }

    /**
     * Methode lagerAuffuellen sorgt dafür, dass das Lager durch Aufruf der Methode
     * des Lagers aufgefüllt wird und dass der Lagerbestand angegeben wird
     */
    public void lagerAuffuellen() {
        lager.lagerAuffuellen();
        lager.lagerBestandAusgeben();
        this.lagerAuffuellungen++;
    }

    /**
     * Mit dieser Methode wird die Arrayliste mit den Bestellungen zurückgegeben.
     * Wird für die Unit Testklasse FabrikTest verwendet
     * 
     * @return bestellteProdukte wird retourniert
     */
    public ArrayList<Bestellung> gibBestellungen() {
        return this.bestellungen;
    }

    /**
     * Mit dieser Methode wird die Instanz des Lagers zurückgegeben.
     * Wird für die Unit Testklasse FabrikTest verwendet
     * 
     * @return bestellteProdukte wird retourniert
     */
    public Lager gibLager() {
        return this.lager;
    }

    /**
     * Diese Methode wird verwendet, um anzugeben, wie oft das Lager aufgefüllt
     * wurde.
     * Wird für die Unit Testklasse FabrikTest verwendet
     * 
     * @return lagerAuffuellungen wird retourniert
     */
    public int gibLagerAuffuellungen() {
        return this.lagerAuffuellungen;
    }

}
