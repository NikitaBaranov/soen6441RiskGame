package game.strategies.GamePhaseStrategies;

import game.model.GameState;

/**
 * Interface for game phase strategy
 */
public interface IGamePhaseStrategy {
    /**
     * Initializations of phase strategy.
     * Setup required states, variables.
     * @param gameState
     */
    public void init(GameState gameState);

    /**
     * Processing of map click.
     * Describes the behavior when user click on map
     * @param gameState
     * @param x
     * @param y
     */
    public void mapClick(GameState gameState, int x, int y);

    /**
     * Next turn button behavior
     * Describes the action after pressing button next turn
     * @param gameState
     */
    public void nextTurnButton(GameState gameState);

    /**
     * Exchange behavior
     * @param gameState
     */
    public void exchangeButton(GameState gameState);

    /**
     * Attack behavior
     * @param gameState
     */
    public void attackButton(GameState gameState);

    /**
     * Cleanup behavior
     * If required, clean required states
     * @param gameState
     */
    public void cleanup(GameState gameState);
}
