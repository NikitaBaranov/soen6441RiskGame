package mapeditor;

import java.util.Scanner;

/**
 * This class provides options for modifications of territories.
 * @author Nikitha Papani, Rodolfo Mota Miranda
 *
 */
public interface IEditorTerritory {
	public void init(Scanner in, ILoadedMap loadedMapObj);
	public void displayterritoryoptions();
	public String displayterritorylist();
	public void processterritorychoice(Integer choice);
	public boolean addterritory();
	public boolean removeterritory();
	public boolean modifyterritoryX();
	public boolean modifyterritoryY();
	public boolean modifyterritoryname();
	public boolean modifyterritorycontinent();
	public boolean addadjacency();
	public boolean removeadjacency();
}
