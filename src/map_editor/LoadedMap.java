package map_editor;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This class contains data structures into which the map is loaded.
 * @author Dinesh Pattapu
 *
 */
public class LoadedMap implements ILoadedMap {
	private String author;
	private HashMap<String, IContinent> listOfContinents;
	private HashMap<String, ITerritory> listOfTerritories;
	
	/**
	 * The default constructor that resets the data structures.
	 */
	public LoadedMap() {
		resetLoadedMap();
	}
	
	/**
	 * The function to reset data structures.
	 */
	public void resetLoadedMap() {
		this.author = "";
		this.listOfContinents = new HashMap<String, IContinent>(2, 2);
		this.listOfTerritories = new HashMap<String, ITerritory>(2, 2);
	}
	
	/**
	 * The function to set the author name
	 * @param author The name of author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	
	/**
	 * The function to get the author name
	 * @return returns the author of map.
	 */
	public String getAuthor() {
		return this.author;
	}
	
	/**
	 * The function to add continent.
	 * @param continent The new continent to be added.
	 * @return The return value whether success of failure.
	 */
	public boolean addContinent(IContinent continent) {
		Integer listSize = this.listOfContinents.size();
		
		if(continent == null) {
			System.out.println("Continent: addContinent: The continent is null");
			return false;
		}
		
		String continentName = continent.getContinentName();
		if(continentName == null) {
			System.out.println("Continent: addContinent: The continent doesnt have a name");
			return false;
		}
		
		this.listOfContinents.put(continentName, continent);
		if(this.listOfContinents.size() == (listSize + 1)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * The function to delete continent
	 * @param continent The continent to be deleted.
	 * @return The return value whether function succeeded or failed.
	 */
	public boolean deleteContinent(IContinent continent) {
		Integer listSize = this.listOfContinents.size();
		if(continent == null) {
			System.out.println("Continent: addContinent: The continent is null");
			return false;
		}
		
		String continentName = continent.getContinentName();
		if(continentName == null) {
			System.out.println("Continent: addContinent: The continent doesnt have a name");
			return false;
		}
		
		this.listOfContinents.remove(continentName);
		if(this.listOfContinents.size() == (listSize - 1)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Function to add territory
	 * @param territory The territory to be added.
	 * @return Success of failure
	 */
	public boolean addTerritory(ITerritory territory) {
		Integer listSize = this.listOfTerritories.size();
		
		if(territory == null) {
			System.out.println("Continent: addITerritory: The territory is null");
			return false;
		}
		
		String territoryName = territory.getTerritoryName();
		if(territoryName == null) {
			System.out.println("Continent: addITerritory: The territory doesnt have a name");
			return false;
		}
		if(this.listOfContinents.containsKey(territory.getContinent()) == false) {
			System.out.println("The continent does not exist. Please first create the continent");
			return false;
		}
		this.listOfTerritories.put(territoryName, territory);
		if(this.listOfTerritories.size() == (listSize + 1)) {
			IContinent tmpContinent = this.listOfContinents.get(territory.getContinent());
			tmpContinent.addTerritory(territoryName);
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Function to delete territory.
	 * @param territory The territory to be deleted.
	 * @return Success or failure.
	 */
	public boolean deleteTerritory(ITerritory territory) {
		Integer listSize = this.listOfTerritories.size();
		if(territory == null) {
			System.out.println("Continent: addTerritory: The territory is null");
			return false;
		}
		
		String territoryName = territory.getTerritoryName();
		if(territoryName == null) {
			System.out.println("Continent: addTerritory: The territory doesnt have a name");
			return false;
		}
		
		IContinent tmpContinent = this.listOfContinents.get(territory.getContinent());
		tmpContinent.removeTerritory(territoryName);
		this.listOfTerritories.remove(territoryName);
		if(this.listOfTerritories.size() == (listSize - 1)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * The function to get continent by name.
	 * @param continentName The name of continent.
	 * @return The continent object found by name.
	 */
	public IContinent getContinent(String continentName) {
		return this.listOfContinents.get(continentName);
	}
	
	/**
	 * The function to get territory by name.
	 * @param territoryName The name of territory.
	 * @return The territory object found by name.
	 */
	public ITerritory getTerritory(String territoryName) {
		return this.listOfTerritories.get(territoryName);
	}
	
	/**
	 * The function to get list of continents.
	 * @return The arraylist of continents.
	 */
	public ArrayList<String> getListOfContinents() {
		return new ArrayList<String>(listOfContinents.keySet());
	}
	
	/**
	 * The function to get list of territories.
	 * @return The arraylist of territories.
	 */
	public ArrayList<String> getListOfTerritories() {
		return new ArrayList<String>(listOfTerritories.keySet());
	}
	
	/**
	 * The function to handle continent name change.
	 * @param oldName Old name of continent.
	 * @param newName New name of continent.
	 * @param continent The continent object to be modified.
	 * 
	 */
	public void handle_continent_name_change(String oldName, String newName, IContinent continent) {
		this.listOfContinents.remove(oldName);
		this.listOfContinents.put(newName, continent);
	}
	
	/**
	 * The function to handle territory name change.
	 * @param oldName Old name of territory.
	 * @param newName New name of territory.
	 * @param territory object to be modified.
	 */
	public void handle_territory_name_change(String oldName, String newName, ITerritory territory) {
		this.listOfTerritories.remove(oldName);
		this.listOfTerritories.put(newName, territory);
	}
	
	/**
	 * The function to save map to file.
	 * @param path The path of file.
	 * @return Returns success or failure.
	 */
	@SuppressWarnings("rawtypes")
	public boolean saveMapToFile(String path) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter(path, "UTF-8");
		writer.println("[Map]");
		writer.println("author=" + this.author);
		writer.println();
		writer.println("[Continents]");
		
		Iterator continents = this.listOfContinents.entrySet().iterator();
		while(continents.hasNext()) {
			Map.Entry pair = (Map.Entry)continents.next();
			IContinent cont = (IContinent) pair.getValue();
			writer.println(cont.getContinentName() + "=" + cont.getControlValue());
		}
		
		writer.println();
		writer.println("[Territories]");
		
		Iterator territories = this.listOfTerritories.entrySet().iterator();
		while(territories.hasNext()) {
			Map.Entry pair = (Map.Entry)territories.next();
			ITerritory terr = (ITerritory) pair.getValue();
			writer.print(terr.getTerritoryName() + "," + terr.getX() + "," +
					terr.getY() + "," + terr.getContinent());
			
			ArrayList<String> adjacents = terr.getAdjacents();
			for(int i = 0; i < adjacents.size(); i++) {
				writer.print("," + adjacents.get(i));
			}
			writer.println("");
			
		}

		writer.close();		
		return true;
	}
}
