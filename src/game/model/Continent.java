package game.model;

import java.util.ArrayList;
import java.util.List;

public class Continent {
    private String name = "";
    private List<Country> countryList = new ArrayList<>();
    private int bonus = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Country> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }
}
