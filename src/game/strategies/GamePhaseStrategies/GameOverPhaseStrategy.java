package game.strategies.GamePhaseStrategies;

import game.model.GameState;

import static game.strategies.GamePhaseStrategies.GamePhaseEnum.GAME_OVER;

/**
 * Game over phase strategy. Describes the game over actions.
 *
 * @author Dmitry Kryukov
 * @see BasePhaseStrategy
 */
public class GameOverPhaseStrategy extends BasePhaseStrategy {
    /**
     * Initialization of game over phase. Show status messages. Block buttons.
     * @param gameState
     */
    @Override
    public void init(GameState gameState) {
        super.init(gameState);
        // TODO doesn't work for Aggressive AI. It proceed to play even when the opponent doesn't have any country
        gameState.setCurrentGamePhase(GAME_OVER);
        gameState.setCurrentTurnPhraseText("Game over. The " + gameState.getCurrentPlayer().getName() + " win.");
        gameState.setNextTurnButton(false);
        gameState.notifyObservers();
    }
}
