package map_editor;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class verifies map data in data structures.
 * @author Dinesh Pattapu
 *
 */
public class Verification implements IVerification {

	ILoadedMap map;
	
	/**
	 * This is the function called by editor to make verifications.
	 * Also this functions calls actual verification functions.
	 * @param map The loaded map
	 * @return The success of failure.
	 */
	@Override
	public boolean verifyMap(ILoadedMap map) {
		boolean result = false;
		this.map = map;
		result = checkEmptyContinents();
		if(result == false) return result;
		
		result = checkTerritoryAdjacencyRelation();
		if(result == false) return result;
		
		result = checkTerritoryConnectivity();
		if(result == false) return result;
		
		try {
			this.map.saveMapToFile("D:\\New folder\\temp.map");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			
			e.printStackTrace();
		} 
		
		return true;
	}

	/**
	 * The function to check for whether there are any continents
	 * taht have no territories.
	 * @return The success or failure.
	 */
	@Override
	public boolean checkEmptyContinents() {
		ArrayList<String> continents = this.map.getListOfContinents();
		for(int i = 0; i < continents.size(); i++) {
			IContinent continent = this.map.getContinent(continents.get(i));
			if(continent.numTerritories() == 0) {
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
				if(adjacentTerritory.checkIfAdjacent(territory.getTerritoryName()) == false) {
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
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * TBD
	 */
	public void checkContinentConnectivity() {
		
	}
	
	/**
	 * The recursive function to check for a connected graph among territories.
	 * First make a hashmap of all territories in map, with a boolean value as false.
	 * The function traverses adjacent territories of each territory in hashmap.
	 * The visited territories are then made to have their value as true.
	 * In the end, we check the hashmap. If any false value found, means not connected.
	 * @param territory
	 * @param visited
	 */
	public void startVisiting(ITerritory territory, HashMap<String, Boolean> visited) {
		visited.put(territory.getTerritoryName(), true);
		ArrayList<String> adjacents = territory.getAdjacents();
		for(int i = 0; i < adjacents.size(); i++) {
			ITerritory adjacentTerritory = this.map.getTerritory(adjacents.get(i));
			if(visited.get(adjacentTerritory.getTerritoryName()) == true)
			{
				continue;
			}
			startVisiting(adjacentTerritory, visited);
		}
	}

}
