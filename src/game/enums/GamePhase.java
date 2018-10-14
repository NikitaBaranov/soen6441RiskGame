package game.enums;

public enum  GamePhase {
    PLACING_ARMIES(1, "Placing New Armies"),
    ATACKING(2, "Attacking"),
    FORTIFYING(3, "Fortifying your position");

    private String name;
    private int number;

    GamePhase(int number, String name) {
        this.name = name;
        this.number = number;
    }
}
