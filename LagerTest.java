import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * The test class LagerTest.
 *
 * @author  Rafael Estermann
 * @version 23.11.24
 */
public class LagerTest
{
    String nameTestClasse = "LagerTest"; // Name der Testklasse
    Lager testee;
    PrintStream originalOut = System.out;
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    
    /**
     * Konstruktor von FabrikTest
     */
    public LagerTest() {

    }

    /**
     * Anweisungen vor jedem Testlauf
     */
    @BeforeEach
    public void setUp() {      
        System.out.println("Testlauf " + nameTestClasse + " Start");
        System.out.println();
        testee = new Lager();
        System.setOut(new PrintStream(outContent));
    }

    /**
     * Anweisungen nach jedem Testlauf
     */
    @AfterEach
    public void tearDown() {
        System.out.println();
        System.out.println("Testlauf " + nameTestClasse + " Ende");
        System.out.println("------------------------");
                System.setOut(originalOut);
    }
    
     @Test
    /**
     * Testet das Zurückgeben der Beschaffungszeit, anhand der Bestellung (hier: wenn die Lagerkapazität nicht ausreicht)
     */
    public void testeGibBeschaffungszeit_wennZuwenigLager() {        
        testee.lagerAuffuellen();
        Bestellung bestellung = new Bestellung(10000, 10000, 1);
        int beschaffungsZeit = testee.gibBeschaffungszeit(bestellung);        
        assertEquals(beschaffungsZeit, 2);
    }
    
    @Test
    /**
    * Testet das Zurückgeben der Beschaffungszeit, anhand der Bestellung (hier: wenn die Lagerkapazität ausreicht)
     */
    public void testeGibBeschaffungszeit_wennGenuegendLager() {
        testee.lagerAuffuellen();
        Bestellung bestellung = new Bestellung(1, 1, 1);
        int beschaffungsZeit = testee.gibBeschaffungszeit(bestellung);        
        assertEquals(beschaffungsZeit, 0);
    }   
    
    @Test
    /**
    * Testet das korrekte Ausgeben des Lagerbestandes bei maximalen Lagerbestand
     */
    public void testLagerBestandAusgeben_WennFull() {
        testee.lagerAuffuellen();

        // Erwartete Ausgabe
        String expectedOutput = getFullKapazitaetString();

        // Methode aufrufen
        testee.lagerBestandAusgeben();

        // Überprüfen, ob die Ausgabe korrekt ist           
        assertTrue(outContent.toString().trim().contains(expectedOutput), "Actual Output ist nicht Erwarteten Output enthalten");
    }
    
    @Test
    /**
    * Testet das korrekte Ausgeben des Lagerbestandes bei minimalem Lagerbestand
     */
    public void testLagerBestandAusgeben_WennEmpty() {
        // Erwartete Ausgabe
        String expectedOutput = getEmptyKapazitaetString();
            
        // Methode aufrufen
        testee.lagerBestandAusgeben();
        
        // Überprüfen, ob die Ausgabe korrekt ist
        assertTrue(outContent.toString().trim().contains(expectedOutput), "Actual Output ist nicht Erwarteten Output enthalten");
    }
    
    @Test
    /**
    * Testet das korrekte Ausgeben des Lagerbestandes bei minimalem Lagerbestand
     */
    public void testLagerAuffuellen_WennEmpty() {        
        // Erwartete Ausgabe
        String expectedOutputOne = getEmptyKapazitaetString();
            
        // Methode aufrufen
        testee.lagerBestandAusgeben();
        
        // Überprüfen, ob die Ausgabe korrekt ist
        assertTrue(outContent.toString().trim().contains(expectedOutputOne), "Actual Output ist nicht Erwarteten Output enthalten");
        
        testee.lagerAuffuellen();
        
        // Erwartete Ausgabe (nach Auffüllen)
        String expectedOutputTwo = getFullKapazitaetString();
            
        // Methode aufrufen
        testee.lagerBestandAusgeben();
        
        // Überprüfen, ob die Ausgabe korrekt ist
        assertTrue(outContent.toString().trim().contains(expectedOutputTwo), "Actual Output ist nicht Erwarteten Output enthalten");
    }
    
    @Test
    /**
     * Testet die Getters für die maximalen Einheiten
     */
    public void testeMaxEinheitenGetters() {
        assertEquals(Lager.gibMaxFarbeinheiten(), 1000);
        assertEquals(Lager.gibMaxGlaseinheiten(), 100);
        assertEquals(Lager.gibMaxHolzeinheiten(), 1000);
        assertEquals(Lager.gibMaxKartoneinheiten(), 1000);
        assertEquals(Lager.gibMaxSchrauben(), 5000);

        System.out.println("Getters von Max Einheiten des Lagers erfolgreich");
    }

     @Test
    /**
     * Testet ob MinimalBestand Boolean korrekterweise True zurückgibt, bei leerem Lager
     */
    public void testeIstUnterMinimalbestandWennLagerLeer() {   
        
        // Überprüfen, ob die istUnterMinimalbestand Methode "True" zurückgibt -> da Lager komplett leer (= <30%)
        assertTrue(testee.istUnterMinimalbestand(), "Actual Output ist nicht Erwarteten Output enthalten");
 
    }
    
    @Test
    /**
     * Testet ob MinimalBestand Boolean korrekterweise True zurückgibt, bei leerem Lager
     */
    public void testeIstNichtUnterMinimalbestandWennLagerLeer() {   
        
        //Auffüllen des Lagers -> sodass nicht unter minimal Bestand.
        testee.lagerAuffuellen();
        
        // Überprüfen, ob die istUnterMinimalbestand Methode "True" zurückgibt -> da Lager komplett leer (= <30%)
        assertFalse(testee.istUnterMinimalbestand(), "Actual Output ist nicht Erwarteten Output enthalten");
 
    }
    
    private String getFullKapazitaetString(){
        return String.join(System.lineSeparator(),
            "Lagerbestand:",
            "Holzeinheiten: " + Lager.gibMaxHolzeinheiten(),
            "Schrauben: " + Lager.gibMaxSchrauben(),
            "Farbeinheiten: "+ Lager.gibMaxFarbeinheiten(),
            "Kartoneinheiten: " + Lager.gibMaxKartoneinheiten(),
            "Glaseinheiten: " + Lager.gibMaxGlaseinheiten());       
    }
    
    private String getEmptyKapazitaetString(){
        return String.join(System.lineSeparator(),
            "Lagerbestand:",
            "Holzeinheiten: 0",
            "Schrauben: 0",
            "Farbeinheiten: 0",
            "Kartoneinheiten: 0",
            "Glaseinheiten: 0");      
    }
  
   
}
