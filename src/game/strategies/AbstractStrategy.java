package game.strategies;

public class AbstractStrategy implements IStrategy {

    @Override
    public void placeArmies() {
        System.out.println("Place Armies is not implemented in " + this.getClass().getName() + " strategy.");
    }

    @Override
    public void reinforce() {
        System.out.println("Reinforcement is not implemented in " + this.getClass().getName() + " strategy.");
    }

    @Override
    public void attack() {
        System.out.println("Attacking is not implemented in " + this.getClass().getName() + " strategy.");
    }

    @Override
    public void fortify() {
        System.out.println("Fortifying is not implemented in " + this.getClass().getName() + " strategy.");
    }
}
