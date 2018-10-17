package game.model;


import java.awt.*;

public class Player {
    private String name = "";

    private Color color = Color.BLACK;
    private int infantry = 0;
    private int cavalry = 0;
    private int artillery = 0;
    private int wildcards = 0;
    private int bonus = 0;
    private int armies = 5;

    public Player() {
    }

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getInfantry() {
        return infantry;
    }

    public void setInfantry(int infantry) {
        this.infantry = infantry;
    }

    public int getCavalry() {
        return cavalry;
    }

    public void setCavalry(int cavalry) {
        this.cavalry = cavalry;
    }

    public int getArtillery() {
        return artillery;
    }

    public void setArtillery(int artillery) {
        this.artillery = artillery;
    }

    public int getWildcards() {
        return wildcards;
    }

    public void setWildcards(int wildcards) {
        this.wildcards = wildcards;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public int getArmies() {
        return armies;
    }

    public void setArmies(int armies) {
        this.armies = armies;
    }
}