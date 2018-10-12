package map_editor;

import java.util.Scanner;

/**
 * The class lets the user modify the basic properties of map.
 * @author Nikitha Papani
 *
 */
public interface IEditorBasicProp {
	public void init(Scanner in, ILoadedMap loadedMapObj);
	public void display_basic_prop_options();
	public void process_and_edit_basic_options(Integer choice);
}
