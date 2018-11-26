package game.utils;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * The map loader test
 * @author Dmitry Kryukov, Ksenia Popova
 * @see MapLoader
 */
public class MapLoaderTest {
    static List<String> playersModes = new ArrayList<>();
    MapLoader loader;

    @Before
    public void init(){
        playersModes.add("Human");
        playersModes.add("Human");
        loader = new MapLoader(2, "wrongContinentNoCountries.map", false, playersModes, new NotificationWindow());
    }

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