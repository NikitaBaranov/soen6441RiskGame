package game;

import view.DicePanel;
import view.MapPanel;
import view.PlayerStatusPanel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Main {

    public Main(Game game) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGui(game);
            }
        });
    }

    private static void createAndShowGui(Game game) {
        final JFrame frame = new JFrame("Risk");
        frame.setPreferredSize(new Dimension(800, 600));

        // Top Status Game Info Bar
        JPanel statusMessagePanel = new JPanel();
        statusMessagePanel.setPreferredSize(new Dimension(600 - 2, 30 - 2));
        statusMessagePanel.add(new Label("Status panel"), Component.CENTER_ALIGNMENT);


        // Right Panel
        // Right Controls Panel
        JPanel infoPanel = new JPanel(new FlowLayout());
        infoPanel.setPreferredSize(new Dimension(200 - 2, 600 - 2));

        // Right Info Panel
        // Player Panel
        PlayerStatusPanel playerStatusPanel = new PlayerStatusPanel(200 - 2, 100 - 2);
        playerStatusPanel.setBorder(new LineBorder(Color.BLUE, 1));


        // Player Panel
        DicePanel dicePanel = new DicePanel(100 - 2, 150 - 2);
//        dicePanel.setBorder(new LineBorder(Color.BLACK, 1));


        // Left Panel
        // Map Panel Map
        //TODO: Extract Logic and reshuffle top to bottom left to right.
        MapPanel mapPanel = new MapPanel(new Dimension(600 - 2, 570 - 2), game.getCountries(), game.neighbours, playerStatusPanel);
        mapPanel.setBorder(new LineBorder(Color.BLACK, 1));


        // Adding panels
        infoPanel.add(playerStatusPanel);
        infoPanel.add(dicePanel);
        infoPanel.setBorder(new LineBorder(Color.RED, 1));

        JPanel boardPanel = new JPanel(new BorderLayout());

        boardPanel.setBorder(new LineBorder(Color.BLACK, 1));
        boardPanel.add(mapPanel);

        frame.add(statusMessagePanel, BorderLayout.NORTH);
        frame.add(boardPanel, BorderLayout.WEST);

        frame.add(infoPanel, BorderLayout.EAST);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
