package mapeditor;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * This class contains data structures into which the map is loaded.
 * @author Nikitha Papani & Dinesh Pattapu & Rodolfo Miranda
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
        public ArrayList<IContinent> getContinents();
	public ArrayList<String> getListOfTerritories();
        public ArrayList<ITerritory> getTerritories();
	public boolean saveMapToFile(String path) throws FileNotFoundException, UnsupportedEncodingException;
	public void handlecontinentnamechange(String oldName, String newName, IContinent continent);
	public void handleterritorynamechange(String oldName, String newName, ITerritory territory);
}
