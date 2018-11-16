package game.strategies.PlayerStrategies;

import game.model.enums.CardsEnum;

import java.util.List;

public interface IPlayerStrategy {

    public void placeArmies();

    public void exchange(List<CardsEnum> cardsEnumList);

    public void reinforce();

    public void beforeAndAfterAttack();

    public void attack();

    public void fortify();
}
