package game.model;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

public class Country {
    private int HIGHLIGHT_BORDER_WITHT = 10;
    private int ARMY_BACKGROUND_WITHT = 6;

    private String name;
    private Player player;

    private int x = 0;
    private int y = 0;
    private int radius = 0;
    private int army = 1;
    private List<Country> neighbours = new ArrayList<>();
    private boolean isSelected = false;
    private boolean isHighlited = false;

    public Country(String name, Player player) {
        this.name = name;
        this.player = player;
    }

    public Country(String name, int x, int y, int radius, Player player) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.player = player;
    }

    public String getName() {
        return name;
    }

    public int getArmy() {
        return army;
    }

    public void setArmy(int army) {
        this.army = army;
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void resetView(){
        isSelected = false;
        isHighlited = false;
    }

    public void select(){
        isSelected = true;
        for (Country country: neighbours) {
            if(country.getPlayer() != this.player){
                country.setHighlited(true);
            }
        }
    }

    public void unSelect(){
        isSelected = false;
        for (Country country: neighbours) {
            if(country.getPlayer() != this.player){
                country.setHighlited(false);
            }
        }
    }

    public boolean isHighlited() {
        return isHighlited;
    }

    public void setHighlited(boolean highlited) {
        isHighlited = highlited;
    }

    public boolean isInBorder(int x, int y){
        return Math.sqrt( Math.pow( x - this.x, 2) + Math.pow( y - this.y, 2) ) < radius;
    }

    public void draw (Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        if(isSelected){
            Ellipse2D.Double selection = new Ellipse2D.Double(x - radius - HIGHLIGHT_BORDER_WITHT/2, y - radius -HIGHLIGHT_BORDER_WITHT/2 , radius * 2 + HIGHLIGHT_BORDER_WITHT, radius * 2 + HIGHLIGHT_BORDER_WITHT);
            g2d.setColor(Color.GREEN);
            g2d.fill(selection);
            g2d.setColor(Color.BLACK);
            g2d.draw(selection);

        } else if (isHighlited){
            Ellipse2D.Double highlight = new Ellipse2D.Double(x - radius - HIGHLIGHT_BORDER_WITHT/2, y - radius -HIGHLIGHT_BORDER_WITHT/2 , radius * 2 + HIGHLIGHT_BORDER_WITHT, radius * 2 + HIGHLIGHT_BORDER_WITHT);
            g2d.setColor(Color.RED);
            g2d.fill(highlight);
            g2d.setColor(Color.BLACK);
            g2d.draw(highlight);
        }

        Ellipse2D.Double playerColor = new Ellipse2D.Double(x - radius, y - radius, radius * 2, radius * 2);
        g2d.setColor(player.getColor());
        g2d.fill(playerColor);
        g2d.setColor(Color.BLACK);
        g2d.draw(playerColor);

        Ellipse2D.Double armyBackground = new Ellipse2D.Double(x - (radius - ARMY_BACKGROUND_WITHT) , y - (radius - ARMY_BACKGROUND_WITHT) , (radius - ARMY_BACKGROUND_WITHT) * 2, (radius - ARMY_BACKGROUND_WITHT) * 2);
        g2d.setColor(Color.WHITE);
        g2d.fill(armyBackground);
        g2d.setColor(Color.BLACK);
        g2d.draw(armyBackground);

        g2d.setColor(Color.BLACK);
        g2d.drawString(Integer.toString(army), x - 3, y + 5);

        g2d.setColor(Color.BLACK);
        g2d.drawString(name, x - radius, y - radius - ARMY_BACKGROUND_WITHT);
    }
}