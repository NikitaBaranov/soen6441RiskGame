package game.utils;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * The warning window test
 * @author Dmitry Kryukov, Ksenia Popova
 * @see WarningWindow
 */
public class WarningWindowTest {

    /**
     * If the instance of closing window exist
     */
    @Test
    public void ifClosingExist() {
        assertTrue(new WarningWindow("I'm the warning") != null);
    }

    /**
     * If the instance of not closing window exist
     */
    @Test
    public void ifNotClosingExist() {
        assertTrue(new WarningWindow("I'm the warning", false) != null);
    }

}