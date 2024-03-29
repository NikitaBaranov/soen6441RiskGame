package game.strategies.GamePhaseStrategies;

/**
 * Class for storing the phases that used in the game.
 *
 * @author Dmitry Kryukov
 */
public enum GamePhaseEnum {
    PLACING_ARMIES(0, "Initial Army placing."),
    REINFORCEMENT(1, "Reinforce your positions."),
    ATTACK(2, "Attack your enemy."),
    FORTIFICATION(3, "Fortify your position."),
    GAME_OVER(4, "Game Over!");

    private String name;
    private int number;

    /**
     * Constructor of the class.
     *
     * @param number of phase
     * @param name   of phase
     */
    GamePhaseEnum(int number, String name) {
        this.name = name;
        this.number = number;
    }

    /**
     * Getter for name. Return the name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name. Set the name
     *
     * @param name name of phase
     */
    public void setName(String name) {
        this.name = name;
    }
}