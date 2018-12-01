package game.model;


import game.model.enums.CardsEnum;
import game.strategies.PlayerStrategies.IPlayerStrategy;
import game.strategies.PlayerStrategies.PlayerStrategyEnum;
import game.strategies.PlayerStrategies.PlayerStrategyFactory;
import lombok.Data;

import java.awt.*;
import java.io.Serializable;
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
@Data
public class Player implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean computerPlayer;
    private PlayerStrategyEnum playerStrategyEnum;
    private transient IPlayerStrategy strategy;
    private String name;
    private Color color;
    private Map<CardsEnum, Integer> cardsEnumIntegerMap = new HashMap<>();
    private int armies = 5;
    private boolean lost = false;

    /**
     * Constructor of the class
     *
     * @param name  name of player
     * @param color color of player
     */
    public Player(String name, Color color, PlayerStrategyEnum playerStrategyEnum, boolean computerPlayer) {
        this.name = name;
        this.color = color;
        this.playerStrategyEnum = playerStrategyEnum;
        this.strategy = PlayerStrategyFactory.getStrategy(playerStrategyEnum);
        this.computerPlayer = computerPlayer;

        cardsEnumIntegerMap.put(INFANTRY, 0);
        cardsEnumIntegerMap.put(CAVALRY, 0);
        cardsEnumIntegerMap.put(ARTILLERY, 0);
        cardsEnumIntegerMap.put(WILDCARDS, 0);
    }

    /**
     * Initialization of strategy for player
     */
    public void initStategy() {
        this.strategy = PlayerStrategyFactory.getStrategy(playerStrategyEnum);
    }

    /**
     * Place armies. First phase
     * @param gameState
     */
    public void placeArmies(GameState gameState) {
        strategy.placeArmies(gameState);
    }

    /**
     * Reinforcement for player
     */
    public void reinforce(GameState gameState) {
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
}