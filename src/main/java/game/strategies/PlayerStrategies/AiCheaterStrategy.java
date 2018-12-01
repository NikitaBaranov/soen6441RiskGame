package game.strategies.PlayerStrategies;

import static game.strategies.GamePhaseStrategies.BasePhaseStrategy.isGameWonBy;
import static game.strategies.GamePhaseStrategies.GamePhaseEnum.GAME_OVER;
import static game.utils.MapFunctionsUtil.countNeighbors;

import game.Game;
import game.model.Country;
import game.model.GameState;
import game.strategies.GamePhaseStrategies.GamePhaseStrategyFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.SwingWorker;

public class AiCheaterStrategy extends BaseStrategy {

  /**
   * Method need to be implemented
   */
  @Override
  public void placeArmies(GameState gameState) {
    System.out.println("AI Cheater Place Armies!");
    new PlaceArmiesWorker(gameState).execute();
  }

  /**
   * method needs to be implemented
   */
  @Override
  public void reinforce(GameState gameState) {
    exchangeCards(gameState);
    pauseAndRefresh(gameState, PAUSE * 2);
    System.out.println("AI Cheater Reinforce!");
    new ReinforceWorker(gameState).execute();
  }

  /**
   * method needs to be implemented
   */
  @Override
  public void attack(GameState gameState) {
    System.out.println("AI Cheater Attack!");
    new AttackWorker(gameState).execute();
  }

  /**
   * method needs to be implemented
   */
  @Override
  public void fortify(GameState gameState) {
    System.out.println("AI Cheater Fortify!");
    new FortifyWorker(gameState).execute();
  }


  private class PlaceArmiesWorker extends SwingWorker<Void, String> {

    GameState gameState;

    /**
     * Constructor of the class.
     */
    public PlaceArmiesWorker(GameState gameState) {
      this.gameState = gameState;
    }

    /**
     * Automatic placing armies in backgrounds.
     */
    @Override
    protected Void doInBackground() {
      for (Country country : gameState.getCountries()) {
        if (country.getPlayer() == gameState.getCurrentPlayer()) {
          country.setSelected(true);
          country.setArmy(country.getArmy() * 2);
          gameState.getCurrentPlayer().setArmies(gameState.getCurrentPlayer().getArmies() - 1);
          String message = gameState.getCurrentPlayer().getName() + " placed army to " + country.getName() + ". Armies to place: CHEAT MODE";
          gameState.setCurrentTurnPhraseText(message);
          publish(message);
        }
      }

      pauseAndRefresh(gameState, PAUSE);
      return null;
    }

    /**
     * Debug method
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
   * Worker for reinforcement phase. required to correctly repaint swing components.
   */
  private class ReinforceWorker extends SwingWorker<Void, String> {

    GameState gameState;

    /**
     * Constructor of the class.
     */
    public ReinforceWorker(GameState gameState) {
      this.gameState = gameState;
    }

    /**
     * Automatic reinforcement in backgrounds.
     */
    @Override
    protected Void doInBackground() {
      for (Country country : gameState.getCountries()) {
        if (country.getPlayer() == gameState.getCurrentPlayer()) {
          country.setSelected(true);
          country.setArmy(country.getArmy() * 2);
          String message = gameState.getCurrentPlayer().getName() + " reinforce " + country.getName() + " by double";
          gameState.getCurrentPlayer().setArmies(0);
          gameState.setCurrentTurnPhraseText(message);
          publish(message);
        }
        pauseAndRefresh(gameState, PAUSE);
      }

      pauseAndRefresh(gameState, PAUSE);
      return null;
    }

    /**
     * Debug method
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
   * Attack phase worker for Ai Required to correctly repaint swing components
   */
  private class AttackWorker extends SwingWorker<Void, String> {

    GameState gameState;

    /**
     * Constructor of the class
     */
    public AttackWorker(GameState gameState) {
      this.gameState = gameState;
    }

    /**
     * Do attack actions in the background.
     */
    @Override
    protected Void doInBackground() {
      for (Country country : gameState.getCountries()) {
        if (country.getPlayer() == gameState.getCurrentPlayer()) {
          for (Country neighbour : country.getNeighbours()) {
            if (neighbour.getPlayer() != gameState.getCurrentPlayer()) {
              neighbour.setSelected(true);
              neighbour.setPlayer(gameState.getCurrentPlayer());

              String message = gameState.getCurrentPlayer().getName() + " captured " + neighbour.getName();
              gameState.setCurrentTurnPhraseText(message);
              publish(message);
              pauseAndRefresh(gameState, PAUSE);
            }
          }
        }
      }
      pauseAndRefresh(gameState, PAUSE * 2);
      return null;
    }

    /**
     * Debug method
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
   * Fortifying phase worker for AI. Required for correctly repaint the swing components.
   */
  private class FortifyWorker extends SwingWorker<Void, String> {

    GameState gameState;

    /**
     * Constructor of the class
     */
    public FortifyWorker(GameState gameState) {
      this.gameState = gameState;
    }

    /**
     * Do the foctification actions in the background
     */
    @Override
    protected Void doInBackground() {
      for (Country country : gameState.getCountries()) {
        if (country.getPlayer() == gameState.getCurrentPlayer()) {
          if (countNeighbors(country.getNeighbours(), gameState.getCurrentPlayer(), true ) > 0){
            country.setHighlighted(true);
            country.setArmy(country.getArmy() * 2);
            String message = gameState.getCurrentPlayer().getName() + " fortify " + country.getName();
            gameState.setCurrentTurnPhraseText(message);
            publish(message);
            pauseAndRefresh(gameState, PAUSE);
          }
        }
      }

      pauseAndRefresh(gameState, PAUSE * 2);
      return null;
    }

    /**
     * Debug method
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
