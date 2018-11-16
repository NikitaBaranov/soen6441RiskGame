package game.strategies.GamePhaseStrategies;

public class GamePhaseStrategyFactory {

    public static IGamePhaseStrategy getStrategy(GamePhaseEnum gamePhaseEnum) {
        switch (gamePhaseEnum) {

            case PLACING_ARMIES:
                return new PlacingArmiesPhaseStrategy();

            case REINFORCEMENT:
                return null;

            case ATTACK:
                return null;

            case FORTIFICATION:
                return null;

            case GAME_OVER:
                return null;

            default:
                System.out.println("Incorrect Game Phase.");
                return null;
        }
    }
}
