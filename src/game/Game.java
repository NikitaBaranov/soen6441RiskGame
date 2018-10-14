package game;

import game.model.Country;
import game.model.Neighbour;
import game.utils.MapLoader;

import java.util.List;

public class Game {
    public int RADIUS = MapLoader.RADIUS;
    public List<Country> countries = MapLoader.countries;
    public List<Neighbour> neighbours = MapLoader.neighbours;

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
