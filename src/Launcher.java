import ui.MainWindow;

public class Launcher {

    public Launcher(String title, int width, int height){
        System.out.println("DEBUG: Launcher is tarted\n-------------------------\n");
        MainWindow window = new MainWindow(title, width, height);
    }

    public static void main(String[] args){
        new Launcher("RiskGame by Team 3", 1100, 750);
    }

}