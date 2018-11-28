package mapeditor;

import java.io.*;
import java.util.ArrayList;

/**
 * This class loads the map into data structures. This loaded information
 * is passed to the editor, and is used to accept user modifications
 * And later save the data from data structures to file as map.
 * @author Nikitha Papani, Dinesh Pattapu, Rodolfo Mota Miranda
 *
 */
public class MapLoader implements IMapLoader {
	ILoadedMap loadedMapObj;
	/**
	 * Constructor accepts filepath and laods it into memory.
	 * @param filePath The path of map file.
	 * @param createEditFlag The flag to check whether to create map or edit it.
	 */
	public MapLoader(String filePath, Integer createEditFlag) {
		if(createEditFlag == 1)
		{
			loadmap(filePath);
		}
		else 
		{
			createMap(filePath);
		}
	}
	
	/**
	 * This function initializes the data structures for the new map.
	 * @param filePath The path of the new file.
	 */
	public void createMap(String filePath) {
		initDataStructures();
	}
	
	/**
	 * Function to actual load map into memory.
	 * @param filePath The path of map file.
	 */
	public void loadmap(String filePath) {
		initDataStructures();
		readmap(filePath);
//    if(verifyLoadedMap() == false) {
//			System.out.println("The map cannot be loaded");
//			this.loadedMapObj = null;
//		}

	}
	
	/**
	 * Function to verify the loaded map
	 */
	public boolean verifyLoadedMap() {
		IVerification verificationObj = new Verification();
		return verificationObj.runChecks(loadedMapObj);
	}
	
	/**
	 * Function to get the loaded map.
	 * @return The loaded map.
	 */
	public ILoadedMap getLoadedMap() {
		return this.loadedMapObj;
	}
	
	/**
	 * Function to initialize data structures.
	 */
	private void initDataStructures() {
		loadedMapObj = new LoadedMap();
		loadedMapObj.resetLoadedMap();
	}
	
	/**
	 * Function to parse map file, and call appropriate functions.
	 * @param filePath The path of map file.
	 */
	private void readmap(String filePath) {
		// 0 - parse_map_line, 1 - parse_continent_line, 2 - parse_territory_line
		Integer currentParserMethod = 0;
		
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				
				sCurrentLine.replaceAll(" ", "");
				sCurrentLine = sCurrentLine.toLowerCase();
				
				if(sCurrentLine.equals("[map]") == true) {
					currentParserMethod = 0;
				}
				else if(sCurrentLine.equals("[continents]") == true) {
					currentParserMethod = 1;
				}
				else if(sCurrentLine.equals("[territories]") == true) {
					currentParserMethod = 2;
				}
				else if(currentParserMethod == 0) {
					parseMapLine(sCurrentLine);
				}
				else if(currentParserMethod == 1) {
					parseContinentLine(sCurrentLine);
				}
				else if(currentParserMethod == 2) {
					parseTerritoryLine(sCurrentLine);
				}
			}

		} catch (IOException e) {
			//System.out.println("File could not be loaded. Please check path");
		}
	}
	
	/**
	 * The function to parse map line.
	 * @param lineText The line from map section.
	 */
	private void parseMapLine(String lineText) {
		if(lineText.contains("author=")) {
			Integer substrBegIdx = "author=".length();
			String author = lineText.substring(substrBegIdx);
			loadedMapObj.setAuthor(author);
		}
	}
	
	/**
	 * The function to parse continent line.
	 * @param lineText The line from continent section.
	 */
	private void parseContinentLine(String lineText) {
		if(lineText.contains("=") == false)
			return;
		String tokens[] = lineText.split("=");
		String continentName = tokens[0];
		Integer controlValue = 0;
		
		try {
			controlValue = Integer.parseInt(tokens[1]);
		}
		catch(NumberFormatException e) {
			System.out.println("MapLoader: parse_continent_line: The control value cannot be converted to integer. Defaulted to 0");
		}
		
		IContinent continent = new Continent(continentName, controlValue);
		loadedMapObj.addContinent(continent);
	}
	
	/**
	 * The function to parse territory line.
	 * @param lineText The line of territory section.
	 */
	private void parseTerritoryLine(String lineText) {
		String tokens[] = lineText.split(",");
		if(tokens.length < 5) {
			return;
		}
		
		// Load basic territory information.
		String territoryName = tokens[0];
		Integer X = 0;
		Integer Y = 0;
		String continentName = tokens[3];
		
		try {
			X = Integer.parseInt(tokens[1]);
			Y = Integer.parseInt(tokens[2]);
		}
		catch (NumberFormatException e) {
		}
		
		// Arraylist to store the adjacent territories.
		ArrayList<String> adjacents = new ArrayList<String>();
		for(int i = 4; i < tokens.length; i++)
		{
			adjacents.add(tokens[i]);
		}
		
		ITerritory territory = new Territory(territoryName, X, Y, continentName, adjacents);
		loadedMapObj.addTerritory(territory);
		IContinent continent = loadedMapObj.getContinent(continentName);
		continent.addTerritory(territoryName);
	}
	
}
