package game.model;


import game.enums.CardsEnum;
import game.ui.view.IPanelObserver;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static game.enums.CardsEnum.ARTILLERY;
import static game.enums.CardsEnum.BONUS;
import static game.enums.CardsEnum.CAVALRY;
import static game.enums.CardsEnum.INFANTRY;
import static game.enums.CardsEnum.WILDCARDS;

/**
 * The Player model. Describes the Players parameters.
 * @author Dmitry Kryukov, Ksenia Popova
 * @see CardsEnum
 */
public class Player implements IModelObservable {
    private String name;

    private Color color;
    private Map<CardsEnum, Integer> cardsEnumIntegerMap = new HashMap<>();
    private int armies = 5;

    /**
     * Constructor of the class
     * @param name name of player
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

    @Override
    public void attachObserver(IPanelObserver iPanelObserver) {

    }

    @Override
    public void detachObserver(IPanelObserver iPanelObserver) {

    }

    @Override
    public void notifyObservers() {

    }

    /**
     * Get the name of player
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the player's name
     * @param name name of the player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for bonus cards for player
     * @return cardsEnumIntegerMap
     */
    public Map<CardsEnum, Integer> getCardsEnumIntegerMap() {
        return cardsEnumIntegerMap;
    }

    /**
     * Set the bonus cards for player
     * @param cardsEnumIntegerMap Bonus cards for the player
     */
    public void setCardsEnumIntegerMap(Map<CardsEnum, Integer> cardsEnumIntegerMap) {
        this.cardsEnumIntegerMap = cardsEnumIntegerMap;
    }

    /**
     * Get the player color
     * @return color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Set the player color
     * @param color Color of the player
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Get the armies of player
     * @return armies
     */
    public int getArmies() {
        return armies;
    }

    /**
     * Set the armies for player
     * @param armies Armies of the player
     */
    public void setArmies(int armies) {
        this.armies = armies;
    }
}