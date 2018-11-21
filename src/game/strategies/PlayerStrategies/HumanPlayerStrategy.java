package game.strategies.PlayerStrategies;

import game.Game;
import game.model.Dice;
import game.model.GameState;
import game.model.enums.CardsEnum;

import java.util.stream.Collectors;

public class HumanPlayerStrategy extends BasePlayerStrategy {

    /**
     * Reinforcement for player
     */
    @Override
    public void reinforce(GameState gameState) {
        if (gameState.getCurrentPlayer().getArmies() > 0) {
            gameState.getCurrentCountry().setArmy(gameState.getCurrentCountry().getArmy() + 1);
            gameState.getCurrentPlayer().setArmies(gameState.getCurrentPlayer().getArmies() - 1);
            gameState.setCurrentTurnPhraseText("Armies to place " + gameState.getCurrentPlayer().getArmies());
        } else {
            unHighlightCountries(gameState);
        }
    }

    /**
     * Preparing for attach phase
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
                    if (!game.isMoreAttacks()) {
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
                    gameState.setCurrentTurnPhraseText("Use Attack window to Attack.");
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
     */
    @Override
    public void attack(GameState gameState) {
        if (gameState.getCountryFrom() != null && gameState.getCountryFrom().getArmy() >= 2 && gameState.getCountryTo() != null) {

//            Dice.rollDiceAndProcessResults(gameState.getNumberOfRedDicesSelected(), gameState.getNumberOfWhiteDicesSelected(), gameState.getRedDice(), gameState.getWhiteDice());
//
//            for (int i = 0; i < Math.min(gameState.getNumberOfRedDicesSelected(), gameState.getNumberOfWhiteDicesSelected()); i++) {
//                if (gameState.getRedDice()[i].getNumber() > gameState.getWhiteDice()[i].getNumber()) {
//                    gameState.getCountryTo().setArmy(gameState.getCountryTo().getArmy() - 1);
//                } else {
//                    gameState.getCountryFrom().setArmy(gameState.getCountryFrom().getArmy() - 1);
//                }
//            }

            rollDiceAndProcessResults(gameState);

//            if (gameState.getCountryTo().getArmy() == 0) {
//                gameState.setWinBattle(true);
//                gameState.getCountryTo().setPlayer(gameState.getCurrentPlayer());
//                gameState.setMinArmiesToMoveAfterWin(gameState.getNumberOfRedDicesSelected());
//                gameState.setGiveACard(true);
//            }
        }
    }

    /**
     * Fortification for Player
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
            gameState.setCurrentTurnPhraseText("Click on country to move one army.");
        }
        if (gameState.getCountryFrom() != null && gameState.getCountryFrom().getArmy() > 1 && gameState.getCountryTo() != null) {
            gameState.getCountryFrom().setArmy(gameState.getCountryFrom().getArmy() - 1);
            gameState.getCountryTo().setArmy(gameState.getCountryTo().getArmy() + 1);
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
