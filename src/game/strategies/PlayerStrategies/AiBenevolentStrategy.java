package game.strategies.PlayerStrategies;

import static game.strategies.GamePhaseStrategies.BasePhaseStrategy.isGameWonBy;
import static game.strategies.GamePhaseStrategies.GamePhaseEnum.GAME_OVER;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.SwingWorker;

import game.Game;
import game.model.Country;
import game.model.GameState;
import game.strategies.GamePhaseStrategies.GamePhaseStrategyFactory;

public class AiBenevolentStrategy extends BaseStrategy {
	
    /**
     * Benevolent places armies on the weakest countries
     * @param gameState
     */
    @Override
    public void placeArmies(GameState gameState) {
    	System.out.println("AI Benevolent Place Armies!");
        new PlaceArmiesWorker(gameState).execute();
    }

    /**
     * method needs to be implemented
     * @param gameState
     */
    @Override
    public void reinforce(GameState gameState) {
    	exchangeCards(gameState);
        pauseAndRefresh(gameState, PAUSE * 2);
    	System.out.println("AI Benevolent Reinforce!");
        new PlaceArmiesWorker(gameState).execute();
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
    	new AttackWorker(gameState).execute();
    }

    /**
     * method needs to be implemented
     * @param gameState
     */
    @Override
    public void fortify(GameState gameState) {
    	System.out.println("AI Benevolent Fortify!");
        new FortifyWorker(gameState).execute();
        //System.out.println("Fortifying is not implemented in " + this.getClass().getName() + " strategy.");
    }
    

    private class PlaceArmiesWorker extends SwingWorker<Void, String> {

        GameState gameState;

        /**
         * Constructor of the class.
         *
         * @param gameState
         */
        public PlaceArmiesWorker(GameState gameState) {
            this.gameState = gameState;
        }

        /**
         * Automatic reinforcement in backgrounds.
         *
         * @return
         */
        @Override
        protected Void doInBackground() {
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

            pauseAndRefresh(gameState, PAUSE);
            return null;
        }

        /**
         * Debug method
         *
         * @param chunks
         */
        @Override
        protected void process(List<String> chunks) {
            for (String c : chunks) {
                System.out.println(c);
            }
        }

        /**
         * Automatic go to next turn when phase is done
         */
        @Override
        protected void done() {
            Game.getInstance().getGamePhaseStrategy().nextTurnButton(gameState);
        }
    }

    /**
     * Worker for reinforcement phase.
     * required to correctly repaint swing components.
     */
    private class ReinforceWorker extends SwingWorker<Void, String> {

        GameState gameState;

        /**
         * Constructor of the class.
         *
         * @param gameState
         */
        public ReinforceWorker(GameState gameState) {
            this.gameState = gameState;
        }

        /**
         * Automatic reinforcement in backgrounds.
         *
         * @return
         */
        @Override
        protected Void doInBackground() {
            /*

            You need to put your code here

             */

            pauseAndRefresh(gameState, PAUSE);
            return null;
        }

        /**
         * Debug method
         *
         * @param chunks
         */
        @Override
        protected void process(List<String> chunks) {
            for (String c : chunks) {
                System.out.println(c);
            }
        }

        /**
         * Automatic go to next turn when phase is done
         */
        @Override
        protected void done() {
            Game.getInstance().getGamePhaseStrategy().nextTurnButton(gameState);
        }
    }

    /**
     * Fortifying phase worker for AI.
     * Required for correctly repaint the swing components.
     */
    private class FortifyWorker extends SwingWorker<Void, String> {

        GameState gameState;

        /**
         * Constructor of the class
         *
         * @param gameState
         */
        public FortifyWorker(GameState gameState) {
            this.gameState = gameState;
        }

        /**
         * Do the foctification actions in the background
         *
         * @return
         */
        @Override
        protected Void doInBackground() {
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

            pauseAndRefresh(gameState, PAUSE * 2);
            return null;
        }

        /**
         * Debug method
         *
         * @param chunks
         */
        @Override
        protected void process(List<String> chunks) {
            for (String c : chunks) {
                System.out.println(c);
            }
        }

        /**
         * Automatic go to next turn when phase is done
         */
        @Override
        protected void done() {
            Game.getInstance().getGamePhaseStrategy().nextTurnButton(gameState);
        }
    }
    

    /**
     * Attack phase worker for Ai
     * Required to correctly repaint swing components
     */
    private class AttackWorker extends SwingWorker<Void, String> {

        GameState gameState;

        /**
         * Constructor of the class
         *
         * @param gameState
         */
        public AttackWorker(GameState gameState) {
            this.gameState = gameState;
        }

        /**
         * Do attack actions in the background.
         *
         * @return
         */
        @Override
        protected Void doInBackground() {
            // No code

            pauseAndRefresh(gameState, PAUSE * 2);
            return null;
        }

        /**
         * Debug method
         *
         * @param chunks
         */
        @Override
        protected void process(List<String> chunks) {
            for (String c : chunks) {
                System.out.println(c);
            }
        }

        /**
         * Automatic go to next turn when phase is done
         */
        @Override
        protected void done() {
            if (isGameWonBy(gameState, gameState.getCurrentPlayer())) {
                // TODO Add message that attacker win battle
                Game.getInstance().setGamePhaseStrategy(GamePhaseStrategyFactory.getStrategy(GAME_OVER));
                Game.getInstance().getGamePhaseStrategy().init(gameState);
            } else {
                Game.getInstance().getGamePhaseStrategy().nextTurnButton(gameState);
            }

        }
    }
}
