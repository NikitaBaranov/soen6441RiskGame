package game.ui;

import game.Game;
import game.ui.view.DicePanel;
import game.ui.view.MapPanel;
import game.ui.view.RightStatusPanel;
import game.ui.view.TopStatusPanel;

import javax.swing.*;
import java.awt.*;

public class Main {

    private int width = 1200;
    private int height = 700;

    public Main(Game game) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGui(game);
            }
        });
    }

    private void createAndShowGui(Game game) {
        final JFrame frame = new JFrame("Risk");
        frame.setPreferredSize(new Dimension(width,height));
        frame.setDefaultLookAndFeelDecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // Line below fix window in the middle and forbid to close so I commented out
        // frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        // Top Status Game Info Bar
        TopStatusPanel topStatusPanel = new TopStatusPanel(width, 30);
        //topStatusPanel.setBorder(new LineBorder(Color.BLUE, 1));
        topStatusPanel.setBackground(new Color(121,180,115));

        // Right Panel
        // Right Controls Panel
        JPanel infoPanel = new JPanel(new FlowLayout());
        infoPanel.setPreferredSize(new Dimension(250, height));
        infoPanel.setBackground(new Color(65,102,138));

        // Right Info Panel
        // Player Panel
        RightStatusPanel rightStatusPanel = new RightStatusPanel(220, 250);
        //rightStatusPanel.setBorder(new LineBorder(Color.BLUE, 1));
        rightStatusPanel.setBackground(new Color(65,102,138));

        // Player Panel
        DicePanel dicePanel = new DicePanel(100, 170);
        //dicePanel.setBorder(new LineBorder(Color.BLACK, 1));
        dicePanel.setBackground(new Color(255,255,255));

        // Left Panel
        // Map Panel Map
        //TODO: Extract Logic and reshuffle top to bottom left to right.
        MapPanel mapPanel = new MapPanel(new Dimension(950, height), game.getCountries(), game.neighbours, topStatusPanel, rightStatusPanel);
        //mapPanel.setBorder(new LineBorder(Color.BLACK, 4));
        mapPanel.setBackground(new Color(119,178,140));

        // Adding panels
        infoPanel.add(rightStatusPanel);
        infoPanel.add(dicePanel);
        //infoPanel.setBorder(new LineBorder(Color.RED, 1));

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
    public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }
}