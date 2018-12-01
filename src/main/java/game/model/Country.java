package game.model;

import game.Game;
import lombok.Data;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The country model. Describes the country parameters.
 *
 * @author Dmitry Kryukov, Ksenia Popova
 */
@Data
public class Country implements Serializable {
    private static final long serialVersionUID = 1L;

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
                    if (country.getPlayer() != game.getGameState().getCurrentPlayer()) {
                        country.highlight(enemies, depth == -1 ? -1 : depth - 1);
                    }
                } else {
                    if (country.getPlayer() == game.getGameState().getCurrentPlayer()) {
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
     * Drawer. Display the game in window. GUI
     *
     * @param g instance og graphics object
     */
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        if (isSelected) {
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