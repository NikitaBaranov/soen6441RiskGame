package game.ui.view;

import game.Game;
import game.model.IModelObservable;
import game.model.Player;

import javax.swing.*;
import java.awt.*;

/**
 * The top status panel. Displays the status of the player current state
 * @author Dmitry Kryukov
 * @see Player
 */
public class TopStatusPanel extends JPanel implements IPanelObserver {

    private Player player;
    private String turnPhraseText;
    private String gamePhaseText;


    private JLabel playerName = new JLabel();
    private JLabel gamePhase = new JLabel();
    private JLabel turnPhrase = new JLabel();

    /**
     * Constructor of the class.
     * Draw the needed panels on the main game window.
     * @param width of the panel
     * @param height of the panel
     */
    public TopStatusPanel(int width, int height) {
        this.setPreferredSize(new Dimension(width, height));
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        this.add(new JLabel(" Player: "));
        this.add(playerName);
        JSeparator separator1 = new JSeparator(SwingConstants.HORIZONTAL); // Horizontal separator
        separator1.setBackground(new Color(121,180,115)); // make it invisible
        separator1.setForeground(new Color(121,180,115)); // make it invisible
        this.add(separator1);

        this.add(new JLabel("Status: "));
        this.add(turnPhrase);
        JSeparator separator3 = new JSeparator(SwingConstants.HORIZONTAL); // Horizontal separator
        separator3.setBackground(new Color(121,180,115)); // make it invisible
        separator3.setForeground(new Color(121,180,115)); // make it invisible
        this.add(separator3);

        this.add(new JLabel("Phase: "));
        this.add(gamePhase);

        Game.getInstance().attachObserver(this);
    }

    /**
     * Updater for observer
     * @param iModelObservable
     */
    @Override
    public void updateObserver(IModelObservable iModelObservable) {
        Game game = Game.getInstance();
        playerName.setText(game.getCurrentPlayer() != null ? game.getCurrentPlayer().getName() : "");
        gamePhase.setText(game.getCurrentGamePhase() != null ? game.getCurrentGamePhase().getName() : "");
        turnPhrase.setText(game.getCurrentTurnPhraseText() != null ? game.getCurrentTurnPhraseText() : "");
//        this.repaint();

//        switch (iModelObservable.getClass().getName()) {
//            case "Player":
//                player = (Player) iModelObservable;
//                break;
//            case "Game":
//                gamePhaseText = ((Game) iModelObservable).getCurrentGamePhase().getName();
//                break;
//        }
    }

//    /**
//     * Set the player info on the pstatus panel
//     * @param player Player
//     */
//    public void setPlayer(Player player) {
//        playerName.setText(player.getName());
//        String colorS = Integer.toString(player.getColor().getRGB());
//        playerName.setForeground(new Color(Integer.parseInt(colorS)));
//    }
//
//    /**
//     * Set the current game phase to displaying on the panel
//     * @param gamePhase phase
//     */
//    public void setGamePhase(String gamePhase) {
//        this.gamePhase.setText(gamePhase);
//    }
//
//    /**
//     * Set the status for the phase to displaying on the panel
//     * @param turnPhrase Phrase
//     */
//    public void setTurnPhrase(String turnPhrase) {
//        this.turnPhrase.setText(turnPhrase);
//    }
//
//    /**
//     * Reset the variables for other players
//     */
//    public void reset() {
//        playerName.setText("");
//        gamePhase.setText("");
//        turnPhrase.setText("");
//    }
}