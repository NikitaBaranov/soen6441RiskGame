package game.utils;

import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;

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