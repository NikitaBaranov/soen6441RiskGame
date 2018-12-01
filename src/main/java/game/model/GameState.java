package game.model;

import game.Game;
import game.model.enums.CardsEnum;
import game.model.enums.DiceEnum;
import game.strategies.GamePhaseStrategies.GamePhaseEnum;
import game.ui.view.IPanelObserver;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Game state class. Observable that get all states and update all observers.
 *
 * @author Dmitry Kryukov, Ksenia Popova
 * @see IObservable
 */
@Data
public class GameState implements IObservable, Serializable {

    // Need for serialisation
    private static final long serialVersionUID = 1L;

    private final int ARMIES_TO_EXCHANGE_INCREASE = 5;
    private final int DICE_ROW_TO_SHOW = 3;

    private String mapFilePath = "";

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
    transient private List<IPanelObserver> iPanelObservers = new ArrayList<>();

    private GamePhaseEnum currentGamePhase;

    private int maxNumberOfTurns = -1;
    private int currentTurnNumber = 1;
    private String result;
    private boolean turnament = false;

    // Save specific
    transient private boolean justLoad = false;

    /**
     * Attach observer
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
     * Set current status phrase text
     *
     * @param currentTurnPhraseText
     */
    public void setCurrentTurnPhraseText(String currentTurnPhraseText) {
        this.currentTurnPhraseText = currentTurnPhraseText;
        Game.getInstance().getNotification().setNotification(currentTurnPhraseText);
    }
}
