package game.strategies.PlayerStrategies;

import game.model.Dice;
import game.model.GameState;

/**
 * Base player strategy. Describes the basic player features.
 *
 * @author Dmitry Kryukov, Ksenia popova
 * @see IPlayerStrategy
 */
public class BasePlayerStrategy implements IPlayerStrategy {
    public final int PAUSE = 500;
    /**
     * Method describes the behavior of the game during rolling the dices. i.e. attacking phase.
     * Show status messages.
     * Setup required game states
     * @param gameState
     */
    static void rollDiceAndProcessResults(GameState gameState) {
        Dice.rollDice(gameState.getNumberOfRedDicesSelected(), gameState.getNumberOfWhiteDicesSelected(), gameState.getRedDice(), gameState.getWhiteDice());
        // TODO add additional message to show which user whom attack with number of dices
        gameState.setCurrentTurnPhraseText(gameState.getCurrentPlayer().getName() + " attack " + gameState.getCountryTo().getPlayer().getName() + " with " + gameState.getNumberOfRedDicesSelected() + " dices.");
        for (int i = 0; i < Math.min(gameState.getNumberOfRedDicesSelected(), gameState.getNumberOfWhiteDicesSelected()); i++) {
            if (gameState.getRedDice()[i].getNumber() > gameState.getWhiteDice()[i].getNumber()) {
                gameState.getCountryTo().setArmy(gameState.getCountryTo().getArmy() - 1);
            } else {
                gameState.getCountryFrom().setArmy(gameState.getCountryFrom().getArmy() - 1);
            }
        }
        if (gameState.getCountryTo().getArmy() == 0) {
            gameState.setWinBattle(true);
            // TODO Add message that attacker win battle
            gameState.setCurrentTurnPhraseText(gameState.getCurrentPlayer().getName() + " won the battle! Move at least " + gameState.getNumberOfRedDicesSelected() + " armies to the defeated country.");
            gameState.getCountryTo().setPlayer(gameState.getCurrentPlayer());
            gameState.setMinArmiesToMoveAfterWin(gameState.getNumberOfRedDicesSelected());
            gameState.setGiveACard(true);
        }
    }

    /**
     * Pause method for AI strategies.
     * @param gameState
     * @param milliseconds
     */
    static void pauseAndRefresh(GameState gameState, int milliseconds) {
        gameState.notifyObservers();
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Method need to be implemented
     * @param gameState
     */
    @Override
    public void placeArmies(GameState gameState) {
        System.out.println("Place Armies is not implemented in " + this.getClass().getName() + " strategy.");
    }

    /**
     * method needs to be implemented
     * @param gameState
     */
    @Override
    public void exchange(GameState gameState) {
        System.out.println("Exchange is not implemented in " + this.getClass().getName() + " strategy.");
    }

    /**
     * method needs to be implemented
     * @param gameState
     */
    @Override
    public void reinforce(GameState gameState) {
        System.out.println("Reinforcement is not implemented in " + this.getClass().getName() + " strategy.");
    }

    /**
     * method needs to be implemented
     * @param gameState
     */
    @Override
    public void beforeAndAfterAttack(GameState gameState) {
        System.out.println("BeforeAndAfterAttack is not implemented in " + this.getClass().getName() + " strategy.");
    }

    /**
     * method needs to be implemented
     * @param gameState
     */
    @Override
    public void attack(GameState gameState) {
        System.out.println("Attacking is not implemented in " + this.getClass().getName() + " strategy.");
    }

    /**
     * method needs to be implemented
     * @param gameState
     */
    @Override
    public void fortify(GameState gameState) {
        System.out.println("Fortifying is not implemented in " + this.getClass().getName() + " strategy.");
    }
}
