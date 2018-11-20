package game.strategies.PlayerStrategies;

public class PlayerStrategyFactory {

    public static IPlayerStrategy getStrategy(PlayerStrategyEnum playerStrategyEnum) {
        switch (playerStrategyEnum) {
            case HUMAN_STRATEGY:
                return new HumanPlayerStrategy();

            case AI_AGGRESSIVE_STRATEGY:
                return new AiAggressivePlayerStrategy();

            case AI_BENEVOLENT_STRATEGY:
                return null;

            case AI_RANDOM_STRATEGY:
                return null;

            case AI_CHEATER_STRATEGY:
                return null;

            default:
                System.out.println("Incorrect Player Strategy.");
                return null;
        }
    }
}
