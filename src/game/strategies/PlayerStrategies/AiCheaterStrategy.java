package game.strategies.PlayerStrategies;

import static game.strategies.GamePhaseStrategies.BasePhaseStrategy.isGameWonBy;
import static game.strategies.GamePhaseStrategies.GamePhaseEnum.GAME_OVER;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingWorker;

import game.Game;
import game.model.Country;
import game.model.GameState;
import game.strategies.GamePhaseStrategies.GamePhaseStrategyFactory;

public class AiCheaterStrategy extends BasePlayerStrategy {

    /**
     * Method need to be implemented
     * @param gameState
     */
    @Override
    public void placeArmies(GameState gameState) {
    	System.out.println("AI Aggressive Place Armies!");
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
        System.out.println("AI Aggressive Reinforce!");
        new ReinforceWorker(gameState).execute();
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
    	System.out.println("AI Aggressive Attack!");
        new AttackWorker(gameState).execute();
    }

    /**
     * method needs to be implemented
     * @param gameState
     */
    @Override
    public void fortify(GameState gameState) {
    	System.out.println("AI Aggressive Fortify!");
        new FortifyWorker(gameState).execute();
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
        	System.out.println("Cheater place armies");
        	for (Country country : gameState.getCountries()) {
        		if(country.getPlayer() == gameState.getCurrentPlayer()) {
        			country.setArmy(country.getArmy() * 2);
        			//System.out.println("Reinforcement: Armies doubled on " + country.getName());
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
        	System.out.println("Cheater reinforces");
        	for (Country country : gameState.getCountries()) {
        		if(country.getPlayer() == gameState.getCurrentPlayer()) {
        			country.setArmy(country.getArmy() * 2);
        			System.out.println("Reinforcement: Armies doubled on " + country.getName());
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
}
