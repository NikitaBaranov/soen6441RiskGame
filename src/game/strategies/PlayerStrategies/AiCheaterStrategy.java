package game.strategies.PlayerStrategies;

import java.util.ArrayList;
import java.util.List;

import game.model.Country;
import game.model.GameState;

public class AiCheaterStrategy extends BasePlayerStrategy {

    /**
     * Method need to be implemented
     * @param gameState
     */
    @Override
    public void placeArmies(GameState gameState) {
    	System.out.println("Cheater place armies");
    	for (Country country : gameState.getCountries()) {
    		if(country.getPlayer() == gameState.getCurrentPlayer()) {
    			country.setArmy(country.getArmy() * 2);
    			//System.out.println("Reinforcement: Armies doubled on " + country.getName());
    		}
    	}
    	gameState.getCurrentPlayer().setArmies(0);
    }

    /**
     * method needs to be implemented
     * @param gameState
     */
    @Override
    public void exchange(GameState gameState) {
        //System.out.println("Exchange is not implemented in " + this.getClass().getName() + " strategy.");
    }

    /**
     * method needs to be implemented
     * @param gameState
     */
    @Override
    public void reinforce(GameState gameState) {
    	System.out.println("Cheater reinforces");
    	for (Country country : gameState.getCountries()) {
    		if(country.getPlayer() == gameState.getCurrentPlayer()) {
    			country.setArmy(country.getArmy() * 2);
    			System.out.println("Reinforcement: Armies doubled on " + country.getName());
    		}
    	}
    	gameState.getCurrentPlayer().setArmies(0);
    }

    /**
     * method needs to be implemented
     * @param gameState
     */
    @Override
    public void beforeAndAfterAttack(GameState gameState) {
        //System.out.println("BeforeAndAfterAttack is not implemented in " + this.getClass().getName() + " strategy.");
    }

    /**
     * method needs to be implemented
     * @param gameState
     */
    @Override
    public void attack(GameState gameState) {
    	System.out.println("Cheater attacks");
    	ArrayList<Country> countriesCaptured = new ArrayList<Country>();
    	for (Country country : gameState.getCountries()) {
    		if(country.getPlayer() == gameState.getCurrentPlayer()) {
    			List<Country> neighbours = country.getNeighbours();
    			for(Country neighbour : neighbours) {
    				if(neighbour.getPlayer() != gameState.getCurrentPlayer() && countriesCaptured.contains(neighbour) == false)
    					countriesCaptured.add(neighbour);
    			}
    		}
    	}
    	
    	System.out.println("Countries to capture: " + countriesCaptured.size());
    	for (Country country : countriesCaptured) {
    		country.setPlayer(gameState.getCurrentPlayer());
    	}
    	return;
    }

    /**
     * method needs to be implemented
     * @param gameState
     */
    @Override
    public void fortify(GameState gameState) {
    	System.out.println("Cheater fortifies");
    	for (Country country : gameState.getCountries()) {
    		if(country.getPlayer() == gameState.getCurrentPlayer()) {
    			List<Country> neighbours = country.getNeighbours();
    			for(Country neighbour : neighbours) {
    				if(neighbour.getPlayer() != gameState.getCurrentPlayer()) {
    					country.setArmy(country.getArmy() * 2);
    					//System.out.println("Fortify: Armies doubled on " + country.getName());
    				}
    			}
    		}
    	}
    }
}
