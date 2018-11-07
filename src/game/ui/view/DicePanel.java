package game.ui.view;

import game.Game;
import game.enums.DiceEnum;
import game.model.Dice;
import game.model.IModelObservable;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Dice panel.
 * Stub for future development.
 */
public class DicePanel extends JPanel implements IPanelObserver {
    public final int DICE_ROW_TO_SHOW = 3;
    private DiceEnum[] redDice = new DiceEnum[DICE_ROW_TO_SHOW];
    private DiceEnum[] whiteDice = new DiceEnum[DICE_ROW_TO_SHOW];

    /**
     * Constructor of the class
     * @param width of the panel
     * @param height of the panel
     */
    public DicePanel(int width, int height) {
        this.setPreferredSize(new Dimension(width, height));
        this.setLayout(new GridLayout(3, 2));
        Game.getInstance().attachObserver(this);
    }

    /**
     * Updater for Observer
     * @param iModelObservable
     */
    @Override
    public void updateObserver(IModelObservable iModelObservable) {
        Game game = Game.getInstance();
        whiteDice = game.getWhiteDice();
        redDice = game.getRedDice();
        createPanel();
//        repaint();
    }

    /**
     * Creator for panel
     */
    private void createPanel() {
        this.removeAll();

        for (int i = 0; i < DICE_ROW_TO_SHOW; i++) {
            try {
                BufferedImage imageRed = ImageIO.read(new File(Dice.RED_FILE_PREFIX + redDice[i].getFileSuffix()));
                BufferedImage imageWhite = ImageIO.read(new File(Dice.WHITE_FILE_PREFIX + whiteDice[i].getFileSuffix()));
                JLabel redLabel = new JLabel();
                redLabel.setIcon(new ImageIcon(imageRed));
                redLabel.setHorizontalAlignment(JLabel.CENTER);
                redLabel.setVerticalAlignment(JLabel.CENTER);
                redLabel.setBorder(new LineBorder(Color.BLACK));
                this.add(redLabel);
                JLabel whiteLabel = new JLabel();
                whiteLabel.setIcon(new ImageIcon(imageWhite));
                whiteLabel.setHorizontalAlignment(JLabel.CENTER);
                whiteLabel.setVerticalAlignment(JLabel.CENTER);
                whiteLabel.setBorder(new LineBorder(Color.BLACK));
                this.add(whiteLabel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}