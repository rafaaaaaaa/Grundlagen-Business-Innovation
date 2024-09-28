public class Produkt
{
    //1 = bestellt
    //2 = in Produktion
    //3 = versandbereit
    //4 = versandt
    private int zustand;

    public Produkt()
    {
        zustand = 0;
    }

    public void zustandAendern(int zustand)
    {
        if(this.zustand < zustand)
        {
            // Error Handling
        }
        
        if(zustand > 4)
        {
            // Error Handling
        }
        
        this.zustand = zustand;
    }
    
    public int aktuelleZustand() 
    {        
        return zustand;
    }
}
