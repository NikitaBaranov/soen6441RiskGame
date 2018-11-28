package game;

import game.model.*;
import game.strategies.GamePhaseStrategies.GamePhaseEnum;
import game.ui.Main;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static game.strategies.PlayerStrategies.PlayerStrategyEnum.HUMAN_STRATEGY;
import static org.junit.Assert.assertEquals;

/**
 * Tests for game class i.e. controller of the game.
 * @author Ksenia Popova
 * @see Game
 */
public class GameTest {
    public List<Country> countries = new ArrayList<>();
    public List<Neighbour> neighbours = new ArrayList<>();
    public List<Player> players = new ArrayList<>();
    public List<Continent> continents = new ArrayList<>();
    public String mapPath = "./src/maps/Triangles.map";
    Game game = Game.getInstance();

    @Before
    public void setUp(){
        players.add(new Player("Test Player 1", Color.BLUE, HUMAN_STRATEGY, false));
        players.add(new Player("Test Player 2", Color.BLACK, HUMAN_STRATEGY, false));
        continents.add(new Continent("Continent 1", 1));
        continents.add(new Continent("Continent 2", 2));
        countries.add(new Country("A",10,10, 2, continents.get(0), players.get(0)));
        countries.add(new Country("B",30,30, 2, continents.get(0), players.get(0)));
        countries.add(new Country("C",50,50, 2, continents.get(1), players.get(1)));
        countries.add(new Country("D",70,70, 2, continents.get(1), players.get(1)));

        GameState gameState = new GameState();
        gameState.setCountries(countries);
        gameState.setNeighbours(neighbours);
        gameState.setPlayers(players);
        gameState.setContinents(continents);
        gameState.setMapFilePath(mapPath);


        game.setGameState(gameState);
    }

    @Test
    public void test1(){
        while (!(game.getGameState().getCurrentGamePhase() == GamePhaseEnum.REINFORCEMENT && game.getGameState().getCurrentPlayer() == players.get(0))) {
            game.nextTurn();
        }
        countries.get(0).setArmy(10);
        players.get(0).setArmies(5);
        game.makeAction(0, 0);
        game.makeAction(10, 10);
        assertEquals(11, countries.get(0).getArmy());
    }
//    /**
//     * Test for calculation of number of reinforcment armies
//     */

//     //Re do the test to use strategies.
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
//        Game game = Game.getInstance();
//        game.setGameState(gameState);
//        game.setNotification(notificationWindow);
//
//        assertEquals(3, game.getReinforcementArmies(player1, countryList));
//    }
}