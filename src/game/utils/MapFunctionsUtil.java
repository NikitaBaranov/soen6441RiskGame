package game.utils;

import game.model.Country;
import game.model.GameState;
import game.model.Player;

import java.util.*;

/**
 * Utils methods.
 * methods that required by strategies.
 *
 * @author Dmitry Kryukov
 */
public class MapFunctionsUtil {
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
     *
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
                int enemyNeighbours = countNeighbors(country.getNeighbours(), gameState.getCurrentPlayer(), true);
                if (enemyNeighbours > maxEnemyNeighbors) {
                    countryWithMaxNeighbours = country;
                    maxEnemyNeighbors = enemyNeighbours;
                }
            }
        }
        return countryWithMaxNeighbours;
    }

    public static boolean isCountyWithMoreThenOneArmy(GameState gameState) {
        for (Country country : gameState.getCountries()) {
            if (country.getPlayer() == gameState.getCurrentPlayer() && country.getArmy() > 1) {
                return true;
            }
        }
        return false;
    }

    public static int countNeighbors(List<Country> neighbors, Player player, boolean enemy) {
        int count = 0;
        if (enemy) {
            for (Country neighbor : neighbors) {
                if (neighbor.getPlayer() != player) {
                    count++;
                }
            }
        } else {
            for (Country neighbor : neighbors) {
                if (neighbor.getPlayer() == player) {
                    count++;
                }
            }
        }
        return count;
    }

    public static Map<Player, Integer> getPlayerToCountiesNumberMap(GameState gameState) {
        Map<Player, Integer> playerNumberOfCountriesMap = new HashMap<>();
        for (Country country : gameState.getCountries()) {
            if (playerNumberOfCountriesMap.containsKey(country.getPlayer())) {
                playerNumberOfCountriesMap.put(country.getPlayer(), playerNumberOfCountriesMap.get(country.getPlayer()) + 1);
            } else {
                playerNumberOfCountriesMap.put(country.getPlayer(), 1);
            }
        }

        for (Player player : gameState.getPlayers()) {
            if (!playerNumberOfCountriesMap.containsKey(player)) {
                playerNumberOfCountriesMap.put(player, 0);
            }
            System.out.println("Player " + player.getName() + " has " + playerNumberOfCountriesMap.get(player));
        }

        return playerNumberOfCountriesMap;
    }

    public static List<Country> getRandomCountry(GameState gameState, int minArmy) {
        List<Country> countryRandom = new ArrayList<>();
        for (Country country : gameState.getCountries()) {
            if (country.getPlayer() == gameState.getCurrentPlayer() && country.getArmy() >= minArmy) {
                countryRandom.add(country);
            }
        }
        return countryRandom;
    }

    public static List<Country> getRandomEnemyCountry(GameState gameState, int minArmy) {
        List<Country> countryRandom = new ArrayList<>();
        for (Country country : gameState.getCountries()) {
            if (country.getPlayer() != gameState.getCurrentPlayer() && country.getArmy() >= minArmy) {
                countryRandom.add(country);
            }
        }
        return countryRandom;
    }
}