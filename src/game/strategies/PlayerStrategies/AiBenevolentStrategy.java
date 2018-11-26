package game.strategies.PlayerStrategies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import game.model.Country;
import game.model.GameState;
import game.model.enums.CardsEnum;

public class AiBenevolentStrategy extends BasePlayerStrategy {
	
    /**
     * Benevolent places armies on the weakest countries
     * @param gameState
     */
    @Override
    public void placeArmies(GameState gameState) {
    	System.out.println("Bene place armies");
    	HashMap<Integer, ArrayList<Country>> armyOnCountries = new HashMap<Integer, ArrayList<Country>>();

    	for (Country country : gameState.getCountries()) {
            if (country.getPlayer() == gameState.getCurrentPlayer()) {
            	if(armyOnCountries.containsKey(country.getArmy())) {
            		ArrayList<Country> tmp = armyOnCountries.get(country.getArmy());
            		tmp.add(country);
            	}
            	else {
            		ArrayList<Country> tmp = new ArrayList<Country>();
            		tmp.add(country);
            		armyOnCountries.put(country.getArmy(), tmp);
            	}
            }
        }
    	int playerArmies = gameState.getCurrentPlayer().getArmies();
    	
    	for(int i = 0, j = 0; i < playerArmies; j++)
    	{
    		ArrayList<Country> tmpCountries = armyOnCountries.get(j);
    		if(tmpCountries == null) continue;
    		for(int k = 0; k < tmpCountries.size(); k++) {
    			Country cntry = tmpCountries.remove(k);
    			cntry.setArmy(cntry.getArmy() + 1);
    			if(armyOnCountries.containsKey(cntry.getArmy())) {
    				armyOnCountries.get(cntry.getArmy()).add(cntry);
    			}
    			else {
    				ArrayList<Country> tC = new ArrayList<Country>();
    				tC.add(cntry);
    				armyOnCountries.put(cntry.getArmy(), tC);
    			}
    			i++;
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
    	System.out.println("Bene exchange");
    	String phrase = "";
        if (gameState.getSelectedCardsToExchange().size() == 3) {
            for (CardsEnum cardsEnum : gameState.getSelectedCardsToExchange()) {
                gameState.getCurrentPlayer().getCardsEnumIntegerMap().put(cardsEnum, gameState.getCurrentPlayer().getCardsEnumIntegerMap().get(cardsEnum) - 1);
            }
            phrase = String.join(", ", gameState.getSelectedCardsToExchange().stream().map(CardsEnum::getName).collect(Collectors.toList())) + " cards";
        } else if (gameState.getSelectedCardsToExchange().size() == 1) {
            gameState.getCurrentPlayer().getCardsEnumIntegerMap().put(gameState.getSelectedCardsToExchange().get(0), gameState.getCurrentPlayer().getCardsEnumIntegerMap().get(gameState.getSelectedCardsToExchange().get(0)) - 3);
            phrase = gameState.getSelectedCardsToExchange().get(0).getName() + " card";
        }
        gameState.getCurrentPlayer().setArmies(gameState.getCurrentPlayer().getArmies() + gameState.getArmiesToCardExchange());
        gameState.setArmiesToCardExchange(gameState.getArmiesToCardExchange() + gameState.getARMIES_TO_EXCHANGE_INCREASE());
        gameState.setCurrentTurnPhraseText("Exchanged " + phrase + " for " + gameState.getARMIES_TO_EXCHANGE_INCREASE() + " armies. Armies to place " + gameState.getCurrentPlayer().getArmies());
    }

    /**
     * method needs to be implemented
     * @param gameState
     */
    @Override
    public void reinforce(GameState gameState) {
    	System.out.println("Bene reinforces");
    	placeArmies(gameState);
        //System.out.println("Reinforcement is not implemented in " + this.getClass().getName() + " strategy.");
    }

    /**
     * method needs to be implemented
     * @param gameState
     */
    @Override
    public void beforeAndAfterAttack(GameState gameState) {
        return;
    }

    /**
     * method needs to be implemented
     * @param gameState
     */
    @Override
    public void attack(GameState gameState) {
    	System.out.println("Bene attacks");
        return;
    }

    /**
     * method needs to be implemented
     * @param gameState
     */
    @Override
    public void fortify(GameState gameState) {
    	System.out.println("Bene fortifies");
    	HashMap<Integer, ArrayList<Country>> armyOnCountries = new HashMap<Integer, ArrayList<Country>>();
    	int max = 0;
    	Country maxArmyCountry = null;
    	
    	for (Country country : gameState.getCountries()) {
            if (country.getPlayer() == gameState.getCurrentPlayer()) {
            	if(armyOnCountries.containsKey(country.getArmy())) {
            		ArrayList<Country> tmp = armyOnCountries.get(country.getArmy());
            		tmp.add(country);
            	}
            	else {
            		ArrayList<Country> tmp = new ArrayList<Country>();
            		tmp.add(country);
            		armyOnCountries.put(country.getArmy(), tmp);
            	}
            	if(country.getArmy() > max) {
            		List<Country> neighbours = country.getNeighbours();
            		for(int ctrN = 0; ctrN < neighbours.size(); ctrN++) {
            			if(neighbours.get(ctrN).getPlayer() == gameState.getCurrentPlayer()) {
                    		maxArmyCountry = country;
                    		max = country.getArmy();
            			}
            		}
            	}
            }
        }
    	
    	if(maxArmyCountry != null) {
    		int min = 100;
    		Country minCountry = null;
    		List<Country> neighbours = maxArmyCountry.getNeighbours();
    		for(int ctrN = 0; ctrN < neighbours.size(); ctrN++) {
    			if(neighbours.get(ctrN).getPlayer() == gameState.getCurrentPlayer()) {
            		if(neighbours.get(ctrN).getArmy() < min) {
            			min = neighbours.get(ctrN).getArmy();
            			minCountry = neighbours.get(ctrN);
            		}
    			}
    		}
    		
    		if(minCountry != null) {
	    		while(minCountry.getArmy() <= maxArmyCountry.getArmy()) {
	    			minCountry.setArmy(minCountry.getArmy() + 1);
	    			maxArmyCountry.setArmy(maxArmyCountry.getArmy() + 1);
	    		}
    		}
    	}
        //System.out.println("Fortifying is not implemented in " + this.getClass().getName() + " strategy.");
    }

}
