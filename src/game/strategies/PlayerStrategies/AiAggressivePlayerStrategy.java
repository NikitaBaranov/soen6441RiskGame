package game.strategies.PlayerStrategies;

public class AiAggressivePlayerStrategy extends AbstractPlayerStrategy {

    @Override
    public void placeArmies() {
        System.out.println("Aggressive redefined Placement Armies!");
    }

    @Override
    public void attack() {
        System.out.println("Aggressive redefined Attack!");
    }
}
