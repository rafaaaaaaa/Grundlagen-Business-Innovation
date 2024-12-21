import java.util.LinkedList;
import java.util.concurrent.*;
import java.util.ArrayList;

/**
 * Die Klasse Produktions_Manager steuert die Produktionsprozesse, verwaltet Bestellungen
 * und koordiniert die Roboter in der Fabrik.
 * 
 * @author Rafael Estermann
 * @version 15.12.2024
 */
public class Produktions_Manager extends Thread {

    private Holzbearbeitungs_Roboter holzRoboter;
    private Montage_Roboter montageRoboter;
    private Lackier_Roboter lackierRoboter;
    private Verpackungs_Roboter verpackungsRoboter;
    private Fabrik meineFabrik;
    private Lager meinLager;
    private LinkedList<Bestellung> zuVerarbeitendeBestellungen;
    private LinkedList<Bestellung> bestellungenInProduktion;

    /**
     * Konstruktor für die Klasse Produktions_Manager.
     * 
     * @param meineFabrik Die Fabrik, in der die Roboter operieren
     * @param meinLager Das Lager, aus dem die benötigten Materialien geliefert werden
     */
    public Produktions_Manager(Fabrik meineFabrik, Lager meinLager) {
        // Listen instanzieren
        zuVerarbeitendeBestellungen = new LinkedList<Bestellung>();
        bestellungenInProduktion = new LinkedList<Bestellung>();

        // Roboter instanzieren
        holzRoboter = new Holzbearbeitungs_Roboter("Holzbearbeitungs Roboter");
        montageRoboter = new Montage_Roboter("Montage Roboter");
        lackierRoboter = new Lackier_Roboter("Lackier Roboter");
        verpackungsRoboter = new Verpackungs_Roboter("Verpackungs Roboter");

        // Roboter-Threads starten
        holzRoboter.start();

        // Fabrik und Lager zuweisen
        this.meineFabrik = meineFabrik;
        this.meinLager = meinLager;
    }

    /**
     * Hauptmethode des Produktions-Managers, die kontinuierlich Bestellungen bearbeitet
     * und die Produktion steuert.
     */
    @Override
    public void run() {
        while (true) {
            // Prüfen, ob eine neue Bestellung eingetroffen ist
            if (!zuVerarbeitendeBestellungen.isEmpty()) {
                // Es wird immer nur eine Bestellung gleichzeitig bearbeitet
                if (bestellungenInProduktion.size() < 1) {
                    // Nächste Bestellung aus der Warteschlange holen
                    Bestellung bestellung = zuVerarbeitendeBestellungen.removeFirst();
                    // Bestellung in die Produktionsliste verschieben
                    bestellungenInProduktion.add(bestellung);
                    // Produktion starten
                    starteProduktion(bestellung);
                }
            }

            if (!zuVerarbeitendeBestellungen.isEmpty()) {
                System.out.println("PRODUKTIONSINFO: Zu verarbeitende Bestellungen " + zuVerarbeitendeBestellungen.size());
            }

            // Prüfen, ob Bestellungen in Produktion fertiggestellt wurden
            for (Bestellung bestellung : bestellungenInProduktion) {
                ArrayList<Produkt> produkteInBestellung = bestellung.gibBestellteProdukte();
                boolean alleProdukteProduziert = produkteInBestellung.stream().allMatch(prod -> prod.aktuellerZustand() == 2);

                if (alleProdukteProduziert) {
                    bestellung.setzeAlleProdukteProduziert();
                    System.out.println("Bestellung " + bestellung.gibBestellungsNr() + " bereit zum Versand");
                }
            }

            // Entfernen von fertiggestellten Bestellungen
            bestellungenInProduktion.removeIf(Bestellung::gibAlleProdukteProduziert);

            // Kurzes Warten, bevor die nächste Iteration startet
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
                Thread.currentThread().interrupt(); // Interrupt-Status wiederherstellen
            }
        }
    }

    /**
     * Fügt eine neue Bestellung zur Warteschlange hinzu.
     * 
     * @param bestellung Die hinzuzufügende Bestellung
     */
    public void fuegeZuVerarbeitendeBestellungHinzu(Bestellung bestellung) {
        zuVerarbeitendeBestellungen.add(bestellung);
    }

    /**
     * Gibt die Liste der zu verarbeitenden Bestellungen zurück.
     * Diese Methode wird nur für Unit-Tests benötigt.
     * 
     * @return Die Liste der zu verarbeitenden Bestellungen
     */
    public LinkedList<Bestellung> gibZuVerarbeitendeBestellungen() {
        return zuVerarbeitendeBestellungen;
    }

    /**
     * Gibt die Liste der Bestellungen in Produktion zurück.
     * Diese Methode wird nur für Unit-Tests benötigt.
     * 
     * @return Die Liste der Bestellungen in Produktion
     */
    public LinkedList<Bestellung> gibBestellungenInProduktion() {
        return bestellungenInProduktion;
    }
    
    public Holzbearbeitungs_Roboter gibHolzRoboter() {
        return holzRoboter;
    }
    
    public Montage_Roboter gibMontageRoboter() {
        return montageRoboter;
    }
    
    public Lackier_Roboter gibLackierRoboter() {
        return lackierRoboter;
    }
    
    public Verpackungs_Roboter gibVerpackungsRoboter() {
        return verpackungsRoboter;
    }


    /**
     * Startet die Produktion für eine Bestellung.
     * 
     * @param bestellung Die Bestellung, deren Produkte produziert werden sollen
     */
    private void starteProduktion(Bestellung bestellung) {
        // Materialien aus dem Lager liefern
        meinLager.wareLiefern(bestellung);

        for (Produkt produkt : bestellung.gibBestellteProdukte()) {
            // Produkt auf Status "In Produktion" setzen
            produkt.zustandAendern(1);

            // Produktionsablauf für das Produkt festlegen
            produkt.setzteProduktionsAblauf(holzRoboter);
            //produkt.setzteProduktionsAblauf(montageRoboter);
            //produkt.setzteProduktionsAblauf(lackierRoboter);
            //produkt.setzteProduktionsAblauf(verpackungsRoboter);
            
            // Ersten Produktionsschritt starten
            produkt.naechsteProduktionsStation();
        }
    }
}
