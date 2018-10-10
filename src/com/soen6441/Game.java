package com.soen6441;

import com.soen6441.Model.Country;
import com.soen6441.Model.Neighbour;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final int RADIUS = 15;
    java.util.List<Country> countries = new ArrayList<>();
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
        countries.add(new Country("Canada", 100, 100, RADIUS));
        countries.add(new Country("USA", 100, 300, RADIUS));
        countries.add(new Country("England", 300, 100, RADIUS));
        countries.add(new Country("France", 300, 200, RADIUS));
        countries.add(new Country("Mexico", 150, 400, RADIUS));

        neighbours.add(new Neighbour(countries.get(0), countries.get(1)));
        neighbours.add(new Neighbour(countries.get(1), countries.get(2)));
        neighbours.add(new Neighbour(countries.get(2), countries.get(3)));
        neighbours.add(new Neighbour(countries.get(1), countries.get(4)));
    }
}
