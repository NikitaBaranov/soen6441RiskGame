package game.strategies.PlayerStrategies;

import game.model.Country;
import game.model.GameState;
import game.model.Player;

public class AbstractPlayerStrategy implements IPlayerStrategy {

    /**
     * Check if game was won by player
     *
     * @param player
     * @return boolean
     */
    static boolean isGameWonBy(GameState gameState, Player player) {
        for (Country country : gameState.getCountries()) {
            if (country.getPlayer() != player) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void placeArmies(GameState gameState) {
        System.out.println("Place Armies is not implemented in " + this.getClass().getName() + " strategy.");
    }

    @Override
    public void exchange(GameState gameState) {
        System.out.println("Exchange is not implemented in " + this.getClass().getName() + " strategy.");
    }

    @Override
    public void reinforce(GameState gameState) {
        System.out.println("Reinforcement is not implemented in " + this.getClass().getName() + " strategy.");
    }

    @Override
    public void beforeAndAfterAttack(GameState gameState) {
        System.out.println("BeforeAndAfterAttack is not implemented in " + this.getClass().getName() + " strategy.");
    }

    @Override
    public void attack(GameState gameState) {
        System.out.println("Attacking is not implemented in " + this.getClass().getName() + " strategy.");
    }

    @Override
    public void fortify(GameState gameState) {
        System.out.println("Fortifying is not implemented in " + this.getClass().getName() + " strategy.");
    }
}
