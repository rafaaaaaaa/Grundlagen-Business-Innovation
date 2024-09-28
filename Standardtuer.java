public class Standardtuer extends Produkt
{
    private int holzeinheiten;
    private int schrauben;
    private int farbeinheiten;
    private int kartoneinheiten;
    private int produktionsZeit;
    
    public Standardtuer()
    {
        holzeinheiten = 2;
        schrauben = 10;
        farbeinheiten = 2;
        kartoneinheiten = 1; 
        produktionsZeit = 10;
    }

    public int gibHolzeinheiten()
    {
        return holzeinheiten;
    }
    
    public int gibSchrauben()
    {
        return schrauben;
    }
        
    public int gibKartoneinheiten()
    {
        return kartoneinheiten;
    }
    
    public int gibProduktionszeit()
    {
        return produktionsZeit;
    }   
}
