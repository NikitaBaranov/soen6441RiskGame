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
    /**
     * Test invalid map with map wrong continent
     */
    @Test
    public void invalidMapWrongContinent() {
        MapLoader loader = new MapLoader(2, "wrongContinentNoCountries.map", false);
        assertTrue(loader.invalidMap);
    }
}