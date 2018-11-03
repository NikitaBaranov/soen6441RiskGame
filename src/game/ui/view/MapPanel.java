package game.ui.view;

import game.Game;
import game.model.Country;
import game.model.IModelObservable;
import game.model.Neighbour;
import game.utils.MapLoader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * The map panel class. Draw the map panel on the main window.
 * Displaying the map
 * @author Dmitry Kryukov, Ksenia Popova
 * @see Game
 * @see Country
 * @see Neighbour
 * @see MapLoader
 */
public class MapPanel extends JPanel implements IPanelObserver {
    //    Game game;
    private BufferedImage image = null;
    private java.util.List<Country> countries;
    private List<Neighbour> neighbours;


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

        Game game = Game.getInstance();
        addMouseListener(game.getMouseAdapter());
        game.attachObserver(this);
    }

    @Override
    public void updateObserver(IModelObservable iModelObservable) {
        Game game = Game.getInstance();
        countries = game.getCountries();
        neighbours = game.getNeighbours();
        this.repaint();
    }

    /**
     * Displaying the countries and connections
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
        for (Neighbour neighbour : neighbours) {
            neighbour.draw(g);
        }

        for (Country country : countries) {
            country.draw(g);
        }
    }
}