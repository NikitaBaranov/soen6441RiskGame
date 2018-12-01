package mapeditor;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The class for editing the continent
 * @author Nikitha Papani, Rodolfo Mota Miranda
 *
 */
public class EditorContinent implements IEditorContinent {
	Scanner in;
	ILoadedMap loadedMapObj;
	
	/**
	 * The function to initialize the continent editor.
	 * @param in Scanner object
	 * @param loadedMapObj The loaded map object
	 */
	public void init(Scanner in, ILoadedMap loadedMapObj) {
		this.in = in;
		this.loadedMapObj = loadedMapObj;
		startEditorContinent();
	}

	/**
	 * The function to start editor for continents.
	 */
	public void startEditorContinent() {
		Integer choice = 0;
		while(choice != 5) {
			displaycontinentoptions();
			choice = in.nextInt();
			processcontinentchoice(choice);
		}
	}
	
	/**
	 * The function to display options to user for modifications.
	 */
	@Override
	public void displaycontinentoptions() {
		System.out.println("Please select from below: ");
		System.out.println("1) Add Continent");
		System.out.println("2) Remove Continent");
		System.out.println("3) Modify name of continent");
		System.out.println("4) Modify control value of continent");
		System.out.println("5) Go Back");
		System.out.println("6) Quit");
	}

	/**
	 * The function to display list of continents.
	 * @return The choice of user based on selection of continent for modification.
	 */
	@Override
	public String displaycontinentlist() {
		ArrayList<String> listOfContinents = this.loadedMapObj.getListOfContinents();

		while(true)
		{
			int i = 0;
			System.out.println("Please select the continent: ");
			for(i = 0; i < listOfContinents.size(); i++)
			{
				System.out.println((i + 1) + ") " + listOfContinents.get(i) + 
						"=" + this.loadedMapObj.getContinent(listOfContinents.get(i)).getControlValue());
			}
			System.out.println((i + 1) + ") Quit");
			
			Integer choice = in.nextInt();
			if((choice < 0) || (choice > (listOfContinents.size() + 1))) {
				System.out.println("Invalid Choice. Please try again.");
			}
			
			if(choice == (listOfContinents.size() + 1)) {
				return null;
			}
			else {
				return listOfContinents.get(choice - 1);
			}
		}
	}

	/**
	 * The function to process continent choice made by choice for modification and call
	 * appropriate functions..
	 * @param choice The choice of user.
	 */
	@Override
	public void processcontinentchoice(Integer choice) {
		switch(choice) {
		case 1:
			if(addcontinent() == false) {
				System.out.println("EditorContinent: process_continent_choice: Failed to add continent");
			}
			break;
		case 2:
			if(removecontinent() == false) {
				System.out.println("EditorContinent: process_continent_choice: Failed to remove continent");
			}
			break;
		case 3:
			if(modifycontinentname() == false) {
				System.out.println("EditorContinent: process_continent_choice: Failed to modify name of continent");
			}
			break;
		case 4:
			if(modifycontinentcontrolvalue() == false) {
				System.out.println("EditorContinent: process_continent_choice: Failed to modify control value of continent");
			}
			break;
		case 5:
			break;
		case 6:
			System.out.println("Goodbye !!");
			System.exit(0);
			break;
		}
	}

	/**
	 * The function to add continent.
	 * @return Returns success or failure.
	 */
	@Override
	public boolean addcontinent() {
		System.out.println("Please enter the continent name: ");
		String name = in.next();
		System.out.println("Please enter control value: ");
		Integer controlValue = in.nextInt();
		IContinent continent = new Continent(name, controlValue);
		this.loadedMapObj.addContinent(continent);
		return true;
	}

	/**
	 * The function to remove continent
	 * @return Returns success of failure.
	 */
	@Override
	public boolean removecontinent() {
		String continentName = displaycontinentlist();
		if(continentName == null)
			return false;
		IContinent continent = this.loadedMapObj.getContinent(continentName);
		if(continent == null) {
			System.out.println("EditorContinent: RemoveContinent: System critical error. Continent not found");
			return false;
		}
		this.loadedMapObj.deleteContinent(continent);
		return true;
	}

	/**
	 * The function to modify continent control value.
	 * @return Returns success of failure.
	 */
	@Override
	public boolean modifycontinentcontrolvalue() {
		String continentName = displaycontinentlist();
		if(continentName == null)
			return false;
		
		System.out.println("Please enter the new control value: ");
		Integer newControlValue = in.nextInt();
		IContinent continent = this.loadedMapObj.getContinent(continentName);
		continent.setControlValue(newControlValue);
		
		return true;
	}

	/**
	 * The function to modify continent name.
	 * @return Returns success of failure.
	 */
	@Override
	public boolean modifycontinentname() {
		String continentName = displaycontinentlist();
		if(continentName == null)
			return false;
		System.out.println("Please enter the new name for continent: ");
		String newName = in.next();
		IContinent continent = this.loadedMapObj.getContinent(continentName);
		continent.setContinentName(newName);
		this.loadedMapObj.handlecontinentnamechange(continentName, newName, continent);
		return true;
	}
}
