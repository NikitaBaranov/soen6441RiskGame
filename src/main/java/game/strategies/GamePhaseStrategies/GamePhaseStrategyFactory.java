package game.strategies.GamePhaseStrategies;

/**
 * Factory for choosing the game phase strategies.
 *
 * @author Dmitry Kryukov, Ksenia Popova
 */
public class GamePhaseStrategyFactory {

    public static IGamePhaseStrategy getStrategy(GamePhaseEnum gamePhaseEnum) {
        switch (gamePhaseEnum) {

            case PLACING_ARMIES:
                return new PlacingArmiesPhaseStrategy();

            case REINFORCEMENT:
                return new ReinforcementPhaseStrategy();

            case ATTACK:
                return new AttackPhaseStrategy();

            case FORTIFICATION:
                return new FortificationPhaseStrategy();

            case GAME_OVER:
                return new GameOverPhaseStrategy();

            default:
                System.out.println("Incorrect Game Phase.");
                return null;
        }
    }
}
