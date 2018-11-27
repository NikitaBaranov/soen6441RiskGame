package game.strategies.PlayerStrategies;

import game.Game;
import game.model.Country;
import game.model.GameState;
import game.strategies.GamePhaseStrategies.GamePhaseStrategyFactory;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static game.strategies.GamePhaseStrategies.BasePhaseStrategy.isGameWonBy;
import static game.strategies.GamePhaseStrategies.GamePhaseEnum.GAME_OVER;
import static game.utils.MapFunctionsUtil.*;
import static game.utils.MapFunctionsUtil.resetToAndFrom;

/**
 * AI random player strategy.
 * Describes the behavoir of random ai.
 *
 * @author Dmitry Kryukov
 * @see BasePlayerStrategy
 */
public class AiRandomPlayerStrategy extends BasePlayerStrategy {
    /**
     * Place Armies.
     *
     * @param gameState
     */
    @Override
    public void placeArmies(GameState gameState) {
        System.out.println("AI Random Place Armies!");
        new PlaceArmiesWorker(gameState).execute();
    }

    /**
     * Reinforcement phase for AI via worker
     *
     * @param gameState
     */
    @Override
    public void reinforce(GameState gameState) {
        exchangeCards(gameState);
        pauseAndRefresh(gameState, PAUSE * 2);
        System.out.println("AI Random Reinforce!");
        new ReinforceWorker(gameState).execute();
    }

    /**
     * Attacking phase for AI via worker
     *
     * @param gameState
     */
    @Override
    public void attack(GameState gameState) {
        System.out.println("AI Random Attack!");
        new AttackWorker(gameState).execute();
    }

    /**
     * Fortify phase for AI via worker
     *
     * @param gameState
     */
    @Override
    public void fortify(GameState gameState) {
        System.out.println("AI Random Fortify!");
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
         * Automatic placing armies in backgrounds.
         *
         * @return
         */
        @Override
        protected Void doInBackground() {
            List<Country> randomCountry = getRandomCountry(gameState, 1);
            Random random = new Random();
            int n = random.nextInt(randomCountry.size());
            if (randomCountry != null) {
                randomCountry.get(n).setSelected(true);
                randomCountry.get(n).setArmy(randomCountry.get(n).getArmy() + 1);
                gameState.getCurrentPlayer().setArmies(gameState.getCurrentPlayer().getArmies() - 1);
                String message = gameState.getCurrentPlayer().getName() + " placed army to " + randomCountry.get(n).getName() + " total armies " + gameState.getCurrentPlayer().getArmies();
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
            Country toReinforce = null;
            List<Country> randomCountry = getRandomCountry(gameState, 1);
            Random random = new Random();
            int n = random.nextInt(randomCountry.size());
            if (randomCountry != null){
                toReinforce = randomCountry.get(n);
            }

            if (toReinforce != null) {
                toReinforce.setSelected(true);
                toReinforce.setArmy(toReinforce.getArmy() + gameState.getCurrentPlayer().getArmies());
                String message = gameState.getCurrentPlayer().getName() + " reinforce " + toReinforce.getName() + " by " + gameState.getCurrentPlayer().getArmies();
                gameState.getCurrentPlayer().setArmies(0);
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
            boolean done = false;
            Random random = new Random();
            while (!done) {
                unHighlightCountries(gameState);
                unSelectCountries(gameState);
                resetToAndFrom(gameState);
                done = true;
                int maxArmies = 1;
                for (Country country : gameState.getCountries()) {
                    if (country.getPlayer() == gameState.getCurrentPlayer() && country.getArmy() > maxArmies) {
                        maxArmies = country.getArmy();
                        gameState.setCountryFrom(country);
                    }
                }
                if (gameState.getCountryFrom() != null) {
                    for (Country country : gameState.getCountryFrom().getNeighbours()) {
                        if (country.getPlayer() != gameState.getCurrentPlayer()) {
                            done = false;
                            gameState.setCountryTo(country);
                        }
                    }
                }

                if (gameState.getCountryFrom() != null && gameState.getCountryTo() != null) {
                    String message = gameState.getCurrentPlayer().getName() + " attacks from " + gameState.getCountryFrom().getName() + " to " + gameState.getCountryTo().getName();
                    gameState.setCurrentTurnPhraseText(message);
                    publish(message);

                    gameState.getCountryFrom().setSelected(true);
                    gameState.getCountryTo().setHighlighted(true);
                    pauseAndRefresh(gameState, PAUSE);

//                    gameState.setNumberOfRedDicesSelected(Math.max(0, Math.min(gameState.getCountryFrom().getArmy() - 1, 3)));
//                    gameState.setNumberOfWhiteDicesSelected(Math.max(0, Math.min(gameState.getCountryTo().getArmy(), 2)));

                    gameState.setNumberOfRedDicesSelected(random.nextInt(Math.min(gameState.getCountryFrom().getArmy() - 1, 3)));
                    gameState.setNumberOfWhiteDicesSelected(random.nextInt(Math.min(gameState.getCountryTo().getArmy(), 2)));

                    rollDiceAndProcessResults(gameState);
                    pauseAndRefresh(gameState, PAUSE);

                    if (gameState.getMinArmiesToMoveAfterWin() > 0) {
                        gameState.getCountryTo().setArmy(gameState.getCountryFrom().getArmy() - 1);
                        gameState.getCountryFrom().setArmy(1);

                        pauseAndRefresh(gameState, PAUSE);

                        gameState.setMinArmiesToMoveAfterWin(0);
                        gameState.getCountryFrom().setSelected(false);
                        gameState.getCountryTo().setHighlighted(false);
                    }
                }
                pauseAndRefresh(gameState, PAUSE * 2);
            }
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
         * Do the fortification actions in the background
         *
         * @return
         */
        @Override
        protected Void doInBackground() {
            List<Country> countries = getRandomCountry(gameState, 2);
            Random random = new Random();
            Country fromFortify = countries.get(random.nextInt(countries.size()));
            Country toFortify = null;

            if (fromFortify != null) {
                fromFortify.select(false, -1);
                for (Country country : gameState.getCountries()) {
                    if (country.isHighlighted()) {
                        toFortify = country;
                    }
                }
            }

            if (toFortify != null && fromFortify != null) {
                unHighlightCountries(gameState);
                toFortify.setHighlighted(true);
                int armyToFortify = fromFortify.getArmy() - 1;
                toFortify.setArmy(toFortify.getArmy() + armyToFortify);
                fromFortify.setArmy(1);
                String message = gameState.getCurrentPlayer().getName() + " fortify " + toFortify.getName() + " from " + fromFortify.getName() + " by " + armyToFortify;
                gameState.setCurrentTurnPhraseText(message);
                publish(message);
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