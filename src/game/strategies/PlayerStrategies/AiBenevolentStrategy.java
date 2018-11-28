package game.strategies.PlayerStrategies;

import static game.strategies.GamePhaseStrategies.BasePhaseStrategy.isGameWonBy;
import static game.strategies.GamePhaseStrategies.GamePhaseEnum.GAME_OVER;
import static game.utils.MapFunctionsUtil.countNeighbors;
import static game.utils.MapFunctionsUtil.getCountryWithMaxArmy;
import static game.utils.MapFunctionsUtil.getCountryWithMaxOpponentNeighbours;

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
        new ReinforceWorker(gameState).execute();
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
            Country toPlaceArmy = null;
            if (toPlaceArmy == null) {
                toPlaceArmy = getCountryWithMaxOpponentNeighbours(gameState);
            }
            if (toPlaceArmy != null) {
                toPlaceArmy.setSelected(true);
                toPlaceArmy.setArmy(toPlaceArmy.getArmy() + 1);
                gameState.getCurrentPlayer().setArmies(gameState.getCurrentPlayer().getArmies() - 1);
                String message = gameState.getCurrentPlayer().getName() + " placed army to " + toPlaceArmy.getName() + ". Armies to place: " + gameState.getCurrentPlayer().getArmies();
                gameState.setCurrentTurnPhraseText(message);
                publish(message);
            }
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
            HashMap<Integer, ArrayList<Country>> armyOnCountries = new HashMap<Integer, ArrayList<Country>>();
            int min = 100;
            Country minCountry = null;

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
                    if(country.getArmy() <= min) {
                        List<Country> neighbours = country.getNeighbours();
                        for(int ctrN = 0; ctrN < neighbours.size(); ctrN++) {
                            if(neighbours.get(ctrN).getPlayer() == gameState.getCurrentPlayer()) {
                                minCountry = country;
                                min = country.getArmy();
                            }
                        }
                    }
                }
            }

            if(minCountry != null) {
                int max = 1;
                Country maxArmyCountry = null;
                List<Country> neighbours = minCountry.getNeighbours();
                for(int ctrN = 0; ctrN < neighbours.size(); ctrN++) {
                    if(neighbours.get(ctrN).getPlayer() == gameState.getCurrentPlayer()) {
                        if(neighbours.get(ctrN).getArmy() > max) {
                            max = neighbours.get(ctrN).getArmy();
                            maxArmyCountry = neighbours.get(ctrN);
                        }
                    }
                }

                int armyToFortify = maxArmyCountry.getArmy() - 1;

                if(maxArmyCountry != null) {
                    minCountry.setSelected(true);
                    maxArmyCountry.setSelected(true);
                    pauseAndRefresh(gameState, PAUSE * 3);
                    minCountry.setArmy(minCountry.getArmy() + armyToFortify);
                    maxArmyCountry.setArmy(1);
                    String message = gameState.getCurrentPlayer().getName() + " fortify " + minCountry.getName() + " from " + maxArmyCountry.getName() + " by " + armyToFortify;
                    gameState.setCurrentTurnPhraseText(message);
                    publish(message);
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
            Game.getInstance().getGamePhaseStrategy().nextTurnButton(gameState);
        }
    }
}
