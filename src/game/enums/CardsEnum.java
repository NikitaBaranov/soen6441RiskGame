package game.enums;

/**
 * Store cards.
 * Stub for future development
 * @author Dmitry Kryukov
 */
public enum CardsEnum {
    INFANTRY("Infantry"),
    CAVALRY("Cavalry"),
    ARTILLERY("Artillery"),
    WILDCARDS("Wildcards");
//    BONUS("Bonus");

    private String name;

    /**
     * Constructor of the class.
     * @param name of the card
     */
    CardsEnum(String name) {
        this.name = name;
    }

    /**
     * Method that return the name of card
     * @return name of card
     */
    public String getName() {
        return name;
    }
}