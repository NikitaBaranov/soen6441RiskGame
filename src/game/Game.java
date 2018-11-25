package game;

import game.model.Continent;
import game.model.Country;
import game.model.GameState;
import game.model.Neighbour;
import game.model.Player;
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
     * Getter for the game state. Needs to get the game state from the controller
     * @return gameState
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Setter for the game state. Needs to set the game state to the controller
     * @param gameState
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Getter for strategy of the game.
     * @return gamePhaseStrategy
     */
    public IGamePhaseStrategy getGamePhaseStrategy() {
        return gamePhaseStrategy;
    }

    /**
     * Setter for strategy of the game
     * @param gamePhaseStrategy
     */
    public void setGamePhaseStrategy(IGamePhaseStrategy gamePhaseStrategy) {
        this.gamePhaseStrategy = gamePhaseStrategy;
    }
}