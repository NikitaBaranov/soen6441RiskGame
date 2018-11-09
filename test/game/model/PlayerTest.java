package game.model;

import game.Game;
import game.enums.GamePhase;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static game.enums.CardsEnum.ARTILLERY;
import static game.enums.CardsEnum.CAVALRY;
import static game.enums.CardsEnum.INFANTRY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PlayerTest {

    Game game = Game.getInstance();
    Player player1;
    Player player2;

    Country country1;
    Country country2;
    Country country3;
    Country country4;

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

    @Test
    public void reinforcementSelectedCorrectCountry() {
        game.initialise();
        while (game.getCurrentGamePhase() != GamePhase.ATTACK) {
            game.nextTurn();
        }
        game.makeAction(10, 10);
        assertTrue(country3.isHighlighted());
    }

    @Test
    public void reinforcementNotSelectedIncorrectCountry() {
        game.initialise();
        while (game.getCurrentGamePhase() != GamePhase.ATTACK) {
            game.nextTurn();
        }
        game.makeAction(10, 10);
        assertFalse(country4.isHighlighted());
    }

    @Test
    public void beforeAndAfterAttack() {
    }

    @Test
    public void attack() {
    }

    @Test
    public void fortificationCorrectTransitionToNextPlayer() {
        game.initialise();
        while (game.getCurrentGamePhase() != GamePhase.REINFORCEMENT) {
            game.nextTurn();
        }
        assertEquals(player2, game.getCurrentPlayer());
    }

    @Test
    public void fortificationNoActionsWhenSelectedEnemiesCountry() {
        game.initialise();
        while (game.getCurrentGamePhase() != GamePhase.REINFORCEMENT) {
            game.nextTurn();
        }
        game.makeAction(10, 10);
        assertEquals(1, country1.getArmy());
    }

    @Test
    public void exchangeThreeIdenticalCards() {
        player1.setArmies(0);
        game.setArmiesToCardExchange(5);
        player1.getCardsEnumIntegerMap().put(INFANTRY, 1);

        player1.exchange(Collections.singletonList(INFANTRY));

        assertEquals(5, player1.getArmies());
    }


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