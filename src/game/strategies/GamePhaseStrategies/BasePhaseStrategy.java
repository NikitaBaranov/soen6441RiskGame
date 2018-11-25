package game.strategies.GamePhaseStrategies;

import game.model.Country;
import game.model.GameState;
import game.model.Player;

import static game.strategies.MapFunctionsUtil.resetToAndFrom;
import static game.strategies.MapFunctionsUtil.unHighlightCountries;
import static game.strategies.MapFunctionsUtil.unSelectCountries;

/**
 * Base phase strategy class. Required to prepare game state for base phase.
 *
 * @author Dmitry Kryukov, Ksenia Popova
 * @see IGamePhaseStrategy
 */
public class BasePhaseStrategy implements IGamePhaseStrategy {
    /**
     * Check if game was won by player
     *
     * @param player
     * @return boolean
     */
    public static boolean isGameWonBy(GameState gameState, Player player) {
        for (Country country : gameState.getCountries()) {
            if (country.getPlayer() != player) {
                return false;
            }
        }
        return true;
    }

    /**
     * Debug messages.
     * @param gameState
     */
    static void debugMessage(GameState gameState) {
        System.out.println("-----");
        System.out.println("Current phase " + gameState.getCurrentGamePhase().toString());
        System.out.println("Current player " + gameState.getCurrentPlayer().getName());
        System.out.println("-----");
    }

    /**
     * Initialization of base phase strategy.
     * @param gameState
     */
    @Override
    public void init(GameState gameState) {
        resetToAndFrom(gameState);
        unSelectCountries(gameState);
        unHighlightCountries(gameState);
        if (gameState.getCurrentPlayer() != null && gameState.getCurrentPlayer().isComputerPlayer()) {
            gameState.setNextTurnButton(false);
        } else {
            gameState.setNextTurnButton(true);
        }
    }

    /**
     * Map click for base strategy
     * @param gameState
     * @param x
     * @param y
     */
    @Override
    public void mapClick(GameState gameState, int x, int y) {
        // TODO What are these methods? will they implemented?
        System.out.println("The mapClick method is not implemented in " + this.getClass().getName() + " strategy.");
    }

    /**
     * Next turn button behavior
     * @param gameState
     */
    @Override
    public void nextTurnButton(GameState gameState) {
        System.out.println("The nextTurnButton method is not implemented in " + this.getClass().getName() + " strategy.");
    }

    /**
     * Exchange behavoir
     * @param gameState
     */
    @Override
    public void exchangeButton(GameState gameState) {
        System.out.println("The exchangeButton method is not implemented in " + this.getClass().getName() + " strategy.");
    }

    /**
     * Attack behavoir
     * @param gameState
     */
    @Override
    public void attackButton(GameState gameState) {
        System.out.println("The attackButton method is not implemented in " + this.getClass().getName() + " strategy.");
    }

    /**
     * cleaning
     * @param gameState
     */
    @Override
    public void cleanup(GameState gameState) {
        System.out.println("The cleanup method is not implemented in " + this.getClass().getName() + " strategy.");
    }
}
