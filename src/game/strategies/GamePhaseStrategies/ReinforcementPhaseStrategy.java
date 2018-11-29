package game.strategies.GamePhaseStrategies;

import game.Game;
import game.model.Continent;
import game.model.Country;
import game.model.GameState;
import game.model.Player;

import java.util.List;

import static game.strategies.GamePhaseStrategies.GamePhaseEnum.ATTACK;
import static game.strategies.GamePhaseStrategies.GamePhaseEnum.GAME_OVER;
import static game.strategies.GamePhaseStrategies.GamePhaseEnum.REINFORCEMENT;
import static game.utils.MapFunctionsUtil.highlightPayerCountries;
import static game.utils.MapFunctionsUtil.selectCountry;

/**
 * Reinforcement phase strategy. Describes the reinforcement action.
 *
 * @author Dmitry Kryukov, Ksenia Popova
 * @see BasePhaseStrategy
 */
public class ReinforcementPhaseStrategy extends BasePhaseStrategy {

    /**
     * Get the number of reinforcements armies
     *
     * @param player    current player
     * @param countries countries of player
     * @return int number of reinforce armies
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

    /**
     * Initialization of the phase.
     * Setup required game states.
     * Show status messages
     * @param gameState
     */
    @Override
    public void init(GameState gameState) {
        super.init(gameState);

        gameState.setCurrentGamePhase(REINFORCEMENT);

        System.out.println("\n----------------------------------------------------------------------------\n");
        // Change current player
        do {
            int nextPlayerNumber = (gameState.getPlayers().indexOf(gameState.getCurrentPlayer()) + 1) % gameState.getPlayers().size();
            gameState.setCurrentPlayer(gameState.getPlayers().get(nextPlayerNumber));
            if (nextPlayerNumber == 0) {
                gameState.setCurrentTurnNumber(gameState.getCurrentTurnNumber() + 1);
                System.out.println("Turn " + gameState.getCurrentTurnNumber());
            }
        } while (gameState.getCurrentPlayer().isLost());

        if (gameState.getMaxNumberOfTurns() != -1 && gameState.getCurrentTurnNumber() > gameState.getMaxNumberOfTurns()) {
            Game.getInstance().setGamePhaseStrategy(GamePhaseStrategyFactory.getStrategy(GAME_OVER));
            Game.getInstance().getGamePhaseStrategy().init(gameState);
        } else {

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

            System.out.println("Select next Player. Next Player is " + gameState.getCurrentPlayer().getName());
            gameState.setCurrentTurnPhraseText("Select a country to place your army. Armies to place  " + gameState.getCurrentPlayer().getArmies());
            highlightPayerCountries(gameState.getCountries(), gameState.getCurrentPlayer());
            gameState.notifyObservers();

            debugMessage(gameState);

            if (gameState.getCurrentPlayer().isComputerPlayer()) {
                gameState.getCurrentPlayer().reinforce(gameState);
            }
        }
    }

    /**
     * Map click action behavior.
     * Go to next phase
     * @param gameState
     * @param x
     * @param y
     */
    @Override
    public void mapClick(GameState gameState, int x, int y) {
        if (!gameState.getCurrentPlayer().isComputerPlayer()) {
            if (selectCountry(gameState, x, y)) {
                if (gameState.getCurrentCountry().getPlayer() == gameState.getCurrentPlayer()) {
                    gameState.getCurrentPlayer().reinforce(gameState);
                }
            }
        }
    }

    /**
     * Next turn button action behavior. Set required game states.
     * Force next phase attack.
     * Doesn't allow to go to next turn if user has more than 5 cards to excnahge
     * @param gameState
     */
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

    /**
     * Exchange button behavior. Call the required game state function for exchange.
     * @param gameState
     */
    @Override
    public void exchangeButton(GameState gameState) {
        gameState.getCurrentPlayer().exchange(gameState);
    }
}
