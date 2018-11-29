package game.strategies.GamePhaseStrategies;

import game.Game;
import game.model.GameState;
import game.model.Player;

import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;

import static game.strategies.GamePhaseStrategies.GamePhaseEnum.GAME_OVER;

/**
 * Game over phase strategy. Describes the game over actions.
 *
 * @author Dmitry Kryukov, Ksenia Popova
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
        gameState.setCurrentGamePhase(GAME_OVER);
        List<Player> players = new LinkedList<>();
        for (Player player : gameState.getPlayers()) {
            if (!player.isLost()) {
                players.add(player);
            }
        }
        String message;
        if (players.size() == 1) {
            message = "Game over. The " + players.get(0).getName() + " win.";
            gameState.setResult(players.get(0).getName());
        } else {
            message = "Game over. The Draw.";
            gameState.setResult("Draw.");
        }
        gameState.setCurrentTurnPhraseText(message);
        System.out.println(message);
        gameState.setNextTurnButton(false);
        gameState.notifyObservers();

        if (Game.getInstance().getjFrame() != null) {
            Game.getInstance().getjFrame().dispatchEvent(new WindowEvent(Game.getInstance().getjFrame(), WindowEvent.WINDOW_CLOSING));
        }
    }
}
