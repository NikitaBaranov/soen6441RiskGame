package game.ui.view;

import game.Game;
import game.model.IObservable;
import game.model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The top status panel. Displays the status of the player current state
 * @author Dmitry Kryukov
 * @see Player
 */
public class TopStatusPanel extends JPanel implements IPanelObserver {

    private JLabel playerName = new JLabel();
    private JLabel gamePhase = new JLabel();
    private JLabel turnPhrase = new JLabel();
    private JButton saveGameButton = new JButton();
    private JButton notification = new JButton();

    /**
     * Constructor of the class.
     * Draw the needed panels on the main game window.
     * @param width of the panel
     * @param height of the panel
     */
    public TopStatusPanel(int width, int height) {
        this.setPreferredSize(new Dimension(width, height));
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        JLabel playerLabel = new JLabel("Player: ");
        playerLabel.setForeground(Color.YELLOW);
        this.add(playerLabel);
        this.add(playerName);
        JSeparator separator1 = new JSeparator(SwingConstants.HORIZONTAL); // Horizontal separator
        separator1.setBackground(new Color(121,180,115)); // make it invisible
        separator1.setForeground(new Color(121,180,115)); // make it invisible
        this.add(separator1);
        JLabel statusLabel = new JLabel("Status: ");
        statusLabel.setForeground(Color.YELLOW);
        this.add(statusLabel);
        this.add(turnPhrase);
        JSeparator separator3 = new JSeparator(SwingConstants.HORIZONTAL); // Horizontal separator
        separator3.setBackground(new Color(121,180,115)); // make it invisible
        separator3.setForeground(new Color(121,180,115)); // make it invisible
        this.add(separator3);
        JLabel phaseLabel = new JLabel("Phase: ");
        phaseLabel.setForeground(Color.YELLOW);
        this.add(phaseLabel);
        this.add(gamePhase);

        saveGameButton.setText("Save");
        saveGameButton.addActionListener(saveGameButtonListner());
        saveGameButton.setMargin(new Insets(5, 0, 5, 0));
        this.add(saveGameButton);

        notification.setText("Show/Hide");
        notification.addActionListener(notificationButtonListner());
        notification.setMargin(new Insets(5, 0, 5, 0));
        this.add(notification);

        Game.getInstance().getGameState().attachObserver(this);
    }

    /**
     * Updater for observer
     * @param iObservable
     */
    @Override
    public void updateObserver(IObservable iObservable) {
        Game game = Game.getInstance();
        playerName.setText(game.getGameState().getCurrentPlayer() != null ? game.getGameState().getCurrentPlayer().getName() : "");
        playerName.setForeground(game.getGameState().getCurrentPlayer().getColor());
        gamePhase.setText(game.getGameState().getCurrentGamePhase() != null ? game.getGameState().getCurrentGamePhase().getName() : "");
        turnPhrase.setText(game.getGameState().getCurrentTurnPhraseText() != null ? game.getGameState().getCurrentTurnPhraseText() : "");
    }

    /**
     * Save game button listener
     */
    public ActionListener saveGameButtonListner() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Save game");
            }
        };
    }

    /**
     * Notification button listener
     */
    public ActionListener notificationButtonListner() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Game.getInstance().getNotification().setVisible(!Game.getInstance().getNotification().isVisible());
            }
        };
    }
}