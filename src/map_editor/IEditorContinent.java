package map_editor;

import java.util.Scanner;

/**
 * The class for editing the continent
 * @author Nikitha Papani
 *
 */
public interface IEditorContinent {
	public void init(Scanner in, ILoadedMap loadedMapObj);
	public void display_continent_options();
	public String display_continent_list();
	public void process_continent_choice(Integer choice);
	public boolean add_continent();
	public boolean remove_continent();
	public boolean modify_continent_control_value();
	public boolean modify_continent_name();
}
