package mapeditor;

import java.util.Scanner;

/**
 * This class initializes the map editor and asks user for options
 * to create and modify the map. It also calls the loader to load map in memory.
 * And provides user an option to verify the changes made and save the
 * modified map to file.
 * @author Nikitha Papani, Dinesh Pattapu, Rodolfo Mota Miranda
 *
 */
public class Editor implements IEditor {

	private ILoadedMap loadedMapObj;
	Scanner in;
	String path;
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
		createOrEdit();
	}
	
	/**
	 * This function asks user, whether he wants to create map or edit the previously created map.
	 */
	public void createOrEdit() {
		String choice;
		while(true) {
			System.out.println("Please make your choice: ");
			System.out.println("1) Edit Map");
			System.out.println("2) Create Map");
			System.out.println("3) Quit");
			choice = in.next();
			if(choice.equals("1") == true) {
				if(getMapToEdit() == false) {
					continue;
				}
			}
			else if(choice.equals("2") == true) {
				if(createMapToEdit() == false) {
					continue;
				}
			}
			else if(choice.equals("3") == true) {
				System.out.println("Good Bye!");
				System.exit(0);
			}
			startEditor();
		}
	}
	
	/**
	 * This function starts the editor by giving user options to make changes.
	 */
	public void startEditor() {
		Integer choice = 0;
		while((choice != 5) && (choice != 4)) {
			displayeditoroptions();
			choice = in.nextInt();
			processeditorchoice(choice);
		}
	}
	
	/**
	 * Creates a new map to edit.
	 * @return Success or Failure
	 */
	public boolean createMapToEdit() {
		System.out.println("Please enter the full path with name of new map: ");
		String mapPath = "";
		while(mapPath.length() < 5) {
			mapPath = in.next();
			if(mapPath.length() < 5) {
				System.out.println("Please enter proper path for map : ");
			}
		}
		this.path = mapPath;
		IMapLoader mapLoaderObj = new MapLoader(mapPath, 2);
		this.loadedMapObj = mapLoaderObj.getLoadedMap();
		if(this.loadedMapObj == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * The actual function that calls loader and loads the map.
	 * @return Success or Failure
	 */
	public boolean getMapToEdit() {
		System.out.println("Please enter the full path of map you want to edit: ");
		String mapPath = "";
		while(mapPath.length() < 5) {
			mapPath = in.next();
			if(mapPath.length() < 5) {
				System.out.println("Please enter proper path for map : ");
			}
		}
		this.path = mapPath;
		IMapLoader mapLoaderObj = new MapLoader(mapPath, 1);
		this.loadedMapObj = mapLoaderObj.getLoadedMap();
		if(this.loadedMapObj == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * The printer function to display functions to user.
	 */
	public void displayeditoroptions() {
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
	public void processeditorchoice(Integer choice) {
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
			verificationObj.verifyMap(loadedMapObj, path);
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
