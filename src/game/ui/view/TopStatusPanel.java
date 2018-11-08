package game.ui.view;

import game.Game;
import game.IObservable;
import game.model.Player;

import javax.swing.*;
import java.awt.*;

/**
 * The top status panel. Displays the status of the player current state
 * @author Dmitry Kryukov
 * @see Player
 */
public class TopStatusPanel extends JPanel implements IPanelObserver {

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
     * @param iObservable
     */
    @Override
    public void updateObserver(IObservable iObservable) {
        Game game = Game.getInstance();
        playerName.setText(game.getCurrentPlayer() != null ? game.getCurrentPlayer().getName() : "");
        gamePhase.setText(game.getCurrentGamePhase() != null ? game.getCurrentGamePhase().getName() : "");
        turnPhrase.setText(game.getCurrentTurnPhraseText() != null ? game.getCurrentTurnPhraseText() : "");
    }
}