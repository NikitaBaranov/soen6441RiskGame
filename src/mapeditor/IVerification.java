package mapeditor;

/**
 * This class verifies map data in data structures.
 * @author Nikitha Papani, Dinesh Pattapu
 *
 */
public interface IVerification {
	public boolean verifyMap(ILoadedMap map, String path);
	public boolean checkEmptyContinents();
	public boolean checkTerritoryAdjacencyRelation();
	public boolean checkTerritoryConnectivity();
	public boolean checkContinentExistence();
}
