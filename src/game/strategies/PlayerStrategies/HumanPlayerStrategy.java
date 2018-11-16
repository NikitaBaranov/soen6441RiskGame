package game.strategies.PlayerStrategies;

import game.Game;
import game.model.Dice;
import game.model.enums.CardsEnum;

import java.util.List;
import java.util.stream.Collectors;

public class HumanPlayerStrategy extends AbstractPlayerStrategy {

    /**
     * Reinforcement for player
     */
    @Override
    public void reinforce() {
        Game game = Game.getInstance();
        if (game.getGameState().getCurrentPlayer().getArmies() > 0) {
            game.getGameState().getCurrentCountry().setArmy(game.getGameState().getCurrentCountry().getArmy() + 1);
            game.getGameState().getCurrentPlayer().setArmies(game.getGameState().getCurrentPlayer().getArmies() - 1);
            game.getGameState().setCurrentTurnPhraseText("Armies to place " + game.getGameState().getCurrentPlayer().getArmies());
        } else {
            game.unHighlightCountries();
        }
    }

    /**
     * Preparing for attach phase
     */
    @Override
    public void beforeAndAfterAttack() {
        Game game = Game.getInstance();

        if (game.getGameState().isWinBattle()) {
            //TODO: If at the end of your attacking turn you've conquered at least one territory, then you have earned a Risk card. You cannot earn more than one Risk card for this.
            //TODO: If you manage to wipe out an opponent by destroying his or her last army, you gain possession of all the Risk cards he or she may have had in their hands.

            if (game.getGameState().getMinArmiesToMoveAfterWin() > 0) {
                if (game.getGameState().getCurrentCountry() == game.getGameState().getCountryTo()) {
                    game.getGameState().getCountryTo().setArmy(game.getGameState().getCountryTo().getArmy() + 1);
                    game.getGameState().getCountryFrom().setArmy(game.getGameState().getCountryFrom().getArmy() - 1);
                    game.getGameState().setMinArmiesToMoveAfterWin(game.getGameState().getMinArmiesToMoveAfterWin() - 1);
                }
            } else {
                if (game.getGameState().getCurrentCountry() == game.getGameState().getCountryTo() && game.getGameState().getCountryFrom().getArmy() > 1) {
                    game.getGameState().getCountryTo().setArmy(game.getGameState().getCountryTo().getArmy() + 1);
                    game.getGameState().getCountryFrom().setArmy(game.getGameState().getCountryFrom().getArmy() - 1);
                    game.getGameState().setMinArmiesToMoveAfterWin(game.getGameState().getMinArmiesToMoveAfterWin() - 1);
                } else {
                    game.resetToAndFrom();
                    Dice.resetDice(game.getGameState().getRedDice(), game.getGameState().getWhiteDice());
                    game.getGameState().setWinBattle(false);
                    if (!game.isMoreAttacks()) {
                        game.nextTurn();
                    }
                }
            }
        } else {
            if (game.getGameState().getCurrentCountry() != null) {
                if (game.getGameState().getCurrentCountry().getPlayer() == game.getGameState().getCurrentPlayer()) {
                    if (game.getGameState().getCountryFrom() == null && game.getGameState().getCurrentCountry().getArmy() > 1) {
                        game.unHighlightCountries();
                        game.getGameState().setCountryFrom(game.getGameState().getCurrentCountry());
                        game.getGameState().setCurrentTurnPhraseText("Select a country to Attack.");
                        game.getGameState().getCurrentCountry().select(true, 2);
                    }
                } else if (game.getGameState().getCountryTo() == null && game.getGameState().getCurrentCountry().isHighlighted()) {
                    game.getGameState().getCountryFrom().unSelect(true);
                    game.getGameState().getCountryFrom().setSelected(true);
                    game.getGameState().setCountryTo(game.getGameState().getCurrentCountry());
                    game.getGameState().getCountryTo().setHighlighted(true);
                    game.getGameState().setCurrentTurnPhraseText("Use Attack window to Attack.");
                }
            } else {
                game.getGameState().setCurrentTurnPhraseText("Select a Country to attack from.");
                game.resetToAndFrom();
                Dice.resetDice(game.getGameState().getRedDice(), game.getGameState().getWhiteDice());
            }
        }
    }

    /**
     * Attack phase
     */
    @Override
    public void attack() {
        Game game = Game.getInstance();
        if (game.getGameState().getCountryFrom() != null && game.getGameState().getCountryFrom().getArmy() >= 2 && game.getGameState().getCountryTo() != null) {

            Dice.rollDice(game.getGameState().getNumberOfRedDicesSelected(), game.getGameState().getNumberOfWhiteDicesSelected(), game.getGameState().getRedDice(), game.getGameState().getWhiteDice());

            for (int i = 0; i < Math.min(game.getGameState().getNumberOfRedDicesSelected(), game.getGameState().getNumberOfWhiteDicesSelected()); i++) {
                if (game.getGameState().getRedDice()[i].getNumber() > game.getGameState().getWhiteDice()[i].getNumber()) {
                    game.getGameState().getCountryTo().setArmy(game.getGameState().getCountryTo().getArmy() - 1);
                } else {
                    game.getGameState().getCountryFrom().setArmy(game.getGameState().getCountryFrom().getArmy() - 1);
                }
            }

            if (game.getGameState().getCountryTo().getArmy() == 0) {
                game.getGameState().setWinBattle(true);
                game.getGameState().getCountryTo().setPlayer(game.getGameState().getCurrentPlayer());
                game.getGameState().setMinArmiesToMoveAfterWin(game.getGameState().getNumberOfRedDicesSelected());
                game.getGameState().setGiveACard(true);
                if (game.isGameWonBy(game.getGameState().getCurrentPlayer())) {
                    game.gameOver();
                }
            }
        }
    }

    /**
     * Fortification for Player
     */
    @Override
    public void fortify() {
        Game game = Game.getInstance();
        if (game.getGameState().getCountryFrom() == null) {
            game.unHighlightCountries();
            game.getGameState().setCountryFrom(game.getGameState().getCurrentCountry());
            game.getGameState().setCurrentTurnPhraseText("Select a country to move an army.");
            game.getGameState().getCurrentCountry().select(false, -1);
        } else if (game.getGameState().getCountryTo() == null && game.getGameState().getCurrentCountry().isHighlighted()) {
            game.getGameState().getCountryFrom().unSelect(false);
            game.getGameState().getCountryFrom().setSelected(true);
            game.getGameState().setCountryTo(game.getGameState().getCurrentCountry());
            game.getGameState().getCountryTo().setHighlighted(true);
            game.getGameState().setCurrentTurnPhraseText("Click on country to move one army.");
        }
        if (game.getGameState().getCountryFrom() != null && game.getGameState().getCountryFrom().getArmy() > 1 && game.getGameState().getCountryTo() != null) {
            game.getGameState().getCountryFrom().setArmy(game.getGameState().getCountryFrom().getArmy() - 1);
            game.getGameState().getCountryTo().setArmy(game.getGameState().getCountryTo().getArmy() + 1);
        }
    }

    /**
     * Exchange cards for armies
     *
     * @param cardsEnumList
     */
    @Override
    public void exchange(List<CardsEnum> cardsEnumList) {
        Game game = Game.getInstance();
        String phrase = "";
        if (cardsEnumList.size() == 3) {
            for (CardsEnum cardsEnum : cardsEnumList) {
                game.getGameState().getCurrentPlayer().getCardsEnumIntegerMap().put(cardsEnum, game.getGameState().getCurrentPlayer().getCardsEnumIntegerMap().get(cardsEnum) - 1);
            }
            phrase = String.join(", ", cardsEnumList.stream().map(CardsEnum::getName).collect(Collectors.toList())) + " cards";
        } else if (cardsEnumList.size() == 1) {
            game.getGameState().getCurrentPlayer().getCardsEnumIntegerMap().put(cardsEnumList.get(0), game.getGameState().getCurrentPlayer().getCardsEnumIntegerMap().get(cardsEnumList.get(0)) - 3);
            phrase = cardsEnumList.get(0).getName() + " card";
        }
        game.getGameState().getCurrentPlayer().setArmies(game.getGameState().getCurrentPlayer().getArmies() + game.getGameState().getArmiesToCardExchange());
        game.getGameState().setArmiesToCardExchange(game.getGameState().getArmiesToCardExchange() + game.getGameState().getARMIES_TO_EXCHANGE_INCREASE());
        game.getGameState().setCurrentTurnPhraseText("Exchanged " + phrase + " for " + game.getGameState().getARMIES_TO_EXCHANGE_INCREASE() + " armies. Armies to place " + game.getGameState().getCurrentPlayer().getArmies());
    }
}
