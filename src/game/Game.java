package game;

import game.enums.DiceEnum;
import game.enums.GamePhase;
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
    public TopStatusPanel topStatusPanel;
    public MapPanel mapPanel;
    public RightStatusPanel rightStatusPanel;
    public JButton nextTurnButton;
    public DicePanel dicePanel;
    Map<Integer, GamePhase> gamePhaseMap = new HashMap<>();
    private Random RANDOM = new Random();
    private GamePhase currentGamePhase;
    private Player currentPlayer;
    private int armyToPlace = 10;
    private Map<Integer, DiceEnum> diceEnumMap = new HashMap<>();
    private DiceEnum[] redDice = new DiceEnum[DICE_ROW_TO_SHOW];
    private DiceEnum[] whiteDice = new DiceEnum[DICE_ROW_TO_SHOW];

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

        for (Country c : countries) {
            if (c.getPlayer() == currentPlayer) {
                c.setHighlited(true);
            }
        }

        topStatusPanel.setPlayer(currentPlayer);
        topStatusPanel.setGamePhase(currentGamePhase.getName());
        topStatusPanel.setTurnPhrase("Select a country to place your army.");

        nextTurnButton.setEnabled(false);

        refresh();
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


    public MouseAdapter getMouseAdapter() {
        return new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Point mouse = e.getPoint();
                System.out.println(" x = " + mouse.x + " y = " + mouse.y);
//                for (Country c : countries) {
//                    c.resetView();
//                }

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

//                GamePhase nextGamePhase = gamePhaseMap.get((currentGamePhase.getNumber() + 1) % gamePhaseMap.size());
//                System.out.println("Next Turn Button Clicked. Next phase is " + nextGamePhase.getName());
//                currentGamePhase = nextGamePhase;

                switch (currentGamePhase) {
                    case INITIAL_PLACING_ARMIES:

                        currentGamePhase = ATACKING;
                        System.out.println("Next Turn Button Clicked. Next Player is " + currentGamePhase);
                        break;

                    case PLACING_ARMIES:

                        currentGamePhase = ATACKING;
                        System.out.println("Next Turn Button Clicked. Next Player is " + currentGamePhase);
                        break;

                    case ATACKING:
                        for (Country c : countries) {
                            if (c.getPlayer() == currentPlayer) {
                                c.setHighlited(false);
                            }
                        }

                        // Emulate fights
                        for (Country c : countries) {
                            if (RANDOM.nextInt() > 0.8) {
                                c.setArmy(c.getArmy() - RANDOM.nextInt(c.getArmy()));
                            }
                        }

                        // Init Cards
                        for (Player player : players) {
                            player.setInfantry(RANDOM.nextInt(10));
                            player.setCavalry(RANDOM.nextInt(10));
                            player.setArtillery(RANDOM.nextInt(10));
                            player.setWildcards(RANDOM.nextInt(10));
                            player.setBonus(RANDOM.nextInt(10));
                        }

                        currentGamePhase = FORTIFYING;
                        System.out.println("Next Turn Button Clicked. Next Player is " + currentGamePhase);
                        break;

                    case FORTIFYING:

                        currentGamePhase = PLACING_ARMIES;
                        System.out.println("Next Turn Button Clicked. Next Player is " + currentGamePhase);

                        Player nextPlayer = players.get((players.indexOf(currentPlayer) + 1) % players.size());
                        currentPlayer = nextPlayer;
                        armyToPlace = 10;

                        for (Country country : countries) {
                            if (country.getPlayer() == currentPlayer) {
                                country.setHighlited(true);
                            }
                        }
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

                if (armyToPlace > 0 && country.getPlayer() == currentPlayer) {
                    country.setArmy(country.getArmy() + 1);

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

                    armyToPlace--;
                    topStatusPanel.setTurnPhrase("Armies to place " + armyToPlace);
                }
                if (armyToPlace <= 0) {
                    nextTurnButton.setEnabled(true);
                    topStatusPanel.setTurnPhrase("The turn is over. Press \"Next turn\" button.");
                    for (Country c : countries) {
                        if (c.getPlayer() == currentPlayer) {
                            c.setHighlited(false);
                        }
                    }
                }

                refresh();
                break;

            case PLACING_ARMIES:
                if (country.getPlayer() == currentPlayer) {
                    if (armyToPlace > 0) {
                        reset();

                        country.setArmy(country.getArmy() + 1);
                        armyToPlace--;
                        topStatusPanel.setTurnPhrase("Armies to place " + armyToPlace);
                        rightStatusPanel.setCountry(country);

                    } else {
                        for (Country c : countries) {
                            if (c.getPlayer() == currentPlayer) {
                                c.setHighlited(false);
                            }
                        }
                    }
                    refresh();
                }
                break;

            case ATACKING:
                rollDice();
                refresh();
                break;

            case FORTIFYING:

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

    private void nextPhase() {
        GamePhase nextGamePhase = gamePhaseMap.get((currentGamePhase.getNumber() + 1) % gamePhaseMap.size());
        if (nextGamePhase == INITIAL_PLACING_ARMIES) {
            nextGamePhase = PLACING_ARMIES;
        }
        System.out.println("Next Turn Button Clicked. Next phase is " + nextGamePhase.getName());
        currentGamePhase = nextGamePhase;
    }
}