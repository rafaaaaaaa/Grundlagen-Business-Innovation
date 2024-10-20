import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class FabrikTest {
    private Fabrik testee;
    private int currentBestellNr;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        testee = new Fabrik();
        System.setOut(new PrintStream(outContent));
        currentBestellNr = 0;
    }

    @AfterEach
    public void tearDown() {
        testee = null;
        System.setOut(originalOut);
    }

    @Test
    public void bestellungAusgeben_whenStandard3Premium1_thenCorrectOutput()
    {
        int anzahlStandardTueren = 3;
        int anzahlPremiumTueren = 1;   
        String expectedOutput = buildExpectedOutput(anzahlStandardTueren, anzahlPremiumTueren);
          
        testee.bestellungAufgeben(anzahlStandardTueren, anzahlPremiumTueren); 
        testee.bestellungAusgeben(); 
    
        assertEquals(expectedOutput.trim(), outContent.toString().trim());
    }  
    
    @Test
    public void bestellungAusgeben_whenStandard5Premium2_thenCorrectOutput() 
    {
        int anzahlStandardTueren = 5;
        int anzahlPremiumTueren = 2;   
        String expectedOutput = buildExpectedOutput(anzahlStandardTueren, anzahlPremiumTueren);
          
        testee.bestellungAufgeben(anzahlStandardTueren, anzahlPremiumTueren); 
        testee.bestellungAusgeben(); 
    
        assertEquals(expectedOutput.trim(), outContent.toString().trim());
    }
    
    @Test
    public void bestellungAusgeben_whenStandard0Premium3_thenCorrectOutput() 
    {
        int anzahlStandardTueren = 0;
        int anzahlPremiumTueren = 3;   
        String expectedOutput = buildExpectedOutput(anzahlStandardTueren, anzahlPremiumTueren);
          
        testee.bestellungAufgeben(anzahlStandardTueren, anzahlPremiumTueren); 
        testee.bestellungAusgeben(); 
    
        assertEquals(expectedOutput.trim(), outContent.toString().trim());
    }
    
    @Test
    public void bestellungAusgeben_when3Bestellungen_thenCorrectOutput() 
    {
        int anzahlStandardTuerenBestellung1 = 1;
        int anzahlPremiumTuerenBestellung1 = 3;   
        int anzahlStandardTuerenBestellung2 = 0;
        int anzahlPremiumTuerenBestellung2 = 2;  
        int anzahlStandardTuerenBestellung3 = 5;
        int anzahlPremiumTuerenBestellung3 = 2; 
        
        String expectedOutput = buildExpectedOutput(anzahlStandardTuerenBestellung1, anzahlPremiumTuerenBestellung1) 
        + buildExpectedOutput(anzahlStandardTuerenBestellung2, anzahlPremiumTuerenBestellung2)
        + buildExpectedOutput(anzahlStandardTuerenBestellung3, anzahlPremiumTuerenBestellung3);
          
        testee.bestellungAufgeben(anzahlStandardTuerenBestellung1, anzahlPremiumTuerenBestellung1); 
        testee.bestellungAufgeben(anzahlStandardTuerenBestellung2, anzahlPremiumTuerenBestellung2); 
        testee.bestellungAufgeben(anzahlStandardTuerenBestellung3, anzahlPremiumTuerenBestellung3); 
        testee.bestellungAusgeben(); 
    
        assertEquals(expectedOutput.trim(), outContent.toString().trim());
    }
    
    @Test
    public void bestellungAusgeben_whenAllStandardDoors_thenCorrectOutput()
    {
        int anzahlStandardTuerenBestellung1 = 2;
        int anzahlPremiumTuerenBestellung1 = 0;
        int anzahlStandardTuerenBestellung2 = 4;
        int anzahlPremiumTuerenBestellung2 = 0;
        int anzahlStandardTuerenBestellung3 = 3;
        int anzahlPremiumTuerenBestellung3 = 0;
    
        String expectedOutput = buildExpectedOutput(anzahlStandardTuerenBestellung1, anzahlPremiumTuerenBestellung1) 
            + buildExpectedOutput(anzahlStandardTuerenBestellung2, anzahlPremiumTuerenBestellung2)
            + buildExpectedOutput(anzahlStandardTuerenBestellung3, anzahlPremiumTuerenBestellung3);
    
        testee.bestellungAufgeben(anzahlStandardTuerenBestellung1, anzahlPremiumTuerenBestellung1); 
        testee.bestellungAufgeben(anzahlStandardTuerenBestellung2, anzahlPremiumTuerenBestellung2); 
        testee.bestellungAufgeben(anzahlStandardTuerenBestellung3, anzahlPremiumTuerenBestellung3); 
        testee.bestellungAusgeben(); 
    
        assertEquals(expectedOutput.trim(), outContent.toString().trim());
    }
    
    @Test
    public void bestellungAusgeben_whenAllPremiumDoors_thenCorrectOutput()
    {
        int anzahlStandardTuerenBestellung1 = 0;
        int anzahlPremiumTuerenBestellung1 = 1;
        int anzahlStandardTuerenBestellung2 = 0;
        int anzahlPremiumTuerenBestellung2 = 2;
        int anzahlStandardTuerenBestellung3 = 0;
        int anzahlPremiumTuerenBestellung3 = 3;
    
        String expectedOutput = buildExpectedOutput(anzahlStandardTuerenBestellung1, anzahlPremiumTuerenBestellung1) 
            + buildExpectedOutput(anzahlStandardTuerenBestellung2, anzahlPremiumTuerenBestellung2)
            + buildExpectedOutput(anzahlStandardTuerenBestellung3, anzahlPremiumTuerenBestellung3);
    
        testee.bestellungAufgeben(anzahlStandardTuerenBestellung1, anzahlPremiumTuerenBestellung1); 
        testee.bestellungAufgeben(anzahlStandardTuerenBestellung2, anzahlPremiumTuerenBestellung2); 
        testee.bestellungAufgeben(anzahlStandardTuerenBestellung3, anzahlPremiumTuerenBestellung3); 
        testee.bestellungAusgeben(); 
    
        assertEquals(expectedOutput.trim(), outContent.toString().trim());
    }
    
    @Test
    public void bestellungAufgeben_whenStandard0Premium0_thenPrintErrorMessage() {
        int anzahlStandardTueren = 0;
        int anzahlPremiumTueren = 0;        
        String expectedOutput = "Fehlerhafte Anzahl Türen ausgegeben. Bestellung wird nicht aufgegeben";
        
        testee.bestellungAufgeben(anzahlStandardTueren, anzahlPremiumTueren);
        
        assertEquals(expectedOutput.trim(), outContent.toString().trim());
    }
    
    private String buildExpectedOutput(int anzahlStandard, int anzahlPremium)
    {
        currentBestellNr = currentBestellNr + 1;
        return "Bestellnummer: " + currentBestellNr + "\n" +
                               "Anzahl Standardtüren: " + anzahlStandard + "\n" +
                               "Anzahl Premiumtüren: " + anzahlPremium + "\n" +
                               "Ist Bestellung bestätigt: Ja\n" +
                               "Beschaffungszeit: 1440\n"; 
    }
    
    
}