package ui.view;

import model.Country;
import model.Player;

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

    public RightStatusPanel(int width, int height) {
        // setup overall setup
        this.setPreferredSize(new Dimension(width, height));
//        this.setLayout(new BoxLayout(this, PAGE_AXIS));
        this.setLayout(new GridLayout(8, 1));
        nextButton.setMargin(new Insets(5, 10, 5, 10));
        this.add(nextButton);
        this.add(new JSeparator(), BorderLayout.CENTER);
        this.add(new JLabel("Country:", null, SwingConstants.CENTER), BorderLayout.CENTER);
        this.add(countryPanel);
        this.add(new JSeparator(), BorderLayout.CENTER);
        this.add(new JLabel("Cards:", null, SwingConstants.CENTER), BorderLayout.CENTER);
        this.add(cardsPanel);
        exchangeButton.setMargin(new Insets(5, 10, 5, 10));
        this.add(exchangeButton, BorderLayout.CENTER);

        // setup Country panel
        countryPanel.setMaximumSize(new Dimension(width, height*1/3));
        countryPanel.setLayout(new GridLayout(2, 2));
        countryPanel.add(new JLabel("Name", null, SwingConstants.TRAILING));
        countryPanel.add(countryName);
        countryPanel.add(new JLabel("Army", null, SwingConstants.TRAILING));
        countryPanel.add(countryArmy);

        // setup Country panel
        cardsPanel.setMaximumSize(new Dimension(width, height*2/3));
        cardsPanel.setLayout(new GridLayout(5, 2));
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