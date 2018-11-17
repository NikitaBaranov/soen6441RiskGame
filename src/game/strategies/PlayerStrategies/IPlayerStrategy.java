package game.strategies.PlayerStrategies;

import game.model.GameState;

public interface IPlayerStrategy {

    public void placeArmies(GameState gameState);

    public void exchange(GameState gameState);

    public void reinforce(GameState gameState);

    public void beforeAndAfterAttack(GameState gameState);

    public void attack(GameState gameState);

    public void fortify(GameState gameState);
}
