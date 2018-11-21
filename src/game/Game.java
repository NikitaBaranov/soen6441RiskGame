package game;

import game.model.*;
import game.model.enums.CardsEnum;
import game.model.enums.DiceEnum;
import game.strategies.GamePhaseStrategies.GamePhaseEnum;
import game.strategies.GamePhaseStrategies.GamePhaseStrategyFactory;
import game.strategies.GamePhaseStrategies.IGamePhaseStrategy;
import game.ui.view.DicePanel;
import game.ui.view.MapPanel;
import game.ui.view.RightStatusPanel;
import game.ui.view.TopStatusPanel;

import static game.strategies.GamePhaseStrategies.GamePhaseEnum.PLACING_ARMIES;

/**
 * The game file which control all the game flow.
 * i.e. Controller in the MVC arcthitecture model
 *
 * @author Dmitry kryukov, Ksenia Popova
 * @see DiceEnum
 * @see CardsEnum
 * @see GamePhaseEnum
 * @see Continent
 * @see Country
 * @see Player
 * @see Neighbour
 * @see DicePanel
 * @see MapPanel
 * @see RightStatusPanel
 * @see TopStatusPanel
 */
public class Game {

    private static Game gameInstance;
    private GameState gameState;
    private IGamePhaseStrategy gamePhaseStrategy;

    /**
     * get instance method for Controller
     *
     * @return gameInstance
     */
    public static Game getInstance() {
        if (gameInstance == null) {
            gameInstance = new Game();
        }
        return gameInstance;
    }

    /**
     * Initialize the game
     */
    public void initialise() {
        gamePhaseStrategy = GamePhaseStrategyFactory.getStrategy(PLACING_ARMIES);
        gamePhaseStrategy.init(gameState);
        gameState.notifyObservers();
    }

    /**
     * Method describes the main flow. I.E. actions with the game.
     */
    public void makeAction(int x, int y) {
        gamePhaseStrategy.mapClick(gameState, x, y);
        gameState.notifyObservers();
    }

    /**
     * Next turn functionality
     */
    public void nextTurn() {
        gamePhaseStrategy.nextTurnButton(gameState);
        gameState.notifyObservers();
    }

    /**
     * Exchange methods for exchanging cards for player.
     */
    public void exchange() {
        gameState.getCurrentPlayer().exchange(gameState);
        gameState.notifyObservers();
    }

    /**
     * Attack
     */
    public void attack() {
        gamePhaseStrategy.attackButton(gameState);
        gameState.notifyObservers();
    }


    /**
     * Reset highlights
     */
    public void resetToAndFrom() {
        unHighlightCountries();
        if (gameState.getCountryFrom() != null) {
            gameState.getCountryFrom().unSelect(false);
        }
        gameState.setCountryFrom(null);

        if (gameState.getCountryTo() != null) {
            gameState.getCountryTo().unSelect(false);
        }
        gameState.setCountryTo(null);
    }

    /**
     * Method that unhighlight the players countries
     */
    public void unHighlightCountries() {
        for (Country c : gameState.getCountries()) {
            c.setHighlighted(false);
        }
    }

    /**
     * Check if player can attack anybody or go to next turn
     *
     * @return
     */
    public boolean isMoreAttacks() {
        for (Country country : gameState.getCountries()) {
            if (country.getPlayer() == gameState.getCurrentPlayer() && country.getArmy() >= 2) {
                for (Country neighbor : country.getNeighbours()) {
                    if (neighbor.getPlayer() != gameState.getCurrentPlayer()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public IGamePhaseStrategy getGamePhaseStrategy() {
        return gamePhaseStrategy;
    }

    public void setGamePhaseStrategy(IGamePhaseStrategy gamePhaseStrategy) {
        this.gamePhaseStrategy = gamePhaseStrategy;
    }
}