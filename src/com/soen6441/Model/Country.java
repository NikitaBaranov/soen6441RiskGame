package com.soen6441.Model;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.List;

public class Country {
    private String name;
    private int x;
    private int y;
    private int radius;
    private List<Country> neighbours;


    public Country(String name, int x, int y, int radius) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public List<Country> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(List<Country> neighbours) {
        this.neighbours = neighbours;
    }

    public boolean isInBorder(int x, int y){
        return Math.sqrt( Math.pow( x - this.x, 2) + Math.pow( y - this.y, 2) ) < radius;
    }

    public void draw (Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        Ellipse2D.Double circle = new Ellipse2D.Double(x - radius, y - radius, radius * 2, radius * 2);


        g2d.setColor(Color.BLACK);
        g2d.fill(circle);
        g2d.drawString(name, x - radius, y - radius);
    }
}
