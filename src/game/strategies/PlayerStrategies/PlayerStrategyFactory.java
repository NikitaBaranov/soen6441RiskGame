package game.strategies.PlayerStrategies;

public class PlayerStrategyFactory {

    public IPlayerStrategy getStrategy(StrategyEnum strategyEnum) {
        try {
            return strategyEnum.getaClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
//        return strategyEnum.getInstance();
    }
}
