package game.model;

import game.model.enums.CardsEnum;
import game.model.enums.DiceEnum;
import game.strategies.GamePhaseStrategies.GamePhaseEnum;
import game.ui.view.IPanelObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Game state class. Observable that get all states and update all observers.
 * @author Dmitry Kryukov, Ksenia Popova
 * @see IObservable
 */
public class GameState implements IObservable {

    private final int ARMIES_TO_EXCHANGE_INCREASE = 5;
    private final int DICE_ROW_TO_SHOW = 3;

    private boolean nextTurnButton;
    private List<Country> countries;
    private List<Neighbour> neighbours;
    private List<Player> players;
    private Player currentPlayer;
    private String currentTurnPhraseText;
    private Country currentCountry;

    private DiceEnum[] redDice = new DiceEnum[DICE_ROW_TO_SHOW];
    private DiceEnum[] whiteDice = new DiceEnum[DICE_ROW_TO_SHOW];

    // Reinforcement
    private int armiesToCardExchange = 5;
    private List<CardsEnum> selectedCardsToExchange;

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

    // Observers
    private List<IPanelObserver> iPanelObservers = new ArrayList<>();
    private GamePhaseEnum currentGamePhase;


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
        IPanelObserver[] iPanelObserversArray = iPanelObservers.toArray(new IPanelObserver[0]);
        for (int i = 0; i < iPanelObserversArray.length; i++) {
            iPanelObserversArray[i].updateObserver(this);
        }
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
     * get current phase of the game
     *
     * @return currentGamePhase
     */
    public GamePhaseEnum getCurrentGamePhase() {
        return currentGamePhase;
    }

    public void setCurrentGamePhase(GamePhaseEnum currentGamePhase) {
        this.currentGamePhase = currentGamePhase;
    }

    /**
     * Get current player
     *
     * @return gameState.getCurrentPlayer()
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
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

    public void setRedDice(DiceEnum[] redDice) {
        this.redDice = redDice;
    }

    /**
     * get white dice
     *
     * @return whiteDice
     */
    public DiceEnum[] getWhiteDice() {
        return whiteDice;
    }

    public void setWhiteDice(DiceEnum[] whiteDice) {
        this.whiteDice = whiteDice;
    }

    /**
     * Next turn button
     *
     * @return nextTurnButton
     */
    public boolean isNextTurnButton() {
        return nextTurnButton;
    }

    public void setNextTurnButton(boolean nextTurnButton) {
        this.nextTurnButton = nextTurnButton;
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
     *
     * @return winBattle
     */
    public boolean isWinBattle() {
        return winBattle;
    }

    /**
     * Set the winner of the battle
     *
     * @param winBattle
     */
    public void setWinBattle(boolean winBattle) {
        this.winBattle = winBattle;
    }

    /**
     * Get the minimum armies that player can move to the country after winning
     *
     * @return minArmiesToMoveAfterWin
     */
    public int getMinArmiesToMoveAfterWin() {
        return minArmiesToMoveAfterWin;
    }

    /**
     * Set the minimum number of armies that player can move to the country after winning
     *
     * @param minArmiesToMoveAfterWin
     */
    public void setMinArmiesToMoveAfterWin(int minArmiesToMoveAfterWin) {
        this.minArmiesToMoveAfterWin = minArmiesToMoveAfterWin;
    }

    /**
     * Get armies to card exchange
     *
     * @return armiesToCardExchange
     */
    public int getArmiesToCardExchange() {
        return armiesToCardExchange;
    }

    /**
     * Set armies to card exchange
     *
     * @param armiesToCardExchange
     */
    public void setArmiesToCardExchange(int armiesToCardExchange) {
        this.armiesToCardExchange = armiesToCardExchange;
    }

    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Set players
     *
     * @param players
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Continent> getContinents() {
        return continents;
    }

    /**
     * Set continents
     *
     * @param continents
     */
    public void setContinents(List<Continent> continents) {
        this.continents = continents;
    }

    public int getARMIES_TO_EXCHANGE_INCREASE() {
        return ARMIES_TO_EXCHANGE_INCREASE;
    }

    public boolean isGiveACard() {
        return giveACard;
    }

    /**
     * Set give card
     *
     * @param giveACard
     */
    public void setGiveACard(boolean giveACard) {
        this.giveACard = giveACard;
    }

    /**
     * Getter of selected cards to exchange.
     * @return selectedCardsToExcnahge
     */
    public List<CardsEnum> getSelectedCardsToExchange() {
        return selectedCardsToExchange;
    }

    /**
     * Setter for selected cards to excnahge.
     * @param selectedCardsToExchange
     */
    public void setSelectedCardsToExchange(List<CardsEnum> selectedCardsToExchange) {
        this.selectedCardsToExchange = selectedCardsToExchange;
    }
}
