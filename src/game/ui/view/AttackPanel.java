package game.ui.view;

import game.Game;
import game.enums.GamePhase;
import game.model.IModelObservable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

public class AttackPanel extends JPanel implements IPanelObserver {

    private int redDices = 0;
    private int whiteDices = 0;

    private JPanel numbersPanel;

    private ButtonGroup redDiceGroup;
    private JRadioButton red1;
    private JRadioButton red2;
    private JRadioButton red3;

    private ButtonGroup whiteDiceGroup;
    private JRadioButton white1;
    private JRadioButton white2;

    private DicePanel dicePanel;

    private JButton attackButton;

    public AttackPanel(int width, int height) {
        this.setPreferredSize(new Dimension(width, height));

        this.setLayout(new GridLayout(4, 1));
        Game.getInstance().attachObserver(this);

        JLabel attackLable = new JLabel("Attack:");

        numbersPanel = new JPanel();
        numbersPanel.setLayout(new GridLayout(3, 2));
        numbersPanel.setPreferredSize(new Dimension(100, 100));
        numbersPanel.setBackground(new Color(255, 255, 255));

        red1 = new JRadioButton("1", true);
        red2 = new JRadioButton("2");
        red3 = new JRadioButton("3");

        redDiceGroup = new ButtonGroup();
        redDiceGroup.add(red1);
        redDiceGroup.add(red2);
        redDiceGroup.add(red3);

        white1 = new JRadioButton("1", true);
        white2 = new JRadioButton("2");

        whiteDiceGroup = new ButtonGroup();
        whiteDiceGroup.add(white1);
        whiteDiceGroup.add(white2);

        numbersPanel.add(red1);
        numbersPanel.add(white1);
        numbersPanel.add(red2);
        numbersPanel.add(white2);
        numbersPanel.add(red3);

        dicePanel = new DicePanel(100, 170);
        dicePanel.setBackground(new Color(255, 255, 255));

        attackButton = new JButton();
        attackButton.setText("Attack!");
        attackButton.addActionListener(attackButtonListner());

        this.add(attackLable);
        this.add(numbersPanel);
        this.add(dicePanel);
        this.add(attackButton);

        attackButton.setEnabled(false);
//        this.setVisible(false);
//        numbersPanel.setEnabled(false);
//        dicePanel.setEnabled(false);
//        this.setEnabled(false);
    }

    @Override
    public void updateObserver(IModelObservable iModelObservable) {
        Game game = Game.getInstance();
        if (game.getCurrentGamePhase() == GamePhase.ATTACK) {
//            red1.setSelected(true);
//            white1.setSelected(true);
            this.setVisible(true);
//            this.setEnabled(true);
//            numbersPanel.setEnabled(true);
//            dicePanel.setEnabled(true);
            if (game.getCountryTo() != null && game.getCountryFrom() != null) {
                attackButton.setEnabled(true);
            } else {
                attackButton.setEnabled(false);

            }
        } else {
            this.setVisible(false);
            attackButton.setEnabled(false);
//            this.setEnabled(false);
//            numbersPanel.setEnabled(false);
//            dicePanel.setEnabled(false);
        }
    }

    /**
     * Next button listener
     */
    public ActionListener attackButtonListner() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Game game = Game.getInstance();

                Enumeration<AbstractButton> redEnumeration = redDiceGroup.getElements();
                while (redEnumeration.hasMoreElements()) {
                    JRadioButton jRadioButton = (JRadioButton) redEnumeration.nextElement();
                    if (jRadioButton.isSelected()) {
                        game.setNumberOfRedDicesSelected(Integer.parseInt(jRadioButton.getText()));
                    }
                }

                Enumeration<AbstractButton> whiteEnumeration = whiteDiceGroup.getElements();
                while (whiteEnumeration.hasMoreElements()) {
                    JRadioButton jRadioButton = (JRadioButton) whiteEnumeration.nextElement();
                    if (jRadioButton.isSelected()) {
                        game.setNumberOfWhiteDicesSelected(Integer.parseInt(jRadioButton.getText()));
                    }
                }
                game.attack();
            }
        };
    }
}
