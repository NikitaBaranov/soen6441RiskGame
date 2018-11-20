package game.strategies.GamePhaseStrategies;

import game.Game;
import game.model.Dice;
import game.model.GameState;
import game.model.Player;

import static game.strategies.GamePhaseStrategies.GamePhaseEnum.ATTACK;
import static game.strategies.GamePhaseStrategies.GamePhaseEnum.PLACING_ARMIES;

public class PlacingArmiesPhaseStrategy extends BasePhaseStrategy {

    @Override
    public void init(GameState gameState) {
        gameState.setCurrentGamePhase(PLACING_ARMIES);
        gameState.setCurrentPlayer(gameState.getPlayers().get(0));
        gameState.setNextTurnButton(false);
        Dice.resetDice(gameState.getRedDice(), gameState.getWhiteDice());
        highlightPayerCountries(gameState.getCountries(), gameState.getCurrentPlayer());
    }

    @Override
    public void mapClick(GameState gameState, int x, int y) {
        if (selectCountry(gameState, x, y)) {
            unHighlightCountries(gameState.getCountries());
            if (gameState.getCurrentPlayer().getArmies() > 0 && gameState.getCurrentCountry().getPlayer() == gameState.getCurrentPlayer()) {
                gameState.getCurrentCountry().setArmy(gameState.getCurrentCountry().getArmy() + 1);
                gameState.getCurrentPlayer().setArmies(gameState.getCurrentPlayer().getArmies() - 1);

                Player nextPlayer = gameState.getPlayers().get((gameState.getPlayers().indexOf(gameState.getCurrentPlayer()) + 1) % gameState.getPlayers().size());
                System.out.println("Next Turn Button Clicked. Next Player is " + nextPlayer.getName());

                gameState.setCurrentTurnPhraseText("Select a country to place your army. Armies to place  " + gameState.getCurrentPlayer().getArmies());

                gameState.setCurrentPlayer(nextPlayer);
                highlightPayerCountries(gameState.getCountries(), gameState.getCurrentPlayer());
            }
            if (gameState.getCurrentPlayer().getArmies() <= 0) {
                gameState.setNextTurnButton(true);
                gameState.setCurrentTurnPhraseText("The turn is over. Press \"Next turn\" button.");
                unHighlightCountries(gameState.getCountries());
            }
        }
    }

    @Override
    public void nextTurnButton(GameState gameState) {
        Game.getInstance().setGamePhaseStrategy(GamePhaseStrategyFactory.getStrategy(ATTACK));
        Game.getInstance().getGamePhaseStrategy().init(gameState);
    }
}
