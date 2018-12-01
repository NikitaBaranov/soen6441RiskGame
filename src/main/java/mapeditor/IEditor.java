package mapeditor;

/**
 * This class initializes the map editor and asks user for options
 * to modify the map. It also calls the loader to load map in memory.
 * And provides user an option to verify the changes made and save the
 * modified map to file.
 * @author Nikitha Papani, Dinesh Pattapu , Rodolfo Mota Miranda
 *
 */
public interface IEditor {
	public void displayeditoroptions();
	public void processeditorchoice(Integer choice);
	public ILoadedMap getLoadedMap();
}
