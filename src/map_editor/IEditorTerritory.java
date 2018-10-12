package map_editor;

import java.util.Scanner;

/**
 * This class provides options for modifications of territories.
 * @author Nikitha Papani
 *
 */
public interface IEditorTerritory {
	public void init(Scanner in, ILoadedMap loadedMapObj);
	public void display_territory_options();
	public String display_territory_list();
	public void process_territory_choice(Integer choice);
	public boolean add_territory();
	public boolean remove_territory();
	public boolean modify_territory_X();
	public boolean modify_territory_Y();
	public boolean modify_territory_name();
	public boolean modify_territory_continent();
	public boolean add_adjacency();
	public boolean remove_adjacency();
}
