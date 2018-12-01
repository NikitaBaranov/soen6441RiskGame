package game.model;

import lombok.Data;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The continent model. Describes the continents parameters.
 * @author Dmitry Kryukov, Ksenia Popova
 */
@Data
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
     * @return boolean
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