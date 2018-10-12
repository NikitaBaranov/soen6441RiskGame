package map_editor;

import java.util.Scanner;

/**
 * The class lets the user modify the basic properties of map.
 * @author Nikitha Papani
 *
 */
public class EditorBasicProp implements IEditorBasicProp {
	Scanner in;
	ILoadedMap loadedMapObj;
	
	/**
	 * The function to initialiize the editor for basic properties.
	 * @param in Scanner object
	 * @param loadedMapObj The loaded map object.
	 */
	public void init(Scanner in, ILoadedMap loadedMapObj) {
		this.in = in;
		this.loadedMapObj = loadedMapObj;
		startEditorBasicProp();
	}
	
	/**
	 * The function to start editor and give options to user to modify
	 * 
	 */
	public void startEditorBasicProp() {
		Integer choice = 0;
		while(choice != 2)
		{
			display_basic_prop_options();
			choice = in.nextInt();
			process_and_edit_basic_options(choice);
		}
	}
	
	/**
	 * The function to display basic properties that user can opt for.
	 */
	@Override
	public void display_basic_prop_options() {
		System.out.println("Please select the basic property you wish to modify");
		System.out.println("1) Author");
		System.out.println("2) Go Back to Prev Menu");
		System.out.println("3) Quit");
	}

	/**
	 * The function to process user choice and call appropriate functions.
	 * @param choice the user choice
	 */
	@Override
	public void process_and_edit_basic_options(Integer choice) {
		switch(choice) {
		case 1:
			processChangeInAuthor();
			break;
		case 2:
			break;
		case 3:
			System.out.println("Good Bye!!");
			System.exit(0);
			break;
		}
	}
	
	/**
	 * The function to process the change of author name
	 */
	public void processChangeInAuthor() {
		System.out.println("Please enter new name for Author: ");
		String newAuthorName = in.next();
		this.loadedMapObj.setAuthor(newAuthorName);
	}

}
