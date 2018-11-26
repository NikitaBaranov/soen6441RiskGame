package game.strategies.PlayerStrategies;

import game.Game;
import game.model.Country;
import game.model.GameState;
import game.strategies.GamePhaseStrategies.GamePhaseStrategyFactory;

import javax.swing.*;
import java.util.List;

import static game.strategies.GamePhaseStrategies.BasePhaseStrategy.isGameWonBy;
import static game.strategies.GamePhaseStrategies.GamePhaseEnum.GAME_OVER;
import static game.strategies.MapFunctionsUtil.getCountryWithMaxArmy;
import static game.strategies.MapFunctionsUtil.getCountryWithMaxOpponentNeighbours;
import static game.strategies.MapFunctionsUtil.resetToAndFrom;
import static game.strategies.MapFunctionsUtil.unHighlightCountries;
import static game.strategies.MapFunctionsUtil.unSelectCountries;

/**
 * AI aggressive player strategy.
 * Describes the behavoir of aggressive ai.
 *
 * @author Dmitry Kryukov
 * @see BasePlayerStrategy
 */
// TODO 1. аггрессивная стратегия, нет авто перехода когда не может атаковать. Он делает фортифай автоматически и висит, ждет.
// TODO 2. аггрессивная стратегия, нет окончания игры, продолжает играть даже когда у первого игрока нет стран
public class AiAggressivePlayerStrategy extends BasePlayerStrategy {
    /**
     * Place Armies.
     *
     * @param gameState
     */
    @Override
    public void placeArmies(GameState gameState) {
        System.out.println("AI Aggressive Place Armies!");
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
        System.out.println("AI Aggressive Reinforce!");
        new ReinforceWorker(gameState).execute();
    }

    /**
     * Attacking phase for AI via worker
     *
     * @param gameState
     */
    @Override
    public void attack(GameState gameState) {
        System.out.println("AI Aggressive Attack!");
        new AttackWorker(gameState).execute();
    }

    /**
     * Fortify phase for AI via worker
     *
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
            Country toPlaceArmy = getCountryWithMaxArmy(gameState, 1);
            if (toPlaceArmy == null) {
                toPlaceArmy = getCountryWithMaxOpponentNeighbours(gameState);
            }
            if (toPlaceArmy != null) {
                toPlaceArmy.setSelected(true);
                toPlaceArmy.setArmy(toPlaceArmy.getArmy() + 1);
                gameState.getCurrentPlayer().setArmies(gameState.getCurrentPlayer().getArmies() - 1);
                String message = gameState.getCurrentPlayer().getName() + " placed army to " + toPlaceArmy.getName() + " total armies " + gameState.getCurrentPlayer().getArmies();
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
            int maxArmies = 1;
            //TODO: replace with util function
            for (Country country : gameState.getCountries()) {
                if (country.getPlayer() == gameState.getCurrentPlayer() && country.getArmy() > maxArmies) {
                    toReinforce = country;
                    maxArmies = country.getArmy();
                }
            }
            if (toReinforce == null) {
                //TODO: replace with util function
                int maxEnemyNeighbors = 0;
                for (Country country : gameState.getCountries()) {
                    if (country.getPlayer() == gameState.getCurrentPlayer()) {
                        int enemyNeighbours = 0;
                        for (Country neighbor : country.getNeighbours()) {
                            if (neighbor.getPlayer() != gameState.getCurrentPlayer()) {
                                enemyNeighbours++;
                            }
                        }
                        if (enemyNeighbours > maxEnemyNeighbors) {
                            toReinforce = country;
                            maxEnemyNeighbors = enemyNeighbours;
                        }
                    }
                }
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
                    int minArmies = Integer.MAX_VALUE;
                    for (Country country : gameState.getCountryFrom().getNeighbours()) {
                        if (country.getPlayer() != gameState.getCurrentPlayer() && country.getArmy() < minArmies) {
                            done = false;
                            minArmies = country.getArmy();
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

                    gameState.setNumberOfRedDicesSelected(Math.max(0, Math.min(gameState.getCountryFrom().getArmy() - 1, 3)));
                    gameState.setNumberOfWhiteDicesSelected(Math.max(0, Math.min(gameState.getCountryTo().getArmy(), 2)));

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
         * Do the foctification actions in the background
         *
         * @return
         */
        @Override
        protected Void doInBackground() {

            Country fromFortify = getCountryWithMaxArmy(gameState, 1);

            Country toFortify = null;
            if (fromFortify != null) {
                fromFortify.select(false, -1);
                int maxEnemyNeighbors = 0;
                for (Country country : gameState.getCountries()) {
                    if (country.isHighlighted()) {
                        int enemyNeighbours = 0;
                        for (Country neighbor : country.getNeighbours()) {
                            if (neighbor.getPlayer() != gameState.getCurrentPlayer()) {
                                enemyNeighbours++;
                            }
                        }
                        if (enemyNeighbours > maxEnemyNeighbors) {
                            toFortify = country;
                            maxEnemyNeighbors = enemyNeighbours;
                        }
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