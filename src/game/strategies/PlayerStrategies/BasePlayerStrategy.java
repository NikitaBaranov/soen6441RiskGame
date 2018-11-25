package game.strategies.PlayerStrategies;

import game.model.Country;
import game.model.Dice;
import game.model.GameState;

public class BasePlayerStrategy implements IPlayerStrategy {

    /**
     * Reset highlights
     */
    static void resetToAndFrom(GameState gameState) {
        if (gameState.getCountryFrom() != null) {
            gameState.getCountryFrom().unSelect(false);
        }
        gameState.setCountryFrom(null);

        if (gameState.getCountryTo() != null) {
            gameState.getCountryTo().unSelect(false);
        }
        gameState.setCountryTo(null);
    }

    /**
     * Method that unhighlight the players countries
     */
    static void unHighlightCountries(GameState gameState) {
        for (Country c : gameState.getCountries()) {
            c.setHighlighted(false);
        }
    }

    /**
     * Method that unhighlight the players countries
     */
    //TODO: move to Util
    static void unSelectCountries(GameState gameState) {
        for (Country c : gameState.getCountries()) {
            c.setSelected(false);
        }
    }

    static void rollDiceAndProcessResults(GameState gameState) {
        Dice.rollDice(gameState.getNumberOfRedDicesSelected(), gameState.getNumberOfWhiteDicesSelected(), gameState.getRedDice(), gameState.getWhiteDice());
        // TODO add additional message to show which user whom attack with number of dices
        gameState.setCurrentTurnPhraseText(gameState.getCurrentPlayer().getName()+" attack "+gameState.getCountryTo().getPlayer().getName()+" with "+gameState.getNumberOfRedDicesSelected()+" dices.");
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
            gameState.setCurrentTurnPhraseText(gameState.getCurrentPlayer().getName()+" won the battle! Move at least "+gameState.getNumberOfRedDicesSelected()+" armies to the defeated country. And click on free space in the map.");
            gameState.getCountryTo().setPlayer(gameState.getCurrentPlayer());
            gameState.setMinArmiesToMoveAfterWin(gameState.getNumberOfRedDicesSelected());
            gameState.setGiveACard(true);
        }
    }

    static void pauseAndRefresh(GameState gameState, int seconds) {
        gameState.notifyObservers();
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
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
