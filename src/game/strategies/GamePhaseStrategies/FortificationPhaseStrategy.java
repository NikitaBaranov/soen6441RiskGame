package game.strategies.GamePhaseStrategies;

import game.Game;
import game.model.GameState;

import static game.strategies.GamePhaseStrategies.GamePhaseEnum.FORTIFICATION;
import static game.strategies.GamePhaseStrategies.GamePhaseEnum.REINFORCEMENT;

public class FortificationPhaseStrategy extends AbstractGamePhaseStrategy {

    @Override
    public void init(GameState gameState) {
        gameState.setCurrentGamePhase(FORTIFICATION);
        gameState.setCurrentTurnPhraseText("Select a country to move armies from. ");

        System.out.println("Next Turn Button Clicked. Next Player is " + gameState.getCurrentGamePhase());

        unHighlightCountries(gameState.getCountries());
        resetToAndFrom(gameState);
        highlightPayerCountries(gameState.getCountries(), gameState.getCurrentPlayer());
    }

    @Override
    public void nextTurnButton(GameState gameState) {
        Game.getInstance().setGamePhaseStrategy(GamePhaseStrategyFactory.getStrategy(REINFORCEMENT));
        Game.getInstance().getGamePhaseStrategy().init(gameState);
    }
}
