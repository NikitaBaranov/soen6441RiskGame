package game.strategies.PlayerStrategies;

import game.Game;
import game.model.Dice;
import game.model.GameState;
import game.model.enums.CardsEnum;

import java.util.stream.Collectors;

import static game.strategies.MapFunctionsUtil.isMoreAttacks;
import static game.strategies.MapFunctionsUtil.resetToAndFrom;
import static game.strategies.MapFunctionsUtil.unHighlightCountries;

/**
 * Human player strategy. Describes the actions for human player.
 *
 * @author Dmitry Kryukov, Ksenia Popova
 * @see BasePlayerStrategy
 */
public class HumanPlayerStrategy extends BasePlayerStrategy {


    @Override
    public void placeArmies(GameState gameState) {
        unHighlightCountries(gameState);
        if (gameState.getCurrentPlayer().getArmies() > 0 && gameState.getCurrentCountry().getPlayer() == gameState.getCurrentPlayer()) {
            gameState.getCurrentCountry().setSelected(true);
            gameState.getCurrentCountry().setArmy(gameState.getCurrentCountry().getArmy() + 1);
            gameState.getCurrentPlayer().setArmies(gameState.getCurrentPlayer().getArmies() - 1);
            gameState.setCurrentTurnPhraseText("Select a country to place your army. Armies to place  " + gameState.getCurrentPlayer().getArmies());
        }
    }

    /**
     * Reinforcement for player.
     * Setup requiresd game states. Show status messages
     */
    @Override
    public void reinforce(GameState gameState) {
        if (gameState.getCurrentPlayer().getArmies() > 0) {
            gameState.getCurrentCountry().setArmy(gameState.getCurrentCountry().getArmy() + 1);
            gameState.getCurrentPlayer().setArmies(gameState.getCurrentPlayer().getArmies() - 1);
            gameState.setCurrentTurnPhraseText("Armies to place " + gameState.getCurrentPlayer().getArmies());
        } else {
            unHighlightCountries(gameState);
            // TODO automatic go to next turn if no more armies to place
            // check if this line if fine and in the correct place.
            Game.getInstance().getGamePhaseStrategy().nextTurnButton(gameState);
        }
    }

    /**
     * Preparing for attack phase
     * Checks for winning or failing the battles.
     * Setup required game states
     * Force next turn if needed.
     * Show status messages for attack phase
     */
    @Override
    public void beforeAndAfterAttack(GameState gameState) {
        Game game = Game.getInstance();

        if (gameState.isWinBattle()) {
            //TODO: If at the end of your attacking turn you've conquered at least one territory, then you have earned a Risk card. You cannot earn more than one Risk card for this.
            //TODO: If you manage to wipe out an opponent by destroying his or her last army, you gain possession of all the Risk cards he or she may have had in their hands.

            if (gameState.getMinArmiesToMoveAfterWin() > 0) {
                if (gameState.getCurrentCountry() == gameState.getCountryTo()) {
                    gameState.getCountryTo().setArmy(gameState.getCountryTo().getArmy() + 1);
                    gameState.getCountryFrom().setArmy(gameState.getCountryFrom().getArmy() - 1);
                    gameState.setMinArmiesToMoveAfterWin(gameState.getMinArmiesToMoveAfterWin() - 1);
                }
            } else {
                if (gameState.getCurrentCountry() == gameState.getCountryTo() && gameState.getCountryFrom().getArmy() > 1) {
                    gameState.getCountryTo().setArmy(gameState.getCountryTo().getArmy() + 1);
                    gameState.getCountryFrom().setArmy(gameState.getCountryFrom().getArmy() - 1);
                    gameState.setMinArmiesToMoveAfterWin(gameState.getMinArmiesToMoveAfterWin() - 1);
                } else {
                    unHighlightCountries(gameState);
                    resetToAndFrom(gameState);
                    Dice.resetDice(gameState.getRedDice(), gameState.getWhiteDice());
                    gameState.setWinBattle(false);
                    if (isMoreAttacks(gameState)) {
                        game.nextTurn();
                    }
                }
            }
        } else {
            if (gameState.getCurrentCountry() != null) {
                if (gameState.getCurrentCountry().getPlayer() == gameState.getCurrentPlayer()) {
                    if (gameState.getCountryFrom() == null && gameState.getCurrentCountry().getArmy() > 1) {
                        unHighlightCountries(gameState);
                        gameState.setCountryFrom(gameState.getCurrentCountry());
                        gameState.setCurrentTurnPhraseText("Select a country to Attack.");
                        gameState.getCurrentCountry().select(true, 2);
                    }
                } else if (gameState.getCountryTo() == null && gameState.getCurrentCountry().isHighlighted()) {
                    gameState.getCountryFrom().unSelect(true);
                    gameState.getCountryFrom().setSelected(true);
                    gameState.setCountryTo(gameState.getCurrentCountry());
                    gameState.getCountryTo().setHighlighted(true);
                    gameState.setCurrentTurnPhraseText("Choose number of dices to attack.");
                }
            } else {
                gameState.setCurrentTurnPhraseText("Select a Country to attack from.");

                resetToAndFrom(gameState);
                Dice.resetDice(gameState.getRedDice(), gameState.getWhiteDice());
            }
        }
    }

    /**
     * Attack phase
     * Force base player strategy method roll dice.
     */
    @Override
    public void attack(GameState gameState) {
        if (gameState.getCountryFrom() != null && gameState.getCountryFrom().getArmy() >= 2 && gameState.getCountryTo() != null) {
            rollDiceAndProcessResults(gameState);
        }
    }

    /**
     * Fortification for Player
     * Setup required game states.
     * Show status messages
     * Automatic go to next turn if user can't move anything to another counrty
     */
    @Override
    public void fortify(GameState gameState) {
        if (gameState.getCountryFrom() == null) {
            unHighlightCountries(gameState);
            gameState.setCountryFrom(gameState.getCurrentCountry());
            gameState.setCurrentTurnPhraseText("Select a country to move an army.");
            gameState.getCurrentCountry().select(false, -1);
        } else if (gameState.getCountryTo() == null && gameState.getCurrentCountry().isHighlighted()) {
            gameState.getCountryFrom().unSelect(false);
            gameState.getCountryFrom().setSelected(true);
            gameState.setCountryTo(gameState.getCurrentCountry());
            gameState.getCountryTo().setHighlighted(true);
            gameState.setCurrentTurnPhraseText("Click on country to move an army.");
        }
        if (gameState.getCountryFrom() != null && gameState.getCountryFrom().getArmy() > 1 && gameState.getCountryTo() != null) {
            gameState.getCountryFrom().setArmy(gameState.getCountryFrom().getArmy() - 1);
            gameState.getCountryTo().setArmy(gameState.getCountryTo().getArmy() + 1);
            gameState.setCurrentTurnPhraseText("Move army from "+ gameState.getCountryFrom().getName()+" to "+ gameState.getCountryTo().getName());
        }
        if (gameState.getCountryFrom().getArmy() == 1){
            // TODO automatic go to next turn if user can not move anything to another country
            // check is this line if in correct place and works fine
            Game.getInstance().getGamePhaseStrategy().nextTurnButton(gameState);
        }
    }

    /**
     * Exchange cards for armies
     *
     * @param gameState
     */
    @Override
    public void exchange(GameState gameState) {
        String phrase = "";
        if (gameState.getSelectedCardsToExchange().size() == 3) {
            for (CardsEnum cardsEnum : gameState.getSelectedCardsToExchange()) {
                gameState.getCurrentPlayer().getCardsEnumIntegerMap().put(cardsEnum, gameState.getCurrentPlayer().getCardsEnumIntegerMap().get(cardsEnum) - 1);
            }
            phrase = String.join(", ", gameState.getSelectedCardsToExchange().stream().map(CardsEnum::getName).collect(Collectors.toList())) + " cards";
        } else if (gameState.getSelectedCardsToExchange().size() == 1) {
            gameState.getCurrentPlayer().getCardsEnumIntegerMap().put(gameState.getSelectedCardsToExchange().get(0), gameState.getCurrentPlayer().getCardsEnumIntegerMap().get(gameState.getSelectedCardsToExchange().get(0)) - 3);
            phrase = gameState.getSelectedCardsToExchange().get(0).getName() + " card";
        }
        gameState.getCurrentPlayer().setArmies(gameState.getCurrentPlayer().getArmies() + gameState.getArmiesToCardExchange());
        gameState.setArmiesToCardExchange(gameState.getArmiesToCardExchange() + gameState.getARMIES_TO_EXCHANGE_INCREASE());
        gameState.setCurrentTurnPhraseText("Exchanged " + phrase + " for " + gameState.getARMIES_TO_EXCHANGE_INCREASE() + " armies. Armies to place " + gameState.getCurrentPlayer().getArmies());
    }
}
