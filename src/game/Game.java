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
import game.utils.NotificationWindow;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static game.strategies.GamePhaseStrategies.GamePhaseEnum.PLACING_ARMIES;

/**
 * The game file which control all the game flow.
 * i.e. Controller in the MVC architecture model
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
    private NotificationWindow notification;

    /**
     * Get instance method for Controller
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
     * Initialize the game. Start from the frist phase: Placing armies
     */
    public void initialise() {
        if (gameState.getCurrentGamePhase() == null) {
            gameState.setCurrentGamePhase(PLACING_ARMIES);
        }
        gamePhaseStrategy = GamePhaseStrategyFactory.getStrategy(gameState.getCurrentGamePhase());
        gamePhaseStrategy.init(gameState);
        gameState.notifyObservers();
    }

    /**
     * Method describes action. When user do something on the map
     * notify observers about that
     */
    public void makeAction(int x, int y) {
        gamePhaseStrategy.mapClick(gameState, x, y);
        gameState.notifyObservers();
    }

    /**
     * Next turn. Notify observers when button is pressed
     */
    public void nextTurn() {
        gamePhaseStrategy.nextTurnButton(gameState);
        gameState.notifyObservers();
    }

    /**
     * Exchange.Notify observers when button is pressed
     */
    public void exchange() {
        gameState.getCurrentPlayer().exchange(gameState);
        gameState.notifyObservers();
    }

    /**
     * Attack.Notify observers when button is pressed
     */
    public void attack() {
        gamePhaseStrategy.attackButton(gameState);
        gameState.notifyObservers();
    }

    /**
     * Getter for the game state. Needs to get the game state from the controller
     *
     * @return gameState
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Setter for the game state. Needs to set the game state to the controller
     *
     * @param gameState
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Getter for strategy of the game.
     *
     * @return gamePhaseStrategy
     */
    public IGamePhaseStrategy getGamePhaseStrategy() {
        return gamePhaseStrategy;
    }

    /**
     * Setter for strategy of the game
     *
     * @param gamePhaseStrategy
     */
    public void setGamePhaseStrategy(IGamePhaseStrategy gamePhaseStrategy) {
        this.gamePhaseStrategy = gamePhaseStrategy;
    }

    /**
     * Allows to get notification object everywhere in the game
     *
     * @return
     */
    public NotificationWindow getNotification() {
        return notification;
    }

    /**
     * Set the notification
     *
     * @param notification
     */
    public void setNotification(NotificationWindow notification) {
        this.notification = notification;
    }

    /**
     * Save game action.
     * Save gamestate object with all states to the file.
     */
    public void save() {
        try {
            String path = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "saves/";
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
            FileOutputStream myFileOutputStream = new FileOutputStream(path + "save" + dateTimeFormatter.format(LocalDateTime.now()) + ".risk");
            ObjectOutputStream myObjectOutputStream = new ObjectOutputStream(myFileOutputStream);
            myObjectOutputStream.writeObject(gameState);
            myObjectOutputStream.close();
        } catch (Exception e) {
            System.out.println("Error when saving to file. ");
            e.printStackTrace();
        }
    }

    /**
     * Save game for test purposes action.
     * Save gamestate object with all states to the file.
     */
    public void saveForTest() {
        try {
            FileOutputStream myFileOutputStream = new FileOutputStream("./test/resources/SaveTest.risk");
            ObjectOutputStream myObjectOutputStream = new ObjectOutputStream(myFileOutputStream);
            myObjectOutputStream.writeObject(gameState);
            myObjectOutputStream.close();
        } catch (Exception e) {
            System.out.println("Error when saving to file. ");
            e.printStackTrace();
        }
    }
}