package game;

import game.model.*;
import game.strategies.GamePhaseStrategies.GamePhaseEnum;
import game.strategies.GamePhaseStrategies.GamePhaseStrategyFactory;
import game.ui.Main;
import game.utils.NotificationWindow;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
    GameState gameState;

    @Before
    public void setUp(){
        players.add(new Player("Test Player 1", Color.BLUE, HUMAN_STRATEGY, false));
        players.add(new Player("Test Player 2", Color.BLACK, HUMAN_STRATEGY, false));
        for(Player player : players){
            player.initStategy();
        }
        continents.add(new Continent("Continent 1", 1));
        continents.add(new Continent("Continent 2", 2));

        Country a = new Country("A",10,10, 2, continents.get(0), players.get(0));
        Country b = new Country("B",30,30, 2, continents.get(0), players.get(0));
        Country c = new Country("C",50,50, 2, continents.get(1), players.get(0));
        Country d = new Country("D",70,70, 2, continents.get(1), players.get(1));
        Country e = new Country("E",90,90, 2, continents.get(1), players.get(1));
        countries.add(a);
        countries.add(b);
        countries.add(c);
        countries.add(d);
        countries.add(e);

        a.getNeighbours().add(b);
        a.getNeighbours().add(c);
        a.getNeighbours().add(e);
        a.getNeighbours().add(d);

        b.getNeighbours().add(a);
        b.getNeighbours().add(c);
        b.getNeighbours().add(d);

        c.getNeighbours().add(a);
        c.getNeighbours().add(b);
        c.getNeighbours().add(d);
        c.getNeighbours().add(e);

        d.getNeighbours().add(b);
        d.getNeighbours().add(c);
        d.getNeighbours().add(e);
        d.getNeighbours().add(a);

        e.getNeighbours().add(c);
        e.getNeighbours().add(d);

        for(Country country : countries){
            country.getContinent().getCountryList().add(country);
        }

//        neighbours.add(new Neighbour(a,b));
//        neighbours.add(new Neighbour(a,c));
//        neighbours.add(new Neighbour(a,e));
//
//        neighbours.add(new Neighbour(b,a));
//        neighbours.add(new Neighbour(b,c));
//        neighbours.add(new Neighbour(b,d));
//
//        neighbours.add(new Neighbour(c,a));
//        neighbours.add(new Neighbour(c,b));
//        neighbours.add(new Neighbour(c,d));
//        neighbours.add(new Neighbour(c,e));
//
//        neighbours.add(new Neighbour(d,b));
//        neighbours.add(new Neighbour(d,c));
//        neighbours.add(new Neighbour(d,e));
//
//        neighbours.add(new Neighbour(e,c));
//        neighbours.add(new Neighbour(e,d));


        gameState = new GameState();
        gameState.setCountries(countries);
        gameState.setNeighbours(neighbours);
        gameState.setPlayers(players);
        gameState.setContinents(continents);
//        gameState.setMapFilePath(mapPath);

        game.setNotification(new NotificationWindow());
        game.setGameState(gameState);
        game.initialise();
    }

    /**
     * Startup phase test. Check if possible to assign armies to country
     */
    @Test
    public void startupPhasePlayer1Country1(){
        gameState.setCurrentGamePhase(GamePhaseEnum.PLACING_ARMIES);
        Game.getInstance().setGamePhaseStrategy(GamePhaseStrategyFactory.getStrategy(GamePhaseEnum.PLACING_ARMIES));
        Game.getInstance().getGamePhaseStrategy().init(gameState);
        gameState.setCurrentPlayer(players.get(0));

        players.get(0).setArmies(5);
        countries.get(0).setArmy(1);
        game.makeAction(0, 0);
        game.makeAction(10, 10);
        assertEquals(2, countries.get(0).getArmy());
    }

    /**
     * Startup phase test. Check if possible to assign armies to country
     */
    @Test
    public void startupPhasePlayer1Country2(){
        gameState.setCurrentGamePhase(GamePhaseEnum.PLACING_ARMIES);
        Game.getInstance().setGamePhaseStrategy(GamePhaseStrategyFactory.getStrategy(GamePhaseEnum.PLACING_ARMIES));
        Game.getInstance().getGamePhaseStrategy().init(gameState);
        gameState.setCurrentPlayer(players.get(0));

        players.get(0).setArmies(5);
        countries.get(1).setArmy(1);
        game.makeAction(0, 0);
        game.makeAction(30, 30);
        assertEquals(2, countries.get(1).getArmy());
    }

    /**
     * Startup phase test. Check if possible to assign armies to country
     */
    @Test
    public void startupPhasePlayer2Country1(){
        gameState.setCurrentGamePhase(GamePhaseEnum.PLACING_ARMIES);
        Game.getInstance().setGamePhaseStrategy(GamePhaseStrategyFactory.getStrategy(GamePhaseEnum.PLACING_ARMIES));
        Game.getInstance().getGamePhaseStrategy().init(gameState);
        gameState.setCurrentPlayer(players.get(1));

        players.get(1).setArmies(5);
        countries.get(4).setArmy(1);
        game.makeAction(0, 0);
        game.makeAction(90, 90);
        assertEquals(2, countries.get(4).getArmy());
    }

    /**
     * Startup phase test. Check if possible to assign armies to country
     */
    @Test
    public void startupPhasePlayer2Country2(){
        gameState.setCurrentGamePhase(GamePhaseEnum.PLACING_ARMIES);
        Game.getInstance().setGamePhaseStrategy(GamePhaseStrategyFactory.getStrategy(GamePhaseEnum.PLACING_ARMIES));
        Game.getInstance().getGamePhaseStrategy().init(gameState);
        gameState.setCurrentPlayer(players.get(1));

        players.get(1).setArmies(5);
        countries.get(3).setArmy(2);
        game.makeAction(0, 0);
        game.makeAction(70, 70);
        assertEquals(3, countries.get(3).getArmy());
    }


    /**
     * Reinforcement: Test for calculation of number of reinforcment armies
     */
    @Test
    public void reinforcementCountPlayer1() {
        gameState.setCurrentPlayer(players.get(0));
        gameState.setCurrentGamePhase(GamePhaseEnum.REINFORCEMENT);
        Game.getInstance().setGamePhaseStrategy(GamePhaseStrategyFactory.getStrategy(GamePhaseEnum.REINFORCEMENT));
        Game.getInstance().getGamePhaseStrategy().init(gameState);

        // By default it should assign 5 armies to reinforce to the user
        assertEquals(5, players.get(0).getArmies());
    }

    /**
     * Reinforcement: Test for calculation of number of reinforcment armies
     */
    @Test
    public void reinforcementCountPlayer2() {
        gameState.setCurrentPlayer(players.get(1));
        gameState.setCurrentGamePhase(GamePhaseEnum.REINFORCEMENT);
        Game.getInstance().setGamePhaseStrategy(GamePhaseStrategyFactory.getStrategy(GamePhaseEnum.REINFORCEMENT));
        Game.getInstance().getGamePhaseStrategy().init(gameState);

        // By default it should assign 5 armies to reinforce to the user
        assertEquals(5, players.get(1).getArmies());
    }

    /**
     * Reinforcement:  Test for calculation of number of reinforcment armies
     */
    @Test
    public void reinforcementCountAssignManual() {
        gameState.setCurrentPlayer(players.get(0));
        gameState.setCurrentGamePhase(GamePhaseEnum.REINFORCEMENT);
        Game.getInstance().setGamePhaseStrategy(GamePhaseStrategyFactory.getStrategy(GamePhaseEnum.REINFORCEMENT));
        Game.getInstance().getGamePhaseStrategy().init(gameState);
        players.get(0).setArmies(30);
        assertEquals(30, players.get(0).getArmies());
    }

    /**
     * Attack phase: Check that current phase is attack
     */
    @Test
    public void attackCheckPhase() {
        gameState.setCurrentPlayer(players.get(0));
        countries.get(0).setArmy(2);
        gameState.setCurrentGamePhase(GamePhaseEnum.ATTACK);
        Game.getInstance().setGamePhaseStrategy(GamePhaseStrategyFactory.getStrategy(GamePhaseEnum.ATTACK));
        Game.getInstance().getGamePhaseStrategy().init(gameState);

        assertEquals("Attack your enemy.", gameState.getCurrentGamePhase().getName());
    }

    /**
     * Attack phase: Check that current phrase in status about attacking
     */
    @Test
    public void attackCheckPhrase() {
        gameState.setCurrentPlayer(players.get(0));
        countries.get(0).setArmy(2);
        gameState.setCurrentGamePhase(GamePhaseEnum.ATTACK);
        Game.getInstance().setGamePhaseStrategy(GamePhaseStrategyFactory.getStrategy(GamePhaseEnum.ATTACK));
        Game.getInstance().getGamePhaseStrategy().init(gameState);

        assertEquals("Select a Country to attack from.", gameState.getCurrentTurnPhraseText());
    }

    /**
     * Attack phase: Check that current player ia attacker
     */
    @Test
    public void attackCheckAttacker() {
        gameState.setCurrentPlayer(players.get(0));
        countries.get(0).setArmy(2);
        gameState.setCurrentGamePhase(GamePhaseEnum.ATTACK);
        Game.getInstance().setGamePhaseStrategy(GamePhaseStrategyFactory.getStrategy(GamePhaseEnum.ATTACK));
        Game.getInstance().getGamePhaseStrategy().init(gameState);
        game.makeAction(10, 10); // Select country from

        assertEquals("A", gameState.getCountryFrom().getName());
        assertEquals("Test Player 1", gameState.getCountryFrom().getPlayer().getName()); // Check that attacker is player 1
    }

    /**
     * Attack phase: Check correct deffender
     */
    @Test
    public void attackCheckDeffender() {
        gameState.setCurrentPlayer(players.get(0)); // player 1 is attacker
        countries.get(0).setArmy(2);
        countries.get(3).setArmy(1);
        gameState.setCurrentGamePhase(GamePhaseEnum.ATTACK);
        Game.getInstance().setGamePhaseStrategy(GamePhaseStrategyFactory.getStrategy(GamePhaseEnum.ATTACK));
        Game.getInstance().getGamePhaseStrategy().init(gameState);

        game.makeAction(10, 10); // Select country from
        game.makeAction(70, 70); // Select country to

        assertEquals("D", gameState.getCountryTo().getName());
        assertEquals("Test Player 2", gameState.getCountryTo().getPlayer().getName()); // Check that deffender is player 2
    }

    /**
     * Fortification phase: Check that player 1 able to move countries to another country
     */
    @Test
    public void fortificationPlayer1() {
        gameState.setCurrentPlayer(players.get(0)); // current player 1
        countries.get(0).setArmy(4); // set A 4 for player 1 country
        countries.get(1).setArmy(1); // Set B 1 for player 1 country
        gameState.setCurrentGamePhase(GamePhaseEnum.FORTIFICATION);
        Game.getInstance().setGamePhaseStrategy(GamePhaseStrategyFactory.getStrategy(GamePhaseEnum.FORTIFICATION));
        Game.getInstance().getGamePhaseStrategy().init(gameState);

        game.makeAction(10, 10); // Move from A
        game.makeAction(30, 30); // Move to B

        assertEquals(3, countries.get(0).getArmy()); // should become 3 for A
        assertEquals(2, countries.get(1).getArmy()); // should become 2 for B
    }

    /**
     * Fortification phase: Check that player 2 are not able to move countries to another country i.e. Go to next turn
     * Since on the fortification phase player 2 doesn't have countries with more than 1 armies,
     * he pass the fortification and go to reinforcement
     */
    @Test
    public void fortificationPlayer2() {
        gameState.setCurrentPlayer(players.get(1)); // current player 2
        countries.get(0).setArmy(4); // set A 4 for player 1 counrty
        countries.get(1).setArmy(1); // Set B 1 for player 1 country
        gameState.setCurrentGamePhase(GamePhaseEnum.FORTIFICATION);
        Game.getInstance().setGamePhaseStrategy(GamePhaseStrategyFactory.getStrategy(GamePhaseEnum.FORTIFICATION));
        Game.getInstance().getGamePhaseStrategy().init(gameState);
        // Since on the fortification phase player 2 doesn't have countries with more than 1 armies,
        // he pass the fortification and go to Reinforcement
        assertEquals("Reinforce your positions.", gameState.getCurrentGamePhase().getName());
        assertEquals(4, countries.get(0).getArmy()); // should be 4
        assertEquals(1, countries.get(1).getArmy()); // should be 1
    }

    /**
     * End of game : Test correct ending the game phase
     */
    @Test
    public void endOfGameCheckDraw() {
        gameState.setCurrentPlayer(players.get(1));
        gameState.setCurrentGamePhase(GamePhaseEnum.GAME_OVER);
        Game.getInstance().setGamePhaseStrategy(GamePhaseStrategyFactory.getStrategy(GamePhaseEnum.GAME_OVER));
        Game.getInstance().getGamePhaseStrategy().init(gameState);

        assertEquals("Game over. The Draw.", gameState.getCurrentTurnPhraseText());
    }

    /**
     * End of game : Test correct ending the game phase
     */
    @Test
    public void endOfGameCheckPlayer() {
        gameState.setCurrentGamePhase(GamePhaseEnum.GAME_OVER);
        Game.getInstance().setGamePhaseStrategy(GamePhaseStrategyFactory.getStrategy(GamePhaseEnum.GAME_OVER));
        Game.getInstance().getGamePhaseStrategy().init(gameState);
        // Set the player 2 as a winner
        gameState.setCurrentPlayer(players.get(1));
        players.get(0).setLost(true);
        assertFalse(gameState.getCurrentPlayer().isLost());
    }

    /**
     * End of game : Test correct ending the game phase
     */
    @Test
    public void endOfGameCheckPhase() {
        gameState.setCurrentPlayer(players.get(1));
        gameState.setCurrentGamePhase(GamePhaseEnum.GAME_OVER);
        Game.getInstance().setGamePhaseStrategy(GamePhaseStrategyFactory.getStrategy(GamePhaseEnum.GAME_OVER));
        Game.getInstance().getGamePhaseStrategy().init(gameState);

        assertEquals("Game Over!", gameState.getCurrentGamePhase().getName());
    }
}