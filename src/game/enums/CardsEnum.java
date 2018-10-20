package game.enums;

public enum CardsEnum {
    INFANTRY("Infantry"),
    CAVALRY("Cavalry"),
    ARTILLERY("Artillery"),
    WILDCARDS("Wildcards"),
    BONUS("Bonus");

    private String name;

    CardsEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
