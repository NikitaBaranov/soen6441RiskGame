package game;

import game.enums.CardsEnum;
import game.enums.DiceEnum;
import game.enums.GamePhase;
import game.model.Continent;
import game.model.Country;
import game.model.Neighbour;
import game.model.Player;
import game.ui.view.DicePanel;
import game.ui.view.MapPanel;
import game.ui.view.RightStatusPanel;
import game.ui.view.TopStatusPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static game.enums.GamePhase.ATACKING;
import static game.enums.GamePhase.FORTIFYING;
import static game.enums.GamePhase.INITIAL_PLACING_ARMIES;
import static game.enums.GamePhase.PLACING_ARMIES;

/**
 * The game file which control all the game flow.
 * i.e. Controller in the MVC arcthitecture model
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
 *
 */
public class Game {
    public final int DICE_ROW_TO_SHOW = 3;
    public int RADIUS;
    public List<Country> countries;
    public List<Neighbour> neighbours;
    public List<Player> players;
    public List<Continent> continents;
    public TopStatusPanel topStatusPanel;
    public MapPanel mapPanel;
    public RightStatusPanel rightStatusPanel;
    public JButton nextTurnButton;
    public JButton exchangeButton;
    public DicePanel dicePanel;
    Map<Integer, GamePhase> gamePhaseMap = new HashMap<>();
    private int ARMIES_TO_EXCHANGE_INCREASE = 5;
    private Random RANDOM = new Random();
    private GamePhase currentGamePhase;
    private Player currentPlayer;
    private Map<Integer, DiceEnum> diceEnumMap = new HashMap<>();
    private DiceEnum[] redDice = new DiceEnum[DICE_ROW_TO_SHOW];
    private DiceEnum[] whiteDice = new DiceEnum[DICE_ROW_TO_SHOW];

    // Placing Armies
    private int armiesToCardExchange = 5;

    // Fortifying
    private Country countryFrom;
    private Country countryTo;

    /**
     * The constructor of the class.
     * Genrates the instance of the main object (game) with the game flow.
     * @param RADIUS radius of the default nodes
     * @param countries List of countries
     * @param neighbours List of connections
     * @param players List of players
     * @param continents List of continents
     */
    public Game(int RADIUS, List<Country> countries, List<Neighbour> neighbours, List<Player> players, List<Continent> continents) {
        this.RADIUS = RADIUS;
        this.countries = countries;
        this.neighbours = neighbours;
        this.players = players;
        this.continents = continents;

        // Setup Game Phases
        gamePhaseMap.put(0, INITIAL_PLACING_ARMIES);
        gamePhaseMap.put(1, GamePhase.PLACING_ARMIES);
        gamePhaseMap.put(2, GamePhase.ATACKING);
        gamePhaseMap.put(3, GamePhase.FORTIFYING);

        // Setup dice
        diceEnumMap.put(1, DiceEnum.ONE);
        diceEnumMap.put(2, DiceEnum.TWO);
        diceEnumMap.put(3, DiceEnum.THREE);
        diceEnumMap.put(4, DiceEnum.FOUR);
        diceEnumMap.put(5, DiceEnum.FIVE);
        diceEnumMap.put(6, DiceEnum.SIX);
    }

    /**
     * Initialize the game
     */
    public void initialise() {
        // initial setup.
        currentPlayer = players.get(0);
        currentGamePhase = INITIAL_PLACING_ARMIES;

        highlightPayerCountries();

        topStatusPanel.setPlayer(currentPlayer);
        topStatusPanel.setGamePhase(currentGamePhase.getName());
        topStatusPanel.setTurnPhrase("Select a country to place your army. Armies to place  " + currentPlayer.getArmies());

        nextTurnButton.setEnabled(false);
        exchangeButton.setEnabled(false);

        refresh();
    }


    /**
     * Mouse adapter to handle the mouse events
     */
    public MouseAdapter getMouseAdapter() {
        return new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Point mouse = e.getPoint();
//                System.out.println(" x = " + mouse.x + " y = " + mouse.y);
                for (Country country : countries) {
                    if (country.isInBorder(mouse.x, mouse.y)) {
                        makeAction(country);
                        System.out.println("Selected " + country.getName());
                    }
                }
            }
        };
    }

    /**
     * Get the number of reinforcements armies
     * @param player current player
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
        if ((player.getArmies() + countriesOwnedByPlayer /3 ) < 3 ) return 3;
        else return player.getArmies() + countriesOwnedByPlayer / 3;
    }

    /**
     * Next button listener
     */
    public ActionListener getNextTurnButtonListner() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reset();

                switch (currentGamePhase) {
                    case INITIAL_PLACING_ARMIES:

                        currentGamePhase = ATACKING;
                        topStatusPanel.setTurnPhrase("Attack phase is simulated. Press \"Next turn\" button.");
                        System.out.println("Next Turn Button Clicked. Next Player is " + currentGamePhase);
                        break;

                    case PLACING_ARMIES:
                        // Prepare to next turn
                        currentGamePhase = ATACKING;
                        topStatusPanel.setTurnPhrase("Attack phase is simulated. Press \"Next turn\" button.");
                        System.out.println("Next Turn Button Clicked. Next Player is " + currentGamePhase);
                        unHighlightPlayreCountries();
                        exchangeButton.setEnabled(false);
                        break;

                    case ATACKING:
                        unHighlightPlayreCountries();

                        // Emulate fights
//                        for (Country c : countries) {
//                            if (RANDOM.nextInt() > 0.8) {
//                                c.setArmy(c.getArmy() - RANDOM.nextInt(c.getArmy()));
//                            }
//                        }

                        // Init Cards
                        for (CardsEnum cardsEnum : CardsEnum.values()) {
                            currentPlayer.getCardsEnumIntegerMap()
                                    .put(cardsEnum, RANDOM.nextInt() > 0.5 ?
                                            currentPlayer.getCardsEnumIntegerMap().get(cardsEnum) + 1 :
                                            currentPlayer.getCardsEnumIntegerMap().get(cardsEnum));
                        }

                        // Prepare to next turn
                        currentGamePhase = FORTIFYING;
                        topStatusPanel.setTurnPhrase("Select a country to move armies from. ");
                        System.out.println("Next Turn Button Clicked. Next Player is " + currentGamePhase);
                        highlightPayerCountries();

                        break;

                    case FORTIFYING:
                        currentGamePhase = PLACING_ARMIES;
                        System.out.println("Next Turn Button Clicked. Next Player is " + currentGamePhase);

                        unHighlightPlayreCountries();
                        if (countryFrom != null) {
                            countryFrom.unSelect(false);
                        }
                        countryFrom = null;
                        if (countryTo != null) {
                            countryTo.unSelect(false);
                        }
                        countryTo = null;

                        // Change current player
                        Player nextPlayer = players.get((players.indexOf(currentPlayer) + 1) % players.size());
                        currentPlayer = nextPlayer;

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
                        exchangeButton.setEnabled(true);

                        topStatusPanel.setTurnPhrase("Select a country to place your army. Armies to place  " + currentPlayer.getArmies());
                        highlightPayerCountries();
                        break;
                }

                refresh();
            }
        };
    }


    /**
     * Method describes the main flow. I.E. actions with the game.
     * @param country object
     */
    public void makeAction(Country country) {
        switch (currentGamePhase) {
            case INITIAL_PLACING_ARMIES:
                reset();

                if (currentPlayer.getArmies() > 0 && country.getPlayer() == currentPlayer) {
                    country.setArmy(country.getArmy() + 1);
                    currentPlayer.setArmies(currentPlayer.getArmies() - 1);

                    Player nextPlayer = players.get((players.indexOf(currentPlayer) + 1) % players.size());
                    System.out.println("Next Turn Button Clicked. Next Player is " + nextPlayer.getName());
                    currentPlayer = nextPlayer;

                    for (Country c : countries) {
                        if (c.getPlayer() == currentPlayer) {
                            c.setHighlited(true);
                        } else {
                            c.setHighlited(false);
                        }
                    }

                    topStatusPanel.setTurnPhrase("Select a country to place your army. Armies to place  " + currentPlayer.getArmies());
                }
                if (currentPlayer.getArmies() <= 0) {
                    nextTurnButton.setEnabled(true);
                    topStatusPanel.setTurnPhrase("The turn is over. Press \"Next turn\" button.");
                    unHighlightPlayreCountries();
                }

                refresh();
                break;

            case PLACING_ARMIES:
                if (country.getPlayer() == currentPlayer) {
                    if (currentPlayer.getArmies() > 0) {
                        reset();

                        country.setArmy(country.getArmy() + 1);
                        currentPlayer.setArmies(currentPlayer.getArmies() - 1);
                        topStatusPanel.setTurnPhrase("Armies to place " + currentPlayer.getArmies());
                        rightStatusPanel.setCountry(country);

                    } else {
                        unHighlightPlayreCountries();
                    }
                    refresh();
                }
                break;

            case ATACKING:
                rollDice();
                refresh();
                break;

            case FORTIFYING:
                if (country.getPlayer() == currentPlayer) {
                    if (countryFrom == null) {
                        unHighlightPlayreCountries();
                        countryFrom = country;
                        topStatusPanel.setTurnPhrase("Select a country to move an army.");
                        country.select(false);
                    } else if (countryTo == null && country.isHighlited()) {
                        countryFrom.unSelect(false);
                        countryFrom.setSelected(true);
                        countryTo = country;
                        countryTo.setHighlited(true);
                        topStatusPanel.setTurnPhrase("Click on country to move one army.");
                    }
                    if (countryFrom != null && countryFrom.getArmy() > 1 && countryTo != null) {
                        countryFrom.setArmy(countryFrom.getArmy() - 1);
                        countryTo.setArmy(countryTo.getArmy() + 1);
                    }
                    refresh();
                }
                break;
        }
    }

    /**
     * Action handler for exchange button.
     */
    public ActionListener getExchangeListner() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switch (currentGamePhase) {
                    case PLACING_ARMIES:
                        // Change 3*1 cards
                        for (CardsEnum cardsEnum : CardsEnum.values()) {
                            if (currentPlayer.getCardsEnumIntegerMap().get(cardsEnum) >= 3) {
                                currentPlayer.getCardsEnumIntegerMap().put(cardsEnum, currentPlayer.getCardsEnumIntegerMap().get(cardsEnum) - 3);
                                currentPlayer.setArmies(currentPlayer.getArmies() + armiesToCardExchange);
                                armiesToCardExchange += ARMIES_TO_EXCHANGE_INCREASE;
                                topStatusPanel.setTurnPhrase("Armies to place " + currentPlayer.getArmies());
                                refresh();
                                break;
                            }
                        }

                        // Change 1*3 cards
                        int count = 0;
                        for (CardsEnum cardsEnum : CardsEnum.values()) {
                            if (currentPlayer.getCardsEnumIntegerMap().get(cardsEnum) >= 1) {
                                count++;
                            }
                        }
                        if (count >= 3) {
                            count = 3;
                            for (CardsEnum cardsEnum : CardsEnum.values()) {
                                if (count > 0 && currentPlayer.getCardsEnumIntegerMap().get(cardsEnum) > 0) {
                                    currentPlayer.getCardsEnumIntegerMap().put(cardsEnum, currentPlayer.getCardsEnumIntegerMap().get(cardsEnum) - 1);
                                    count--;
                                } else if (count == 0) {
                                    currentPlayer.setArmies(currentPlayer.getArmies() + armiesToCardExchange);
                                    armiesToCardExchange += ARMIES_TO_EXCHANGE_INCREASE;
                                    topStatusPanel.setTurnPhrase("Armies to place " + currentPlayer.getArmies());
                                    refresh();
                                    break;
                                }
                            }
                        }
                        refresh();
                        break;
                }
                refresh();
            }
        };
    }

    /**
     * Stub for the dice feature
     */
    private void rollDice() {
        for (int i = 0; i < DICE_ROW_TO_SHOW; i++) {
            redDice[i] = diceEnumMap.get(RANDOM.nextInt(6) + 1);
            whiteDice[i] = diceEnumMap.get(RANDOM.nextInt(6) + 1);
        }
        dicePanel.setDices(redDice, whiteDice);
    }

    /**
     * Method for refresh the graphics
     */
    private void refresh() {
        topStatusPanel.setPlayer(currentPlayer);
        topStatusPanel.setGamePhase(currentGamePhase.getName());
        rightStatusPanel.setPlayer(currentPlayer);
        topStatusPanel.repaint();
        rightStatusPanel.repaint();
        dicePanel.repaint();
        mapPanel.repaint();
    }

    /**
     * Method for resetting the panels
     */
    private void reset() {
        topStatusPanel.reset();
        mapPanel.repaint();
        rightStatusPanel.reset();
    }

    /**
     * Method to highlight the player countries
     */
    private void highlightPayerCountries() {
        for (Country c : countries) {
            if (c.getPlayer() == currentPlayer) {
                c.setHighlited(true);
            }
        }
    }

    /**
     * Method that unhighlight the players countries
     */
    private void unHighlightPlayreCountries() {
        for (Country c : countries) {
            if (c.getPlayer() == currentPlayer) {
                c.setHighlited(false);
            }
        }
    }

    /**
     * Method get the radius for nodes on graph
     * @return RADIUS of the nodes
     */
    public int getRADIUS() {
        return RADIUS;
    }

    /**
     * Methods return the list of countries
     * @return countries List of countries
     */
    public List<Country> getCountries() {
        return countries;
    }

    /**
     * Setter for countries.
     * @param countries List of countries
     */
    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    /**
     * Method return the list of connections for country
     * @return neighbours List of connections
     */
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    /**
     * Setter for connections
     * @param neighbours List of neighbours
     */
    public void setNeighbours(List<Neighbour> neighbours) {
        this.neighbours = neighbours;
    }
}