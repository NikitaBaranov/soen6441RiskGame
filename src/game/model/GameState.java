package game.model;

import game.IObservable;
import game.enums.DiceEnum;
import game.enums.GamePhaseEnum;
import game.strategies.StrategiesFactory;
import game.ui.view.IPanelObserver;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GameState implements IObservable {

    private final int ARMIES_TO_EXCHANGE_INCREASE = 5;
    private final int DICE_ROW_TO_SHOW = 3;
    // Strategies
    StrategiesFactory strategiesFactory = new StrategiesFactory();
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
}
