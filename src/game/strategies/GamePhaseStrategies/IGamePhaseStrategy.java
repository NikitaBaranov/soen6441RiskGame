package game.strategies.GamePhaseStrategies;

import game.model.GameState;

/**
 * Interface for game phase strategy
 */
public interface IGamePhaseStrategy {
    /**
     * Initializations of phase strategy
     * @param gameState
     */
    public void init(GameState gameState);

    /**
     * Processing of map click.
     * @param gameState
     * @param x
     * @param y
     */
    public void mapClick(GameState gameState, int x, int y);

    /**
     * Next turn button behavoior
     * @param gameState
     */
    public void nextTurnButton(GameState gameState);

    /**
     * Exchange behavoior
     * @param gameState
     */
    public void exchangeButton(GameState gameState);

    /**
     * Attack behavoir
     * @param gameState
     */
    public void attackButton(GameState gameState);

    /**
     * Cleanup behavoir
     * @param gameState
     */
    public void cleanup(GameState gameState);
}
