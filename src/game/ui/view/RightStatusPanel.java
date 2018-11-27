package game.ui.view;

import game.Game;
import game.model.Continent;
import game.model.Country;
import game.model.IObservable;
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
    private JLabel invisibleLabel = new JLabel("invisible", null, SwingConstants.CENTER);

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
        JLabel dominationLabel = new JLabel("Domination:", null, SwingConstants.CENTER);
        dominationLabel.setForeground(Color.YELLOW);
        this.add(dominationLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        this.add(worldDomination, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.weighty = 0;
        JLabel cardsLabel = new JLabel("Cards:", null, SwingConstants.CENTER);
        cardsLabel.setForeground(Color.YELLOW);
        this.add(cardsLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        this.add(new CardPanel(width, height), gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0;
        gbc.weighty = 0;
        JLabel attackLabel = new JLabel("Attack:", null, SwingConstants.CENTER);
        attackLabel.setForeground(Color.YELLOW);
        this.add(attackLabel, gbc);

        this.add(invisibleLabel);
        invisibleLabel.setVisible(false);

        Game.getInstance().getGameState().attachObserver(this);
    }

    /**
     * Updater for observer
     *
     * @param iObservable
     */
    @Override
    public void updateObserver(IObservable iObservable) {
        Game game = Game.getInstance();

        nextTurnButton.setEnabled(game.getGameState().isNextTurnButton());

        createWorldDominationPanel(worldDomination);

        Random r = new Random();
        invisibleLabel.setText(Integer.toString(r.nextInt()));
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

    /**
     * Domination of the world view
     * @param jPanel
     */
    private void createWorldDominationPanel(JPanel jPanel) {
        Game game = Game.getInstance();
        jPanel.removeAll();
        jPanel.setLayout(new GridLayout(game.getGameState().getPlayers().size() + 1, 4));

        Map<Player, Integer> playerNumberOfCountriesMap = new HashMap<>();
        Map<Player, Integer> playerNumberOfArmiesMap = new HashMap<>();
        for (Country country : game.getGameState().getCountries()) {
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

        for (Player player : game.getGameState().getPlayers()) {
            jPanel.add(new JLabel(player.getName()));
            if (playerNumberOfCountriesMap.containsKey(player)) {
                jPanel.add(new JLabel(Integer.toString((int) (((float) playerNumberOfCountriesMap.get(player) / game.getGameState().getCountries().size()) * 100))));
                jPanel.add(new JLabel(Integer.toString(playerNumberOfArmiesMap.get(player))));
            } else {
                jPanel.add(new JLabel("0"));
                jPanel.add(new JLabel("0"));
            }
            List<String> playerContinents = game.getGameState().getContinents().stream().filter(c -> c.isOwnByPlayer(player)).map(Continent::getName).collect(Collectors.toList());
            jPanel.add(new JLabel(String.join(", ", playerContinents)));
        }
    }
}