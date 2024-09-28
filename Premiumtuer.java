public class Premiumtuer extends Produkt
{
    private int holzeinheiten;
    private int schrauben;
    private int glaseinheiten;
    private int farbeinheiten;
    private int kartoneinheiten;
    private int produktionsZeit;
    
    public Premiumtuer()
    {
        holzeinheiten = 4;
        schrauben = 5;
        glaseinheiten = 5;
        farbeinheiten = 1;
        kartoneinheiten = 5; 
        produktionsZeit = 30;
    }

    public int gibHolzeinheiten()
    {
        return holzeinheiten;
    }
    
    public int gibSchrauben()
    {
        return schrauben;
    }
    
    public int gibGlaseinheiten()
    {
        return glaseinheiten;
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
