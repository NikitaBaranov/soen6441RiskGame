package mapeditor;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class verifies map data in data structures.
 * @author Nikitha Papani, Dinesh Pattapu, Rodolfo Mota Miranda
 *
 */
public class Verification implements IVerification {

	public ILoadedMap map; 
	
	/**
	 * This is the function called by editor to make verifications.
	 * Also this functions calls actual verification functions.
	 * @param map The loaded map
	 * @return The success of failure.
	 */
	@Override
	public boolean verifyMap(ILoadedMap map, String path) {
		if(runChecks(map) == false) {
			return false;
		}
		
		try {
			this.map.saveMapToFile(path);
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			
			e.printStackTrace();
		} 
		
		return true;
	}

	/**
	 * This function runs the checks to verify the loaded map
	 * @param map The loaded map
	 * @return Success or Failure
	 */
	public boolean runChecks(ILoadedMap map) {
		boolean result = false;
		this.map = map;
		result = checkEmptyContinents();
		if(result == false) return result;
		
		result = checkContinentExistence();
		if(result == false) return result;
		
		result = checkTerritoryConnectivity();
		if(result == false) return result;
		
		result = checkContinentConnectivity();
		if(result == false) return result;
		
		return true;
	}
	
	/**
	 * Checks whether the continent is a connected subgraph
	 * @return Success or failure
	 */
	public boolean checkContinentConnectivity() {
		HashMap<String, Boolean> visited = new HashMap<String, Boolean>();
		ArrayList<IContinent> continents = this.map.getContinents();
		for(int j = 0; j < continents.size(); j++)
		{
			ArrayList<String> territories = continents.get(j).getTerritories();
			
			if(territories.size() < 2) {
				System.out.println("Verification: checkTerritoryConnectivity: Minimum territories in a continent for it to be a connected graph should be 2");
				return false;
			}
			
			for(int i = 0; i < territories.size(); i++) {
				visited.put(territories.get(i), false);
			}
			
			startVisiting(this.map.getTerritory(territories.get(0)), visited);
			
			for(int i = 0; i < territories.size(); i++) {
				if(visited.get(territories.get(i)) == false) {
					System.out.println("Error: As the continent " + continents.get(j).getContinentName() + " is not a connected subgraph");
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * This function checks for whether the continent exists, which is seen as
	 * continent of the territory
	 */
	public boolean checkContinentExistence() {
		ArrayList<String> territories = this.map.getListOfTerritories();
		for(int i = 0; i < territories.size(); i++) {
			ITerritory territory = this.map.getTerritory(territories.get(i));
			if(territory == null) {
				return false;
			}
			String continent = territory.getContinent();
			if(this.map.getListOfContinents().contains(continent) == false) {
				System.out.println("Error: The continent " + continent + " does not exist, but territory " + 
			territory.getTerritoryName() + " is supposed to be in that continent");
				return false;
			}
		}
		return true;
	}
	
	/**
	 * The function to check for whether there are any continents
	 * that have no territories.
	 * @return The success or failure.
	 */
	@Override
	public boolean checkEmptyContinents() {
		ArrayList<String> continents = this.map.getListOfContinents();
		for(int i = 0; i < continents.size(); i++) {
			IContinent continent = this.map.getContinent(continents.get(i));
			if(continent.numTerritories() < 2) {
				System.out.println("Error: Map cannot be saved, as " + continent.getContinentName() + " does not have enough territories");
				return false;
			}
		}
		return true;
	}

	/**
	 * This function checks whether territories are adjacent to each other.
	 * @return The success of failure.
	 */
	@Override
	public boolean checkTerritoryAdjacencyRelation() {
		ArrayList<String> territories = this.map.getListOfTerritories();
		for(int i = 0; i < territories.size(); i++)
		{
			ITerritory territory = this.map.getTerritory(territories.get(i));
			ArrayList<String> adjacents = territory.getAdjacents();
			for(int j = 0; j < adjacents.size(); j++) {
				ITerritory adjacentTerritory = this.map.getTerritory(adjacents.get(j));
				if(adjacentTerritory == null) {
					System.out.println("Error: As the adjacent territory " + adjacents.get(j) + " was not added as territory.");
					return false;
				}
				if(adjacentTerritory.checkIfAdjacent(territory.getTerritoryName()) == false) {
					System.out.println("Error: As " + adjacentTerritory.getTerritoryName() + " is not adjacent to " +
							territory.getTerritoryName() + " while " + territory.getTerritoryName() + " says, that " + 
							adjacentTerritory.getTerritoryName() + " is adjacent to it.");
					return false;
				}
			}
		}
		
		return true;
	}

	/**
	 * This function checks whether all territories together make a connected graph or not.
	 * @return The success or failure results.
	 */
	@Override
	public boolean checkTerritoryConnectivity() {
		HashMap<String, Boolean> visited = new HashMap<String, Boolean>();
		ArrayList<String> territories = this.map.getListOfTerritories();
		if(territories.size() < 2) {
			System.out.println("Verification: checkTerritoryConnectivity: Minimum territories should be 2");
			return false;
		}
		
		for(int i = 0; i < territories.size(); i++) {
			visited.put(territories.get(i), false);
		}
		
		startVisiting(this.map.getTerritory(territories.get(0)), visited);
		
		for(int i = 0; i < territories.size(); i++) {
			if(visited.get(territories.get(i)) == false) {
				System.out.println("Error: As all the territories are not connected to each other");
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * The recursive function to check for a connected graph among territories.
	 * First make a hashmap of all territories in map, with a boolean value as false.
	 * The function traverses adjacent territories of each territory in hashmap.
	 * The visited territories are then made to have their value as true.
	 * In the end, we check the hashmap. If any false value found, means not connected.
	 * @param territory The territory to be visited
	 * @param visited The hashmap containing info about visited territories.
	 */
	public void startVisiting(ITerritory territory, HashMap<String, Boolean> visited) {
		visited.put(territory.getTerritoryName(), true);
		ArrayList<String> adjacents = territory.getAdjacents();
		for(int i = 0; i < adjacents.size(); i++) {
			ITerritory adjacentTerritory = this.map.getTerritory(adjacents.get(i));
			if(adjacentTerritory == null) {
				return;
			}
			if(visited.containsKey(adjacentTerritory.getTerritoryName()) == false) {
				continue;
			}
			if(visited.get(adjacentTerritory.getTerritoryName()) == true)
			{
				continue;
			}
			startVisiting(adjacentTerritory, visited);
		}
	}

}
