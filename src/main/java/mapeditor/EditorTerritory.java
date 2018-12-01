package mapeditor;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class provides options for modifications of territories.
 * @author Nikitha Papani, Rodolfo Mota Miranda
 *
 */
public class EditorTerritory implements IEditorTerritory {
	Scanner in;
	ILoadedMap loadedMapObj;
	
	/**
	 * This function initializes the territory editor
	 * @param in Scanner object
	 * @param loadedMapObj The loaded map in memory
	 */
	public void init(Scanner in, ILoadedMap loadedMapObj) {
		this.in = in;
		this.loadedMapObj = loadedMapObj;
		startEditorTerritory();
	}

	/**
	 * This function starts the territory editor.
	 */
	public void startEditorTerritory() {
		Integer choice = 0;
		while(choice != 9) {
			displayterritoryoptions();
			choice = in.nextInt();
			processterritorychoice(choice);
		}
	}
	
	/**
	 * The printer function to display multiple options available to user to make
	 * modifications to the territories.
	 */
	@Override
	public void displayterritoryoptions() {
		System.out.println("Please select from below:");
		System.out.println("1) Add Territory");
		System.out.println("2) Remove Territory");
		System.out.println("3) Modify Territory X");
		System.out.println("4) Modify Territory Y");
		System.out.println("5) Modify Territory Name");
		System.out.println("6) Modify Territory Continent");
		System.out.println("7) Add Adjacent Territory");
		System.out.println("8) Remove Adjacent Territory");
		System.out.println("9) Go Back");
		System.out.println("10) Quit");
	}

	/**
	 * This function displays the territory list and asks user to select one.
	 * @return The selected territory by user.
	 */
	@Override
	public String displayterritorylist() {
		ArrayList<String> territories = this.loadedMapObj.getListOfTerritories();
		while(true)
		{
			int i = 0;
			System.out.println("Please select the territory: ");
			for(i = 0; i < territories.size(); i++)
			{
				ITerritory terr = this.loadedMapObj.getTerritory(territories.get(i));
				System.out.print((i + 1) + ") " + territories.get(i) + 
						"(" + terr.getX() + "," + terr.getY() + ")" + 
						"  -- " + terr.getContinent() + " -- ");
				ArrayList<String> adjacents = terr.getAdjacents();
				for(int k = 0; k < adjacents.size(); k++) {
					System.out.print(adjacents.get(k) + ",");
				}
				System.out.println();
			}
			System.out.println((i + 1) + ") Quit");
			
			Integer choice = in.nextInt();
			if((choice < 0) || (choice > (territories.size() + 1))) {
				System.out.println("Invalid Choice. Please try again.");
			}
			
			if((choice - 1) >= territories.size()) {
				return null;
			}
			else {
				return territories.get(choice - 1);
			}
		}
	}

	/**
	 * The function to process the user choice and make modifications to territory.
	 * @param choice The user choice.
	 */
	@Override
	public void processterritorychoice(Integer choice) {
		switch(choice) {
		case 1:
			if(addterritory() == false) {
				System.out.println("EditorTerritory: process_territory_choice: Failed to add territory");
			}
			break;
		case 2:
			if(removeterritory() == false) {
				System.out.println("EditorTerritory: process_territory_choice: Failed to remove territory");
			}
			break;
		case 3:
			if(modifyterritoryX() == false) {
				System.out.println("EditorTerritory: process_territory_choice: Failed to modify territory X");
			}
			break;
		case 4:
			if(modifyterritoryY() == false) {
				System.out.println("EditorTerritory: process_territory_choice: Failed to modify territory Y");
			}
			break;
		case 5:
			if(modifyterritoryname() == false) {
				System.out.println("EditorTerritory: process_territory_choice: Failed to modify territory name");
			}
			break;
		case 6:
			if(modifyterritorycontinent() == false) {
				System.out.println("EditorTerritory: process_territory_choice: Failed to modify territory continent");
			}
			break;
		case 7:
			if(addadjacency() == false) {
				System.out.println("EditorTerritory: process_territory_choice: Failed to add adjacent territory");
			}
			break;
		case 8:
			if(removeadjacency() == false) {
				System.out.println("EditorTerritory: process_territory_choice: Failed to remove adjacent territory");
			}
			break;
		case 9:
			break;
		case 10:
			System.out.println("Goodbye !!");
			System.exit(0);
			break;
		}
	}

	/**
	 * The function to add territory
	 * @return Returns success of failure.
	 */
	@Override
	public boolean addterritory() {
		System.out.println("Please enter territory name: ");
		String territoryName = in.next();
		System.out.println("Please enter territory X value: ");
		Integer X = in.nextInt();
		System.out.println("Please enter territory Y value: ");
		Integer Y = in.nextInt();
		System.out.println("Please enter continent to which territory belongs to: ");
		String continentName = in.next();
		System.out.println("Please enter all adjacent territories to this territory with comma - \",\" between them: ");
		String adjacents = in.next();
		adjacents.replaceAll(" ", "");
		String tokens[] = adjacents.split(",");
		ArrayList<String> adjacencies = new ArrayList<String>();
		
		for(int i = 0; i < tokens.length; i++)
		{
			adjacencies.add(tokens[i]);
		}
		
		ITerritory territory = new Territory(territoryName, X, Y, continentName, adjacencies);
		this.loadedMapObj.addTerritory(territory);
		
		return true;
	}

	/**
	 * The function to remove territory
	 * @return Returns success of failure.
	 */
	@Override
	public boolean removeterritory() {
		String territoryName = displayterritorylist();
		if(territoryName == null)
			return true;
		
		ITerritory territory = this.loadedMapObj.getTerritory(territoryName);
		this.loadedMapObj.deleteTerritory(territory);
		
		return true;
	}

	/**
	 * The function to modify territory X
	 * @return Returns success or failure.
	 */
	@Override
	public boolean modifyterritoryX() {
		String territoryName = displayterritorylist();
		if(territoryName == null)
			return false;
		ITerritory territory = this.loadedMapObj.getTerritory(territoryName);
		System.out.println("Please enter new value: ");
		Integer newVal = in.nextInt();
		territory.setX(newVal);
		
		return true;
	}

	/**
	 * The function to modify territory Y
	 * @return Returns success of failure
	 */
	@Override
	public boolean modifyterritoryY() {
		String territoryName = displayterritorylist();
		if(territoryName == null)
			return false;
		ITerritory territory = this.loadedMapObj.getTerritory(territoryName);
		System.out.println("Please enter new value: ");
		Integer newVal = in.nextInt();
		territory.setY(newVal);
		
		return true;
	}

	/**
	 * The function to modify territory name.
	 * @return Returns success of failure
	 */
	@Override
	public boolean modifyterritoryname() {
		String territoryName = displayterritorylist();
		if(territoryName == null)
			return false;
		ITerritory territory = this.loadedMapObj.getTerritory(territoryName);
		System.out.println("Please enter new value: ");
		String newVal = in.next();
		territory.setTerritoryName(newVal);
		this.loadedMapObj.handleterritorynamechange(territoryName, newVal, territory);
		return true;
	}

	/**
	 * The function to modify territory's continent.
	 * @return Returns success or failure.
	 */
	@Override
	public boolean modifyterritorycontinent() {
		String territoryName = displayterritorylist();
		if(territoryName == null)
			return false;
		ITerritory territory = this.loadedMapObj.getTerritory(territoryName);
		System.out.println("Please enter new value: ");
		String newVal = in.next();
		territory.setContinent(newVal);
		return true;
	}

	/**
	 * The function to add adjacent territories.
	 * @return Returns success or failure.
	 */
	@Override
	public boolean addadjacency() {
		String territoryName = displayterritorylist();
		if(territoryName == null)
			return false;
		
		ITerritory territory = this.loadedMapObj.getTerritory(territoryName);
		System.out.println("Please enter new value: ");
		String newVal = in.next();
		territory.addAdjacent(newVal);
		
		return true;
	}

	/**
	 * The function to remove adjacency.
	 * @return Returns success or failure.
	 */
	@Override
	public boolean removeadjacency() {
		String territoryName = displayterritorylist();
		if(territoryName == null)
			return false;
		
		ITerritory territory = this.loadedMapObj.getTerritory(territoryName);
		System.out.println("Please enter new value: ");
		String newVal = in.next();
		territory.removeAdjacent(newVal);
		
		return true;
	}
}
