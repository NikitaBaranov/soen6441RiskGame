package com.soen6441.View;

import com.soen6441.Model.Country;
import com.soen6441.Model.Neighbour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MapPanel extends JPanel {
    private List<Neighbour> neighbours;
    private List<Country> countries;
    private StatusPanel countryInfo;

    public MapPanel(Dimension dimension, List<Country> countries, List<Neighbour> neighbours, StatusPanel countryInfo) {
        this.countries = countries;
        this.neighbours = neighbours;
        this.countryInfo = countryInfo;
        this.setPreferredSize(dimension);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Point mouse = e.getPoint();
                System.out.print(" x = " + mouse.x + " y = " + mouse.y);

                for (Country country : countries) {
                    country.resetView();
                }

                for (Country country : countries) {
                    if (country.isInBorder(mouse.x, mouse.y)) {
                        countryInfo.setMessage(country.getName());
                        country.select();
                        System.out.print(" selected " + country.getName());
                    }
                }
                e.getComponent().repaint();
                System.out.println();
            }

        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Country country : countries) {
            country.draw(g);
        }
        for (Neighbour neighbour : neighbours) {
            neighbour.draw(g);
        }
    }
}
