package game.utils;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * The map loader test
 * Map validation tests.
 * @author Dmitry Kryukov, Ksenia Popova
 * @see MapLoader
 */
public class MapLoaderTest {
    /**
     * Check if the instance exist
     */
    @Test
    public void ifLoaderExist() {
        int players = 2;
        List<String> playersModes = new ArrayList<>();
        playersModes.add("Human");
        playersModes.add("Human");
        String mapFile1 = "./src/main/resources/maps/invContinentNoTerr.map"; // no territories
        MapLoader loader = new MapLoader(players, mapFile1, false, playersModes, new NotificationWindow());
        assertTrue(loader != null);
    }

    /**
     * Map Validation: No territories on the continent
     */
    @Test
    public void invContinentNoTerr() {
        int players = 2;
        List<String> playersModes = new ArrayList<>();
        playersModes.add("Human");
        playersModes.add("Human");
        String mapFile1 = "./src/main/resources/maps/invContinentNoTerr.map"; // no territories
        MapLoader loader1 = new MapLoader(players, mapFile1, false, playersModes, new NotificationWindow());
        assertTrue(loader1.invalidMap);
    }

    /**
     * Map Validation: One territory on the continent
     */
    @Test
    public void invContinentOneTerr() {
        int players = 2;
        List<String> playersModes = new ArrayList<>();
        playersModes.add("Human");
        playersModes.add("Human");
        String mapFile2 = "./src/main/resources/maps/invContinentOneTerr.map"; // one territory
        MapLoader loader2 = new MapLoader(players, mapFile2, false, playersModes, new NotificationWindow());
        assertTrue(loader2.invalidMap);
    }

    /**
     * Map Validation: Disconnected continents
     */
    @Test
    public void invDisconnectedContinents() {
        int players = 2;
        List<String> playersModes = new ArrayList<>();
        playersModes.add("Human");
        playersModes.add("Human");
        String mapFile3 = "./src/main/resources/maps/invNoConnectionTwoCont.map"; // Disconnected continents
        MapLoader loader3 = new MapLoader(players, mapFile3, false, playersModes, new NotificationWindow());
        assertTrue(loader3.invalidMap);
    }


    /**
     * Map Validation: Disconnected Continents
     */
    @Test
    public void invDisconnectedCont() {
        int players = 2;
        List<String> playersModes = new ArrayList<>();
        playersModes.add("Human");
        playersModes.add("Human");
        String mapFile5 = "./src/main/resources/maps/UnconnectedContinent.map"; // Disconnected continents
        MapLoader loader5 = new MapLoader(players, mapFile5, false, playersModes, new NotificationWindow());
        assertTrue(loader5.invalidMap);
    }

    /**
     * Map Validation: Disconnected Continents Twin Volcano
     */
    @Test
    public void invTwinVolcano() {
        int players = 2;
        List<String> playersModes = new ArrayList<>();
        playersModes.add("Human");
        playersModes.add("Human");
        String mapFile5 = "./src/main/resources/maps/TwinVolcano.map"; // Disconnected continents
        MapLoader loader5 = new MapLoader(players, mapFile5, false, playersModes, new NotificationWindow());
        assertTrue(loader5.invalidMap);
    }

    /**
     * Map Validation: Correct if map has one direction connections
     */
    @Test
    public void valOneDirection() {
        int players = 2;
        List<String> playersModes = new ArrayList<>();
        playersModes.add("Human");
        playersModes.add("Human");
        String mapFile6 = "./src/main/resources/maps/oneDirection.map";
        MapLoader loader6 = new MapLoader(players, mapFile6, false, playersModes, new NotificationWindow());
        assertFalse(loader6.invalidMap);
    }

    /**
     * Map Validation: 3dCliff
     */
    @Test
    public void val3dCliff() {
        int players = 2;
        List<String> playersModes = new ArrayList<>();
        playersModes.add("Human");
        playersModes.add("Human");
        String mapFile6 = "./src/main/resources/maps/3D Cliff.map";
        MapLoader loader6 = new MapLoader(players, mapFile6, false, playersModes, new NotificationWindow());
        assertFalse(loader6.invalidMap);
    }

    /**
     * Map Validation: World
     */
    @Test
    public void valWorld() {
        int players = 2;
        List<String> playersModes = new ArrayList<>();
        playersModes.add("Human");
        playersModes.add("Human");
        String mapFile6 = "./src/main/resources/maps/World.map";
        MapLoader loader6 = new MapLoader(players, mapFile6, false, playersModes, new NotificationWindow());
        assertFalse(loader6.invalidMap);
    }

}