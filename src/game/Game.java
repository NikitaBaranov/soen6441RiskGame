package game;

import game.enums.DiceEnum;
import game.enums.GamePhase;
import game.model.Country;
import game.model.Neighbour;
import game.model.Player;
import game.ui.view.DicePanel;
import game.ui.view.RightStatusPanel;
import game.ui.view.TopStatusPanel;
import game.utils.MapLoader;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static game.enums.GamePhase.PLACING_ARMIES;

public class Game {
    public int RADIUS = MapLoader.RADIUS;
    public final int DICE_ROW_TO_SHOW = 3;

    public List<Country> countries = MapLoader.countries;
    public List<Neighbour> neighbours = MapLoader.neighbours;
    public List<Player> players = MapLoader.players;

    private GamePhase gamePhase = PLACING_ARMIES;
    private Player currentPlayer = players.get(0);
    private int armyToPlace = 10;

    private Map<Integer, DiceEnum> diceEnumMap = new HashMap<>();
    private DiceEnum[] redDice = new DiceEnum[DICE_ROW_TO_SHOW];
    private DiceEnum[] whiteDice = new DiceEnum[DICE_ROW_TO_SHOW];

    public TopStatusPanel topStatusPanel;
    public RightStatusPanel rightStatusPanel;
    public DicePanel dicePanel;

    public Game() {
        diceEnumMap.put(1, DiceEnum.ONE);
        diceEnumMap.put(2, DiceEnum.TWO);
        diceEnumMap.put(3, DiceEnum.THREE);
        diceEnumMap.put(4, DiceEnum.FOUR);
        diceEnumMap.put(5, DiceEnum.FIVE);
        diceEnumMap.put(6, DiceEnum.SIX);
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


    public MouseAdapter getMouseAdapter (){
        return new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Point mouse = e.getPoint();
//                System.out.print(" x = " + mouse.x + " y = " + mouse.y);

                for (Country country : countries) {
                    if (country.isInBorder(mouse.x, mouse.y) && country.getPlayer() == currentPlayer) {
                        makeAction(country);
//                        System.out.print(" selected " + country.getName());
                    }
                }
                e.getComponent().repaint();
//                System.out.println();
            }
        };
    }

    public void makeAction(Country country){
        switch (gamePhase){
            case PLACING_ARMIES:

                for (Country c : countries) {
                    c.resetView();
                }
                topStatusPanel.reset();
                rightStatusPanel.reset();

                country.setArmy(country.getArmy() + 1);
                armyToPlace--;

                if(armyToPlace <= 0){
                    int nextPlayerIndex = players.lastIndexOf(currentPlayer) + 1;
                    if(nextPlayerIndex < players.size()) {
                        currentPlayer = players.get(nextPlayerIndex);
                    } else {
                        currentPlayer = players.get(0);
                    }
                    armyToPlace = 10;
                    for (Country c : countries) {
                        c.resetView();
                    }
                }

                topStatusPanel.setPlayer(currentPlayer);
//                topStatusPanel.setGameState();
                topStatusPanel.setGamePhase(gamePhase.getName());
                topStatusPanel.setTurnPhrase("Armies to place " + armyToPlace);

                rightStatusPanel.setCountry(country);
                rightStatusPanel.setPlayer(currentPlayer);

                country.select();

                break;

            case ATACKING:
                rollDice();
                break;

            case FORTIFYING:
                System.out.println();
                break;
        }
    }

    private void rollDice(){
        Random r = new Random();

        for (int i = 0; i < DICE_ROW_TO_SHOW; i++) {
            redDice[i] = diceEnumMap.get(r.nextInt(6)+1);
            whiteDice[i] = diceEnumMap.get(r.nextInt(6)+1);
        }

        dicePanel.setDices(redDice, whiteDice);
    }
}
