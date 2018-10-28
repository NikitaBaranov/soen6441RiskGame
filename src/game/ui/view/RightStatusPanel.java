package game.ui.view;

import game.Game;
import game.model.Country;
import game.model.Player;

import javax.swing.*;
import java.awt.*;

import static game.enums.CardsEnum.ARTILLERY;
import static game.enums.CardsEnum.BONUS;
import static game.enums.CardsEnum.CAVALRY;
import static game.enums.CardsEnum.INFANTRY;
import static game.enums.CardsEnum.WILDCARDS;

/**
 * The status panel class. Descrive the properties of the right panel.
 * Displaying the player needed information. Such as bonus cards
 * Displaying the buttons to echange cards or go to next turn
 * @author Dmitry Kryukov
 * @see Country
 * @see Player
 * @see Game
 */
public class RightStatusPanel extends JPanel {
    private JButton nextButton = new JButton("Next Turn");

    private JLabel countryName = new JLabel("", null, SwingConstants.TRAILING);
    private JLabel countryArmy = new JLabel("", null, SwingConstants.TRAILING);

    private JLabel playerInfantry = new JLabel("", null, SwingConstants.TRAILING);
    private JLabel playerCavalry = new JLabel("", null, SwingConstants.TRAILING);
    private JLabel playerArtillery = new JLabel("", null, SwingConstants.TRAILING);
    private JLabel playerWildcards = new JLabel("", null, SwingConstants.TRAILING);
    private JLabel playerBonus = new JLabel("", null, SwingConstants.TRAILING);

    private JButton exchangeButton = new JButton("Exchange");

    private JPanel countryPanel = new JPanel();
    private JPanel cardsPanel = new JPanel();

    /**
     * Constructor of the class.
     * Create the panel and draw it in game main window
     * @param width of the panel
     * @param height of the panel
     * @param game instance of game controller
     */
    public RightStatusPanel(int width, int height) {
        Game game = Game.getInstance();
        // setup overall setup
        this.setPreferredSize(new Dimension(width, height));
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(4, 0, 4, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        nextButton.addActionListener(game.getNextTurnButtonListner());
        game.nextTurnButton = nextButton;
        nextButton.setMargin(new Insets(10, 0, 10, 0));
        this.add(nextButton, gbc);
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
        this.add(cardsPanel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0;
        gbc.weighty = 0;
        exchangeButton.addActionListener(game.getExchangeListner());
        game.exchangeButton = exchangeButton;
        exchangeButton.setMargin(new Insets(10, 0, 10, 0));
        this.add(exchangeButton, gbc);

        // setup Country panel
        countryPanel.setMaximumSize(new Dimension(width, height));
        countryPanel.setLayout(new GridLayout(2, 2));
        countryPanel.setBackground(new Color(121, 180, 115));
        countryPanel.add(new JLabel("Name:", null, SwingConstants.TRAILING));
        countryPanel.add(countryName);
        countryPanel.add(new JLabel("Army:", null, SwingConstants.TRAILING));
        countryPanel.add(countryArmy);

        // setup Country panel
        cardsPanel.setMaximumSize(new Dimension(width, height));
        cardsPanel.setLayout(new GridLayout(5, 2));
        cardsPanel.setBackground(new Color(121, 180, 115));
        cardsPanel.add(new JLabel(INFANTRY.getName() + ":", null, SwingConstants.TRAILING));
        cardsPanel.add(playerInfantry);
        cardsPanel.add(new JLabel(CAVALRY.getName() + ":", null, SwingConstants.TRAILING));
        cardsPanel.add(playerCavalry);
        cardsPanel.add(new JLabel(ARTILLERY.getName() + ":", null, SwingConstants.TRAILING));
        cardsPanel.add(playerArtillery);
        cardsPanel.add(new JLabel(WILDCARDS.getName() + ":", null, SwingConstants.TRAILING));
        cardsPanel.add(playerWildcards);
        cardsPanel.add(new JLabel(BONUS.getName() + ":", null, SwingConstants.TRAILING));
        cardsPanel.add(playerBonus);
    }

    /**
     * Setter for cards for player displaying
     * @param player Player
     */
    public void setPlayer(Player player) {
        playerInfantry.setText(Integer.toString(player.getCardsEnumIntegerMap().get(INFANTRY)));
        playerCavalry.setText(Integer.toString(player.getCardsEnumIntegerMap().get(CAVALRY)));
        playerArtillery.setText(Integer.toString(player.getCardsEnumIntegerMap().get(ARTILLERY)));
        playerWildcards.setText(Integer.toString(player.getCardsEnumIntegerMap().get(WILDCARDS)));
        playerBonus.setText(Integer.toString(player.getCardsEnumIntegerMap().get(BONUS)));
    }

    /**
     * Setter for country and army number displaying
     * @param country Country
     */
    public void setCountry(Country country) {
        countryName.setText(country.getName());
        countryArmy.setText(Integer.toString(country.getArmy()));
    }

    /**
     * Reset the variables for other players
     */
    public void reset() {
        playerInfantry.setText("");
        playerCavalry.setText("");
        playerArtillery.setText("");
        playerWildcards.setText("");
        playerBonus.setText("");
        countryName.setText("");
        countryArmy.setText("");
    }
}