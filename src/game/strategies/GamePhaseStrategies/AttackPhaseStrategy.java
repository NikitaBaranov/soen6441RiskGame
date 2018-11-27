package game.strategies.GamePhaseStrategies;

import game.Game;
import game.model.GameState;
import game.model.Player;
import game.model.enums.CardsEnum;

import java.util.Map;
import java.util.Random;

import static game.model.enums.CardsEnum.ARTILLERY;
import static game.model.enums.CardsEnum.CAVALRY;
import static game.model.enums.CardsEnum.INFANTRY;
import static game.model.enums.CardsEnum.WILDCARDS;
import static game.strategies.GamePhaseStrategies.GamePhaseEnum.ATTACK;
import static game.strategies.GamePhaseStrategies.GamePhaseEnum.FORTIFICATION;
import static game.utils.MapFunctionsUtil.getPlayerToCountiesNumberMap;
import static game.utils.MapFunctionsUtil.isMoreAttacks;
import static game.utils.MapFunctionsUtil.selectCountry;

/**
 * Attack phase strategy class. Describes the attack phase, prepare game for attacing.
 * @author Dmitry Kryukov, Ksenia popova
 * @see BasePhaseStrategy
 */
public class AttackPhaseStrategy extends BasePhaseStrategy {

    /**
     * Initialization for attack phase. Set required states and variables.
     * Show status messages.
     * @param gameState
     */
    @Override
    public void init(GameState gameState) {
        super.init(gameState);
        gameState.setMinArmiesToMoveAfterWin(0);
        gameState.setWinBattle(false);
        gameState.setGiveACard(false);


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

    /**
     * Force changing state after click on map
     * @param gameState
     * @param x
     * @param y
     */
    @Override
    public void mapClick(GameState gameState, int x, int y) {
        if (!gameState.getCurrentPlayer().isComputerPlayer()) {
            selectCountry(gameState, x, y);
            gameState.getCurrentPlayer().beforeAndAfterAttack(gameState);
        }
    }

    /**
     * Assign random card on pressing next turn button. And go to next turn.
     * @param gameState
     */
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

        Map<Player, Integer> playerToCountiesNumberMap = getPlayerToCountiesNumberMap(gameState);
        for (Player player : gameState.getPlayers()) {
            if (playerToCountiesNumberMap.get(player) == 0) {
                player.setLost(true);
            }
        }

        Game.getInstance().setGamePhaseStrategy(GamePhaseStrategyFactory.getStrategy(FORTIFICATION));
        Game.getInstance().getGamePhaseStrategy().init(gameState);
    }

    /**
     *Attack button force required game states and check if game is over.
     * @param gameState
     */
    @Override
    public void attackButton(GameState gameState) {
        gameState.getCurrentPlayer().attack(gameState);
    }
}
