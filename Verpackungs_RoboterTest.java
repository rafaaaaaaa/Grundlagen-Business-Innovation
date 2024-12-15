import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Die Testklasse für die Klasse Verpackungs_Roboter.
 * 
 * @author Rafael Estermann
 * @version 15.12.2024
 */
public class Verpackungs_RoboterTest {

    String nameTestClasse = "Verpackungs_RoboterTest"; // Name der Testklasse

    /**
     * Anweisungen vor jedem Testlauf.
     */
    @BeforeEach
    public void setUp() {
        System.out.println("Testlauf " + nameTestClasse + " Start");
        System.out.println();
    }

    /**
     * Anweisungen nach jedem Testlauf.
     */
    @AfterEach
    public void tearDown() {
        System.out.println();
        System.out.println("Testlauf " + nameTestClasse + " Ende");
        System.out.println("------------------------");
    }

    /**
     * Testet, ob der Name des Roboters korrekt zugewiesen wurde.
     */
    @Test
    public void testNameAssignment() {
        String testname = "Verpackung Roboter";
        Verpackungs_Roboter roboter = new Verpackungs_Roboter(testname);
        assertEquals(testname, roboter.gibName(),
                "Der Name des Roboters sollte eigentlich " + testname + " sein.");
    }
}
