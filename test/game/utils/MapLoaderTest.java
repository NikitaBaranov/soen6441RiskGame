package game.utils;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * The map loader test
 * @author Dmitry Kryukov, Ksenia Popova
 * @see MapLoader
 */
public class MapLoaderTest {
    static MapLoader loader = new MapLoader(2, "wrongContinentNoCountries.map", false, "human");
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