package game.ui.view;

import game.Game;
import game.IObservable;
import game.enums.GamePhase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

/**
 * The panel for attack phase
 * @author Dmitry Kryukov, Ksenia Popova
 */
public class AttackPanel extends JPanel implements IPanelObserver {

    private JPanel numbersPanel;

    private ButtonGroup redDiceGroup;
    private JRadioButton red1;
    private JRadioButton red2;
    private JRadioButton red3;

    private ButtonGroup whiteDiceGroup;
    private JRadioButton white1;
    private JRadioButton white2;

    private DicePanel dicePanel;

    private JButton attackButton = new JButton();
    private JButton attackAllInButton = new JButton();

    /**
     * Constructor for Attack panel
     * @param width
     * @param height
     */
    public AttackPanel(int width, int height) {
        this.setPreferredSize(new Dimension(width, height));
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        numbersPanel = new JPanel();
        numbersPanel.setLayout(new GridLayout(4, 2));
        numbersPanel.setPreferredSize(new Dimension(100, 100));

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


        numbersPanel.add(new JLabel("Red Dices"));
        numbersPanel.add(new JLabel("White Dices"));
        numbersPanel.add(red1);
        numbersPanel.add(white1);
        numbersPanel.add(red2);
        numbersPanel.add(white2);
        numbersPanel.add(red3);

        dicePanel = new DicePanel(100, 170);
//        dicePanel.setBackground(new Color(255, 255, 255));

        attackButton.setText("Attack!");
        attackButton.addActionListener(attackButtonListner());
        attackButton.setMargin(new Insets(5, 0, 5, 0));

        attackAllInButton.setText("All In!");
        attackAllInButton.addActionListener(attackAllInButtonListner());
        attackAllInButton.setMargin(new Insets(5, 0, 5, 0));

//        gbc.fill = GridBagConstraints.BOTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(numbersPanel, gbc);

        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(attackButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        this.add(attackAllInButton, gbc);

        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(dicePanel, gbc);

        attackButton.setEnabled(false);
        attackAllInButton.setEnabled(false);
        this.setVisible(false);

        Game.getInstance().attachObserver(this);
    }

    /**
     * Updater for observer
     * @param iObservable
     */
    @Override
    public void updateObserver(IObservable iObservable) {
        Game game = Game.getInstance();
        if (game.getCurrentGamePhase() == GamePhase.ATTACK) {
            if (!game.isWinBattle()) {
                this.setVisible(true);
                if (game.getCountryFrom() != null && game.getCountryFrom().getArmy() > 1 && game.getCountryTo() != null) {
                    red1.setSelected(true);
                    white1.setSelected(true);

                    red1.setEnabled(game.getCountryFrom().getArmy() >= 2);
                    red2.setEnabled(game.getCountryFrom().getArmy() >= 3);
                    red3.setEnabled(game.getCountryFrom().getArmy() >= 4);

                    white1.setEnabled(game.getCountryTo().getArmy() >= 1);
                    white2.setEnabled(game.getCountryTo().getArmy() >= 2);

                    attackButton.setEnabled(true);
                    attackAllInButton.setEnabled(true);
                } else {
                    setAllDisabled();
                }
            } else {
                setAllDisabled();
            }
        } else {
            this.setVisible(false);
            setAllDisabled();
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

    /**
     * All in button listener
     */
    public ActionListener attackAllInButtonListner() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Game game = Game.getInstance();

                game.setNumberOfRedDicesSelected(Math.max(0, Math.min(game.getCountryFrom().getArmy() - 1, 3)));
                game.setNumberOfWhiteDicesSelected(Math.max(0, Math.min(game.getCountryTo().getArmy(), 2)));

                game.attack();
            }
        };
    }

    /**
     * Set all checkbuttons enabled
     */
    private void setAllDisabled() {
        Enumeration<AbstractButton> redEnumeration = redDiceGroup.getElements();
        while (redEnumeration.hasMoreElements()) {
            JRadioButton jRadioButton = (JRadioButton) redEnumeration.nextElement();
            jRadioButton.setEnabled(false);
        }

        Enumeration<AbstractButton> whiteEnumeration = whiteDiceGroup.getElements();
        while (whiteEnumeration.hasMoreElements()) {
            JRadioButton jRadioButton = (JRadioButton) whiteEnumeration.nextElement();
            jRadioButton.setEnabled(false);
        }
        attackButton.setEnabled(false);
        attackAllInButton.setEnabled(false);
    }
}