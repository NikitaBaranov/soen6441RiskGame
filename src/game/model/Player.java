package game.model;


import game.Game;
import game.enums.CardsEnum;
import game.strategies.IStrategy;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static game.enums.CardsEnum.ARTILLERY;
import static game.enums.CardsEnum.CAVALRY;
import static game.enums.CardsEnum.INFANTRY;
import static game.enums.CardsEnum.WILDCARDS;

/**
 * The Player model. Describes the Players parameters.
 *
 * @author Dmitry Kryukov, Ksenia Popova
 * @see CardsEnum
 */
public class Player {
    private String name;
    IStrategy strategy;

    private Color color;
    private Map<CardsEnum, Integer> cardsEnumIntegerMap = new HashMap<>();
    private int armies = 5;

    /**
     * Constructor of the class
     *
     * @param name  name of player
     * @param color color of player
     */
    public Player(String name, Color color, IStrategy strategy) {
        this.name = name;
        this.color = color;
        this.strategy = strategy;

        cardsEnumIntegerMap.put(INFANTRY, 0);
        cardsEnumIntegerMap.put(CAVALRY, 0);
        cardsEnumIntegerMap.put(ARTILLERY, 0);
        cardsEnumIntegerMap.put(WILDCARDS, 0);
//        cardsEnumIntegerMap.put(BONUS, 0);
    }

    /**
     * Reinforcement for player
     */
    public void reinforcement() {
        Game game = Game.getInstance();
        if (armies > 0) {
            game.getGameState().getCurrentCountry().setArmy(game.getGameState().getCurrentCountry().getArmy() + 1);
            armies--;
            game.getGameState().setCurrentTurnPhraseText("Armies to place " + armies);
        } else {
            game.unHighlightCountries();
        }
    }

    /**
     * Preparing for attach phase
     */
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
                if (game.getGameState().getCurrentCountry().getPlayer() == this) {
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
                game.getGameState().getCountryTo().setPlayer(this);
                game.getGameState().setMinArmiesToMoveAfterWin(game.getGameState().getNumberOfRedDicesSelected());
                game.getGameState().setGiveACard(true);
                if (game.isGameWonBy(this)) {
                    game.gameOver();
                }
            }
        }
    }

    /**
     * Fortification for Player
     */
    public void fortification() {
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
    public void exchange(List<CardsEnum> cardsEnumList) {
        Game game = Game.getInstance();
        String phrase = "";
        if (cardsEnumList.size() == 3) {
            for (CardsEnum cardsEnum : cardsEnumList) {
                cardsEnumIntegerMap.put(cardsEnum, cardsEnumIntegerMap.get(cardsEnum) - 1);
            }
            phrase = String.join(", ", cardsEnumList.stream().map(CardsEnum::getName).collect(Collectors.toList())) + " cards";
        } else if (cardsEnumList.size() == 1) {
            cardsEnumIntegerMap.put(cardsEnumList.get(0), cardsEnumIntegerMap.get(cardsEnumList.get(0)) - 3);
            phrase = cardsEnumList.get(0).getName() + " card";
        }
        armies = armies + game.getGameState().getArmiesToCardExchange();
        game.getGameState().setArmiesToCardExchange(game.getGameState().getArmiesToCardExchange() + game.getGameState().getARMIES_TO_EXCHANGE_INCREASE());
        game.getGameState().setCurrentTurnPhraseText("Exchanged " + phrase + " for " + game.getGameState().getARMIES_TO_EXCHANGE_INCREASE() + " armies. Armies to place " + armies);
    }

    /**
     * Get the name of player
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the player's name
     *
     * @param name name of the player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for bonus cards for player
     *
     * @return cardsEnumIntegerMap
     */
    public Map<CardsEnum, Integer> getCardsEnumIntegerMap() {
        return cardsEnumIntegerMap;
    }

    /**
     * Get the player color
     *
     * @return color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Set the player color
     *
     * @param color Color of the player
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Get the armies of player
     *
     * @return armies
     */
    public int getArmies() {
        return armies;
    }

    /**
     * Set the armies for player
     *
     * @param armies Armies of the player
     */
    public void setArmies(int armies) {
        this.armies = armies;
    }
}