package map_editor;

import java.util.Scanner;

/**
 * This class initializes the map editor and asks user for options
 * to modify the map. It also calls the loader to load map in memory.
 * And provides user an option to verify the changes made and save the
 * modified map to file.
 * @author Nikitha Papani
 *
 */
public class Editor implements IEditor {

	private ILoadedMap loadedMapObj;
	Scanner in;
	
	/**
	 * Default constructor for editor.
	 */
	public Editor() {
		this.in = new Scanner(System.in);
	}
	
	/**
	 * The function to initialize editor, and call the map loader.
	 */
	public void initEditor() {
		getMapToEdit();
		startEditor();
	}
	
	/**
	 * This function starts the editor by giving user options to make changes.
	 */
	public void startEditor() {
		Integer choice = 0;
		while(choice != 5) {
			display_editor_options();
			choice = in.nextInt();
			process_editor_choice(choice);
		}
	}
	
	/**
	 * The actual function that calls loader and loads the map.
	 */
	public void getMapToEdit() {
		System.out.println("Please enter the full path of map you want to edit: ");
		String mapPath = in.next();
		IMapLoader mapLoaderObj = new MapLoader(mapPath);
		this.loadedMapObj = mapLoaderObj.getLoadedMap();
	}
	
	/**
	 * The printer function to display functions to user.
	 */
	public void display_editor_options() {
		System.out.println("Loaded Map Made by " + this.loadedMapObj.getAuthor());
		System.out.println("Please select what would you like to modify: ");
		System.out.println("1) Basic Properties of Map");
		System.out.println("2) Continents");
		System.out.println("3) Territories");
		System.out.println("4) Verify and Save to File");
		System.out.println("5) Quit");
	}

	/**
	 * The function to process user choice.
	 * @param choice The choice entered by user.
	 */
	public void process_editor_choice(Integer choice) {
		switch(choice) {
		case 1:
			IEditorBasicProp edBasic = new EditorBasicProp();
			edBasic.init(this.in, loadedMapObj);
			break;
		case 2:
			IEditorContinent edContinent = new EditorContinent();
			edContinent.init(in, loadedMapObj);
			break;
		case 3:
			IEditorTerritory edTerritory = new EditorTerritory();
			edTerritory.init(in, loadedMapObj);
			break;
		case 4:
			Verification verificationObj = new Verification();
			verificationObj.verifyMap(loadedMapObj);
			break;
		case 5:
			System.out.println("Good Bye!!");
			System.exit(0);
			break;
		default:
			System.out.println("Invalid Choice. Try again.");	
		}
	}
	
	/**
	 * The function to return loaded map.
	 */
	public ILoadedMap getLoadedMap() {
		return this.loadedMapObj;
	}

}
