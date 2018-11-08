package game.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.BeforeClass;

import static org.junit.Assert.*;

/**
 * The map loader test
 * @author Dmitry Kryukov, Ksenia Popova
 * @see MapLoader
 */
public class MapLoaderTest {
    static MapLoader loader = new MapLoader(2, "wrongContinentNoCountries.map", false);
    /**
     * Test invalid map with map wrong continent
     */
    @Test
    public void invalidMapWrongContinent() {
        assertTrue(loader.invalidMap);
    }

    /**
     * Check if the instance exist
     */
    @Test
    public void ifExist() {
        assertTrue(loader != null);
    }
}