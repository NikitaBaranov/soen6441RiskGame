package game.model;


import game.model.enums.CardsEnum;
import game.strategies.PlayerStrategies.IPlayerStrategy;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static game.model.enums.CardsEnum.ARTILLERY;
import static game.model.enums.CardsEnum.CAVALRY;
import static game.model.enums.CardsEnum.INFANTRY;
import static game.model.enums.CardsEnum.WILDCARDS;

/**
 * The Player model. Describes the Players parameters.
 *
 * @author Dmitry Kryukov, Ksenia Popova
 * @see CardsEnum
 */
public class Player {
    IPlayerStrategy strategy;
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
    public Player(String name, Color color, IPlayerStrategy strategy) {
        this.name = name;
        this.color = color;
        this.strategy = strategy;

        cardsEnumIntegerMap.put(INFANTRY, 0);
        cardsEnumIntegerMap.put(CAVALRY, 0);
        cardsEnumIntegerMap.put(ARTILLERY, 0);
        cardsEnumIntegerMap.put(WILDCARDS, 0);
    }

    /**
     * Reinforcement for player
     */
    public void reinforcement(GameState gameState) {
        strategy.reinforce(gameState);
    }

    /**
     * Preparing for attach phase
     */
    public void beforeAndAfterAttack(GameState gameState) {
        strategy.beforeAndAfterAttack(gameState);
    }

    /**
     * Attack phase
     */
    public void attack(GameState gameState) {
        strategy.attack(gameState);
    }

    /**
     * Fortification for Player
     */
    public void fortify(GameState gameState) {
        strategy.fortify(gameState);
    }

    /**
     * Exchange cards for armies
     *
     * @param gameState
     */
    public void exchange(GameState gameState) {
        strategy.exchange(gameState);
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