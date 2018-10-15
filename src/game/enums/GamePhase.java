package game.enums;

public enum  GamePhase {
    PLACING_ARMIES(0, "Placing New Armies"),
    ATACKING(1, "Attacking"),
    FORTIFYING(2, "Fortifying your position"),
    CHANGE_PLAYER(3, "Change Player");

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