package map_editor;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * This class contains data structures into which the map is loaded.
 * @author Dinesh Pattapu
 *
 */
public interface ILoadedMap {
	public void setAuthor(String author);
	public String getAuthor();
	public boolean addContinent(IContinent continent);
	public boolean deleteContinent(IContinent continent);
	public void resetLoadedMap();
	public boolean addTerritory(ITerritory territory);
	public boolean deleteTerritory(ITerritory territory);
	public IContinent getContinent(String continentName);
	public ITerritory getTerritory(String territoryName);
	public ArrayList<String> getListOfContinents();
	public ArrayList<String> getListOfTerritories();
	public boolean saveMapToFile(String path) throws FileNotFoundException, UnsupportedEncodingException;
	public void handle_continent_name_change(String oldName, String newName, IContinent continent);
	public void handle_territory_name_change(String oldName, String newName, ITerritory territory);
}
