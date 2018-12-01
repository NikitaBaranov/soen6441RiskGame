package mapeditor;

/**
 * This class loads the map into data structures. This loaded information
 * is passed to the editor, and is used to accept user modifications
 * And later save the data from data structures to file as map.
 * @author Nikitha Papani, Dinesh Pattapu, Rodolfo Mota Miranda
 *
 */
public interface IMapLoader {
	public void loadmap(String filePath);
	public ILoadedMap getLoadedMap();
}
