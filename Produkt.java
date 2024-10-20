public class Produkt
{
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
        if(zustand > 4 || zustand < 1)
        {
            System.out.println("Ungültiger Zustand. Zustände sind entweder 1, 2, 3 oder 4");
        }
        
        if(this.zustand < zustand)
        {
            System.out.println("Ungültige Zustandsänderung. Der nächste Zustand muss immer größer als der vorherige sein");
        } 
        
        this.zustand = zustand;
    }
    
    public int aktuelleZustand() 
    {        
        return zustand;
    }
}
