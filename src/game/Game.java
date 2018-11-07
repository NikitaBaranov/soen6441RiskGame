package game;

import game.enums.CardsEnum;
import game.enums.DiceEnum;
import game.enums.GamePhase;
import game.model.Continent;
import game.model.Country;
import game.model.Dice;
import game.model.IModelObservable;
import game.model.Neighbour;
import game.model.Player;
import game.ui.view.DicePanel;
import game.ui.view.IPanelObserver;
import game.ui.view.MapPanel;
import game.ui.view.RightStatusPanel;
import game.ui.view.TopStatusPanel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static game.enums.CardsEnum.ARTILLERY;
import static game.enums.CardsEnum.BONUS;
import static game.enums.CardsEnum.CAVALRY;
import static game.enums.CardsEnum.INFANTRY;
import static game.enums.CardsEnum.WILDCARDS;
import static game.enums.GamePhase.ATTACK;
import static game.enums.GamePhase.FORTIFICATION;
import static game.enums.GamePhase.PLACING_ARMIES;
import static game.enums.GamePhase.REINFORCEMENT;

/**
 * The game file which control all the game flow.
 * i.e. Controller in the MVC arcthitecture model
 *
 * @author Dmitry kryukov, Ksenia Popova
 * @see DiceEnum
 * @see CardsEnum
 * @see GamePhase
 * @see Continent
 * @see Country
 * @see Player
 * @see Neighbour
 * @see DicePanel
 * @see MapPanel
 * @see RightStatusPanel
 * @see TopStatusPanel
 */
public class Game implements IModelObservable {
    public static final int ARMIES_TO_EXCHANGE_INCREASE = 5;

    private static Game gameInstance;
    private final int DICE_ROW_TO_SHOW = 3;
    private boolean nextTurnButton;
    private boolean exchangeButton;
    private int RADIUS;
    private List<Country> countries;
    private List<Neighbour> neighbours;
    private List<Player> players;
    private GamePhase currentGamePhase;
    private Player currentPlayer;
    private String currentTurnPhraseText;
    private Country currentCountry;

    private DiceEnum[] redDice = new DiceEnum[DICE_ROW_TO_SHOW];
    private DiceEnum[] whiteDice = new DiceEnum[DICE_ROW_TO_SHOW];

    // Reinforcement
    private int armiesToCardExchange = 5;

    // Attack
    private int numberOfRedDicesSelected;
    private int numberOfWhiteDicesSelected;
    private boolean winBattle;
    private int minArmiesToMoveAfterWin;
    private boolean giveACard;

    // Fortification
    private Country countryFrom;
    private Country countryTo;
    private List<Continent> continents;
    private List<IPanelObserver> iPanelObservers = new ArrayList<>();

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
        currentGamePhase = PLACING_ARMIES;
        currentPlayer = players.get(0);
        nextTurnButton = false;
        exchangeButton = false;
        Dice.resetDice(redDice, whiteDice);
        highlightPayerCountries();

        notifyObservers();
    }

    /**
     * attach observer
     *
     * @param iPanelObserver
     */
    @Override
    public void attachObserver(IPanelObserver iPanelObserver) {
        iPanelObservers.add(iPanelObserver);
    }

    /**
     * Detach observer
     *
     * @param iPanelObserver
     */
    @Override
    public void detachObserver(IPanelObserver iPanelObserver) {
        iPanelObservers.remove(iPanelObserver);
    }

    /**
     * Notify observers
     */
    @Override
    public void notifyObservers() {
        iPanelObservers.stream().forEach(iPanelObserver -> iPanelObserver.updateObserver(this));
    }

    /**
     * Next turn functionality
     */
    public void nextTurn() {
        switch (currentGamePhase) {
            case PLACING_ARMIES:

                currentGamePhase = ATTACK;
                currentTurnPhraseText = "Attack phase is simulated. Press \"Next turn\" button.";
                System.out.println("Next Turn Button Clicked. Next Player is " + currentGamePhase);
                break;

            case REINFORCEMENT:
                // Prepare to next turn
                currentGamePhase = ATTACK;
                currentTurnPhraseText = "Attack phase is simulated. Press \"Next turn\" button.";
                System.out.println("Next Turn Button Clicked. Next Player is " + currentGamePhase);
                unHighlightCountries();
                exchangeButton = false;
                break;

            case ATTACK:

                unHighlightCountries();

                if (giveACard) {
                    CardsEnum[] cardsEnums = new CardsEnum[]{INFANTRY, CAVALRY, ARTILLERY, WILDCARDS, BONUS};
                    Random r = new Random();
                    Map<CardsEnum, Integer> cardsEnumIntegerMap = currentPlayer.getCardsEnumIntegerMap();
                    CardsEnum randomCard = cardsEnums[r.nextInt(cardsEnums.length)];

                    cardsEnumIntegerMap.put(randomCard, cardsEnumIntegerMap.get(randomCard) + 1);
                    giveACard = false;
                }
                currentGamePhase = FORTIFICATION;
                currentTurnPhraseText = "Select a country to move armies from. ";
                System.out.println("Next Turn Button Clicked. Next Player is " + currentGamePhase);

                Dice.resetDice(redDice, whiteDice);

                resetToAndFrom();

                highlightPayerCountries();
                break;

            case FORTIFICATION:
                currentGamePhase = REINFORCEMENT;
                System.out.println("Next Turn Button Clicked. Next Player is " + currentGamePhase);

                resetToAndFrom();

                // Change current player
                currentPlayer = players.get((players.indexOf(currentPlayer) + 1) % players.size());

                // Add base armies
                currentPlayer.setArmies(getReinforcementArmies(currentPlayer, countries));

                // Add continent Bonus
                for (Continent continent : continents) {
                    if (continent.isOwnByOnePlayer()) {
                        if (continent.getCountryList().get(0).getPlayer() == currentPlayer) {
                            currentPlayer.setArmies(currentPlayer.getArmies() + continent.getBonus());
                            System.out.println("Player " + currentPlayer.getName() + " owns " + continent.getName() + " continent and  gets " + continent.getBonus() + " armies.");
                        }
                    }
                }
                exchangeButton = true;
                currentTurnPhraseText = "Select a country to place your army. Armies to place  " + currentPlayer.getArmies();
                highlightPayerCountries();
                break;
        }
        notifyObservers();
    }

    /**
     * Reset highlights
     */
    public void resetToAndFrom() {
        unHighlightCountries();
        if (countryFrom != null) {
            countryFrom.unSelect(false);
        }
        countryFrom = null;

        if (countryTo != null) {
            countryTo.unSelect(false);
        }
        countryTo = null;
    }

    /**
     * Method describes the main flow. I.E. actions with the game.
     */
    public void makeAction(int x, int y) {
        setCurrentCountry(null);

        for (Country country : countries) {
            if (country.isInBorder(x, y)) {
                setCurrentCountry(country);
                System.out.println("Selected " + country.getName());
                break;
            }
        }

        switch (currentGamePhase) {
            case PLACING_ARMIES:
                if (currentCountry != null) {
                    if (currentPlayer.getArmies() > 0 && currentCountry.getPlayer() == currentPlayer) {
                        currentCountry.setArmy(currentCountry.getArmy() + 1);
                        currentPlayer.setArmies(currentPlayer.getArmies() - 1);

                        Player nextPlayer = players.get((players.indexOf(currentPlayer) + 1) % players.size());
                        System.out.println("Next Turn Button Clicked. Next Player is " + nextPlayer.getName());
                        currentPlayer = nextPlayer;

                        for (Country c : countries) {
                            if (c.getPlayer() == currentPlayer) {
                                c.setHighlighted(true);
                            } else {
                                c.setHighlighted(false);
                            }
                        }
                        currentTurnPhraseText = "Select a country to place your army. Armies to place  " + currentPlayer.getArmies();
                    }
                    if (currentPlayer.getArmies() <= 0) {
                        nextTurnButton = true;
                        currentTurnPhraseText = "The turn is over. Press \"Next turn\" button.";
                        unHighlightCountries();
                    }
                }
                break;

            case REINFORCEMENT:
                if (currentCountry != null) {
                    if (currentCountry.getPlayer() == currentPlayer) {
                        currentPlayer.reinforcement();
                    }
                }
                break;

            case ATTACK:
                currentPlayer.beforeAndAfterAttack();
                break;

            case FORTIFICATION:
                if (currentCountry != null) {

                    if (currentCountry.getPlayer() == currentPlayer) {
                        currentPlayer.fortification();
                    }
                }
                break;
        }
        notifyObservers();
    }

    /**
     * Exchange methods for exchanging cards for player.
     * @param cardsEnumList
     */
    public void exchange(List<CardsEnum> cardsEnumList) {
        currentPlayer.exchange(cardsEnumList);
        notifyObservers();
    }

    /**
     * Attack
     */
    public void attack() {
        currentPlayer.attack();
        notifyObservers();
    }

    /**
     * Method to highlight the player countries
     */
    private void highlightPayerCountries() {
        for (Country c : countries) {
            if (c.getPlayer() == currentPlayer) {
                c.setHighlighted(true);
            }
        }
    }

    /**
     * Method that unhighlight the players countries
     */
    public void unHighlightCountries() {
        for (Country c : countries) {
            c.setHighlighted(false);
        }
    }

    public void setRADIUS(int RADIUS) {
        this.RADIUS = RADIUS;
    }

    /**
     * Methods return the list of countries
     *
     * @return countries List of countries
     */
    public List<Country> getCountries() {
        return countries;
    }

    /**
     * Setter for countries.
     *
     * @param countries List of countries
     */
    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    /**
     * Method return the list of connections for country
     *
     * @return neighbours List of connections
     */
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    /**
     * Setter for connections
     *
     * @param neighbours List of neighbours
     */
    public void setNeighbours(List<Neighbour> neighbours) {
        this.neighbours = neighbours;
    }

    /**
     * Set players
     *
     * @param players
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     * Set continents
     *
     * @param continents
     */
    public void setContinents(List<Continent> continents) {
        this.continents = continents;
    }

    /**
     * get current phase of the game
     *
     * @return currentGamePhase
     */
    public GamePhase getCurrentGamePhase() {
        return currentGamePhase;
    }

    /**
     * Get current player
     *
     * @return currentPlayer
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }


    /**
     * Get current status phrase
     *
     * @return currentTurnPhraseText
     */
    public String getCurrentTurnPhraseText() {
        return currentTurnPhraseText;
    }

    /**
     * Set current status phrase text
     *
     * @param currentTurnPhraseText
     */
    public void setCurrentTurnPhraseText(String currentTurnPhraseText) {
        this.currentTurnPhraseText = currentTurnPhraseText;
    }

    /**
     * Get current country
     *
     * @return currentCountry
     */
    public Country getCurrentCountry() {
        return currentCountry;
    }

    /**
     * set current country
     *
     * @param currentCountry
     */
    public void setCurrentCountry(Country currentCountry) {
        this.currentCountry = currentCountry;
    }

    /**
     * get red dice
     *
     * @return redDice
     */
    public DiceEnum[] getRedDice() {
        return redDice;
    }

    /**
     * get white dice
     *
     * @return whiteDice
     */
    public DiceEnum[] getWhiteDice() {
        return whiteDice;
    }

    /**
     * Next turn button
     *
     * @return nextTurnButton
     */
    public boolean isNextTurnButton() {
        return nextTurnButton;
    }

    /**
     * Exchange button
     *
     * @return exchangeButton
     */
    public boolean isExchangeButton() {
        return exchangeButton;
    }

    /**
     * get country from
     *
     * @return countryFrom
     */
    public Country getCountryFrom() {
        return countryFrom;
    }

    /**
     * Set country from
     *
     * @param countryFrom
     */
    public void setCountryFrom(Country countryFrom) {
        this.countryFrom = countryFrom;
    }

    /**
     * Get country to
     *
     * @return countryTo
     */
    public Country getCountryTo() {
        return countryTo;
    }

    /**
     * set country to
     *
     * @param countryTo
     */
    public void setCountryTo(Country countryTo) {
        this.countryTo = countryTo;
    }

    /**
     * Get number of red dices that were selected
     *
     * @return numberOfRedDicesSelected
     */
    public int getNumberOfRedDicesSelected() {
        return numberOfRedDicesSelected;
    }

    /**
     * Set number of red dices that were selected
     *
     * @param numberOfRedDicesSelected
     */
    public void setNumberOfRedDicesSelected(int numberOfRedDicesSelected) {
        this.numberOfRedDicesSelected = numberOfRedDicesSelected;
    }

    /**
     * Get number of white dices that were selected
     *
     * @return numberOfWhiteDicesSelected
     */
    public int getNumberOfWhiteDicesSelected() {
        return numberOfWhiteDicesSelected;
    }

    /**
     * Set number of white dices that were selected
     *
     * @param numberOfWhiteDicesSelected
     */
    public void setNumberOfWhiteDicesSelected(int numberOfWhiteDicesSelected) {
        this.numberOfWhiteDicesSelected = numberOfWhiteDicesSelected;
    }

    /**
     * Winner of the battle
     * @return winBattle
     */
    public boolean isWinBattle() {
        return winBattle;
    }

    /**
     * Set the winner of the battle
     * @param winBattle
     */
    public void setWinBattle(boolean winBattle) {
        this.winBattle = winBattle;
    }

    /**
     * Get the minimum armies that player can move to the country after winning
     * @return minArmiesToMoveAfterWin
     */
    public int getMinArmiesToMoveAfterWin() {
        return minArmiesToMoveAfterWin;
    }

    /**
     * Set the minimum number of armies that player can move to the country after winning
     * @param minArmiesToMoveAfterWin
     */
    public void setMinArmiesToMoveAfterWin(int minArmiesToMoveAfterWin) {
        this.minArmiesToMoveAfterWin = minArmiesToMoveAfterWin;
    }

    /**
     * Get armies to card exchange
     * @return armiesToCardExchange
     */
    public int getArmiesToCardExchange() {
        return armiesToCardExchange;
    }

    /**
     * Set armies to card exchange
     * @param armiesToCardExchange
     */
    public void setArmiesToCardExchange(int armiesToCardExchange) {
        this.armiesToCardExchange = armiesToCardExchange;
    }

    /**
     * Set give card
     * @param giveACard
     */
    public void setGiveACard(boolean giveACard) {
        this.giveACard = giveACard;
    }
}