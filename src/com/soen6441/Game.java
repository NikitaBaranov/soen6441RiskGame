package com.soen6441;

import com.soen6441.Model.Country;
import com.soen6441.Model.Neighbour;
import com.soen6441.Model.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private final int RADIUS = 20;
    List<Country> countries = new ArrayList<>();
    List<Neighbour> neighbours = new ArrayList<>();

    public Game() {
        populateContent();
    }

    public int getRADIUS() {
        return RADIUS;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(List<Neighbour> neighbours) {
        this.neighbours = neighbours;
    }

    private void populateContent() {

        Player player1 = new Player("Player 1 Name", Color.BLUE);
        Player player2 = new Player("Player 2 Name", Color.RED);

        countries.add(new Country("Canada", 100, 100, RADIUS, player1));
        countries.add(new Country("USA", 100, 300, RADIUS, player1));
        countries.add(new Country("England", 300, 100, RADIUS, player2));
        countries.add(new Country("France", 300, 200, RADIUS, player2));
        countries.add(new Country("Mexico", 150, 400, RADIUS, player2));

        neighbours.add(new Neighbour(countries.get(0), countries.get(1)));
        neighbours.add(new Neighbour(countries.get(1), countries.get(2)));
        neighbours.add(new Neighbour(countries.get(2), countries.get(3)));
        neighbours.add(new Neighbour(countries.get(1), countries.get(4)));
    }
}
