package game.strategies;

import game.enums.StrategyEnum;

public class StrategiesFactory {

    public IStrategy getStrategy(StrategyEnum strategyEnum) {
        try {
            return strategyEnum.getaClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
//        return strategyEnum.getInstance();
    }
}
