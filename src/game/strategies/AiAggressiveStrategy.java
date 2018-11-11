package game.strategies;

public class AiAggressiveStrategy extends AbstractStrategy {

    @Override
    public void placeArmies() {
        System.out.println("Aggressive redefined Placement Armies!");
    }

    @Override
    public void attack() {
        System.out.println("Aggressive redefined Attack!");
    }
}
