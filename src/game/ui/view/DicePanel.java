package game.ui.view;

import game.enums.DiceEnum;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Dice panel.
 * Stub for future development.
 */
public class DicePanel extends JPanel {
    public final int DICE_ROW_TO_SHOW = 3;
    private final int DICE_MARGIN = 5;
    private DiceEnum[] redDice = new DiceEnum[DICE_ROW_TO_SHOW];
    private DiceEnum[] whiteDice = new DiceEnum[DICE_ROW_TO_SHOW];
    private int width;
    private int height;

    /**
     * Constructor of the class
     * @param width of the panel
     * @param height of the panel
     */
    public DicePanel(int width, int height) {
        this.width = width;
        this.height = height;
        this.setPreferredSize(new Dimension(width, height));
        this.setLayout(new GridLayout(3, 2));
        for (int i = 0; i < DICE_ROW_TO_SHOW; i++) {
            redDice[i] = DiceEnum.SIX;
            whiteDice[i] = DiceEnum.SIX;
        }
        setDices();
    }

    /**
     * Set the dices
     */
    public void setDices() {
        setDices(redDice, whiteDice);
    }

    /**
     * Set the dices with parameters
     * @param redDice Dices of player
     * @param whiteDice Dices of enemie
     */
    public void setDices(DiceEnum[] redDice, DiceEnum[] whiteDice) {
        this.redDice = redDice;
        this.whiteDice = whiteDice;
        this.removeAll();
        for (int i = 0; i < DICE_ROW_TO_SHOW; i++) {
            try {
                BufferedImage imageRed = ImageIO.read(new File(DiceEnum.RED_FILE_PREFIX + redDice[i].getFileSuffix()));
                BufferedImage imageWhite = ImageIO.read(new File(DiceEnum.WHITE_FILE_PREFIX + whiteDice[i].getFileSuffix()));
                JLabel redLabel = new JLabel();
                redLabel.setIcon(new ImageIcon(imageRed));
                redLabel.setHorizontalAlignment(JLabel.CENTER);
                redLabel.setVerticalAlignment(JLabel.CENTER);
                this.add(redLabel);
                JLabel whiteLabel = new JLabel();
                whiteLabel.setIcon(new ImageIcon(imageWhite));
                whiteLabel.setHorizontalAlignment(JLabel.CENTER);
                whiteLabel.setVerticalAlignment(JLabel.CENTER);
                this.add(whiteLabel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        for(int i = 0; i < DICE_ROW_TO_SHOW; i++){
//            diceRows[i] = new JPanel();
//            diceRows[i].add(new ImageIcon("ressources/" + "white_" + whiteDice[i].getFileSuffix()));
//            diceRows[i].add(new ImageIcon("ressources/" + "red_"+ redDice[i].getFileSuffix()));
//        }
    }
    //    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        for (int i = 0; i < DICE_ROW_TO_SHOW; i++) {
//            try {
//                File imageRed = new File("game.resources/red_" + redDice[0].getFileSuffix());
//                g.drawImage(ImageIO.read(imageRed).getScaledInstance(width / 2, height / 3, Image.SCALE_SMOOTH), 0, 0 + i * (height - DICE_MARGIN) / 3, this);
//
//                File whiteRed = new File("game.resources/white_" + redDice[0].getFileSuffix());
//                g.drawImage(ImageIO.read(whiteRed).getScaledInstance(width / 2, height / 3, Image.SCALE_SMOOTH), 0 + (width - DICE_MARGIN) / 2, 0 + i * (height - DICE_MARGIN) / 3, this);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
