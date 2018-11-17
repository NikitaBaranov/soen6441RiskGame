package game;

import game.model.Continent;
import game.model.Country;
import game.model.GameState;
import game.model.Neighbour;
import game.model.Player;
import game.model.enums.CardsEnum;
import game.model.enums.DiceEnum;
import game.strategies.GamePhaseStrategies.GamePhaseEnum;
import game.strategies.GamePhaseStrategies.GamePhaseStrategyFactory;
import game.strategies.GamePhaseStrategies.IGamePhaseStrategy;
import game.ui.view.DicePanel;
import game.ui.view.MapPanel;
import game.ui.view.RightStatusPanel;
import game.ui.view.TopStatusPanel;

import java.util.List;

import static game.strategies.GamePhaseStrategies.GamePhaseEnum.GAME_OVER;
import static game.strategies.GamePhaseStrategies.GamePhaseEnum.PLACING_ARMIES;

/**
 * The game file which control all the game flow.
 * i.e. Controller in the MVC arcthitecture model
 *
 * @author Dmitry kryukov, Ksenia Popova
 * @see DiceEnum
 * @see CardsEnum
 * @see GamePhaseEnum
 * @see Continent
 * @see Country
 * @see Player
 * @see Neighbour
 * @see DicePanel
 * @see MapPanel
 * @see RightStatusPanel
 * @see TopStatusPanel
 */
public class Game {

    private static Game gameInstance;
    private GameState gameState;
    private IGamePhaseStrategy gamePhaseStrategy;

    /**
     * get instance method for Controller
     *
     * @return gameInstance
     */
    public static Game getInstance() {
        if (gameInstance == null) {
            gameInstance = new Game();
        }
        return gameInstance;
    }

    /**
     * Initialize the game
     */
    public void initialise() {
        gamePhaseStrategy = GamePhaseStrategyFactory.getStrategy(PLACING_ARMIES);
        gamePhaseStrategy.init(gameState);
    }

    /**
     * Next turn functionality
     */
    public void nextTurn() {
        gamePhaseStrategy.nextTurnButton(gameState);
        gameState.notifyObservers();
    }

    /**
     * Reset highlights
     */
    public void resetToAndFrom() {
        unHighlightCountries();
        if (gameState.getCountryFrom() != null) {
            gameState.getCountryFrom().unSelect(false);
        }
        gameState.setCountryFrom(null);

        if (gameState.getCountryTo() != null) {
            gameState.getCountryTo().unSelect(false);
        }
        gameState.setCountryTo(null);
    }

    /**
     * Method describes the main flow. I.E. actions with the game.
     */
    public void makeAction(int x, int y) {
        gameState.setCurrentCountry(null);

        for (Country country : gameState.getCountries()) {
            if (country.isInBorder(x, y)) {
                gameState.setCurrentCountry(country);
                System.out.println("Selected " + country.getName());
                break;
            }
        }

        gamePhaseStrategy.mapClick(gameState, x, y);

        switch (gameState.getCurrentGamePhase()) {

            case REINFORCEMENT:
                if (gameState.getCurrentCountry() != null) {
                    if (gameState.getCurrentCountry().getPlayer() == gameState.getCurrentPlayer()) {
                        gameState.getCurrentPlayer().reinforcement();
                    }
                }
                break;

            case ATTACK:
                gameState.getCurrentPlayer().beforeAndAfterAttack();
                break;

            case FORTIFICATION:
                if (gameState.getCurrentCountry() != null) {

                    if (gameState.getCurrentCountry().getPlayer() == gameState.getCurrentPlayer()) {
                        gameState.getCurrentPlayer().fortify();
                    }
                }
                break;
        }
        gameState.notifyObservers();
    }

    /**
     * Exchange methods for exchanging cards for player.
     *
     * @param cardsEnumList
     */
    public void exchange(List<CardsEnum> cardsEnumList) {
        gameState.getCurrentPlayer().exchange(cardsEnumList);
        gameState.notifyObservers();
    }

    /**
     * Attack
     */
    public void attack() {
        gameState.getCurrentPlayer().attack();
        gameState.notifyObservers();
    }

    /**
     * Method that unhighlight the players countries
     */
    public void unHighlightCountries() {
        for (Country c : gameState.getCountries()) {
            c.setHighlighted(false);
        }
    }

    /**
     * Check if game was won by player
     *
     * @param player
     * @return boolean
     */
    public boolean isGameWonBy(Player player) {
        for (Country country : gameState.getCountries()) {
            if (country.getPlayer() != player) {
                return false;
            }
        }
        return true;
    }

    /**
     * Game over phase. Block any actions
     */
    public void gameOver() {
        gameState.setCurrentGamePhase(GAME_OVER);
        getGameState().setCurrentTurnPhraseText("Game over. The " + gameState.getCurrentPlayer().getName() + " win.");
        unHighlightCountries();
        gameState.setNextTurnButton(false);
        gameState.notifyObservers();
    }

    /**
     * Check if player can attack anybody or go to next turn
     *
     * @return
     */
    public boolean isMoreAttacks() {
        for (Country country : gameState.getCountries()) {
            if (country.getPlayer() == gameState.getCurrentPlayer() && country.getArmy() >= 2) {
                for (Country neighbor : country.getNeighbours()) {
                    if (neighbor.getPlayer() != gameState.getCurrentPlayer()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public IGamePhaseStrategy getGamePhaseStrategy() {
        return gamePhaseStrategy;
    }

    public void setGamePhaseStrategy(IGamePhaseStrategy gamePhaseStrategy) {
        this.gamePhaseStrategy = gamePhaseStrategy;
    }
}