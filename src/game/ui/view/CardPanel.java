package game.ui.view;

import game.Game;
import game.enums.CardsEnum;
import game.enums.GamePhase;
import game.model.IModelObservable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static game.enums.CardsEnum.ARTILLERY;
import static game.enums.CardsEnum.BONUS;
import static game.enums.CardsEnum.CAVALRY;
import static game.enums.CardsEnum.INFANTRY;
import static game.enums.CardsEnum.WILDCARDS;

/**
 * The cards panel. Display the cards functionality
 * @author Dmitry Kryukov, Ksenia Popova
 */
public class CardPanel extends JPanel implements IPanelObserver {

    Map<String, CardsEnum> stringCardsEnumMap = new HashMap<>();
    List<JCheckBox> jCheckBoxList = new ArrayList<>();
    boolean enabled = false;

    private JLabel playerInfantry = new JLabel("", null, SwingConstants.TRAILING);
    private JLabel playerCavalry = new JLabel("", null, SwingConstants.TRAILING);
    private JLabel playerArtillery = new JLabel("", null, SwingConstants.TRAILING);
    private JLabel playerWildcards = new JLabel("", null, SwingConstants.TRAILING);
    private JLabel playerBonus = new JLabel("", null, SwingConstants.TRAILING);

    private JCheckBox playerInfantryCheckBox = new JCheckBox(INFANTRY.getName());
    private JCheckBox playerCavalryCheckBox = new JCheckBox(CAVALRY.getName());
    private JCheckBox playerArtilleryCheckBox = new JCheckBox(ARTILLERY.getName());
    private JCheckBox playerWildcardsCheckBox = new JCheckBox(WILDCARDS.getName());
    private JCheckBox playerBonusCheckBox = new JCheckBox(BONUS.getName());

    private JButton exchangeButton = new JButton("Exchange");

    /**
     * The constructor of the card panel
     * @param width
     * @param height
     */
    public CardPanel(int width, int height) {
        stringCardsEnumMap.put(INFANTRY.getName(), INFANTRY);
        stringCardsEnumMap.put(CAVALRY.getName(), CAVALRY);
        stringCardsEnumMap.put(ARTILLERY.getName(), ARTILLERY);
        stringCardsEnumMap.put(WILDCARDS.getName(), WILDCARDS);
        stringCardsEnumMap.put(BONUS.getName(), BONUS);

        this.setMaximumSize(new Dimension(width, height));
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        jCheckBoxList.add(playerInfantryCheckBox);
        jCheckBoxList.add(playerCavalryCheckBox);
        jCheckBoxList.add(playerArtilleryCheckBox);
        jCheckBoxList.add(playerWildcardsCheckBox);
        jCheckBoxList.add(playerBonusCheckBox);

        JPanel list = new JPanel(new GridLayout(5, 2));
        list.add(playerInfantryCheckBox);
//        list.add(new JLabel(INFANTRY.getName() + ":", null, SwingConstants.TRAILING));
        list.add(playerInfantry);
        list.add(playerCavalryCheckBox);
//        list.add(new JLabel(CAVALRY.getName() + ":", null, SwingConstants.TRAILING));
        list.add(playerCavalry);
        list.add(playerArtilleryCheckBox);
//        list.add(new JLabel(ARTILLERY.getName() + ":", null, SwingConstants.TRAILING));
        list.add(playerArtillery);
        list.add(playerWildcardsCheckBox);
//        list.add(new JLabel(WILDCARDS.getName() + ":", null, SwingConstants.TRAILING));
        list.add(playerWildcards);
        list.add(playerBonusCheckBox);
//        list.add(new JLabel(BONUS.getName() + ":", null, SwingConstants.TRAILING));
        list.add(playerBonus);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(4, 0, 4, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(list, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        exchangeButton.addActionListener(exchangeButtonListner());
        exchangeButton.setMargin(new Insets(1, 0, 1, 0));
        this.add(exchangeButton, gbc);

//        exchangeButton.setEnabled(false);

        Game.getInstance().attachObserver(this);
    }

    /**
     * Updater for the observer
     * @param iModelObservable
     */
    @Override
    public void updateObserver(IModelObservable iModelObservable) {
        Game game = Game.getInstance();
        if (game.getCurrentGamePhase() == GamePhase.REINFORCEMENT) {
            if (!enabled) {
                setAllEnabled(true);
            }
            playerInfantry.setText(Integer.toString(game.getCurrentPlayer().getCardsEnumIntegerMap().get(INFANTRY)));
            playerCavalry.setText(Integer.toString(game.getCurrentPlayer().getCardsEnumIntegerMap().get(CAVALRY)));
            playerArtillery.setText(Integer.toString(game.getCurrentPlayer().getCardsEnumIntegerMap().get(ARTILLERY)));
            playerWildcards.setText(Integer.toString(game.getCurrentPlayer().getCardsEnumIntegerMap().get(WILDCARDS)));
            playerBonus.setText(Integer.toString(game.getCurrentPlayer().getCardsEnumIntegerMap().get(BONUS)));

        } else {
            setAllEnabled(false);
            exchangeButton.setEnabled(false);
        }
    }

    /**
     * Action handler for exchange button.
     */
    public ActionListener exchangeButtonListner() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<CardsEnum> selectedCards = new ArrayList<>();
                for (JCheckBox jCheckBox : jCheckBoxList) {
                    if (jCheckBox.isSelected() &&
                            Game.getInstance().getCurrentPlayer().getCardsEnumIntegerMap().get(stringCardsEnumMap.get(jCheckBox.getText())) >= 1) {
                        selectedCards.add(stringCardsEnumMap.get(jCheckBox.getText()));
                    }
                }
                if (selectedCards.size() == 3) {
                    Game.getInstance().exchange(selectedCards);
                } else if (selectedCards.size() == 1 &&
                        Game.getInstance().getCurrentPlayer().getCardsEnumIntegerMap().get(stringCardsEnumMap.get(selectedCards.get(0).getName())) >= 3) {
                    Game.getInstance().exchange(selectedCards);
                }
            }
        };
    }

    /**
     * Enable all cards for selecting
     * @param value
     */
    private void setAllEnabled(boolean value) {
        enabled = value;
        for (JCheckBox jCheckBox : jCheckBoxList) {
            jCheckBox.setEnabled(value);
            jCheckBox.setSelected(false);
        }
        exchangeButton.setEnabled(value);
    }
}