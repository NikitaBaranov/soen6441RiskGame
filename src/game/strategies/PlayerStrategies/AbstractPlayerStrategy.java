package game.strategies.PlayerStrategies;

import game.model.enums.CardsEnum;

import java.util.List;

public class AbstractPlayerStrategy implements IPlayerStrategy {

    @Override
    public void placeArmies() {
        System.out.println("Place Armies is not implemented in " + this.getClass().getName() + " strategy.");
    }

    @Override
    public void exchange(List<CardsEnum> cardsEnumList) {
        System.out.println("Exchange is not implemented in " + this.getClass().getName() + " strategy.");
    }

    @Override
    public void reinforce() {
        System.out.println("Reinforcement is not implemented in " + this.getClass().getName() + " strategy.");
    }

    @Override
    public void beforeAndAfterAttack() {
        System.out.println("BeforeAndAfterAttack is not implemented in " + this.getClass().getName() + " strategy.");
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
