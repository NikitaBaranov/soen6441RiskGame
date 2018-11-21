package game.utils;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * The warning window test
 * @author Dmitry Kryukov, Ksenia Popova
 * @see WarningWindow
 */
public class WarningWindowTest {
    static WarningWindow window = new WarningWindow("I'm the warning");

    /**
     * If the instance exist
     */
    @Test
    public void ifExist() {
        assertTrue(window != null);
    }

}