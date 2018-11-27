package game.model;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The continnt model. Describes the continents parameters.
 * @author Dmitry Kryukov, Ksenia Popova
 */
public class Continent implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name = "";
    private List<Country> countryList = new ArrayList<>();
    private int bonus = 0;
    private Color color;

    /**
     * Empty constructor
     */
    public Continent() {
    }

    /**
     * Constructor with parameters
     * @param name of the continent
     * @param bonus of the continent
     */
    public Continent(String name, int bonus) {
        this.name = name;
        this.bonus = bonus;
    }

    /**
     * Getter for the name
     * @return name of the continent
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name
     * @param name of the continent
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Country list for the continent
     * @return countryLust of countries on continent
     */
    public List<Country> getCountryList() {
        return countryList;
    }

    /**
     * Method return the bonus for continent
     * @return bonus
     */
    public int getBonus() {
        return bonus;
    }

    /**
     * Method that descrive the color for continent
     * @return color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Method describes if the whole continent owned by one player
     * @return boolean
     */
    public boolean isOwnByOnePlayer() {
        for (Country country : countryList) {
            if (country.getPlayer() != countryList.get(0).getPlayer()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Is continent owned by player?
     * @param player
     * @return
     */
    public boolean isOwnByPlayer(Player player) {
        for (Country country : countryList) {
            if (country.getPlayer() != player) {
                return false;
            }
        }
        return true;
    }
}