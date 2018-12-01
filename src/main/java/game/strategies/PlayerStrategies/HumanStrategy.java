package game.strategies.PlayerStrategies;

import game.Game;
import game.model.Dice;
import game.model.GameState;
import game.model.enums.CardsEnum;
import game.strategies.GamePhaseStrategies.GamePhaseStrategyFactory;

import java.util.stream.Collectors;

import static game.strategies.GamePhaseStrategies.BasePhaseStrategy.isGameWonBy;
import static game.strategies.GamePhaseStrategies.GamePhaseEnum.GAME_OVER;
import static game.utils.MapFunctionsUtil.isCountyWithMoreThenOneArmy;
import static game.utils.MapFunctionsUtil.isMoreAttacks;
import static game.utils.MapFunctionsUtil.resetToAndFrom;
import static game.utils.MapFunctionsUtil.unHighlightCountries;

/**
 * Human player strategy. Describes the actions for human player.
 *
 * @author Dmitry Kryukov, Ksenia Popova
 * @see BaseStrategy
 */
public class HumanStrategy extends BaseStrategy {


    @Override
    public void placeArmies(GameState gameState) {
        gameState.getCurrentCountry().setSelected(true);
        gameState.getCurrentCountry().setArmy(gameState.getCurrentCountry().getArmy() + 1);
        gameState.getCurrentPlayer().setArmies(gameState.getCurrentPlayer().getArmies() - 1);
        String message = gameState.getCurrentPlayer().getName() + " placed army to " + gameState.getCurrentCountry().getName() + ". Armies to place: " + gameState.getCurrentPlayer().getArmies();
        gameState.setCurrentTurnPhraseText(message);
        System.out.println(message);
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
        }
        if (gameState.getCurrentPlayer().getArmies() == 0) {
            unHighlightCountries(gameState);
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
        if (gameState.isWinBattle()) {

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
                }
            }
            if (gameState.getMinArmiesToMoveAfterWin() == 0 && !isCountyWithMoreThenOneArmy(gameState)) {
                Game.getInstance().getGamePhaseStrategy().nextTurnButton(gameState);
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
                unHighlightCountries(gameState);
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
            if (isGameWonBy(gameState, gameState.getCurrentPlayer())) {
                Game.getInstance().setGamePhaseStrategy(GamePhaseStrategyFactory.getStrategy(GAME_OVER));
                Game.getInstance().getGamePhaseStrategy().init(gameState);
            }
            if (gameState.getMinArmiesToMoveAfterWin() == 0 && !isMoreAttacks(gameState)) {
                Game.getInstance().getGamePhaseStrategy().nextTurnButton(gameState);
            }
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
        } else if (gameState.getCountryTo() == null && gameState.getCurrentCountry().isHighlighted() && gameState.getCurrentCountry() != gameState.getCountryFrom()) {
            gameState.getCountryFrom().unSelect(false);
            gameState.getCountryFrom().setSelected(true);
            gameState.setCountryTo(gameState.getCurrentCountry());
            gameState.getCountryTo().setHighlighted(true);
            gameState.setCurrentTurnPhraseText("Click on country to move an army.");
        }
        if (gameState.getCountryFrom() != null && gameState.getCountryFrom().getArmy() > 1 && gameState.getCountryTo() != null && gameState.getCountryTo() == gameState.getCurrentCountry()) {
            gameState.getCountryFrom().setArmy(gameState.getCountryFrom().getArmy() - 1);
            gameState.getCountryTo().setArmy(gameState.getCountryTo().getArmy() + 1);
            gameState.setCurrentTurnPhraseText("Move army from " + gameState.getCountryFrom().getName() + " to " + gameState.getCountryTo().getName());
        }
        if (gameState.getCountryFrom().getArmy() == 1) {
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