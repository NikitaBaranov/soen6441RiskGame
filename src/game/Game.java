package game;

import model.Country;
import model.Neighbour;
import model.Player;
import utils.MapLoader;

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
}
