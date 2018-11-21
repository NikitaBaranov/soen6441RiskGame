package mapeditor;

import java.util.ArrayList;

/**
 * This class is responsible for managing the continents loaded from file.
 * @author Nikitha Papani, Dinesh Pattapu, Rodolfo Mota Miranda
 *
 */
public class Continent implements IContinent {
	private String continentName;
	private Integer controlValue;
	private ArrayList<String> listOfTerritories;
        static ArrayList<IContinent> continents = new ArrayList<IContinent>();
        
        public static ArrayList<IContinent> getContinents(){
            return continents;
        }
	public static void setContinents(ArrayList<IContinent> inputContinents){
            continents = inputContinents;
        }
	/**
	 * The default constructor.
	 */
	public Continent() {
		this.listOfTerritories = new ArrayList<String>();
	}
	
	/**
	 * This constructor to initialize name and control value of continent.
	 * @param continentName Name of continent
	 * @param controlValue Control value of continent
	 */
	public Continent(String continentName, Integer controlValue) {
		this.continentName = continentName;
		this.controlValue = controlValue;
		this.listOfTerritories = new ArrayList<String>();
	}
	
	/**
	 * Getter method for continent name
	 * @return continent name
	 */
	public String getContinentName() {
		return continentName;
	}
	
	/**
	 * Setter method for continent name
	 * @param continentName Name of continent
	 */
	public void setContinentName(String continentName) {
		this.continentName = continentName; 
	}
	
	/**
	 * Getter method for control value of continent.
	 * @return Continent control value
	 */
	public Integer getControlValue() {
		return this.controlValue;
	}
	
	/**
	 * Setter method for continent control value.
	 * @param controlValue Control Value of continent.
	 */
	public void setControlValue(Integer controlValue) {
		this.controlValue = controlValue;
	}
	
	/**
	 * Function to add territory to the continent
	 * @param territoryName The name of new territory to be added
	 */
	public void addTerritory(String territoryName) {
		this.listOfTerritories.add(territoryName);
	}
	
	/**
	 * Function to remove territory from the continent
	 * @param territoryName The name of territory to be removed.
	 */
	public void removeTerritory(String territoryName) {
		this.listOfTerritories.remove(territoryName);
	}
	
	/**
	 * Returns the number of territories in this continent
	 * @return The number of territories.
	 */
	public Integer numTerritories() {
		return this.listOfTerritories.size();
	}
	
	/**
	 * Returns the list of territories.
	 * @return The list of territories.
	 */
	public ArrayList<String> getTerritories() {
		return this.listOfTerritories;
	}
}
