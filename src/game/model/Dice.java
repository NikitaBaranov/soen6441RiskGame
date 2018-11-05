package game.model;

import game.enums.DiceEnum;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * The dice model. Describes the dice parameters
 * @author Dmitry Kryukov, Ksenia Popova
 */
public class Dice {

    private static final String imageSourceDir = "src/game/resources/dice/";
    public static final String RED_FILE_PREFIX = imageSourceDir + "red_";
    public static final String WHITE_FILE_PREFIX = imageSourceDir + "white_";

    /**
     * Map for dices
     * @return diceEnumMap map
     */
    public static Map<Integer, DiceEnum> diceEnumMap() {
        Map<Integer, DiceEnum> diceEnumMap = new HashMap<>();
        diceEnumMap.put(1, DiceEnum.ONE);
        diceEnumMap.put(2, DiceEnum.TWO);
        diceEnumMap.put(3, DiceEnum.THREE);
        diceEnumMap.put(4, DiceEnum.FOUR);
        diceEnumMap.put(5, DiceEnum.FIVE);
        diceEnumMap.put(6, DiceEnum.SIX);
        return diceEnumMap;
    }

    /**
     * Stub for the dice feature
     */
    public static void rollDice(int redDiceNumber, int whiteDiceNumber, DiceEnum[] redDice, DiceEnum[] whiteDice) {
        Random r = new Random();
        for (int i = 0; i < redDice.length; i++) {
            if (i < redDiceNumber) {
                redDice[i] = diceEnumMap().get(r.nextInt(6) + 1);
            } else {
                redDice[i] = DiceEnum.EMPTY;
            }
        }
        Arrays.sort(redDice, Collections.reverseOrder());

        for (int i = 0; i < whiteDice.length; i++) {
            if (i < whiteDiceNumber) {
                whiteDice[i] = diceEnumMap().get(r.nextInt(6) + 1);
            } else {
                whiteDice[i] = DiceEnum.EMPTY;
            }
        }
        Arrays.sort(whiteDice, Collections.reverseOrder());
    }

    /**
     * Reset dice
     * @param redDice
     * @param whiteDice
     */
    public static void resetDice(DiceEnum[] redDice, DiceEnum[] whiteDice) {
        for (int i = 0; i < redDice.length; i++) {
            redDice[i] = DiceEnum.EMPTY;
        }
        for (int i = 0; i < whiteDice.length; i++) {
            whiteDice[i] = DiceEnum.EMPTY;
        }
    }
}
