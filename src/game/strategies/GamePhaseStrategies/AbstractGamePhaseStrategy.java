package game.strategies.GamePhaseStrategies;

import game.model.Country;
import game.model.GameState;
import game.model.Player;

import java.util.List;

public class AbstractGamePhaseStrategy implements IGamePhaseStrategy {

    /**
     * Method to highlight the player countries
     */
    static void highlightPayerCountries(List<Country> countries, Player player) {
        for (Country c : countries) {
            if (c.getPlayer() == player) {
                c.setHighlighted(true);
            }
        }
    }

    /**
     * Method that unhighlight the players countries
     */
    static void unHighlightCountries(List<Country> countries) {
        for (Country c : countries) {
            c.setHighlighted(false);
        }
    }

    /**
     * Reset highlights
     */
    static void resetToAndFrom(GameState gameState) {
        unHighlightCountries(gameState.getCountries());
        if (gameState.getCountryFrom() != null) {
            gameState.getCountryFrom().unSelect(false);
        }
        gameState.setCountryFrom(null);

        if (gameState.getCountryTo() != null) {
            gameState.getCountryTo().unSelect(false);
        }
        gameState.setCountryTo(null);
    }


    boolean selectCountry(GameState gameState, int x, int y) {
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

    @Override
    public void init(GameState gameState) {
        System.out.println("The init method is not implemented in " + this.getClass().getName() + " strategy.");
    }

    @Override
    public void mapClick(GameState gameState, int x, int y) {
        System.out.println("The mapClick method is not implemented in " + this.getClass().getName() + " strategy.");
    }

    @Override
    public void nextTurnButton(GameState gameState) {
        System.out.println("The nextTurnButton method is not implemented in " + this.getClass().getName() + " strategy.");
    }

    @Override
    public void exchangeButton(GameState gameState) {
        System.out.println("The exchangeButton method is not implemented in " + this.getClass().getName() + " strategy.");
    }

    @Override
    public void attackButton(GameState gameState) {
        System.out.println("The attackButton method is not implemented in " + this.getClass().getName() + " strategy.");
    }

    @Override
    public void cleanup(GameState gameState) {
        System.out.println("The cleanup method is not implemented in " + this.getClass().getName() + " strategy.");
    }
}
