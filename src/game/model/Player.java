package game.model;


import game.Game;
import game.enums.CardsEnum;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static game.enums.CardsEnum.ARTILLERY;
import static game.enums.CardsEnum.BONUS;
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

    private Color color;
    private Map<CardsEnum, Integer> cardsEnumIntegerMap = new HashMap<>();
    private int armies = 5;

    /**
     * Constructor of the class
     *
     * @param name  name of player
     * @param color color of player
     */
    public Player(String name, Color color) {
        this.name = name;
        this.color = color;

        cardsEnumIntegerMap.put(INFANTRY, 0);
        cardsEnumIntegerMap.put(CAVALRY, 0);
        cardsEnumIntegerMap.put(ARTILLERY, 0);
        cardsEnumIntegerMap.put(WILDCARDS, 0);
        cardsEnumIntegerMap.put(BONUS, 0);
    }

    /**
     * Reinforcement for player
     */
    public void reinforcement() {
        Game game = Game.getInstance();
        if (armies > 0) {
            game.getCurrentCountry().setArmy(game.getCurrentCountry().getArmy() + 1);
            armies--;
            game.setCurrentTurnPhraseText("Armies to place " + armies);
        } else {
            game.unHighlightCountries();
        }
    }

    /**
     * Preparing for attach phase
     */
    public void beforeAndAfterAttack() {
        Game game = Game.getInstance();

        if (game.isWinBattle()) {
            //TODO: If at the end of your attacking turn you've conquered at least one territory, then you have earned a Risk card. You cannot earn more than one Risk card for this.
            //TODO: If you manage to wipe out an opponent by destroying his or her last army, you gain possession of all the Risk cards he or she may have had in their hands.

            if (game.getMinArmiesToMoveAfterWin() > 0) {
                if (game.getCurrentCountry() == game.getCountryTo()) {
                    game.getCountryTo().setArmy(game.getCountryTo().getArmy() + 1);
                    game.getCountryFrom().setArmy(game.getCountryFrom().getArmy() - 1);
                    game.setMinArmiesToMoveAfterWin(game.getMinArmiesToMoveAfterWin() - 1);
                }
            } else {
                if (game.getCurrentCountry() == game.getCountryTo() && game.getCountryFrom().getArmy() > 1) {
                    game.getCountryTo().setArmy(game.getCountryTo().getArmy() + 1);
                    game.getCountryFrom().setArmy(game.getCountryFrom().getArmy() - 1);
                    game.setMinArmiesToMoveAfterWin(game.getMinArmiesToMoveAfterWin() - 1);
                } else {
                    game.resetToAndFrom();
                    Dice.resetDice(game.getRedDice(), game.getWhiteDice());
                    game.setWinBattle(false);
                }
            }
        } else {
            if (game.getCurrentCountry() != null) {
                if (game.getCurrentCountry().getPlayer() == this) {
                    if (game.getCountryFrom() == null && game.getCurrentCountry().getArmy() > 1) {
                        game.unHighlightCountries();
                        game.setCountryFrom(game.getCurrentCountry());
                        game.setCurrentTurnPhraseText("Select a country to Attack.");
                        game.getCurrentCountry().select(true, 2);
                    }
                } else if (game.getCountryTo() == null && game.getCurrentCountry().isHighlighted()) {
                    game.getCountryFrom().unSelect(true);
                    game.getCountryFrom().setSelected(true);
                    game.setCountryTo(game.getCurrentCountry());
                    game.getCountryTo().setHighlighted(true);
                    game.setCurrentTurnPhraseText("Use Attack window to Attack.");
                }
            } else {
                game.resetToAndFrom();
                Dice.resetDice(game.getRedDice(), game.getWhiteDice());
            }
        }
    }

    /**
     * Attack phase
     */
    public void attack() {
        Game game = Game.getInstance();
        if (game.getCountryFrom() != null && game.getCountryFrom().getArmy() >= 2 && game.getCountryTo() != null) {
            Dice.resetDice(game.getRedDice(), game.getWhiteDice());

            Dice.rollDice(game.getNumberOfRedDicesSelected(), game.getNumberOfWhiteDicesSelected(), game.getRedDice(), game.getWhiteDice());

            for (int i = 0; i < Math.min(game.getNumberOfRedDicesSelected(), game.getNumberOfWhiteDicesSelected()); i++) {
                if (game.getRedDice()[i].getNumber() > game.getWhiteDice()[i].getNumber()) {
                    game.getCountryTo().setArmy(game.getCountryTo().getArmy() - 1);
                } else {
                    game.getCountryFrom().setArmy(game.getCountryFrom().getArmy() - 1);

                }
            }

            if (game.getCountryTo().getArmy() == 0) {
                game.setWinBattle(true);
                game.getCountryTo().setPlayer(this);
                game.setMinArmiesToMoveAfterWin(game.getNumberOfRedDicesSelected());
                game.setGiveACard(true);
            }
        }
    }

    /**
     * Fortification for Player
     */
    public void fortification() {
        Game game = Game.getInstance();
        if (game.getCountryFrom() == null) {
            game.unHighlightCountries();
            game.setCountryFrom(game.getCurrentCountry());
            game.setCurrentTurnPhraseText("Select a country to move an army.");
            game.getCurrentCountry().select(false, -1);
        } else if (game.getCountryTo() == null && game.getCurrentCountry().isHighlighted()) {
            game.getCountryFrom().unSelect(false);
            game.getCountryFrom().setSelected(true);
            game.setCountryTo(game.getCurrentCountry());
            game.getCountryTo().setHighlighted(true);
            game.setCurrentTurnPhraseText("Click on country to move one army.");
        }
        if (game.getCountryFrom() != null && game.getCountryFrom().getArmy() > 1 && game.getCountryTo() != null) {
            game.getCountryFrom().setArmy(game.getCountryFrom().getArmy() - 1);
            game.getCountryTo().setArmy(game.getCountryTo().getArmy() + 1);
        }
    }

    public void exchange(List<CardsEnum> cardsEnumList) {
        if (cardsEnumList.size() == 3) {
            for (CardsEnum cardsEnum : cardsEnumList) {
                cardsEnumIntegerMap.put(cardsEnum, cardsEnumIntegerMap.get(cardsEnum) - 1);
            }
        } else if (cardsEnumList.size() == 1) {
            cardsEnumIntegerMap.put(cardsEnumList.get(0), cardsEnumIntegerMap.get(cardsEnumList.get(0)) - 3);
        }
        Game game = Game.getInstance();
        armies = armies + game.getArmiesToCardExchange();
        game.setArmiesToCardExchange(game.getArmiesToCardExchange() + Game.ARMIES_TO_EXCHANGE_INCREASE);
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
     * Set the bonus cards for player
     *
     * @param cardsEnumIntegerMap Bonus cards for the player
     */
    public void setCardsEnumIntegerMap(Map<CardsEnum, Integer> cardsEnumIntegerMap) {
        this.cardsEnumIntegerMap = cardsEnumIntegerMap;
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