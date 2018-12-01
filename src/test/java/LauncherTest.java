import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertTrue;

/**
 * The Launcher test.
 * @author Dmitry Kryukov
 * @see Launcher
 */
public class LauncherTest {
    private Launcher launcher;

    /**
     * Create object launcher
     */
    @Before
    public void setUp(){
        launcher = new Launcher("Test", 100,100);
    }

    /**
     * True if object is exist
     */
    @Test
    public void launcherExistence(){
        assertTrue(launcher != null);
    }
}
