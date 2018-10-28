package game.ui.view;

import game.Game;
import game.model.Country;
import game.model.Neighbour;
import game.utils.MapLoader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The map panel class. Draw the map panel on the main window.
 * Displaying the map
 * @author Dmitry Kryukov, Ksenia Popova
 * @see Game
 * @see Country
 * @see Neighbour
 * @see MapLoader
 */
public class MapPanel extends JPanel {
    Game game;
    BufferedImage image = null;

    /**
     * Constructor of the class
     * @param dimension size
     * @param loader map loader
     */
    public MapPanel(Dimension dimension, MapLoader loader) {
        try {
            image = ImageIO.read(new File(loader.getFilePath().split(".map")[0] + ".bmp"));
        }
        catch (IOException e) {
        }
        this.setPreferredSize(dimension);
        this.game = Game.getInstance();
        addMouseListener(game.getMouseAdapter());
    }

    /**
     * Displaying the countries and connections
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
        for (Neighbour neighbour : game.getNeighbours()) {
            neighbour.draw(g);
        }

        for (Country country : game.getCountries()) {
            country.draw(g);
        }
    }
}