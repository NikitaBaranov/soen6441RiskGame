package map_editor;

/**
 * This class initializes the map editor and asks user for options
 * to modify the map. It also calls the loader to load map in memory.
 * And provides user an option to verify the changes made and save the
 * modified map to file.
 * @author Nikitha Papani
 *
 */
public interface IEditor {
	public void display_editor_options();
	public void process_editor_choice(Integer choice);
	public ILoadedMap getLoadedMap();
}
