package game.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for storing the dices.
 * Stub for future development
 * @author Dmitry Kryukov
 */
public enum DiceEnum {
    EMPTY(0, "die0.png"),
    ONE(1, "die1.png"),
    TWO(2, "die2.png"),
    THREE(3, "die3.png"),
    FOUR(4, "die4.png"),
    FIVE(5, "die5.png"),
    SIX(6, "die6.png");

    private int number;
    private String fileSuffix;

    /**
     * Constructor of the class
     * @param number of points on dice
     * @param fileSuffix proper image with number
     */
    DiceEnum(int number, String fileSuffix) {
        this.number = number;
        this.fileSuffix = fileSuffix;
    }

    private static final String imageSourceDir = "src/game/resources/dice/";
    public static final String RED_FILE_PREFIX= imageSourceDir + "red_";
    public static final String WHITE_FILE_PREFIX= imageSourceDir + "white_";

    /**
     * Method that return the number on dice
     * @return number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Method return the appropriate image with number
     * @return fileSuffix i.e. image with dice
     */
    public String getFileSuffix() {
        return fileSuffix;
    }
}