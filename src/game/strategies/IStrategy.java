package game.strategies;

import game.enums.CardsEnum;

import java.util.List;

public interface IStrategy {

    public void placeArmies();

    public void exchange(List<CardsEnum> cardsEnumList);

    public void reinforce();

    public void beforeAndAfterAttack();

    public void attack();

    public void fortify();
}
