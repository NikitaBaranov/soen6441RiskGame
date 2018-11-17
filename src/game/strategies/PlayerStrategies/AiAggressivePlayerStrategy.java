package game.strategies.PlayerStrategies;

import game.model.GameState;

public class AiAggressivePlayerStrategy extends AbstractPlayerStrategy {

    @Override
    public void placeArmies(GameState gameState) {
        System.out.println("Aggressive redefined Placement Armies!");
    }

    @Override
    public void attack(GameState gameState) {
        System.out.println("Aggressive redefined Attack!");
    }
}
