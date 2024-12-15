
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
        
        //Simulierung von eingehenden Bestellungen.
        try {
            testFabrik.bestellungAufgeben(1, 3);           
            Thread.sleep(10000);
            testFabrik.bestellungAufgeben(2, 0);    
            Thread.sleep(10000);
            testFabrik.bestellungAufgeben(1, 1);
            testFabrik.bestellungAufgeben(1, 1);
            Thread.sleep(2000);
            testFabrik.bestellungAufgeben(0, 1);
        }
        catch(Exception e){
            System.out.println(e);    
        }

    }

}
