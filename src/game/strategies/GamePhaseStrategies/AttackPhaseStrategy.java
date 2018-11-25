package game.strategies.GamePhaseStrategies;

import game.Game;
import game.model.Country;
import game.model.GameState;
import game.model.enums.CardsEnum;

import java.util.Map;
import java.util.Random;

import static game.model.enums.CardsEnum.ARTILLERY;
import static game.model.enums.CardsEnum.CAVALRY;
import static game.model.enums.CardsEnum.INFANTRY;
import static game.model.enums.CardsEnum.WILDCARDS;
import static game.strategies.GamePhaseStrategies.GamePhaseEnum.ATTACK;
import static game.strategies.GamePhaseStrategies.GamePhaseEnum.FORTIFICATION;
import static game.strategies.GamePhaseStrategies.GamePhaseEnum.GAME_OVER;

public class AttackPhaseStrategy extends BasePhaseStrategy {

    /**
     * Check if player can attack anybody or go to next turn
     *
     * @return
     */
    private static boolean isMoreAttacks(GameState gameState) {
        for (Country country : gameState.getCountries()) {
            if (country.getPlayer() == gameState.getCurrentPlayer() && country.getArmy() >= 2) {
                for (Country neighbor : country.getNeighbours()) {
                    if (neighbor.getPlayer() != gameState.getCurrentPlayer()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void init(GameState gameState) {
        resetToAndFrom(gameState);
        gameState.setMinArmiesToMoveAfterWin(0);
        gameState.setWinBattle(false);
        gameState.setGiveACard(false);
        unHighlightCountries(gameState);
        unSelectCountries(gameState);

        if (!isMoreAttacks(gameState)) {
            nextTurnButton(gameState);
        } else {
            gameState.setCurrentGamePhase(ATTACK);
            gameState.setCurrentTurnPhraseText("Select a Country to attack from.");

            debugMessage(gameState);
            if (gameState.getCurrentPlayer().isComputerPlayer()) {
                gameState.getCurrentPlayer().attack(gameState);
            }
        }
    }

    @Override
    public void mapClick(GameState gameState, int x, int y) {
        selectCountry(gameState, x, y);
        gameState.getCurrentPlayer().beforeAndAfterAttack(gameState);
    }

    @Override
    public void nextTurnButton(GameState gameState) {
        if (gameState.isGiveACard()) {
            CardsEnum[] cardsEnums = new CardsEnum[]{INFANTRY, CAVALRY, ARTILLERY, WILDCARDS};
            Random r = new Random();
            Map<CardsEnum, Integer> cardsEnumIntegerMap = gameState.getCurrentPlayer().getCardsEnumIntegerMap();
            CardsEnum randomCard = cardsEnums[r.nextInt(cardsEnums.length)];

            cardsEnumIntegerMap.put(randomCard, cardsEnumIntegerMap.get(randomCard) + 1);
            gameState.setGiveACard(false);
        }

        Game.getInstance().setGamePhaseStrategy(GamePhaseStrategyFactory.getStrategy(FORTIFICATION));
        Game.getInstance().getGamePhaseStrategy().init(gameState);
    }

    @Override
    public void attackButton(GameState gameState) {
        gameState.getCurrentPlayer().attack(gameState);
        if (isGameWonBy(gameState, gameState.getCurrentPlayer())) {
            Game.getInstance().setGamePhaseStrategy(GamePhaseStrategyFactory.getStrategy(GAME_OVER));
            Game.getInstance().getGamePhaseStrategy().init(gameState);
        }
    }
}
