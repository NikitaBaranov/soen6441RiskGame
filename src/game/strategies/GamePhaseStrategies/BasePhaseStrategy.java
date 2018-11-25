package game.strategies.GamePhaseStrategies;

import game.model.Country;
import game.model.GameState;
import game.model.Player;

import static game.strategies.MapFunctionsUtil.resetToAndFrom;
import static game.strategies.MapFunctionsUtil.unHighlightCountries;
import static game.strategies.MapFunctionsUtil.unSelectCountries;

public class BasePhaseStrategy implements IGamePhaseStrategy {

    /**
     * Check if game was won by player
     *
     * @param player
     * @return boolean
     */
    static boolean isGameWonBy(GameState gameState, Player player) {
        for (Country country : gameState.getCountries()) {
            if (country.getPlayer() != player) {
                return false;
            }
        }
        return true;
    }

    static void debugMessage(GameState gameState) {
        System.out.println("-----");
        System.out.println("Current phase " + gameState.getCurrentGamePhase().toString());
        System.out.println("Current player " + gameState.getCurrentPlayer().getName());
        System.out.println("-----");
    }

    @Override
    public void init(GameState gameState) {
        resetToAndFrom(gameState);
        unSelectCountries(gameState);
        unHighlightCountries(gameState);
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
