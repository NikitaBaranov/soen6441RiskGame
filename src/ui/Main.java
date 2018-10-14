package ui;

import game.Game;
import ui.view.DicePanel;
import ui.view.MapPanel;
import ui.view.RightStatusPanel;
import ui.view.TopStatusPanel;

import javax.swing.*;
import javax.swing.border.LineBorder;
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
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

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
        RightStatusPanel rightStatusPanel = new RightStatusPanel(220, 360);
        //rightStatusPanel.setBorder(new LineBorder(Color.BLUE, 1));
        rightStatusPanel.setBackground(new Color(65,64,115));

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
        frame.setVisible(true);
    }
}