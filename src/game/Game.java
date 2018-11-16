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
import game.ui.view.DicePanel;
import game.ui.view.MapPanel;
import game.ui.view.RightStatusPanel;
import game.ui.view.TopStatusPanel;

import java.util.List;
import java.util.Map;
import java.util.Random;

import static game.model.enums.CardsEnum.ARTILLERY;
import static game.model.enums.CardsEnum.CAVALRY;
import static game.model.enums.CardsEnum.INFANTRY;
import static game.model.enums.CardsEnum.WILDCARDS;
import static game.strategies.GamePhaseStrategies.GamePhaseEnum.ATTACK;
import static game.strategies.GamePhaseStrategies.GamePhaseEnum.FORTIFICATION;
import static game.strategies.GamePhaseStrategies.GamePhaseEnum.GAME_OVER;
import static game.strategies.GamePhaseStrategies.GamePhaseEnum.PLACING_ARMIES;
import static game.strategies.GamePhaseStrategies.GamePhaseEnum.REINFORCEMENT;

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
     * Get the number of reinforcements armies
     *
     * @param player    current player
     * @param countries countries of player
     * @return int number of reinforcement armies
     */
    public static int getReinforcementArmies(Player player, List<Country> countries) {
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
     * Initialize the game
     */
    public void initialise() {
        // initial setup.
        gameState.setGamePhaseStrategy(GamePhaseStrategyFactory.getStrategy(PLACING_ARMIES));
        gameState.getGamePhaseStrategy().init(gameState);
    }

    /**
     * Next turn functionality
     */
    public void nextTurn() {
        gameState.getGamePhaseStrategy().nextTurnButton(gameState);

        switch (gameState.getCurrentGamePhase()) {

            case REINFORCEMENT:
                int cards = 0;
                for (Integer i : gameState.getCurrentPlayer().getCardsEnumIntegerMap().values()) {
                    cards += i;
                }

                if (cards >= 5) {
                    getGameState().setCurrentTurnPhraseText("The current player has more than 5 cards on hands. Players have to change them to armies.");

                    break;
                }
                // Prepare to next turn
                gameState.setCurrentGamePhase(ATTACK);
                getGameState().setCurrentTurnPhraseText("Select a Country to attack from.");
                System.out.println("Next Turn Button Clicked. Next Player is " + gameState.getCurrentGamePhase());
                unHighlightCountries();

                if (!isMoreAttacks()) {
                    nextTurn();
                }
                break;

            case ATTACK:

                unHighlightCountries();

                if (gameState.isGiveACard()) {
                    CardsEnum[] cardsEnums = new CardsEnum[]{INFANTRY, CAVALRY, ARTILLERY, WILDCARDS};
                    Random r = new Random();
                    Map<CardsEnum, Integer> cardsEnumIntegerMap = gameState.getCurrentPlayer().getCardsEnumIntegerMap();
                    CardsEnum randomCard = cardsEnums[r.nextInt(cardsEnums.length)];

                    cardsEnumIntegerMap.put(randomCard, cardsEnumIntegerMap.get(randomCard) + 1);
                    gameState.setGiveACard(false);
                }
                gameState.setCurrentGamePhase(FORTIFICATION);
                getGameState().setCurrentTurnPhraseText("Select a country to move armies from. ");
                System.out.println("Next Turn Button Clicked. Next Player is " + gameState.getCurrentGamePhase());

                resetToAndFrom();
                highlightPayerCountries();
                break;

            case FORTIFICATION:
                gameState.setCurrentGamePhase(REINFORCEMENT);
                System.out.println("Next Turn Button Clicked. Next Phase is " + gameState.getCurrentGamePhase());

                resetToAndFrom();

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
                getGameState().setCurrentTurnPhraseText("Select a country to place your army. Armies to place  " + gameState.getCurrentPlayer().getArmies());
                highlightPayerCountries();
                break;
        }
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

        gameState.getGamePhaseStrategy().mapClick(gameState, x, y);

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
     * Method to highlight the player countries
     */
    private void highlightPayerCountries() {
        for (Country c : gameState.getCountries()) {
            if (c.getPlayer() == gameState.getCurrentPlayer()) {
                c.setHighlighted(true);
            }
        }
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
}