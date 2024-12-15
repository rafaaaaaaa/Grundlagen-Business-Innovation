import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Die Testklasse für die Klasse Montage_Roboter.
 * 
 * @author Rafael Estermann
 * @version 15.12.2024
 */
public class Montage_RoboterTest {

    String nameTestClasse = "Montage_RoboterTest"; // Name der Testklasse

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
        String testname = "Montage Roboter";
        Montage_Roboter roboter = new Montage_Roboter(testname);
        assertEquals(testname, roboter.gibName(), "Der Name des Roboters sollte eigentlich " + testname + " sein.");
    }
}
