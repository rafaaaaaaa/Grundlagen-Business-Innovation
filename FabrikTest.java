import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class FabrikTest {
    private Fabrik testee;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    public FabrikTest() {
        testee = new Fabrik();
    }

    @BeforeEach
    public void setUp() {
        testee = new Fabrik();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void tearDown() {
        testee = null;
        System.setOut(originalOut);
    }

    @Test
    public void testBestellungAufgeben_InvalidAndAusgeben() {
        testee.bestellungAufgeben(3, 1);
        testee.bestellungAusgeben();
        String expectedOutput = "Anzahl Premiumtüren = 1";
        assertTrue(outContent.toString().contains(expectedOutput), "Expected output to contain: " + expectedOutput);
    }
}