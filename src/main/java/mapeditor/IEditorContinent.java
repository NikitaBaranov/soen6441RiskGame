package mapeditor;

import java.util.Scanner;

/**
 * The class for editing the continent
 * @author Nikitha Papani, Rodolfo Mota Miranda
 *
 */
public interface IEditorContinent {
	public void init(Scanner in, ILoadedMap loadedMapObj);
	public void displaycontinentoptions();
	public String displaycontinentlist();
	public void processcontinentchoice(Integer choice);
	public boolean addcontinent();
	public boolean removecontinent();
	public boolean modifycontinentcontrolvalue();
	public boolean modifycontinentname();
}
