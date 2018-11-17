package game.ui.view;

import game.Game;
import game.model.IObservable;
import game.model.enums.CardsEnum;
import game.strategies.GamePhaseStrategies.GamePhaseEnum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static game.model.enums.CardsEnum.ARTILLERY;
import static game.model.enums.CardsEnum.CAVALRY;
import static game.model.enums.CardsEnum.INFANTRY;
import static game.model.enums.CardsEnum.WILDCARDS;

/**
 * The cards panel. Display the cards functionality
 *
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
//    private JCheckBox playerBonusCheckBox = new JCheckBox(BONUS.getName());

    private JButton exchangeButton = new JButton("Exchange");

    /**
     * The constructor of the card panel
     *
     * @param width
     * @param height
     */
    public CardPanel(int width, int height) {
        stringCardsEnumMap.put(INFANTRY.getName(), INFANTRY);
        stringCardsEnumMap.put(CAVALRY.getName(), CAVALRY);
        stringCardsEnumMap.put(ARTILLERY.getName(), ARTILLERY);
        stringCardsEnumMap.put(WILDCARDS.getName(), WILDCARDS);
//        stringCardsEnumMap.put(BONUS.getName(), BONUS);

        this.setMaximumSize(new Dimension(width, height));
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        jCheckBoxList.add(playerInfantryCheckBox);
        jCheckBoxList.add(playerCavalryCheckBox);
        jCheckBoxList.add(playerArtilleryCheckBox);
        jCheckBoxList.add(playerWildcardsCheckBox);
//        jCheckBoxList.add(playerBonusCheckBox);

        JPanel list = new JPanel(new GridLayout(5, 2));
        list.add(playerInfantryCheckBox);
        list.add(playerInfantry);
        list.add(playerCavalryCheckBox);
        list.add(playerCavalry);
        list.add(playerArtilleryCheckBox);
        list.add(playerArtillery);
        list.add(playerWildcardsCheckBox);
        list.add(playerWildcards);
//        list.add(playerBonusCheckBox);
//        list.add(playerBonus);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(1, 0, 1, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(list, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        exchangeButton.addActionListener(exchangeButtonListner());
        exchangeButton.setMargin(new Insets(1, 0, 1, 0));
        this.add(exchangeButton, gbc);

        Game.getInstance().getGameState().attachObserver(this);
    }

    /**
     * Updater for the observer
     *
     * @param iObservable
     */
    @Override
    public void updateObserver(IObservable iObservable) {
        Game game = Game.getInstance();
        if (game.getGameState().getCurrentGamePhase() == GamePhaseEnum.REINFORCEMENT) {
            if (!enabled) {
                setAllEnabled(true);
            }
            playerInfantry.setText(Integer.toString(game.getGameState().getCurrentPlayer().getCardsEnumIntegerMap().get(INFANTRY)));
            playerCavalry.setText(Integer.toString(game.getGameState().getCurrentPlayer().getCardsEnumIntegerMap().get(CAVALRY)));
            playerArtillery.setText(Integer.toString(game.getGameState().getCurrentPlayer().getCardsEnumIntegerMap().get(ARTILLERY)));
            playerWildcards.setText(Integer.toString(game.getGameState().getCurrentPlayer().getCardsEnumIntegerMap().get(WILDCARDS)));
//            playerBonus.setText(Integer.toString(game.getCurrentPlayer().getCardsEnumIntegerMap().get(BONUS)));

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
                Game game = Game.getInstance();
                List<CardsEnum> selectedCards = new ArrayList<>();
                for (JCheckBox jCheckBox : jCheckBoxList) {
                    if (jCheckBox.isSelected() &&
                            game.getGameState().getCurrentPlayer().getCardsEnumIntegerMap().get(stringCardsEnumMap.get(jCheckBox.getText())) >= 1) {
                        selectedCards.add(stringCardsEnumMap.get(jCheckBox.getText()));
                    }
                }
                if (selectedCards.size() == 3) {
                    game.getGameState().setSelectedCardsToExchange(selectedCards);
                    game.exchange();
                } else if (selectedCards.size() == 1 &&
                        Game.getInstance().getGameState().getCurrentPlayer().getCardsEnumIntegerMap().get(stringCardsEnumMap.get(selectedCards.get(0).getName())) >= 3) {
                    game.getGameState().setSelectedCardsToExchange(selectedCards);
                    game.exchange();
                }
            }
        };
    }

    /**
     * Enable all cards for selecting
     *
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