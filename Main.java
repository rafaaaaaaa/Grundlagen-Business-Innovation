
/**
 * Die Klasse Main ist die Hauptklasse des Programms.
 * Hier befindet sich die main Methode zum Starten des Programms.
 *
 * @author Alex Marchese
 * @version 04.12.2024
 */
public class Main {

    public static void main(String[] args) {

        Fabrik testFabrik = new Fabrik();

        System.out.println("Willkommen bei der AEKI Fabrik.");      

        testFabrik.bestellungAufgeben(3, 2);        
        testFabrik.bestellungAufgeben(1, 0);     
        
        try {
             Thread.sleep(15000);
             testFabrik.bestellungAufgeben(1, 0);           
             
             Thread.sleep(2000);
             testFabrik.bestellungAufgeben(2, 5);
             testFabrik.bestellungAufgeben(4, 9);
             testFabrik.bestellungAufgeben(1, 6);
             
             Thread.sleep(2000);
             testFabrik.bestellungAufgeben(3, 8);
        }
        catch(Exception e){
            
        }

    }

}
