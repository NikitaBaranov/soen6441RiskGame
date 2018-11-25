package game.strategies.GamePhaseStrategies;

import game.Game;
import game.model.Dice;
import game.model.GameState;
import game.model.Player;

import static game.strategies.GamePhaseStrategies.GamePhaseEnum.ATTACK;
import static game.strategies.GamePhaseStrategies.GamePhaseEnum.PLACING_ARMIES;
import static game.strategies.MapFunctionsUtil.highlightPayerCountries;
import static game.strategies.MapFunctionsUtil.selectCountry;
import static game.strategies.MapFunctionsUtil.unHighlightCountries;

/**
 * Startup phase strategy.
 * Setup requires game states. Preparing the game for startup phase
 * Show status messages
 *
 * @author Dmitry Kryukov, Ksenia Popova
 * @see BasePhaseStrategy
 */
public class PlacingArmiesPhaseStrategy extends BasePhaseStrategy {
    /**
     * Initialization of the startup phase. Setup required game states and vars.
     * @param gameState
     */
    @Override
    public void init(GameState gameState) {
        super.init(gameState);

        gameState.setCurrentGamePhase(PLACING_ARMIES);
        gameState.setCurrentPlayer(gameState.getPlayers().get(0));
        gameState.setNextTurnButton(false);
        Dice.resetDice(gameState.getRedDice(), gameState.getWhiteDice());
        highlightPayerCountries(gameState.getCountries(), gameState.getCurrentPlayer());
        gameState.setCurrentTurnPhraseText("Select a country to place your army.");
    }

    /**
     * Behavoir of map click action.
     * Seup requires game states, show status messages. Checks of different game rules conditions
     * @param gameState
     * @param x
     * @param y
     */
    @Override
    public void mapClick(GameState gameState, int x, int y) {
        if (selectCountry(gameState, x, y)) {
            unHighlightCountries(gameState);
            if (gameState.getCurrentPlayer().getArmies() > 0 && gameState.getCurrentCountry().getPlayer() == gameState.getCurrentPlayer()) {
                gameState.getCurrentCountry().setArmy(gameState.getCurrentCountry().getArmy() + 1);
                gameState.getCurrentPlayer().setArmies(gameState.getCurrentPlayer().getArmies() - 1);

                Player nextPlayer = gameState.getPlayers().get((gameState.getPlayers().indexOf(gameState.getCurrentPlayer()) + 1) % gameState.getPlayers().size());
                System.out.println("Next Turn Button Clicked. Next Player is " + nextPlayer.getName());

                gameState.setCurrentTurnPhraseText("Select a country to place your army. Armies to place  " + gameState.getCurrentPlayer().getArmies());

                gameState.setCurrentPlayer(nextPlayer);
                highlightPayerCountries(gameState.getCountries(), gameState.getCurrentPlayer());
            }
//            if (gameState.getCurrentPlayer().isComputerPlayer()){
//                gameState.getCurrentPlayer().placeArmies(gameState);
//                gameState.setNextTurnButton(true);
//                nextTurnButton(gameState);
//            }
            if (gameState.getCurrentPlayer().getArmies() <= 0) {
                gameState.setNextTurnButton(true);
                gameState.setCurrentTurnPhraseText("The turn is over. Press \"Next turn\" button.");
                unHighlightCountries(gameState);
                // TODO Check if its done correctly. Automatic go to next turn when placing armies is over
                nextTurnButton(gameState);
            }
        }
    }

    /**
     * Next turn button behavoir. Force the game to next phase attack
     * @param gameState
     */
    @Override
    public void nextTurnButton(GameState gameState) {
        Game.getInstance().setGamePhaseStrategy(GamePhaseStrategyFactory.getStrategy(ATTACK));
        Game.getInstance().getGamePhaseStrategy().init(gameState);
    }
}
