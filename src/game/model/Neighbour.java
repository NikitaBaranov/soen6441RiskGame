package game.model;

import java.awt.*;

public class Neighbour {
    private Country one;
    private Country two;

    public Neighbour(Country one, Country two) {
        this.one = one;
        this.two = two;
        one.getNeighbours().add(two);
        two.getNeighbours().add(one);
    }

    public Country getOne() {
        return one;
    }

    public Country getTwo() {
        return two;
    }

    public void draw (Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.BLACK);

        g2d.drawLine(one.getX(), one.getY(), two.getX(), two.getY());
    }
}