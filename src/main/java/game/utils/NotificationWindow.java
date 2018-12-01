package game.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The window that appears to print the notifications message dirung the game.
 * @author Dmitry Kryukov
 */
public class NotificationWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private int width, height;
    private JTextArea notification = new JTextArea();

    /**
     * The constructor of the class.
     */
    public NotificationWindow() {
        super("Notification.");
        this.width = 600;
        this.height = 600;

        setSize(this.width, this.height);
        setResizable(false);
        setAlwaysOnTop(false);

        notification.setEditable(false);
        notification.setLineWrap(true);
        notification.setWrapStyleWord(true);
        notification.setBackground(Color.DARK_GRAY);
        notification.setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane (notification, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        this.add(scroll);
        setLocationRelativeTo(null);

        // pack(); // ignore sizing
        setLocationRelativeTo(null);
        setVisible(false);
    }

    /**
     * Append new notification to the notificaton window
     * @param message
     */
    public void setNotification(String message) {
        notification.append("Action: "+message+"\n");
    }

    /**
     * Method creates an panel on the window woth message and close button
     * @return messagePanel object to attach to the window
     */
    private JTextArea addNotification() {
        return null;
    }
}