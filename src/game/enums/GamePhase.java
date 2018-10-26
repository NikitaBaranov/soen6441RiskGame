package game.enums;

/**
 * Class for storing the phases that used in game.
 * @author Dmitry Kryukov
 */
public enum  GamePhase {
    INITIAL_PLACING_ARMIES(0, "Initial Army placing"),
    PLACING_ARMIES(1, "Placing New Armies"),
    ATACKING(2, "Attacking"),
    FORTIFYING(3, "Fortifying your position");

    private String name;
    private int number;

    /**
     * Constructor of the class.
     * @param number of phase
     * @param name of phase
     */
    GamePhase(int number, String name) {
        this.name = name;
        this.number = number;
    }

    /**
     * Getter for name. Return the name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * setter for the name. Set the name
     * @param name name of phase
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the number
     * @return number
     */
    public int getNumber() {
        return number;
    }

    /**
     * setter for the number
     * @param number number of phase
     */
    public void setNumber(int number) {
        this.number = number;
    }
}