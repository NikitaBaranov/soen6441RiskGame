package game.strategies.PlayerStrategies;

public enum PlayerStrategyEnum {
    HUMAN_STRATEGY(false),
    AI_AGGRESSIVE_STRATEGY(true),
    AI_BENEVOLENT_STRATEGY(true),
    AI_RANDOM_STRATEGY(true),
    AI_CHEATER_STRATEGY(true);

    private boolean autoProcess;

    PlayerStrategyEnum(boolean autoProcess) {
        this.autoProcess = autoProcess;
    }

    public boolean isAutoProcess() {
        return autoProcess;
    }
}