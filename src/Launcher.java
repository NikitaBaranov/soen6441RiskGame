/**
 * The launcher of the game.
 * @see MainMenu
 * @author Dmitry Kryukov
 */
public class Launcher {

    /**
     * Constructor of the class. Creates an instance of MainMenu object
     * for displaying the menu window
     * @param title message on title bar
     * @param width of the menu
     * @param height of the menu
     */
    public Launcher(String title, int width, int height){
        System.out.println("DEBUG: Launcher is started\n-------------------------\n");
        MainMenu window = new MainMenu(title, width, height);
    }

    /**
     * The starter method
     * @param args arguments if needed
     */
    public static void main(String[] args){
        new Launcher("RiskGame by Team 3", 450, 250);
    }

}