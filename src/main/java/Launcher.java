import game.utils.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * The launcher of the game.
 * @see MainMenu
 * @author Dmitry Kryukov
 */
public class Launcher {
    private static final Logger log = LogManager.getLogger(Launcher.class);

    /**
     * Constructor of the class. Creates an instance of MainMenu object
     * for displaying the menu window
     * @param title message on title bar
     * @param width of the menu
     * @param height of the menu
     */
    public Launcher(String title, int width, int height){
        Config conf = new Config();
        log.debug(conf.prop.getProperty("ai.luck"));
        log.debug(conf.prop.getProperty("ai.pause"));
        log.debug("DEBUG: Launcher is started");
        new MainMenu(title, width, height);
    }

    /**
     * The starter method
     * @param args arguments if needed
     */
    public static void main(String[] args){
        new Launcher("RiskGame by Team 3", 450, 250);
    }

}