package game;

import view.MapPanel;
import view.StatusPanel;

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

//        Game game = new Game();

        // Right Panel
        // Right Controls Panel
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setPreferredSize(new Dimension(200 - 2, 600 - 2));

        // Right Info Panel
        StatusPanel statusPanel = new StatusPanel(200 - 2, 100 - 2);
        statusPanel.setBorder(new LineBorder(Color.BLUE, 1));

        // Left Panel
        // Game message Panel on top
        JPanel statusMessagePanel = new JPanel();
        statusMessagePanel.setPreferredSize(new Dimension(600 - 2, 30 - 2));
        statusMessagePanel.add(new Label("Status panel"), Component.CENTER_ALIGNMENT);

        // Map Panel Map
        MapPanel mapPanel = new MapPanel(new Dimension(600 - 2, 570 - 2), game.getCountries(), game.neighbours, statusPanel);
        mapPanel.setBorder(new LineBorder(Color.BLACK, 1));


        // Adding panels
        infoPanel.add(statusPanel, BorderLayout.NORTH);
        infoPanel.setBorder(new LineBorder(Color.RED, 1));

        JPanel boardPanel = new JPanel(new BorderLayout());

        boardPanel.add(statusMessagePanel, BorderLayout.NORTH);
        boardPanel.setBorder(new LineBorder(Color.GREEN, 1));
        boardPanel.add(mapPanel);

        frame.add(boardPanel, BorderLayout.WEST);

        frame.add(infoPanel, BorderLayout.EAST);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}