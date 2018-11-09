package mapeditor;

import java.util.Scanner;

/**
 * The class lets the user modify the basic properties of map.
 * @author Nikitha Papani, Rodolfo Mota Miranda
 *
 */
public interface IEditorBasicProp {
	public void init(Scanner in, ILoadedMap loadedMapObj);
	public void displaybasicpropoptions();
	public void processandeditbasicoptions(Integer choice);
}
