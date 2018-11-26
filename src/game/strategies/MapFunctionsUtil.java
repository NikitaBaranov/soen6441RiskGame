package game.strategies;

import game.model.Country;
import game.model.GameState;
import game.model.Player;

import java.util.List;

/**
 * Utils methods.
 * methods that required by strategies.
 * @author Dmitry Kryukov
 */
public class MapFunctionsUtil {
    // TODO Move me to the utils folder
    /**
     * Reset highlights
     */
    public static void resetToAndFrom(GameState gameState) {
        if (gameState.getCountryFrom() != null) {
            gameState.getCountryFrom().unSelect(false);
        }
        gameState.setCountryFrom(null);

        if (gameState.getCountryTo() != null) {
            gameState.getCountryTo().unSelect(false);
        }
        gameState.setCountryTo(null);
    }

    /**
     * Method that unhighlight the players countries
     */
    public static void unHighlightCountries(GameState gameState) {
        for (Country c : gameState.getCountries()) {
            c.setHighlighted(false);
        }
    }

    /**
     * Method that unhighlight the players countries
     */
    public static void unSelectCountries(GameState gameState) {
        for (Country c : gameState.getCountries()) {
            c.setSelected(false);
        }
    }

    /**
     * Method to highlight the player countries
     */
    public static void highlightPayerCountries(List<Country> countries, Player player) {
        for (Country c : countries) {
            if (c.getPlayer() == player) {
                c.setHighlighted(true);
            }
        }
    }

    /**
     * Select country action.
     * @param gameState
     * @param x
     * @param y
     * @return
     */
    public static boolean selectCountry(GameState gameState, int x, int y) {
        gameState.setCurrentCountry(null);

        for (Country country : gameState.getCountries()) {
            if (country.isInBorder(x, y)) {
                gameState.setCurrentCountry(country);
                System.out.println("Selected " + country.getName());
                return true;
            }
        }
        return false;
    }

    /**
     * Check if player can attack anybody or go to next turn
     *
     * @return
     */
    public static boolean isMoreAttacks(GameState gameState) {
        for (Country country : gameState.getCountries()) {
            if (country.getPlayer() == gameState.getCurrentPlayer() && country.getArmy() >= 2) {
                for (Country neighbor : country.getNeighbours()) {
                    if (neighbor.getPlayer() != gameState.getCurrentPlayer()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static Country getCountryWithMaxArmy(GameState gameState, int minArmy) {
        Country countryWithMaxArmy = null;
        int maxArmies = minArmy;
        for (Country country : gameState.getCountries()) {
            if (country.getPlayer() == gameState.getCurrentPlayer() && country.getArmy() > maxArmies) {
                countryWithMaxArmy = country;
                maxArmies = country.getArmy();
            }
        }
        return countryWithMaxArmy;
    }

    public static Country getCountryWithMaxOpponentNeighbours(GameState gameState) {
        Country countryWithMaxNeighbours = null;
        int maxEnemyNeighbors = 0;
        for (Country country : gameState.getCountries()) {
            if (country.getPlayer() == gameState.getCurrentPlayer()) {
                int enemyNeighbours = 0;
                for (Country neighbor : country.getNeighbours()) {
                    if (neighbor.getPlayer() != gameState.getCurrentPlayer()) {
                        enemyNeighbours++;
                    }
                }
                if (enemyNeighbours > maxEnemyNeighbors) {
                    countryWithMaxNeighbours = country;
                    maxEnemyNeighbors = enemyNeighbours;
                }
            }
        }
        return countryWithMaxNeighbours;
    }

    public static boolean isFortifyPossible(GameState gameState) {
        for (Country country : gameState.getCountries()) {
            if (country.getPlayer() == gameState.getCurrentPlayer() && country.getArmy() > 1) {
                return true;
            }
        }
        return false;
    }
}