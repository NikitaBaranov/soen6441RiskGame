package game.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * The window that appears to print the error message dirung the map loading.
 * @author Dmitry Kryukov
 */
public class WarningWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private int width, height;
    private String message;

    /**
     * The constructor of the class.
     * @param message that should be printed
     */
    public WarningWindow(String message) {
        super("Warning!");
        this.message = message;
        this.width = 800;
        this.height = 100;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(this.width, this.height);
        setResizable(false);
        setAlwaysOnTop(true);

        getContentPane().add(printMessage());

        // pack(); // ignore sizing
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Method creates an panel on the window woth message and close button
     * @return messagePanel object to attach to the window
     */
    private JPanel printMessage() {
        JPanel messagePanel = new JPanel();

        messagePanel.setPreferredSize(new Dimension(this.width, this.height));
        messagePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,15,10,15);
        JButton quit = new JButton(new ExitAction());
        JLabel printMessage = new JLabel(this.message);
        gbc.gridx = 0;
        gbc.gridy = 0;
        messagePanel.add(printMessage, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        messagePanel.add(quit, gbc);

        return messagePanel;
    }

    /**
     * The exit functionality, triggered when the user press the close button
     */
    class ExitAction extends AbstractAction {
        private static final long serialVersionUID = 1L;
        ExitAction() {
            putValue(NAME, "Close");
        }
        public void actionPerformed(ActionEvent e) {
            // We can just close the window but also clean everything to be able start the program from scratch
            // Like clean all models variables (Country, Continent, etc)
            // dispose();
            System.exit(0); // exit the program
        }
    }
}