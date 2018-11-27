package game.strategies.PlayerStrategies;

/**
 * Player strategy factory. Allow us to choose strategies for player.
 *
 * @author Dmitry kryukov, Ksenia Popova
 */
public class PlayerStrategyFactory {
    /**
     * Get strategies.
     * @param playerStrategyEnum
     * @return
     */
    public static IPlayerStrategy getStrategy(PlayerStrategyEnum playerStrategyEnum) {
        switch (playerStrategyEnum) {
            case HUMAN_STRATEGY:
                return new HumanStrategy();

            case AI_AGGRESSIVE_STRATEGY:
                return new AiAggressiveStrategy();

            case AI_BENEVOLENT_STRATEGY:
                return new AiBenevolentStrategy();

            case AI_RANDOM_STRATEGY:
                return new AiRandomStrategy();

            case AI_CHEATER_STRATEGY:
                return new AiCheaterStrategy();

            default:
                System.out.println("Incorrect Player Strategy.");
                return null;
        }
    }
}
