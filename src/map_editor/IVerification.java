package map_editor;

/**
 * This class verifies map data in data structures.
 * @author Dinesh Pattapu
 *
 */
public interface IVerification {
	public boolean verifyMap(ILoadedMap map);
	public boolean checkEmptyContinents();
	public boolean checkTerritoryAdjacencyRelation();
	public boolean checkTerritoryConnectivity();
}
