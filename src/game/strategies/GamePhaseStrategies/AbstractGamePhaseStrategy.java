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

    /**
     * Method that unhighlight the players countries
     */
    public void unHighlightCountries(List<Country> countries) {
        for (Country c : countries) {
            c.setHighlighted(false);
        }
    }


}
