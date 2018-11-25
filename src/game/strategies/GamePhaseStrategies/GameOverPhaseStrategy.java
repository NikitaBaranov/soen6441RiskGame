package game.strategies.GamePhaseStrategies;

import game.model.GameState;

import static game.strategies.GamePhaseStrategies.GamePhaseEnum.GAME_OVER;

public class GameOverPhaseStrategy extends BasePhaseStrategy {

    @Override
    public void init(GameState gameState) {
        super.init(gameState);

        gameState.setCurrentGamePhase(GAME_OVER);
        gameState.setCurrentTurnPhraseText("Game over. The " + gameState.getCurrentPlayer().getName() + " win.");
        gameState.setNextTurnButton(false);
    }
}
