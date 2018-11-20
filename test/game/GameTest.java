package game;

import game.strategies.PlayerStrategies.PlayerStrategyFactory;

/**
 * Tests for game class i.e. controller of the game.
 * @author Ksenia Popova
 * @see Game
 */
public class GameTest {
    PlayerStrategyFactory playerStrategyFactory = new PlayerStrategyFactory();

    /**
     * Test for calculation of number of reinforcment armies
     */

    // Re do the test to use strategies.
    //    @Test
//    public void reinforcementCount() {
//        Player player1 = new Player("test Player 1", Color.BLACK, playerStrategyFactory.getStrategy(PlayerStrategyEnum.HUMAN_STRATEGY));
//        player1.setArmies(0);
//        Player player2 = new Player("test Player 2", Color.GREEN, playerStrategyFactory.getStrategy(PlayerStrategyEnum.HUMAN_STRATEGY));
//        player2.setArmies(0);
//        Continent continent = new Continent("Continent", 1);
//        List<Country> countryList = new ArrayList<>();
//        countryList.add(new Country("Country 1", 10, 10, 2, continent, player1));
//        countryList.add(new Country("Country 2", 20, 20, 2, continent, player1));
//        countryList.add(new Country("Country 3", 30, 30, 2, continent, player1));
//        countryList.add(new Country("Country 4", 40, 40, 2, continent, player2));
//
//        assertEquals(3, Game.getReinforcementArmies(player1, countryList));
//    }
}