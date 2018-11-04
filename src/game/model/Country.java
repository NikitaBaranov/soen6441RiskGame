package game.model;

import game.Game;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

/**
 * The country model. Describes the country parameters.
 *
 * @author Dmitry Kryukov, Ksenia Popova
 */
public class Country {
    private int HIGHLIGHT_BORDER_WITHT = 10;
    private int ARMY_BACKGROUND_WITHT = 6;

    private String name;
    private Player player;
    private Continent continent;

    private int x = 0;
    private int y = 0;
    private int radius = 0;
    private int army = 1;
    private List<Country> neighbours = new ArrayList<>();
    private boolean isSelected = false;
    private boolean isHighlighted = false;

//    /**
//     * Country constructor.
//     * @param name of country
//     * @param x coordinate
//     * @param y coordinate
//     * @param radius radius of the node
//     * @param player assigned player
//     */
//    @Deprecated
//    public Country(String name, int x, int y, int radius, Player player) {
//        this.name = name;
//        this.x = x;
//        this.y = y;
//        this.radius = radius;
//        this.player = player;
//    }

    /**
     * Constructor of country class.
     *
     * @param name      of the country
     * @param x         coordinate
     * @param y         coordinate
     * @param radius    radius of the node
     * @param continent assigned continent
     * @param player    assigned player
     */
    public Country(String name, int x, int y, int radius, Continent continent, Player player) {
        this.name = name;
        this.player = player;
        this.continent = continent;
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    /**
     * Get name of country
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Get army number for country
     *
     * @return army
     */
    public int getArmy() {
        return army;
    }

    /**
     * Set the number of armies to country
     *
     * @param army Armies to country
     */
    public void setArmy(int army) {
        this.army = army;
    }

    /**
     * Get the x coodrinate
     *
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * Set the x coordinate
     *
     * @param x horizontal coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Get the y coordinate
     *
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * Set the y coordinate
     *
     * @param y vertical coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Get the radius of the country
     *
     * @return radius
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Set the radius for the country
     *
     * @param radius radius of the node
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    /**
     * Get the connections for country
     *
     * @return neighbours
     */
    public List<Country> getNeighbours() {
        return neighbours;
    }

    /**
     * Set the connections for country
     *
     * @param neighbours connections for country
     */
    public void setNeighbours(List<Country> neighbours) {
        this.neighbours = neighbours;
    }

    /**
     * Get the assigned player for country
     *
     * @return player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Set the player for country
     *
     * @param player Player object
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Is the country selected in the window
     *
     * @return boolean
     */
    public boolean isSelected() {
        return isSelected;
    }

    /**
     * Set the country selected state
     *
     * @param selected Is country selected
     */
    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    /**
     * Selection of enemies
     *
     * @param enemies Players enemies
     */
    public void select(boolean enemies, int depth) {
        isSelected = true;
        highlight(enemies, depth);
    }

    /**
     * Unselection of enemies
     *
     * @param enemies Players enemies
     */
    public void unSelect(boolean enemies) {
        isSelected = false;
        unHighlight(enemies);
    }

    /**
     * Highlight the country enemies
     *
     * @param enemies Players enemies
     */
    private void highlight(boolean enemies, int depth) {
        if (depth != 0 && !isHighlighted) {
            Game game = Game.getInstance();
            isHighlighted = true;
            for (Country country : neighbours) {
                if (enemies) {
                    if (country.getPlayer() != game.getCurrentPlayer()) {
                        country.highlight(enemies, depth == -1 ? -1 : depth - 1);
                    }
                } else {
                    if (country.getPlayer() == game.getCurrentPlayer()) {
                        country.highlight(enemies, depth == -1 ? -1 : depth - 1);
                    }
                }
            }
        }
    }

    /**
     * Unhighlight the enemies fir country
     *
     * @param enemies Player enemies
     */
    private void unHighlight(boolean enemies) {
        if (isHighlighted) {
            isHighlighted = false;
            for (Country country : neighbours) {
                if (enemies) {
                    if (country.getPlayer() != this.player) {
                        country.unHighlight(enemies);
                    }
                } else {
                    if (country.getPlayer() == this.player) {
                        country.unHighlight(enemies);
                    }
                }
            }
        }
    }

    /**
     * Check if country is highlighted
     *
     * @return boolean
     */
    public boolean isHighlighted() {
        return isHighlighted;
    }

    /**
     * Set highlight
     *
     * @param highlighted Is highlighted
     */
    public void setHighlighted(boolean highlighted) {
        isHighlighted = highlighted;
    }

    /**
     * Check if the country in border
     *
     * @param x coordinate
     * @param y coordinate
     * @return boolean
     */
    public boolean isInBorder(int x, int y) {
        return Math.sqrt(Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2)) < radius;
    }

    /**
     * Get the continent for country
     *
     * @return continent
     */
    public Continent getContinent() {
        return continent;
    }

    /**
     * Set the continent for country
     *
     * @param continent Continent to country
     */
    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    /**
     * Drawer. Display the game in window. GUI
     *
     * @param g instance og graphics object
     */
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        if (isSelected) {
//            Ellipse2D.Double selection = new Ellipse2D.Double(x - radius - HIGHLIGHT_BORDER_WITHT, y - radius - HIGHLIGHT_BORDER_WITHT, radius * 2 + HIGHLIGHT_BORDER_WITHT * 2, radius * 2 + HIGHLIGHT_BORDER_WITHT * 2);
            Ellipse2D.Double selection = new Ellipse2D.Double(x - radius - HIGHLIGHT_BORDER_WITHT / 2, y - radius - HIGHLIGHT_BORDER_WITHT / 2, radius * 2 + HIGHLIGHT_BORDER_WITHT, radius * 2 + HIGHLIGHT_BORDER_WITHT);
            g2d.setColor(Color.GREEN);
            g2d.fill(selection);
            g2d.setColor(Color.BLACK);
            g2d.draw(selection);

        } else if (isHighlighted) {
            Ellipse2D.Double highlight = new Ellipse2D.Double(x - radius - HIGHLIGHT_BORDER_WITHT / 2, y - radius - HIGHLIGHT_BORDER_WITHT / 2, radius * 2 + HIGHLIGHT_BORDER_WITHT, radius * 2 + HIGHLIGHT_BORDER_WITHT);
            g2d.setColor(Color.RED);
            g2d.fill(highlight);
            g2d.setColor(Color.BLACK);
            g2d.draw(highlight);
        }

        // Player Color
        Ellipse2D.Double playerColor = new Ellipse2D.Double(x - radius, y - radius, radius * 2, radius * 2);
        g2d.setColor(player.getColor());
        g2d.fill(playerColor);
        g2d.setColor(Color.BLACK);
        g2d.draw(playerColor);

        // Army background
        Ellipse2D.Double armyBackground = new Ellipse2D.Double(x - (radius - ARMY_BACKGROUND_WITHT), y - (radius - ARMY_BACKGROUND_WITHT), (radius - ARMY_BACKGROUND_WITHT) * 2, (radius - ARMY_BACKGROUND_WITHT) * 2);
        g2d.setColor(Color.WHITE);
        g2d.fill(armyBackground);
        g2d.setColor(Color.BLACK);
        g2d.draw(armyBackground);

        g2d.setColor(Color.BLACK);
        g2d.drawString(Integer.toString(army), x - 3, y + 5);

        g2d.setColor(Color.BLACK);
        g2d.drawString(name, x - radius, y - radius - ARMY_BACKGROUND_WITHT);
    }
}