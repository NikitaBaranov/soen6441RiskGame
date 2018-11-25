package game.strategies.PlayerStrategies;

import game.model.GameState;

/**
 * Interface for player strategy
 */
public interface IPlayerStrategy {
    /**
     * Place armies action.
     * @param gameState
     */
    public void placeArmies(GameState gameState);

    /**
     * Exchange action.
     * @param gameState
     */
    public void exchange(GameState gameState);

    /**
     * Reinforcement action
     * @param gameState
     */
    public void reinforce(GameState gameState);

    /**
     * Action required before and after attack
     * @param gameState
     */
    public void beforeAndAfterAttack(GameState gameState);

    /**
     * Attack action
     * @param gameState
     */
    public void attack(GameState gameState);

    /**
     * Fortification action.
     * @param gameState
     */
    public void fortify(GameState gameState);
}
