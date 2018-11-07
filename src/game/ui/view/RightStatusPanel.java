package game.ui.view;

import game.Game;
import game.model.Country;
import game.model.IModelObservable;
import game.model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The status panel class. Descrive the properties of the right panel.
 * Displaying the player needed information. Such as bonus cards
 * Displaying the buttons to echange cards or go to next turn
 * @author Dmitry Kryukov
 * @see Country
 * @see Player
 * @see Game
 */
public class RightStatusPanel extends JPanel implements IPanelObserver {
    private JButton nextTurnButton = new JButton("Next Turn");

    private JLabel countryName = new JLabel("", null, SwingConstants.TRAILING);
    private JLabel countryArmy = new JLabel("", null, SwingConstants.TRAILING);

    private JLabel playerInfantry = new JLabel("", null, SwingConstants.TRAILING);
    private JLabel playerCavalry = new JLabel("", null, SwingConstants.TRAILING);
    private JLabel playerArtillery = new JLabel("", null, SwingConstants.TRAILING);
    private JLabel playerWildcards = new JLabel("", null, SwingConstants.TRAILING);
    private JLabel playerBonus = new JLabel("", null, SwingConstants.TRAILING);

    private JButton exchangeButton = new JButton("Exchange");

    private JPanel countryPanel = new JPanel();
//    private JPanel cardsPanel = new JPanel();

    /**
     * Constructor of the class.
     * Create the panel and draw it in game main window
     * @param width of the panel
     * @param height of the panel
     */
    public RightStatusPanel(int width, int height) {
        Game game = Game.getInstance();
        // setup overall setup
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
        this.add(new JLabel("Country:", null, SwingConstants.CENTER), gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        this.add(countryPanel, gbc);
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
//        exchangeButton.addActionListener(exchangeButtonListner());
//        exchangeButton.setMargin(new Insets(10, 0, 10, 0));
//        this.add(exchangeButton, gbc);

        // setup Country panel
        countryPanel.setMaximumSize(new Dimension(width, height));
        countryPanel.setLayout(new GridLayout(2, 2));
        countryPanel.setBackground(new Color(121, 180, 115));
        countryPanel.add(new JLabel("Name:", null, SwingConstants.TRAILING));
        countryPanel.add(countryName);
        countryPanel.add(new JLabel("Army:", null, SwingConstants.TRAILING));
        countryPanel.add(countryArmy);


        Game.getInstance().attachObserver(this);
    }

    /**
     * Updater for observer
     * @param iModelObservable
     */
    @Override
    public void updateObserver(IModelObservable iModelObservable) {
        Game game = Game.getInstance();

        countryName.setText(game.getCurrentCountry() != null ? game.getCurrentCountry().getName() : "");
        countryArmy.setText(game.getCurrentCountry() != null ? Integer.toString(game.getCurrentCountry().getArmy()) : "");

        nextTurnButton.setEnabled(game.isNextTurnButton());
        exchangeButton.setEnabled(game.isExchangeButton());
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
}