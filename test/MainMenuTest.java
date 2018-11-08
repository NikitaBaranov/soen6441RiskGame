import mapeditor.StartEditor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.BeforeClass;

import static org.junit.Assert.*;

/**
 * The main menu test.
 * @author Dmitry Kryukov
 * @see MainMenu
 */
public class MainMenuTest {
    private MainMenu window;

    /**
     * Test if the window object exists.
     * @throws Exception
     */
    @Test
    public void ifExist() throws Exception {
        MainMenu window = new MainMenu("RiskGame by Team 3", 450, 250);
        assertTrue(window != null);
    }
}
