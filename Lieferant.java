public class Lieferant {

    public boolean wareBestellen(int holzEinheiten, int schrauben, int farbEinheiten, int kartonEinheiten, int glasEinheiten) {
        // Beispielhafte Logik: Die Bestellung ist erfolgreich, wenn alle Einheiten größer oder gleich null sind
        // In einer echten Anwendung könnten hier weitere Überprüfungen erfolgen, wie z.B. Lagerbestände des Lieferanten prüfen

        if (holzEinheiten < 0 || schrauben < 0 || farbEinheiten < 0 || kartonEinheiten < 0 || glasEinheiten < 0) {
            System.out.println("Bestellung fehlgeschlagen: Ungültige Bestellmengen.");
            return false;
        }

        System.out.println("Bestellung erfolgreich:");
        System.out.println("Bestellte Holzeinheiten: " + holzEinheiten);
        System.out.println("Bestellte Schrauben: " + schrauben);
        System.out.println("Bestellte Farbeinheiten: " + farbEinheiten);
        System.out.println("Bestellte Kartoneinheiten: " + kartonEinheiten);
        System.out.println("Bestellte Glaseinheiten: " + glasEinheiten);
        
        // Wenn die Bestellung erfolgreich ist, wird true zurückgegeben
        return true;
    }
}
