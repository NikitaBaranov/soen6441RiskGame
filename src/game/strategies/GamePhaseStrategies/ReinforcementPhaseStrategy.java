package game.strategies.GamePhaseStrategies;

import game.Game;
import game.model.Continent;
import game.model.Country;
import game.model.GameState;
import game.model.Player;

import java.util.List;

import static game.strategies.GamePhaseStrategies.GamePhaseEnum.ATTACK;
import static game.strategies.GamePhaseStrategies.GamePhaseEnum.REINFORCEMENT;

public class ReinforcementPhaseStrategy extends AbstractGamePhaseStrategy {

    /**
     * Get the number of reinforcements armies
     *
     * @param player    current player
     * @param countries countries of player
     * @return int number of reinforcement armies
     */
    private static int getReinforcementArmies(Player player, List<Country> countries) {
        int countriesOwnedByPlayer = 0;
        for (Country country : countries) {
            if (country.getPlayer() == player) {
                countriesOwnedByPlayer++;
            }
        }
        System.out.println("Player " + player.getName() + " owns " + countriesOwnedByPlayer + " countries and  gets " + countriesOwnedByPlayer / 3 + " armies.");
        if ((player.getArmies() + countriesOwnedByPlayer / 3) < 3) return 3;
        else return player.getArmies() + countriesOwnedByPlayer / 3;
    }

    @Override
    public void init(GameState gameState) {
        gameState.setCurrentGamePhase(REINFORCEMENT);
        System.out.println("Next Turn Button Clicked. Next Phase is " + gameState.getCurrentGamePhase());

        resetToAndFrom(gameState);

        // Change current player
        gameState.setCurrentPlayer(gameState.getPlayers().get((gameState.getPlayers().indexOf(gameState.getCurrentPlayer()) + 1) % gameState.getPlayers().size()));
        System.out.println("Select next Player. Next Player is " + gameState.getCurrentPlayer().getName());

        // Add base armies
        gameState.getCurrentPlayer().setArmies(getReinforcementArmies(gameState.getCurrentPlayer(), gameState.getCountries()));

        // Add continent Bonus
        for (Continent continent : gameState.getContinents()) {
            if (continent.isOwnByOnePlayer()) {
                if (continent.getCountryList().get(0).getPlayer() == gameState.getCurrentPlayer()) {
                    gameState.getCurrentPlayer().setArmies(gameState.getCurrentPlayer().getArmies() + continent.getBonus());
                    System.out.println("Player " + gameState.getCurrentPlayer().getName() + " owns " + continent.getName() + " continent and  gets " + continent.getBonus() + " armies.");
                }
            }
        }
        gameState.setCurrentTurnPhraseText("Select a country to place your army. Armies to place  " + gameState.getCurrentPlayer().getArmies());
        highlightPayerCountries(gameState.getCountries(), gameState.getCurrentPlayer());
    }

    @Override
    public void mapClick(GameState gameState, int x, int y) {
        if (selectCountry(gameState, x, y)) {
            if (gameState.getCurrentCountry().getPlayer() == gameState.getCurrentPlayer()) {
                gameState.getCurrentPlayer().reinforcement(gameState);
            }
        }
    }

    @Override
    public void nextTurnButton(GameState gameState) {

        int cards = 0;
        for (Integer i : gameState.getCurrentPlayer().getCardsEnumIntegerMap().values()) {
            cards += i;
        }

        if (cards >= 5) {
            gameState.setCurrentTurnPhraseText("The current player has more than 5 cards on hands. Players have to change them to armies.");
        } else {
            Game.getInstance().setGamePhaseStrategy(GamePhaseStrategyFactory.getStrategy(ATTACK));
            Game.getInstance().getGamePhaseStrategy().init(gameState);
        }
    }

    @Override
    public void exchangeButton(GameState gameState) {
        gameState.getCurrentPlayer().exchange(gameState);
    }
}
