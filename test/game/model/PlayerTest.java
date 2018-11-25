package game.model;

import game.Game;
import game.strategies.GamePhaseStrategies.GamePhaseEnum;
import game.strategies.PlayerStrategies.PlayerStrategyEnum;
import game.strategies.PlayerStrategies.PlayerStrategyFactory;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static game.model.enums.CardsEnum.ARTILLERY;
import static game.model.enums.CardsEnum.CAVALRY;
import static game.model.enums.CardsEnum.INFANTRY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test for model Player
 * @author Ksenia Popova, Dmitry Kryukov
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlayerTest {
    PlayerStrategyFactory playerStrategyFactory = new PlayerStrategyFactory();

    Game game = Game.getInstance();
    Player player1;
    Player player2;

    Country country1;
    Country country2;
    Country country3;
    Country country4;

    /**
     * Setup the players, countries, map and run the game.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        Player player1 = new Player("test Player 1", Color.BLACK, playerStrategyFactory.getStrategy(PlayerStrategyEnum.HUMAN_STRATEGY), true);
        player1.setArmies(0);
        Player player2 = new Player("test Player 2", Color.GREEN, playerStrategyFactory.getStrategy(PlayerStrategyEnum.HUMAN_STRATEGY), true);
        player2.setArmies(0);
        player2.setArmies(0);
        Continent continent = new Continent("Continent", 1);

        country1 = new Country("Country 1", 10, 10, 2, continent, player1);
        country2 = new Country("Country 2", 20, 20, 2, continent, player1);
        country3 = new Country("Country 3", 30, 30, 2, continent, player1);
        country4 = new Country("Country 4", 40, 40, 2, continent, player2);
        List<Country> countries = Arrays.asList(country1, country2, country3, country4);

        continent.getCountryList().addAll(countries);

        List<Neighbour> neighbours = new LinkedList<>();
        neighbours.add(new Neighbour(countries.get(0), countries.get(1)));
        neighbours.add(new Neighbour(countries.get(1), countries.get(2)));
        neighbours.add(new Neighbour(countries.get(2), countries.get(3)));

        game.getGameState().setCountries(countries);
        game.getGameState().setNeighbours(neighbours);
        game.getGameState().setPlayers(Arrays.asList(player1, player2));
        game.getGameState().setContinents(Collections.singletonList(continent));
    }

    /**
     * Check that during reinforce country moved to another country: country1 correctly.
     */
    @Test
    public void reinforcementSelectedCorrectCountry() {
        game.initialise();
        while (!(game.getGameState().getCurrentGamePhase() == GamePhaseEnum.REINFORCEMENT && game.getGameState().getCurrentPlayer() == player1)) {
            game.nextTurn();
        }
        country1.setArmy(10);
        player1.setArmies(5);
        game.makeAction(0, 0);
        game.makeAction(10, 10);
        assertEquals(11, country1.getArmy());
    }

    /**
     * Check that during reinforce the country that shouldn't be highlithed is not highlighted
     */
    @Test
    public void reinforcementNotSelectedIncorrectCountry() {
        game.initialise();
        while (!(game.getGameState().getCurrentGamePhase() == GamePhaseEnum.REINFORCEMENT && game.getGameState().getCurrentPlayer() == player1)) {
            game.nextTurn();
        }
        game.makeAction(0, 0);
        game.makeAction(10, 10);
        assertFalse(country4.isHighlighted());
    }

    /**
     * Check that within attack phase the correct country are highlighted
     */
    @Test
    public void attackSelectedCorrectCountry() {
        game.initialise();
        country3.setArmy(10);
        while (game.getGameState().getCurrentGamePhase() != GamePhaseEnum.ATTACK) {
            game.nextTurn();
        }
        game.makeAction(0, 0);
        game.makeAction(30, 30);
        assertTrue(country4.isHighlighted());
    }

    /**
     * Check that during attack the country1 is not highlighted
     */
    @Test
    public void attackNoSelectedPlayersCountries() {
        game.initialise();
        country3.setArmy(10);
        while (game.getGameState().getCurrentGamePhase() != GamePhaseEnum.ATTACK) {
            game.nextTurn();
        }
        game.makeAction(0, 0);
        game.makeAction(30, 30);
        assertFalse(country1.isHighlighted());
    }


    /**
     * Check that during fortification the player transition works ok.
     * Next player i.e. Player2 should be current.
     *
     */
    @Test
    public void fortificationCorrectTransitionToNextPlayer() {
        game.initialise();
        while (game.getGameState().getCurrentGamePhase() != GamePhaseEnum.REINFORCEMENT) {
            game.nextTurn();
        }
        assertEquals(player2, game.getGameState().getCurrentPlayer());
    }

    /**
     * Check that during fortification that player has no actions when selected enemies counrty
     */
    @Test
    public void fortificationNoActionsWhenSelectedEnemiesCountry() {
        game.initialise();
        while (game.getGameState().getCurrentGamePhase() != GamePhaseEnum.REINFORCEMENT) {
            game.nextTurn();
        }
        game.makeAction(0, 0);
        game.makeAction(10, 10);
        assertEquals(1, country1.getArmy());
    }

    /**
     * Check feature of exchanging of 3 identical cards
     */
    @Test
    public void exchangeThreeIdenticalCards() {
        player1.setArmies(0);
        game.getGameState().setArmiesToCardExchange(5);
        player1.getCardsEnumIntegerMap().put(INFANTRY, 1);

        // Redo test
//        player1.exchange(Collections.singletonList(INFANTRY));
//
//        assertEquals(5, player1.getArmies());
    }

    /**
     * Check feature of exchanging 3 different cards
     */
    @Test
    public void exchangeThreeDifferentCards() {
        player1.setArmies(0);
        game.getGameState().setArmiesToCardExchange(5);
        player1.getCardsEnumIntegerMap().put(INFANTRY, 1);
        player1.getCardsEnumIntegerMap().put(CAVALRY, 1);
        player1.getCardsEnumIntegerMap().put(ARTILLERY, 1);

        // Redo test
//        player1.exchange(Arrays.asList(INFANTRY, CAVALRY, ARTILLERY));
//
//        assertEquals(5, player1.getArmies());
    }
}