package game;

import game.enums.CardsEnum;
import game.enums.DiceEnum;
import game.enums.GamePhaseEnum;
import game.model.Continent;
import game.model.Country;
import game.model.Dice;
import game.model.GameState;
import game.model.Neighbour;
import game.model.Player;
import game.ui.view.DicePanel;
import game.ui.view.MapPanel;
import game.ui.view.RightStatusPanel;
import game.ui.view.TopStatusPanel;

import java.util.List;
import java.util.Map;
import java.util.Random;

import static game.enums.CardsEnum.ARTILLERY;
import static game.enums.CardsEnum.CAVALRY;
import static game.enums.CardsEnum.INFANTRY;
import static game.enums.CardsEnum.WILDCARDS;
import static game.enums.GamePhaseEnum.ATTACK;
import static game.enums.GamePhaseEnum.FORTIFICATION;
import static game.enums.GamePhaseEnum.GAME_OVER;
import static game.enums.GamePhaseEnum.PLACING_ARMIES;
import static game.enums.GamePhaseEnum.REINFORCEMENT;

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

    // Strategies
//    StrategiesFactory strategiesFactory = new StrategiesFactory();

    //    private List<Player> players;
    //    private List<Neighbour> neighbours;
    //    private List<Country> countries;
//    private Player gameState.getCurrentPlayer();

//    private String currentTurnPhraseText;
//    private Country currentCountry;

//    private final int DICE_ROW_TO_SHOW = 3;
//    private DiceEnum[] redDice = new DiceEnum[DICE_ROW_TO_SHOW];
//    private DiceEnum[] whiteDice = new DiceEnum[DICE_ROW_TO_SHOW];

    // Reinforcement
//    private int armiesToCardExchange = 5;

    // Attack
//    private int numberOfRedDicesSelected;
//    private int numberOfWhiteDicesSelected;
//    private boolean winBattle;
//    private int minArmiesToMoveAfterWin;
//    private boolean giveACard;

    // Fortification
//    private Country countryFrom;
//    private Country countryTo;
//    private List<Continent> continents;

    // Observers
//    private List<IPanelObserver> iPanelObservers = new ArrayList<>();

//    private GamePhaseEnum currentGamePhase;

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
        gameState.setCurrentGamePhase(PLACING_ARMIES);
        gameState.setCurrentPlayer(gameState.getPlayers().get(0));
        gameState.setNextTurnButton(false);
        Dice.resetDice(gameState.getRedDice(), gameState.getWhiteDice());
        highlightPayerCountries();

        gameState.notifyObservers();
    }

//    /**
//     * attach observer
//     *
//     * @param iPanelObserver
//     */
//    @Override
//    public void attachObserver(IPanelObserver iPanelObserver) {
//        iPanelObservers.add(iPanelObserver);
//    }
//
//    /**
//     * Detach observer
//     *
//     * @param iPanelObserver
//     */
//    @Override
//    public void detachObserver(IPanelObserver iPanelObserver) {
//        iPanelObservers.remove(iPanelObserver);
//    }
//
//    /**
//     * Notify observers
//     */
//    @Override
//    public void notifyObservers() {
//        IPanelObserver[] iPanelObserversArray = iPanelObservers.toArray(new IPanelObserver[0]);
//        for (int i = 0; i < iPanelObserversArray.length; i++) {
//            iPanelObserversArray[i].updateObserver(this);
//        }
//    }

    /**
     * Next turn functionality
     */
    public void nextTurn() {
        switch (gameState.getCurrentGamePhase()) {
            case PLACING_ARMIES:

                gameState.setCurrentGamePhase(ATTACK);
                getGameState().setCurrentTurnPhraseText("Select a Country to attack from.");
                System.out.println("Next Turn Button Clicked. Next Player is " + gameState.getCurrentGamePhase());
                break;

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
//                    CardsEnum[] cardsEnums = new CardsEnum[]{INFANTRY, CAVALRY, ARTILLERY, WILDCARDS, BONUS};
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

//                Dice.resetDice(redDice, whiteDice);

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

        // Example of Strategy in use
//        IStrategy iStrategy = strategiesFactory.getStrategy(StrategyEnum.HUMAN_STRATEGY);
//        iStrategy.placeArmies();
//        iStrategy.reinforce();
//        iStrategy.attack();
//        iStrategy.fortify();
//
//        iStrategy = strategiesFactory.getStrategy(StrategyEnum.AI_AGGRESSIVE_STRATEGY);
//        iStrategy.placeArmies();
//        iStrategy.reinforce();
//        iStrategy.attack();
//        iStrategy.fortify();
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

        switch (gameState.getCurrentGamePhase()) {
            case PLACING_ARMIES:
                if (gameState.getCurrentCountry() != null) {
                    if (gameState.getCurrentPlayer().getArmies() > 0 && gameState.getCurrentCountry().getPlayer() == gameState.getCurrentPlayer()) {
                        gameState.getCurrentCountry().setArmy(gameState.getCurrentCountry().getArmy() + 1);
                        gameState.getCurrentPlayer().setArmies(gameState.getCurrentPlayer().getArmies() - 1);

                        Player nextPlayer = gameState.getPlayers().get((gameState.getPlayers().indexOf(gameState.getCurrentPlayer()) + 1) % gameState.getPlayers().size());
                        System.out.println("Next Turn Button Clicked. Next Player is " + nextPlayer.getName());
                        gameState.setCurrentPlayer(nextPlayer);

                        for (Country c : gameState.getCountries()) {
                            if (c.getPlayer() == gameState.getCurrentPlayer()) {
                                c.setHighlighted(true);
                            } else {
                                c.setHighlighted(false);
                            }
                        }
                        getGameState().setCurrentTurnPhraseText("Select a country to place your army. Armies to place  " + gameState.getCurrentPlayer().getArmies());
                    }
                    if (gameState.getCurrentPlayer().getArmies() <= 0) {
                        gameState.setNextTurnButton(true);
                        getGameState().setCurrentTurnPhraseText("The turn is over. Press \"Next turn\" button.");
                        unHighlightCountries();
                    }
                }
                break;

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
                        gameState.getCurrentPlayer().fortification();
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

//    /**
//     * Set radius method
//     *
//     * @param RADIUS
//     */
//    public void setRADIUS(int RADIUS) {
//        this.RADIUS = RADIUS;
//    }

//    /**
//     * Methods return the list of countries
//     *
//     * @return countries List of countries
//     */
//    public List<Country> getCountries() {
//        return countries;
//    }

//    /**
//     * Setter for countries.
//     *
//     * @param countries List of countries
//     */
//    public void setCountries(List<Country> countries) {
//        this.countries = countries;
//    }

//    /**
//     * Method return the list of connections for country
//     *
//     * @return neighbours List of connections
//     */
//    public List<Neighbour> getNeighbours() {
//        return neighbours;
//    }

//    /**
//     * Setter for connections
//     *
//     * @param neighbours List of neighbours
//     */
//    public void setNeighbours(List<Neighbour> neighbours) {
//        this.neighbours = neighbours;
//    }

//    /**
//     * get current phase of the game
//     *
//     * @return currentGamePhase
//     */
//    public GamePhaseEnum getCurrentGamePhase() {
//        return currentGamePhase;
//    }

//    /**
//     * Get current player
//     *
//     * @return gameState.getCurrentPlayer()
//     */
//    public Player getCurrentPlayer() {
//        return currentPlayer;
//    }

//    /**
//     * Get current status phrase
//     *
//     * @return currentTurnPhraseText
//     */
//    public String getCurrentTurnPhraseText() {
//        return currentTurnPhraseText;
//    }

//    /**
//     * Set current status phrase text
//     *
//     * @param currentTurnPhraseText
//     */
//    public void setCurrentTurnPhraseText(String currentTurnPhraseText) {
//        this.currentTurnPhraseText = currentTurnPhraseText;
//    }

//    /**
//     * Get current country
//     *
//     * @return currentCountry
//     */
//    public Country getCurrentCountry() {
//        return currentCountry;
//    }

//    /**
//     * set current country
//     *
//     * @param currentCountry
//     */
//    public void setCurrentCountry(Country currentCountry) {
//        this.currentCountry = currentCountry;
//    }

//    /**
//     * get red dice
//     *
//     * @return redDice
//     */
//    public DiceEnum[] getRedDice() {
//        return redDice;
//    }

//    /**
//     * get white dice
//     *
//     * @return whiteDice
//     */
//    public DiceEnum[] getWhiteDice() {
//        return whiteDice;
//    }

//    /**
//     * Next turn button
//     *
//     * @return nextTurnButton
//     */
//    public boolean isNextTurnButton() {
//        return nextTurnButton;
//    }

//    /**
//     * get country from
//     *
//     * @return countryFrom
//     */
//    public Country getCountryFrom() {
//        return countryFrom;
//    }
//
//    /**
//     * Set country from
//     *
//     * @param countryFrom
//     */
//    public void setCountryFrom(Country countryFrom) {
//        this.countryFrom = countryFrom;
//    }
//
//    /**
//     * Get country to
//     *
//     * @return countryTo
//     */
//    public Country getCountryTo() {
//        return countryTo;
//    }
//
//    /**
//     * set country to
//     *
//     * @param countryTo
//     */
//    public void setCountryTo(Country countryTo) {
//        this.countryTo = countryTo;
//    }

//    /**
//     * Get number of red dices that were selected
//     *
//     * @return numberOfRedDicesSelected
//     */
//    public int getNumberOfRedDicesSelected() {
//        return numberOfRedDicesSelected;
//    }
//
//    /**
//     * Set number of red dices that were selected
//     *
//     * @param numberOfRedDicesSelected
//     */
//    public void setNumberOfRedDicesSelected(int numberOfRedDicesSelected) {
//        this.numberOfRedDicesSelected = numberOfRedDicesSelected;
//    }
//
//    /**
//     * Get number of white dices that were selected
//     *
//     * @return numberOfWhiteDicesSelected
//     */
//    public int getNumberOfWhiteDicesSelected() {
//        return numberOfWhiteDicesSelected;
//    }
//
//    /**
//     * Set number of white dices that were selected
//     *
//     * @param numberOfWhiteDicesSelected
//     */
//    public void setNumberOfWhiteDicesSelected(int numberOfWhiteDicesSelected) {
//        this.numberOfWhiteDicesSelected = numberOfWhiteDicesSelected;
//    }

//    /**
//     * Winner of the battle
//     *
//     * @return winBattle
//     */
//    public boolean isWinBattle() {
//        return winBattle;
//    }
//
//    /**
//     * Set the winner of the battle
//     *
//     * @param winBattle
//     */
//    public void setWinBattle(boolean winBattle) {
//        this.winBattle = winBattle;
//    }

//    /**
//     * Get the minimum armies that player can move to the country after winning
//     *
//     * @return minArmiesToMoveAfterWin
//     */
//    public int getMinArmiesToMoveAfterWin() {
//        return minArmiesToMoveAfterWin;
//    }
//
//    /**
//     * Set the minimum number of armies that player can move to the country after winning
//     *
//     * @param minArmiesToMoveAfterWin
//     */
//    public void setMinArmiesToMoveAfterWin(int minArmiesToMoveAfterWin) {
//        this.minArmiesToMoveAfterWin = minArmiesToMoveAfterWin;
//    }

//    /**
//     * Get armies to card exchange
//     *
//     * @return armiesToCardExchange
//     */
//    public int getArmiesToCardExchange() {
//        return armiesToCardExchange;
//    }

//    /**
//     * Set armies to card exchange
//     *
//     * @param armiesToCardExchange
//     */
//    public void setArmiesToCardExchange(int armiesToCardExchange) {
//        this.armiesToCardExchange = armiesToCardExchange;
//    }

//    /**
//     * Set give card
//     *
//     * @param giveACard
//     */
//    public void setGiveACard(boolean giveACard) {
//        this.giveACard = giveACard;
//    }

//    public List<Player> getPlayers() {
//        return players;
//    }

//    /**
//     * Set players
//     *
//     * @param players
//     */
//    public void setPlayers(List<Player> players) {
//        this.players = players;
//    }

//    public List<Continent> getContinents() {
//        return continents;
//    }

//    /**
//     * Set continents
//     *
//     * @param continents
//     */
//    public void setContinents(List<Continent> continents) {
//        this.continents = continents;
//    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}