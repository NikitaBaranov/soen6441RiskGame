package mapeditor;

import java.util.ArrayList;

/**
 * This class is responsible for managing the continents loaded from file.
 * @author Nikitha Papani, Dinesh Pattapu, Rodolfo Mota Miranda
 *
 */
public interface IContinent {
	public String getContinentName();
	public void setContinentName(String continentName);
	public Integer getControlValue();
	public void setControlValue(Integer controlValue);
	public void addTerritory(String territoryName);
	public void removeTerritory(String territoryName);
	public Integer numTerritories();
	public ArrayList<String> getTerritories();
}
