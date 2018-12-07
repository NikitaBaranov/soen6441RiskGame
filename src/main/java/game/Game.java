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
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
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
@Data
public class Game {
    private static final Logger log = LogManager.getLogger(Game.class);
    private static Game gameInstance;
    private GameState gameState;
    private IGamePhaseStrategy gamePhaseStrategy;
    private NotificationWindow notification;
    private JFrame jFrame;

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
    public void initialise(JFrame jFrame) {
        this.jFrame = jFrame;
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
     * Save game action.
     * Save gamestate object with all states to the file.
     */
    public void save() {
        try {
            String path = this.getClass().getResource("/saves").getPath();
            log.debug("Path for save: "+path);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
            FileOutputStream myFileOutputStream = new FileOutputStream(path + "save" + dateTimeFormatter.format(LocalDateTime.now()) + ".risk");
            ObjectOutputStream myObjectOutputStream = new ObjectOutputStream(myFileOutputStream);
            myObjectOutputStream.writeObject(gameState);
            myObjectOutputStream.close();
        } catch (Exception e) {
            log.error("Error when saving to file. ");
            e.printStackTrace();
        }
    }

    /**
     * Save game for test purposes action.
     * Save gamestate object with all states to the file.
     */
    public void saveForTest() {
        try {
            FileOutputStream myFileOutputStream = new FileOutputStream("./src/test/resources/SaveTest.risk");
            ObjectOutputStream myObjectOutputStream = new ObjectOutputStream(myFileOutputStream);
            myObjectOutputStream.writeObject(gameState);
            myObjectOutputStream.close();
        } catch (Exception e) {
            log.error("Error when saving to file. ");
            e.printStackTrace();
        }
    }
}