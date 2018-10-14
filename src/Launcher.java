import game.ui.MainMenu;

public class Launcher {

    public Launcher(String title, int width, int height){
        System.out.println("DEBUG: Launcher is started\n-------------------------\n");
        MainMenu window = new MainMenu(title, width, height);
    }

    public static void main(String[] args){
        new Launcher("RiskGame by Team 3", 450, 250);
    }

}