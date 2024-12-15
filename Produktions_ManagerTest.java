import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.concurrent.*;

/**
 * The test class Produktions_ManagerTest.
 *
 * @author Rafael Estermann
 * @version 15.12.2024
 */
public class Produktions_ManagerTest
{
    @Test
    public void testFuegeZuVerarbeitendeBestellungHinzu() {
        // Arrange
        Produktions_Manager manager = new Produktions_Manager(new Fabrik(), new Lager(Executors.newSingleThreadExecutor()));
        Bestellung bestellung = new Bestellung(1, 0, 123);
    
        // Act
        manager.fuegeZuVerarbeitendeBestellungHinzu(bestellung);
    
        // Assert
        assertEquals(1, manager.gibZuVerarbeitendeBestellungen().size(),
            "Die Liste der zu verarbeitenden Bestellungen sollte einen Eintrag haben.");
    }
    
    @Test
    public void testZuVerarbeitendeBestellungenSinktMitDerZeit() throws InterruptedException {
        // Arrange
        Produktions_Manager manager = new Produktions_Manager(new Fabrik(), new Lager(Executors.newSingleThreadExecutor()));
        Bestellung bestellung = new Bestellung(1, 0, 123);
        manager.fuegeZuVerarbeitendeBestellungHinzu(bestellung);
    
        // Act
        Thread managerThread = new Thread(manager);
        managerThread.start();
        Thread.sleep(1500); // Warten, bis die Bestellung verarbeitet wird
        managerThread.interrupt(); // Manager-Thread beenden
    
        // Assert
        assertTrue(manager.gibZuVerarbeitendeBestellungen().isEmpty(),
            "Die Liste der zu verarbeitenden Bestellungen sollte leer sein, nachdem sie abgearbeitet wurde.");
    }
    
    @Test
    public void testBestellungWirdInProduktionVerschoben() throws InterruptedException {
        // Arrange
        Produktions_Manager manager = new Produktions_Manager(new Fabrik(), new Lager(Executors.newSingleThreadExecutor()));
        Bestellung bestellung = new Bestellung(1, 0, 123);
        manager.fuegeZuVerarbeitendeBestellungHinzu(bestellung);
    
        // Act
        Thread managerThread = new Thread(manager);
        managerThread.start();
        Thread.sleep(1500); // Warten, bis die Bestellung verschoben wird
        managerThread.interrupt(); // Manager-Thread beenden
    
        // Assert
        assertTrue(manager.gibBestellungenInProduktion().contains(bestellung),
            "Die Bestellung sollte in der Liste der Bestellungen in Produktion sein.");
        assertFalse(manager.gibZuVerarbeitendeBestellungen().contains(bestellung),
            "Die Bestellung sollte nicht mehr in der Liste der zu verarbeitenden Bestellungen sein.");
    }
    
    @Test
    public void testNurEineBestellungInProduktion() throws InterruptedException {
        // Arrange
        Produktions_Manager manager = new Produktions_Manager(new Fabrik(), new Lager(Executors.newSingleThreadExecutor()));
        Bestellung bestellung1 = new Bestellung(1, 0, 123);
        Bestellung bestellung2 = new Bestellung(0, 1, 124);
        manager.fuegeZuVerarbeitendeBestellungHinzu(bestellung1);
        manager.fuegeZuVerarbeitendeBestellungHinzu(bestellung2);
    
        // Act
        Thread managerThread = new Thread(manager);
        managerThread.start();
        Thread.sleep(500); // Warten, bis die erste Bestellung verarbeitet wird
        managerThread.interrupt(); // Manager-Thread beenden
    
        // Assert
        assertEquals(1, manager.gibBestellungenInProduktion().size(),
            "Es sollte immer nur eine Bestellung in Produktion sein.");
        assertTrue(manager.gibBestellungenInProduktion().contains(bestellung1),
            "Die erste Bestellung sollte in Produktion sein.");
        assertTrue(manager.gibZuVerarbeitendeBestellungen().contains(bestellung2),
            "Die zweite Bestellung sollte noch in der Liste der zu verarbeitenden Bestellungen sein.");
    }
    
    @Test
    public void testBestellungAlsFertigMarkiert() throws InterruptedException {
        // Arrange
        Produktions_Manager manager = new Produktions_Manager(new Fabrik(), new Lager(Executors.newSingleThreadExecutor()));
        Bestellung bestellung = new Bestellung(1, 0, 123);
        Produkt produkt = bestellung.gibBestellteProdukte().get(0);
        produkt.zustandAendern(2);
        manager.fuegeZuVerarbeitendeBestellungHinzu(bestellung);
    
        // Act
        Thread managerThread = new Thread(manager);
        managerThread.start();
        Thread.sleep(3500); // Warten, bis die Bestellung verarbeitet wird
        managerThread.interrupt(); // Manager-Thread beenden
    
        // Assert
        assertTrue(bestellung.gibAlleProdukteProduziert(),
            "Die Bestellung sollte als fertig markiert sein.");
    }

    @Test
public void testProdukteHabenStatus2NachProduktion() throws InterruptedException {
    // Arrange
    Produktions_Manager manager = new Produktions_Manager(new Fabrik(), new Lager(Executors.newSingleThreadExecutor()));
    Bestellung bestellung = new Bestellung(3, 0, 123); // Bestellung mit 3 Standardtüren
    manager.fuegeZuVerarbeitendeBestellungHinzu(bestellung);

    // Act
    Thread managerThread = new Thread(manager);
    managerThread.start();
    Thread.sleep(5000); // Warten, bis die Produktion sicherlich abgeschlossen ist
    managerThread.interrupt(); // Manager-Thread beenden

    // Assert
    for (Produkt produkt : bestellung.gibBestellteProdukte()) {
        assertEquals(2, produkt.aktuellerZustand(),
            "Das Produkt sollte den Zustand '2' haben, nachdem es vollständig produziert wurde.");
           }
}


}
