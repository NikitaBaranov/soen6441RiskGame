package game.ui;

import game.Game;
import game.ui.view.AttackPanel;
import game.ui.view.DicePanel;
import game.ui.view.MapPanel;
import game.ui.view.RightStatusPanel;
import game.ui.view.TopStatusPanel;
import game.utils.MapLoader;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * The main window of the game. Graphics interface.
 * @author Dmitry Kryukov, Ksenia Popova
 * @see Game
 * @see DicePanel
 * @see MapPanel
 * @see RightStatusPanel
 * @see TopStatusPanel
 * @see MapLoader
 */
public class Main extends Thread {

    private int width = 1300;
    private int height = 700;

    private Game game;

    public Main(Game game) {
        this.game = game;
    }

    /**
     * Constructor of the class. Runs the game
     * @param game instance of the game class
     */
    public String runGame(Game game) {
        Runnable runnable = new Runnable() {
            public void run() {
                createAndShowGui(game);
            }
        };

        SwingUtilities.invokeLater(runnable);

        try {
            runnable.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return game.getGameState().getResult();
    }

    @Override
    public void run() {
        createAndShowGui(game);
    }

    private void closeWindow() {
        // shutdown the thread.
    }

    /**
     * Method created the panels, windows and all gui, then runs the game.
     * @param game instance of game controller
     */
    private void createAndShowGui(Game game) {
        final JFrame frame = new JFrame("Risk");
        frame.setPreferredSize(new Dimension(width,height));
        //frame.setDefaultLookAndFeelDecorated(true);
        if (game.getGameState().isTurnament()) {
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            frame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    closeWindow();
                }
            });
        } else {
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
        frame.setResizable(false);
        // Top Status Game Info Bar
        TopStatusPanel topStatusPanel = new TopStatusPanel(width, 30);
        topStatusPanel.setBackground(new Color(121,180,115));

        // Right Panel
        // Right Controls Panel
        JPanel infoPanel = new JPanel(new FlowLayout());
        infoPanel.setPreferredSize(new Dimension(350, height));
        infoPanel.setBackground(new Color(65,102,138));

        // Right Info Panel
//        JLabel attackLable = new JLabel("Attack:");
        // Player Panel
        RightStatusPanel rightStatusPanel = new RightStatusPanel(320, 300);
        rightStatusPanel.setBackground(new Color(65,102,138));

        // Attack Panel
        AttackPanel attackPanel = new AttackPanel(320, 340);
        attackPanel.setBackground(new Color(65,102,138));

        // Dice Panel
//        DicePanel dicePanel = new DicePanel(100, 170);
//        dicePanel.setBackground(new Color(255,255,255));

        // Left Panel
        // Map Panel Map
        //TODO: Extract Logic and reshuffle top to bottom left to right.
        MapPanel mapPanel = new MapPanel(new Dimension(width - 350, height));
        //mapPanel.setBorder(new LineBorder(Color.BLACK, 4));
        mapPanel.setBackground(new Color(119,178,140));
//        game.mapPanel = mapPanel;

        // Adding panels
        infoPanel.add(rightStatusPanel);

//        infoPanel.add(attackLable);
        infoPanel.add(attackPanel);
//        infoPanel.add(dicePanel);
        infoPanel.setBorder(new LineBorder(Color.BLACK, 1));

        game.initialise(frame);

        frame.add(topStatusPanel, BorderLayout.NORTH);
        frame.add(mapPanel, BorderLayout.WEST);
        frame.add(infoPanel, BorderLayout.EAST);

        frame.pack();
        frame.setLocationRelativeTo(null);
        // Try this if the above setLocationRelativeTo doesnt'work
        // I have two screens ... centreWindow opens a windows in the middle of them
        // centreWindow(frame);
        frame.setVisible(true);
    }

    /**
     * Additional method to center window if needed
     * @param frame object with window
     */
    public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }
}