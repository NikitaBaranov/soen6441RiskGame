package game.strategies.GamePhaseStrategies;

import game.Game;
import game.model.Dice;
import game.model.GameState;
import game.model.Player;

import static game.strategies.GamePhaseStrategies.GamePhaseEnum.ATTACK;
import static game.strategies.GamePhaseStrategies.GamePhaseEnum.PLACING_ARMIES;
import static game.utils.MapFunctionsUtil.highlightPayerCountries;
import static game.utils.MapFunctionsUtil.selectCountry;
import static game.utils.MapFunctionsUtil.unHighlightCountries;
import static game.utils.MapFunctionsUtil.unSelectCountries;

/**
 * Startup phase strategy.
 * Setup requires game states. Preparing the game for startup phase
 * Show status messages
 *
 * @author Dmitry Kryukov, Ksenia Popova
 * @see BasePhaseStrategy
 */
// TODO place armies выводит неправильное количество оставшихся армий, доходит до 0 и позволяет поставить последнюю армию.
public class PlacingArmiesPhaseStrategy extends BasePhaseStrategy {
    /**
     * Initialization of the startup phase. Setup required game states and vars.
     *
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
        debugMessage(gameState);
        if (gameState.getCurrentPlayer().isComputerPlayer()) {
            gameState.getCurrentPlayer().placeArmies(gameState);
        } else {
            gameState.setCurrentTurnPhraseText("Select a country to place your army. Armies to place  " + gameState.getCurrentPlayer().getArmies());
        }
    }

    /**
     * Behavoir of map click action.
     * Seup requires game states, show status messages. Checks of different game rules conditions
     *
     * @param gameState
     * @param x
     * @param y
     */
    @Override
    public void mapClick(GameState gameState, int x, int y) {
        if (!gameState.getCurrentPlayer().isComputerPlayer()) {
            if (selectCountry(gameState, x, y) && gameState.getCurrentPlayer().getArmies() > 0 && gameState.getCurrentCountry().getPlayer() == gameState.getCurrentPlayer()) {
                unHighlightCountries(gameState);
                gameState.getCurrentPlayer().placeArmies(gameState);
                nextTurnButton(gameState);
            }
        }
    }

    /**
     * Next turn button behavoir. Force the game to next phase attack
     *
     * @param gameState
     */
    @Override
    public void nextTurnButton(GameState gameState) {
        unSelectCountries(gameState);
        unHighlightCountries(gameState);
        gameState.notifyObservers();
        int maxArmiesLeft = 0;
        for (Player player : gameState.getPlayers()) {
            if (player.getArmies() > maxArmiesLeft) {
                maxArmiesLeft = player.getArmies();
            }
        }
        if (maxArmiesLeft == 0) {
            gameState.setCurrentPlayer(gameState.getPlayers().get(0));
            Game.getInstance().setGamePhaseStrategy(GamePhaseStrategyFactory.getStrategy(ATTACK));
            Game.getInstance().getGamePhaseStrategy().init(gameState);
            gameState.notifyObservers();
            // TODO Check if its done correctly. Automatic go to next turn when placing armies is over
            // I'm not sure that it works well. Just be sure that I added this line in the correct place
        } else {
            Player nextPlayer = gameState.getPlayers().get((gameState.getPlayers().indexOf(gameState.getCurrentPlayer()) + 1) % gameState.getPlayers().size());
            gameState.setCurrentPlayer(nextPlayer);
            highlightPayerCountries(gameState.getCountries(), gameState.getCurrentPlayer());
            gameState.notifyObservers();
            if (nextPlayer.isComputerPlayer()) {
                nextPlayer.placeArmies(gameState);
            } else {
                gameState.setCurrentTurnPhraseText("Select a country to place your army. Armies to place  " + gameState.getCurrentPlayer().getArmies());
            }
        }
    }
}
