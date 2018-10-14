package ui.view;

import model.Player;

import javax.swing.*;
import java.awt.*;

public class TopStatusPanel extends JPanel {
    private JLabel playerColor = new JLabel();
    private JLabel playerName = new JLabel();
    private JLabel gameState = new JLabel();
    private JLabel gamePhase = new JLabel();
    private JLabel turnPhrase = new JLabel();

    public TopStatusPanel(int width, int height) {
        this.setPreferredSize(new Dimension(width, height));
//        this.setBackground(Color.GRAY);
        this.setLayout(new FlowLayout());

        this.add(new JLabel("Player "));
        this.add(playerColor);
        this.add(playerName);
        this.add(new JLabel(" turn."));
        this.add(new JSeparator());
        this.add(new JLabel("State: "));
        this.add(gameState);
        this.add(new JSeparator());
        this.add(new JLabel("Phase: "));
        this.add(gamePhase);
        this.add(new JSeparator());
        this.add(new JLabel("Phrase: "));
        this.add(turnPhrase);
    }

    public void setPlayer(Player player) {
        playerName.setText(player.getName());
    }

    public void setGameState(String gameState) {
        this.gameState.setText(gameState);
    }

    public void setGamePhase(String gamePhase) {
        this.gamePhase.setText(gamePhase);
    }

    public void setTurnPhrase(String turnPhrase) {
        this.turnPhrase.setText(turnPhrase);
    }

    public void reset() {
        playerName.setText("");
        gameState.setText("");
        gamePhase.setText("");
        turnPhrase.setText("");
    }
}