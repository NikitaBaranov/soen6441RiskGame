package map_editor;

import java.util.ArrayList;

/**
 * The class to load territory in data structures.
 * @author Dinesh Pattapu
 *
 */
public interface ITerritory {
	public String getTerritoryName();
	public void setTerritoryName(String territory_name);
	public Integer getX();
	public void setX(Integer x);
	public Integer getY();
	public void setY(Integer y);
	public String getContinent();
	public void setContinent(String continent);
	public ArrayList<String> getAdjacents();
	public void setAdjacents(ArrayList<String> adjacents);
	public void addAdjacent(String adjacentName);
	public void removeAdjacent(String adjacentName);
	public boolean checkIfAdjacent(String territoryName);
}
