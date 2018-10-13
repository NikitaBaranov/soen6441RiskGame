package view;

import model.Country;

import javax.swing.*;
import java.awt.*;

public class PlayerStatusPanel extends JPanel {
    Country country;
    Label name = new Label();
    Label army = new Label();

    public PlayerStatusPanel(int width, int height) {
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.GRAY);
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
//        this.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(name);
        this.add(army);
    }

    public void setCountry(Country country) {
        this.country = country;
        name.setText("Country " + country.getName());
        army.setText("Army " + country.getArmy());
    }

    public void reset() {
        name.setText("");
        army.setText("");
    }
}
