package game.ui.view;

import game.Game;
import game.model.Country;
import game.model.Neighbour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MapPanel extends JPanel {
    Game game;

    public MapPanel(Dimension dimension, Game game) {
        this.setPreferredSize(dimension);
        this.game = game;
        addMouseListener(game.getMouseAdapter());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Neighbour neighbour : game.neighbours) {
            neighbour.draw(g);
        }

        for (Country country : game.countries) {
            country.draw(g);
        }
    }
}