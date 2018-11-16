package game.strategies.GamePhaseStrategies;

import game.model.GameState;

public interface IGamePhaseStrategy {

    public void init(GameState gameState);

    public void mapClick(GameState gameState, int x, int y);

    public void nextTurnButton(GameState gameState);

    public void exchangeButton(GameState gameState);

    public void attackButton(GameState gameState);

    public void cleanup(GameState gameState);
}
