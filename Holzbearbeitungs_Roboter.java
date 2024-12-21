/**
 * Diese Klasse repräsentiert einen Holzbearbeitungs-Roboter, der Produkte bearbeitet,
 * indem er die spezifischen Produktionsschritte für Holzprodukte durchführt.
 * 
 * @author Rafael Estermann
 * @version 15.12.2024
 */
public class Holzbearbeitungs_Roboter extends Roboter {

    /**
     * Konstruktor für die Klasse Holzbearbeitungs_Roboter.
     * 
     * @param name Der Name des Roboters
     */
    public Holzbearbeitungs_Roboter(String name) {
        super(name, 24000);
    }

    /**
     * Produziert ein Produkt, indem die Bearbeitungszeit für Standardtüren und Premiumtüren simuliert wird.
     * Führt den nächsten Produktionsschritt ein, nachdem der aktuelle abgeschlossen ist.
     * 
     * @param produkt Das zu produzierende Produkt
     */
    @Override
    public void produziereProdukt(Produkt produkt) {
        try {
            if (produkt instanceof Standardtuer) {
                // Simulierung der Bearbeitungszeit für eine Standardtür
                Thread.sleep(166); // 10 Minuten Produktionszeit simuliert (da konsistent mit 48h = 48s hier 166ms)
                System.out.println(gibName() + " hat Standardtür Prozessschritt abgeschlossen");
            } else {
                // Simulierung der Bearbeitungszeit für eine Premiumtür
                Thread.sleep(500); // 30 Minuten Produktionszeit simuliert (da konsistent mit 48h = 48s hier 500ms)
                System.out.println(gibName() + " hat Premiumtür Prozessschritt abgeschlossen");
            }

            // Einleiten des nächsten Produktionsschritts
            produkt.naechsteProduktionsStation();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
