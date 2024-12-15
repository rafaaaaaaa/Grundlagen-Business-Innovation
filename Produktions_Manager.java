import java.util.LinkedList;
import java.util.concurrent.*;
import java.util.ArrayList;

/**
 * Beschreiben Sie hier die Klasse Produktions_Manager.
 * 
 * @author Rafael Estermann 
 * @version 15.12.2024
 */
public class Produktions_Manager extends Thread
{
    private Holzbearbeitungs_Roboter holzRoboter;
    private Montage_Roboter montageRoboter;
    private Lackier_Roboter lackierRoboter;
    private Verpackungs_Roboter verpackungsRoboter;
    private Fabrik meineFabrik;
    private Lager meinLager;
    private LinkedList<Bestellung> zuVerarbeitendeBestellungen;
    private LinkedList<Bestellung> bestellungenInProduktion;
    
    /**
     * Konstruktor für Objekte der Klasse Produktions_Manager
     */
    public Produktions_Manager(Fabrik meineFabrik, Lager meinLager)
    {
        //Listen instanzieren
        zuVerarbeitendeBestellungen = new LinkedList<Bestellung>();
        bestellungenInProduktion = new LinkedList<Bestellung>();
        
        //Roboter instanzieren
        holzRoboter = new Holzbearbeitungs_Roboter("Holzbearbeitungs Roboter");
        montageRoboter = new Montage_Roboter("Montage Roboter");   
        lackierRoboter = new Lackier_Roboter("Lackier Roboter");
        verpackungsRoboter = new Verpackungs_Roboter("Verpackungs Roboter");
        
        //Exemplarisches Starten Holz Roboter Threads
        holzRoboter.start();  
        
        //Fabrik und Lager assignen
        this.meineFabrik = meineFabrik;
        this.meinLager = meinLager;
    }   
    
    @Override
    public void run() {
          while (true) {
                // Prüfen, ob eine neue Bestellung eingetroffen ist
                if (!zuVerarbeitendeBestellungen.isEmpty()) {
                    // Die nächste Bestellung aus der Liste der zu verarbeitenden Bestellungen entnehmen (First In, First Out)
                    Bestellung bestellung = zuVerarbeitendeBestellungen.removeFirst();
                                        
                    // Bestellung in die Liste der Bestellungen in Produktion verschieben
                    bestellungenInProduktion.add(bestellung);
                    
                    // Produktion starten 
                    starteProduktion(bestellung);
                }           
                
                if(zuVerarbeitendeBestellungen.size() != 0){
                    System.out.println("Zu verarbeitende Bestellungen " + zuVerarbeitendeBestellungen.size());
                }
                               
                //Prüfen ob bestehende Produkte bestehender Bestellungen bereits fertig produziert sind
                for(Bestellung bestellung : bestellungenInProduktion){
                    ArrayList<Produkt> produkteInBestellung = bestellung.gibBestellteProdukte();
                    boolean alleProdukteProduziert = produkteInBestellung.stream().allMatch(prod -> prod.aktuellerZustand() == 2);
                    
                    if(alleProdukteProduziert)
                    {
                        bestellung.setzeAlleProdukteProduziert();    
                        System.out.println("Bestellung " + bestellung.gibBestellungsNr() + " bereit zum Versand");
                    }   
                }
                
                bestellungenInProduktion.removeIf(bestellung -> bestellung.gibAlleProdukteProduziert());                
            
                // Den Thread eine kurze Zeit (1s) schlafen lassen, bis zur nächsten Überprüfung
                try {
                    Thread.sleep(1000); 
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                    // Hier könnte man den Interrupt-Status wiederherstellen oder Aufräumarbeiten ausführen
                    Thread.currentThread().interrupt();
                }
            }
    }
    
    public void fuegeZuVerarbeitendeBestellungHinzu(Bestellung bestellung)
    {
        zuVerarbeitendeBestellungen.add(bestellung);
    }
    
    private void starteProduktion(Bestellung bestellung){
        meinLager.wareLiefern(bestellung);
        
        for (Produkt produkt : bestellung.gibBestellteProdukte()) {
            produkt.zustandAendern(1);
            
            //Setze Produktionsablauf (Aufgrund Aufgabeneinschränkung nur Holz Roboter)
            produkt.setzteProduktionsAblauf(holzRoboter);  
            //produkt.setzteProduktionsAblauf(montageRoboter);
            //produkt.setzteProduktionsAblauf(lackierRoboter);
            //produkt.setzteProduktionsAblauf(verpackungsRoboter);
            
            //Einleiten 1. Produktionsschritt (-> Holzverarbeitung).
            produkt.naechsteProduktionsStation();      
        }
    }
}
