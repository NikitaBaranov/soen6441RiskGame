package map_editor;

import java.io.*;
import java.util.ArrayList;

/**
 * This class loads the map into data structures. This loaded information
 * is passed to the editor, and is used to accept user modifications
 * And later save the data from data structures to file as map.
 * @author Dinesh Pattapu
 *
 */
public class MapLoader implements IMapLoader {
	ILoadedMap loadedMapObj;
	
	/**
	 * Constructor accepts filepath and laods it into memory.
	 * @param filePath The path of map file.
	 */
	public MapLoader(String filePath) {
		loadmap(filePath);
	}
	
	/**
	 * Function to actual load map into memory.
	 * @param filePath The path of map file.
	 */
	public void loadmap(String filePath) {
		init_data_structures();
		read_map(filePath);
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
	private void init_data_structures() {
		loadedMapObj = new LoadedMap();
		loadedMapObj.resetLoadedMap();
	}
	
	/**
	 * Function to parse map file, and call appropriate functions.
	 * @param filePath The path of map file.
	 */
	private void read_map(String filePath) {
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
					parse_map_line(sCurrentLine);
				}
				else if(currentParserMethod == 1) {
					parse_continent_line(sCurrentLine);
				}
				else if(currentParserMethod == 2) {
					parse_territory_line(sCurrentLine);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * The function to parse map line.
	 * @param lineText The line from map section.
	 */
	private void parse_map_line(String lineText) {
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
	private void parse_continent_line(String lineText) {
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
	private void parse_territory_line(String lineText) {
		String tokens[] = lineText.split(",");
		if(tokens.length < 5) {
			System.out.println("MapLoader: parse_territory_line: The territory information is incomplete. Skipped");
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
			System.out.println("MapLoader: parse_territory_line: The territory coordinates cannot be converted to integer. Defaulted to 0");
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
