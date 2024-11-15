/**
 * Klasse Lager beinhaltet die Methoden des Lager
 *
 * @author Rafael Estermann
 * @version 23.11.2024
 */
public class Lieferant 
{
    /**
     * Mit dieser Methode werden neue Ware bestellt, anhand zu bestellenden Einheiten 
     * 
     * @param holzEinheiten Anzahl zu bestellenden Holzeinheiten
     * @param schraubenEinheiten Anzahl zu bestellenden Holzeinheiten
     * @param farbEinheiten Anzahl zu bestellenden Holzeinheiten
     * @param kartonEinheiten Anzahl zu bestellenden Holzeinheiten
     * @param glasEinheiten Anzahl zu bestellenden Holzeinheiten
     * @return bestellungErfolgreich Je nachdem ob bestellung gemacht werden konnte oder nicht.
     */ 
   public boolean wareBestellen(int holzEinheiten, int schraubenEinheiten, int farbEinheiten, int kartonEinheiten, int glasEinheiten) {
        boolean bestellungErfolgreich = true;
        // Beispielhafte Logik: Die Bestellung ist erfolgreich, wenn alle Einheiten größer oder gleich null sind
        // In einer echten Anwendung könnten hier weitere Überprüfungen erfolgen, wie z.B. Lagerbestände des Lieferanten prüfen
        if (holzEinheiten < 0 || schraubenEinheiten < 0 || farbEinheiten < 0 || kartonEinheiten < 0 || glasEinheiten < 0) {
            System.out.println("Bestellung fehlgeschlagen: Ungültige Bestellmengen.");
            return false;
        }

        System.out.println("Bestellung erfolgreich:");
        System.out.println("Bestellte Holzeinheiten: " + holzEinheiten);
        System.out.println("Bestellte Schrauben: " + schraubenEinheiten);
        System.out.println("Bestellte Farbeinheiten: " + farbEinheiten);
        System.out.println("Bestellte Kartoneinheiten: " + kartonEinheiten);
        System.out.println("Bestellte Glaseinheiten: " + glasEinheiten);
        
        // Wenn die Bestellung erfolgreich ist, wird true zurückgegeben
        return bestellungErfolgreich;
    }

}
