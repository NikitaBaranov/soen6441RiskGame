package game.strategies.GamePhaseStrategies;

import game.model.GameState;

import static game.strategies.GamePhaseStrategies.GamePhaseEnum.GAME_OVER;

public class GameOverPhaseStrategy extends BasePhaseStrategy {

    @Override
    public void init(GameState gameState) {
        gameState.setCurrentGamePhase(GAME_OVER);
        gameState.setCurrentTurnPhraseText("Game over. The " + gameState.getCurrentPlayer().getName() + " win.");
        unHighlightCountries(gameState);
        gameState.setNextTurnButton(false);
    }
}
