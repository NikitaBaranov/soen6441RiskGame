package com.soen6441.View;

import javax.swing.*;
import java.awt.*;

public class StatusPanel extends JPanel {
    Label label = new Label();

    public StatusPanel(int width, int height, String message) {
        this.setPreferredSize(new Dimension(width, height));
        this.label.setText(message);
        this.add(label);
    }

    public void setMessage(String message) {
        this.label.setText(message);
    }
}
