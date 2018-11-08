package game.ui.view;

import game.Game;
import game.IObservable;
import game.model.Continent;
import game.model.Country;
import game.model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * The status panel class. Descrive the properties of the right panel.
 * Displaying the player needed information. Such as bonus cards
 * Displaying the buttons to echange cards or go to next turn
 *
 * @author Dmitry Kryukov
 * @see Country
 * @see Player
 * @see Game
 */
public class RightStatusPanel extends JPanel implements IPanelObserver {
    private JButton nextTurnButton = new JButton("Next Turn");

    private JLabel countryName = new JLabel("", null, SwingConstants.TRAILING);
    private JLabel countryArmy = new JLabel("", null, SwingConstants.TRAILING);

    private JButton exchangeButton = new JButton("Exchange");
    private JLabel invisibleLable = new JLabel("invisible", null, SwingConstants.CENTER);

    private JPanel worldDomination = new JPanel();


    /**
     * Constructor of the class.
     * Create the panel and draw it in game main window
     *
     * @param width  of the panel
     * @param height of the panel
     */
    public RightStatusPanel(int width, int height) {
        this.setPreferredSize(new Dimension(width, height));
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(1, 0, 1, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        nextTurnButton.addActionListener(nextTurnButtonListner());
        nextTurnButton.setMargin(new Insets(5, 0, 5, 0));
        this.add(nextTurnButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(new JLabel("Domination:", null, SwingConstants.CENTER), gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        this.add(worldDomination, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.weighty = 0;
        this.add(new JLabel("Cards:", null, SwingConstants.CENTER), gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        this.add(new CardPanel(width, height), gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0;
        gbc.weighty = 0;
        this.add(new JLabel("Attack:", null, SwingConstants.CENTER), gbc);
        this.add(invisibleLable);
        invisibleLable.setVisible(false);

        Game.getInstance().attachObserver(this);
    }

    /**
     * Updater for observer
     *
     * @param iObservable
     */
    @Override
    public void updateObserver(IObservable iObservable) {
        Game game = Game.getInstance();

        countryName.setText(game.getCurrentCountry() != null ? game.getCurrentCountry().getName() : "");
        countryArmy.setText(game.getCurrentCountry() != null ? Integer.toString(game.getCurrentCountry().getArmy()) : "");
        nextTurnButton.setEnabled(game.isNextTurnButton());
        exchangeButton.setEnabled(game.isExchangeButton());

        createWoldDominationPanel(worldDomination);
        Random r = new Random();
        invisibleLable.setText(Integer.toString(r.nextInt()));
    }

    /**
     * Next button listener
     */
    public ActionListener nextTurnButtonListner() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Game.getInstance().nextTurn();
            }
        };
    }

    private void createWoldDominationPanel(JPanel jPanel) {
        Game game = Game.getInstance();
        jPanel.removeAll();
        jPanel.setLayout(new GridLayout(game.getPlayers().size() + 1, 4));

        Map<Player, Integer> playerNumberOfCountriesMap = new HashMap<>();
        Map<Player, Integer> playerNumberOfArmiesMap = new HashMap<>();
        for (Country country : game.getCountries()) {
            if (playerNumberOfCountriesMap.containsKey(country.getPlayer())) {
                playerNumberOfCountriesMap.put(country.getPlayer(), playerNumberOfCountriesMap.get(country.getPlayer()) + 1);
                playerNumberOfArmiesMap.put(country.getPlayer(), playerNumberOfArmiesMap.get(country.getPlayer()) + country.getArmy());
            } else {
                playerNumberOfCountriesMap.put(country.getPlayer(), 1);
                playerNumberOfArmiesMap.put(country.getPlayer(), country.getArmy());
            }
        }

        jPanel.add(new JLabel("Player"));
        jPanel.add(new JLabel("%"));
        jPanel.add(new JLabel("Armies #"));
        jPanel.add(new JLabel("Continents"));

        for (Player player : game.getPlayers()) {
            jPanel.add(new JLabel(player.getName()));
            jPanel.add(new JLabel(Integer.toString((int) (((float) playerNumberOfCountriesMap.get(player) / game.getCountries().size()) * 100))));
            jPanel.add(new JLabel(Integer.toString(playerNumberOfArmiesMap.get(player))));
            List<String> playerContinents = game.getContinents().stream().filter(c -> c.isOwnByPlayer(player)).map(Continent::getName).collect(Collectors.toList());
            jPanel.add(new JLabel(String.join(", ", playerContinents)));
        }
    }
}