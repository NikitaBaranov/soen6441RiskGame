package game.ui.view;

import game.Game;
import game.model.Country;
import game.model.Player;

import javax.swing.*;
import java.awt.*;

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

    public RightStatusPanel(int width, int height, Game game) {
        // setup overall setup
        this.setPreferredSize(new Dimension(width, height));
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(4,0,4,0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        nextButton.addActionListener(game.getNextTurnButton());
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
        exchangeButton.setMargin(new Insets(10, 0, 10, 0));
        this.add(exchangeButton, gbc);

        // setup Country panel
        countryPanel.setMaximumSize(new Dimension(width, height));
        countryPanel.setLayout(new GridLayout(2, 2));
        countryPanel.setBackground(new Color(121,180,115));
        countryPanel.add(new JLabel("Name:", null, SwingConstants.TRAILING));
        countryPanel.add(countryName);
        countryPanel.add(new JLabel("Army:", null, SwingConstants.TRAILING));
        countryPanel.add(countryArmy);

        // setup Country panel
        cardsPanel.setMaximumSize(new Dimension(width, height));
        cardsPanel.setLayout(new GridLayout(5, 2));
        cardsPanel.setBackground(new Color(121,180,115));
        cardsPanel.add(new JLabel("Infantry:",null, SwingConstants.TRAILING));
        cardsPanel.add(playerInfantry);
        cardsPanel.add(new JLabel("Cavalry:",null, SwingConstants.TRAILING));
        cardsPanel.add(playerCavalry);
        cardsPanel.add(new JLabel("Artillery:",null, SwingConstants.TRAILING));
        cardsPanel.add(playerArtillery);
        cardsPanel.add(new JLabel("Wildcards:",null, SwingConstants.TRAILING));
        cardsPanel.add(playerWildcards);
        cardsPanel.add(new JLabel("Bonus:",null, SwingConstants.TRAILING));
        cardsPanel.add(playerBonus);

//        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
//        this.setAlignmentX(Component.CENTER_ALIGNMENT);
//        this.add(name);
//        this.add(army);
    }

    public void setPlayer(Player player){
        playerInfantry.setText(Integer.toString(player.getInfantry()));
        playerCavalry.setText(Integer.toString(player.getCavalry()));
        playerArtillery.setText(Integer.toString(player.getArtillery()));
        playerWildcards.setText(Integer.toString(player.getWildcards()));
        playerBonus.setText(Integer.toString(player.getBonus()));
    }

    public void setCountry(Country country) {
        countryName.setText(country.getName());
        countryArmy.setText(Integer.toString(country.getArmy()));
    }

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