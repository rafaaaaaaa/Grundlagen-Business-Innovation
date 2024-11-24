
/**
 * Die Klasse Main ist die Hauptklasse des Programms.
 * Hier befindet sich die main Methode zum Starten des Programms.
 *
 * @author Rafael Estermann
 * @version 23.11.2024
 */
public class Main 
{

    public static void main(String[] args) 
    {

        Fabrik testFabrik = new Fabrik();
              
        System.out.println("Willkommen bei der AEKI Fabrik.");     

        //Hier könnte in zukünftiger Aufgabe ein Batchjob auf einem eigenen Thread laufen, welcher die Bestellungen nach und nach Produziert.
        
        // Best 1: 2, 2
        testFabrik.bestellungAufgeben(2, 2);
        testFabrik.bestellungAusgeben();

        // Best 2: 5, 0
        testFabrik.bestellungAufgeben(5, 0);
        testFabrik.bestellungAusgeben();

        // Best 3: 0, 6
        testFabrik.bestellungAufgeben(0, 6);
        testFabrik.bestellungAusgeben();

        // Best 4: -5, 6
        testFabrik.bestellungAufgeben(-5, 6);
        testFabrik.bestellungAusgeben();

        // Best 5: 1000000, 6
        testFabrik.bestellungAufgeben(1000000, 6);
        testFabrik.bestellungAusgeben();

        // Best 6: 0, 0
        testFabrik.bestellungAufgeben(0, 0);
        testFabrik.bestellungAusgeben();

    }

}
