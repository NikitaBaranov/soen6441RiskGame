package game.strategies.PlayerStrategies;

import game.Game;
import game.model.Country;
import game.model.GameState;
import game.model.enums.CardsEnum;

import java.util.stream.Collectors;

public class AiAggressivePlayerStrategy extends BasePlayerStrategy {

    @Override
    public void placeArmies(GameState gameState) {

    }

    @Override
    public void attack(GameState gameState) {
        System.out.println("AI Aggressive Attack!");
        boolean done = false;
        while (!done) {
            done = true;
            resetToAndFrom(gameState);
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
                System.out.println("AI Aggressive attacks from " + gameState.getCountryFrom().getName() + " to " + gameState.getCountryTo().getName());
                gameState.setCurrentTurnPhraseText("Attack from " + gameState.getCountryFrom().getName() + " to " + gameState.getCountryTo().getName());
                gameState.getCountryFrom().setSelected(true);
                gameState.getCountryTo().setHighlighted(true);
                pauseAndRefresh(gameState, 1);
                gameState.setNumberOfRedDicesSelected(Math.max(0, Math.min(gameState.getCountryFrom().getArmy() - 1, 3)));
                gameState.setNumberOfWhiteDicesSelected(Math.max(0, Math.min(gameState.getCountryTo().getArmy(), 2)));

                rollDiceAndProcessResults(gameState);
                pauseAndRefresh(gameState, 1);

                if (gameState.getMinArmiesToMoveAfterWin() > 0) {
                    gameState.getCountryTo().setArmy(gameState.getCountryFrom().getArmy() - 1);
                    gameState.getCountryFrom().setArmy(1);

                    pauseAndRefresh(gameState, 1);

                    gameState.setMinArmiesToMoveAfterWin(0);
                    gameState.getCountryFrom().setSelected(false);
                    gameState.getCountryTo().setHighlighted(false);
                }
            }
        }
        Game.getInstance().getGamePhaseStrategy().nextTurnButton(gameState);
    }

    // Copy from Human
    @Override
    public void exchange(GameState gameState) {
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

    // Copy from Human
    @Override
    public void reinforce(GameState gameState) {
        if (gameState.getCurrentPlayer().getArmies() > 0) {
            gameState.getCurrentCountry().setArmy(gameState.getCurrentCountry().getArmy() + 1);
            gameState.getCurrentPlayer().setArmies(gameState.getCurrentPlayer().getArmies() - 1);
            gameState.setCurrentTurnPhraseText("Armies to place " + gameState.getCurrentPlayer().getArmies());
        } else {
            unHighlightCountries(gameState);
        }
    }

    // Copy from Human
    @Override
    public void fortify(GameState gameState) {
        if (gameState.getCountryFrom() == null) {
            unHighlightCountries(gameState);
            gameState.setCountryFrom(gameState.getCurrentCountry());
            gameState.setCurrentTurnPhraseText("Select a country to move an army.");
            gameState.getCurrentCountry().select(false, -1);
        } else if (gameState.getCountryTo() == null && gameState.getCurrentCountry().isHighlighted()) {
            gameState.getCountryFrom().unSelect(false);
            gameState.getCountryFrom().setSelected(true);
            gameState.setCountryTo(gameState.getCurrentCountry());
            gameState.getCountryTo().setHighlighted(true);
            gameState.setCurrentTurnPhraseText("Click on country to move one army.");
        }
        if (gameState.getCountryFrom() != null && gameState.getCountryFrom().getArmy() > 1 && gameState.getCountryTo() != null) {
            gameState.getCountryFrom().setArmy(gameState.getCountryFrom().getArmy() - 1);
            gameState.getCountryTo().setArmy(gameState.getCountryTo().getArmy() + 1);
        }
    }
}
