public class Produkt
{
    //Wir definierten 4 mögliche Zustände. Initial ist der Zustand immer "bestellt" (1). 
    //1 = bestellt
    //2 = in Produktion
    //3 = versandbereit
    //4 = versandt
    private int zustand;

    public Produkt()
    {
        zustand = 1;
    }

    public void zustandAendern(int zustand)
    {
        //Die Logik, wann sich ein Zustand ändert wird grundsätzlich in folgenden Aufgaben implementiert. Momentan sind noch keine Ereignisse laut Aufgabenstellung zu implementieren, welche diesen ändern (Time-Based Änderungen, ...).
        
        //Zustände, welche nicht 1, 2, 3 oder 4 sind, sind laut unserer Definition ungültig. Entsprechend wird eine Exception geworfen, der beim Aufrufer gecatched und entsprechend gehandelt werden muss.
        if(zustand > 4 || zustand < 1)
        {
            throw new IllegalArgumentException("Ungültiger Zustand. Zustände sind entweder 1, 2, 3 oder 4");
        }
        
        //Wir haben uns überlegt, dass nur Zustandsänderungen gültig sind, welche höher sind als der momentane Zustand (Intuition: wie kann ein Produkt, dass versandbereit ist wieder in Produktion kommen?)
        if(this.zustand < zustand)
        {
            throw new IllegalArgumentException("Ungültige Zustandsänderung. Der nächste Zustand muss immer größer als der vorherige sein");
        } 
        
        this.zustand = zustand;
    }
    
    public int aktuelleZustand() 
    {        
        return zustand;
    }
}
