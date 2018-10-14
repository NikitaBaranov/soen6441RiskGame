package game.ui.view;

import game.model.Country;
import game.model.Neighbour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MapPanel extends JPanel {
    private List<Neighbour> neighbours;
    private List<Country> countries;
    TopStatusPanel topStatusPanel;
    private RightStatusPanel rightStatusPanel;

    public MapPanel(Dimension dimension, List<Country> countries, List<Neighbour> neighbours, TopStatusPanel topStatusPanel, RightStatusPanel rightStatusPanel) {
        this.countries = countries;
        this.neighbours = neighbours;
        this.topStatusPanel = topStatusPanel;
        this.rightStatusPanel = rightStatusPanel;
        this.setPreferredSize(dimension);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Point mouse = e.getPoint();
                System.out.print(" x = " + mouse.x + " y = " + mouse.y);

                for (Country country : countries) {
                    country.resetView();
                }
                topStatusPanel.reset();
                rightStatusPanel.reset();

                for (Country country : countries) {
                    if (country.isInBorder(mouse.x, mouse.y)) {
                        topStatusPanel.setPlayer(country.getPlayer());
                        topStatusPanel.setGamePhase("Game Phase");
                        topStatusPanel.setGameState("Game State");
                        topStatusPanel.setTurnPhrase("Turn phrase");

                        rightStatusPanel.setCountry(country);
                        rightStatusPanel.setPlayer(country.getPlayer());
                        country.select();
                        System.out.print(" selected " + country.getName());
                    }
                }
                e.getComponent().repaint();
                System.out.println();
            }

        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Neighbour neighbour : neighbours) {
            neighbour.draw(g);
        }

        for (Country country : countries) {
            country.draw(g);
        }
    }
}