package game;

import game.model.Continent;
import game.model.Country;
import game.model.Player;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests for game class i.e. controller of the game.
 * @author Ksenia Popova
 * @see Game
 */
public class GameTest {
    /**
     * Test for calculation of number of reinforcment armies
     */
    @Test
    public void reinforcementCount() {
        Player player1 = new Player("test Player 1", Color.BLACK);
        player1.setArmies(0);
        Player player2 = new Player("test Player 2", Color.GREEN);
        player2.setArmies(0);
        Continent continent = new Continent("Continent", 1);
        List<Country> countryList = new ArrayList<>();
        countryList.add(new Country("Country 1", 10, 10, 2, continent, player1));
        countryList.add(new Country("Country 2", 20, 20, 2, continent, player1));
        countryList.add(new Country("Country 3", 30, 30, 2, continent, player1));
        countryList.add(new Country("Country 4", 40, 40, 2, continent, player2));

//        assertTrue(Game.getReinforcementArmies(player1, countryList) == 1);
        assertEquals(3, Game.getReinforcementArmies(player1, countryList));
    }
}