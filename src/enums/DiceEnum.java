package enums;

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

    DiceEnum(int number, String fileSuffix) {
        this.number = number;
        this.fileSuffix = fileSuffix;
    }

    private static final String imageSourceDir = "src/resources/dice/";
    public static final String RED_FILE_PREFIX= imageSourceDir + "red_";
    public static final String WHITE_FILE_PREFIX= imageSourceDir + "white_";

    public int getNumber() {
        return number;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }
}