package game.model;

import game.Game;
import game.enums.GamePhaseEnum;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static game.enums.CardsEnum.*;
import static org.junit.Assert.*;

/**
 * Test for model Player
 * @author Ksenia Popova, Dmitry Kryukov
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlayerTest {

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
        player1 = new Player("test Player 1", Color.BLACK);
        player1.setArmies(0);
        player2 = new Player("test Player 2", Color.GREEN);
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

        game.setCountries(countries);
        game.setNeighbours(neighbours);
        game.setPlayers(Arrays.asList(player1, player2));
        game.setContinents(Collections.singletonList(continent));
    }

    /**
     * Check if reinforcement selected correct country
     */
    @Test
    public void reinforcementSelectedCorrectCountry() {
        game.initialise();
        while (!(game.getCurrentGamePhase() == GamePhaseEnum.REINFORCEMENT && game.getCurrentPlayer() == player1)) {
            game.nextTurn();
        }
        country1.setArmy(10);
        player1.setArmies(5);
        game.makeAction(0, 0);
        game.makeAction(10, 10);
        assertEquals(11, country1.getArmy());
    }

    /**
     * Check if reinforcement not selected incorrect country
     */
    @Test
    public void reinforcementNotSelectedIncorrectCountry() {
        game.initialise();
        while (!(game.getCurrentGamePhase() == GamePhaseEnum.REINFORCEMENT && game.getCurrentPlayer() == player1)) {
            game.nextTurn();
        }
        game.makeAction(0, 0);
        game.makeAction(10, 10);
        assertFalse(country4.isHighlighted());
    }

    @Test
    public void attackSelectedCorrectCountry() {
        game.initialise();
        country3.setArmy(10);
        while (game.getCurrentGamePhase() != GamePhaseEnum.ATTACK) {
            game.nextTurn();
        }
        game.makeAction(0, 0);
        game.makeAction(30, 30);
        assertTrue(country4.isHighlighted());
    }

    @Test
    public void attackNoSelectedPlayersCountries() {
        game.initialise();
        country3.setArmy(10);
        while (game.getCurrentGamePhase() != GamePhaseEnum.ATTACK) {
            game.nextTurn();
        }
        game.makeAction(0, 0);
        game.makeAction(30, 30);
        assertFalse(country1.isHighlighted());
    }


    /**
     * Check fortification correct transition to next player
     */
    @Test
    public void fortificationCorrectTransitionToNextPlayer() {
        game.initialise();
        while (game.getCurrentGamePhase() != GamePhaseEnum.REINFORCEMENT) {
            game.nextTurn();
        }
        assertEquals(player2, game.getCurrentPlayer());
    }

    /**
     * Check fortification that player has no actions when selected enemies counrty
     */
    @Test
    public void fortificationNoActionsWhenSelectedEnemiesCountry() {
        game.initialise();
        while (game.getCurrentGamePhase() != GamePhaseEnum.REINFORCEMENT) {
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
        game.setArmiesToCardExchange(5);
        player1.getCardsEnumIntegerMap().put(INFANTRY, 1);

        player1.exchange(Collections.singletonList(INFANTRY));

        assertEquals(5, player1.getArmies());
    }

    /**
     * Check feature of exchanging 3 different cards
     */
    @Test
    public void exchangeThreeDifferentCards() {
        player1.setArmies(0);
        game.setArmiesToCardExchange(5);
        player1.getCardsEnumIntegerMap().put(INFANTRY, 1);
        player1.getCardsEnumIntegerMap().put(CAVALRY, 1);
        player1.getCardsEnumIntegerMap().put(ARTILLERY, 1);

        player1.exchange(Arrays.asList(INFANTRY, CAVALRY, ARTILLERY));

        assertEquals(5, player1.getArmies());
    }
}