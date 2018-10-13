package game;

import model.Country;
import model.Neighbour;
import model.Player;
import utils.MapLoader;

import java.awt.*;
import java.util.List;

public class Game {
    int RADIUS = MapLoader.RADIUS;
    List<Country> countries = MapLoader.countries;
    List<Neighbour> neighbours = MapLoader.neighbours;

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
        Player player2 = new Player("Player 2 Name", Color.PINK);

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
