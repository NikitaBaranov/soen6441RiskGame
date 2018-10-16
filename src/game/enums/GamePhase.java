package game.enums;

public enum  GamePhase {
    INITIAL_PLACING_ARMIES(0, "Initial Army placing"),
    PLACING_ARMIES(1, "Placing New Armies"),
    ATACKING(2, "Attacking"),
    FORTIFYING(3, "Fortifying your position");

    private String name;
    private int number;

    GamePhase(int number, String name) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}