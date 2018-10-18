package game;

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
import game.utils.MapLoader;

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

public class Game {
    public final int DICE_ROW_TO_SHOW = 3;
    public int RADIUS = MapLoader.RADIUS;

    public List<Country> countries = MapLoader.countries;
    public List<Neighbour> neighbours = MapLoader.neighbours;
    public List<Player> players = MapLoader.players;
    public List<Continent> continents = MapLoader.continents;

    public TopStatusPanel topStatusPanel;
    public MapPanel mapPanel;
    public RightStatusPanel rightStatusPanel;
    public JButton nextTurnButton;
    public DicePanel dicePanel;
    Map<Integer, GamePhase> gamePhaseMap = new HashMap<>();
    private Random RANDOM = new Random();
    private GamePhase currentGamePhase;
    private Player currentPlayer;
    private Map<Integer, DiceEnum> diceEnumMap = new HashMap<>();
    private DiceEnum[] redDice = new DiceEnum[DICE_ROW_TO_SHOW];
    private DiceEnum[] whiteDice = new DiceEnum[DICE_ROW_TO_SHOW];

    // Fortifying
    private Country countryFrom;
    private Country countryTo;

    public Game() {
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

    public void initialise() {
        // initial setup.
        currentPlayer = players.get(0);
        currentGamePhase = INITIAL_PLACING_ARMIES;

        highlightPayerCountries();

        topStatusPanel.setPlayer(currentPlayer);
        topStatusPanel.setGamePhase(currentGamePhase.getName());
        topStatusPanel.setTurnPhrase("Select a country to place your army. Armies to place  " + currentPlayer.getArmies());

        nextTurnButton.setEnabled(false);

        refresh();
    }


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

    public ActionListener getNextTurnButton() {
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
                        for (Player player : players) {
                            player.setInfantry(RANDOM.nextInt() > 0.5 ? player.getInfantry() + 1 : player.getInfantry());
                            player.setCavalry(RANDOM.nextInt() > 0.5 ? player.getCavalry() + 1 : player.getCavalry());
                            player.setArtillery(RANDOM.nextInt() > 0.5 ? player.getArtillery() + 1 : player.getArtillery());
                            player.setWildcards(RANDOM.nextInt() > 0.5 ? player.getWildcards() + 1 : player.getWildcards());
                            player.setBonus(RANDOM.nextInt() > 0.5 ? player.getBonus() + 1 : player.getBonus());
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
                        int countriesOwnedByPlayer = 0;
                        for (Country country : countries) {
                            if (country.getPlayer() == currentPlayer) {
                                countriesOwnedByPlayer++;
                            }
                        }
                        // Integer always rounded down
                        System.out.println("Player " + currentPlayer.getName() + " owns " + countriesOwnedByPlayer + " countries and  gets " + countriesOwnedByPlayer / 3 + " armies.");
                        currentPlayer.setArmies(currentPlayer.getArmies() + countriesOwnedByPlayer / 3);

                        // Add continent Bonus
                        for (Continent continent : continents) {
                            if (continent.isOwnByOnePlayer()) {
                                if (continent.getCountryList().get(0).getPlayer() == currentPlayer) {
                                    currentPlayer.setArmies(currentPlayer.getArmies() + continent.getBonus());
                                    System.out.println("Player " + currentPlayer.getName() + " owns " + continent.getName() + " continent and  gets " + continent.getBonus() + " armies.");
                                }
                            }
                        }

                        topStatusPanel.setTurnPhrase("Select a country to place your army. Armies to place  " + currentPlayer.getArmies());
                        highlightPayerCountries();
                        break;
                }

                refresh();
            }
        };
    }


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

    private void rollDice() {
        for (int i = 0; i < DICE_ROW_TO_SHOW; i++) {
            redDice[i] = diceEnumMap.get(RANDOM.nextInt(6) + 1);
            whiteDice[i] = diceEnumMap.get(RANDOM.nextInt(6) + 1);
        }
        dicePanel.setDices(redDice, whiteDice);
    }

    private void refresh() {
        topStatusPanel.setPlayer(currentPlayer);
        topStatusPanel.setGamePhase(currentGamePhase.getName());
        mapPanel.repaint();
        rightStatusPanel.setPlayer(currentPlayer);
    }

    private void reset() {
        topStatusPanel.reset();
        mapPanel.repaint();
        rightStatusPanel.reset();
    }

    private void highlightPayerCountries() {
        for (Country c : countries) {
            if (c.getPlayer() == currentPlayer) {
                c.setHighlited(true);
            }
        }
    }

    private void unHighlightPlayreCountries() {
        for (Country c : countries) {
            if (c.getPlayer() == currentPlayer) {
                c.setHighlited(false);
            }
        }
    }

    public int getRADIUS() {
        return RADIUS;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(List<Neighbour> neighbours) {
        this.neighbours = neighbours;
    }
}