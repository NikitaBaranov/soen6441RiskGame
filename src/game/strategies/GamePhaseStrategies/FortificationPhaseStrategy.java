package game.strategies.GamePhaseStrategies;

import game.Game;
import game.model.GameState;

import static game.strategies.GamePhaseStrategies.GamePhaseEnum.FORTIFICATION;
import static game.strategies.GamePhaseStrategies.GamePhaseEnum.REINFORCEMENT;
import static game.strategies.MapFunctionsUtil.highlightPayerCountries;
import static game.strategies.MapFunctionsUtil.isCountyWithMoreThenOneArmy;
import static game.strategies.MapFunctionsUtil.selectCountry;

/**
 * Fortification phase strategy class.
 * Required to prepare game for fortification phase and setup game state before that.
 *
 * @author Dmitry Kryukov, Ksenia Popova
 * @see BasePhaseStrategy
 */
public class FortificationPhaseStrategy extends BasePhaseStrategy {
    /**
     * Initialization of the phase. Setup all required states, variables.
     * Prepare game for fortification. Show status messages.
     *
     * @param gameState
     */
    @Override
    public void init(GameState gameState) {
        super.init(gameState);

        gameState.setCurrentGamePhase(FORTIFICATION);
        gameState.setCurrentTurnPhraseText("Select a country to move armies from. ");

        System.out.println("Next Turn Button Clicked. Next Player is " + gameState.getCurrentGamePhase());

        highlightPayerCountries(gameState.getCountries(), gameState.getCurrentPlayer());

        debugMessage(gameState);

        if (isCountyWithMoreThenOneArmy(gameState)) {
            if (gameState.getCurrentPlayer().isComputerPlayer()) {
                gameState.getCurrentPlayer().fortify(gameState);
            }
        } else {
            nextTurnButton(gameState);
        }
    }

    /**
     * Force game states to fortify when clicked on map
     * @param gameState
     * @param x
     * @param y
     */
    @Override
    public void mapClick(GameState gameState, int x, int y) {
        if (!gameState.getCurrentPlayer().isComputerPlayer()) {
            if (selectCountry(gameState, x, y)) {
                if (gameState.getCurrentCountry().getPlayer() == gameState.getCurrentPlayer()) {
                    gameState.getCurrentPlayer().fortify(gameState);
                }
            }
        }
    }

    /**
     * Force next turn. Go to reinforcement phase.
     * @param gameState
     */
    @Override
    public void nextTurnButton(GameState gameState) {
        Game.getInstance().setGamePhaseStrategy(GamePhaseStrategyFactory.getStrategy(REINFORCEMENT));
        Game.getInstance().getGamePhaseStrategy().init(gameState);
    }
}
