package map_editor;

import java.util.ArrayList;

/**
 * The class to load territory in data structures.
 * @author Dinesh Pattapu
 *
 */
public class Territory implements ITerritory {
	private String territory_name;
	private Integer X;
	private Integer Y;
	private String continent;
	private ArrayList<String> adjacents;
	
	/**
	 * The constructor to initialize the class variables.
	 * @param territory_name The territory name
	 * @param X The territory X cords.
	 * @param Y The territory Y cords.
	 * @param continent The continent in which territory belongs.
	 * @param adjacents The adjacent countries to this territory.
	 */
	public Territory(String territory_name, Integer X, Integer Y, 
			String continent, ArrayList<String> adjacents) {
		this.territory_name = territory_name;
		this.X = X;
		this.Y = Y;
		this.continent = continent;
		this.adjacents = adjacents;
	}
	
	/**
	 * The function to get territory name.
	 * @return The name of territory
	 */
	public String getTerritoryName() {
		return territory_name;
	}
	
	/**
	 * The function to set territory name.
	 * @param territory_name The new territory name
	 */
	public void setTerritoryName(String territory_name) {
		this.territory_name = territory_name;
	}
	
	/**
	 * The function to get X cords.
	 * @return Returns the X cords
	 */
	public Integer getX() {
		return X;
	}
	
	/**
	 * The function to set X cords.
	 * @param x The new value of x.
	 */
	public void setX(Integer x) {
		X = x;
	}
	
	/**
	 * The function to get Y cords.
	 * @return Y The new value of y.
	 */
	public Integer getY() {
		return Y;
	}
	
	/**
	 * The function to set the Y cords.
	 * @param y The new value of y.
	 */
	public void setY(Integer y) {
		Y = y;
	}
	
	/**
	 * The function to get the continent name.
	 * @return The name of continent.
	 */
	public String getContinent() {
		return continent;
	}
	
	/**
	 * The function to set the name of continent name.
	 * @param The new name of continent.
	 */
	public void setContinent(String continent) {
		this.continent = continent;
	}
	
	/**
	 * The function to get adjacent territories.
	 * @return The list of adjacent territories.
	 */
	public ArrayList<String> getAdjacents() {
		return adjacents;
	}
	
	/**
	 * The function to set adjacent territories.
	 * @param adjacents The list of new adjacent territories.
	 */
	public void setAdjacents(ArrayList<String> adjacents) {
		this.adjacents = adjacents;
	}
	
	/**
	 * The function to add new adjacent territories.
	 * @param adjacentName The name of new adjacent country.
	 */
	public void addAdjacent(String adjacentName) {
		this.adjacents.add(adjacentName);
	}
	
	/**
	 * The function to remove adjacent territory
	 * @param adjacentName The name of adjacent territory to be removed
	 */
	public void removeAdjacent(String adjacentName) {
		this.adjacents.remove(adjacentName);
	}
	
	/**
	 * This function checks whether received territory is adjacent to this or not.
	 * @param territoryName The name of territory to be checked for adjacency.
	 * @return The sucess of failure results.
	 */
	public boolean checkIfAdjacent(String territoryName) {
		if(this.adjacents.contains(territoryName)) {
			return true;
		}
		return false;
	}
	
}
