package game.strategies.PlayerStrategies;

public enum StrategyEnum {
    HUMAN_STRATEGY(HumanPlayerStrategy.class),
    AI_AGGRESSIVE_STRATEGY(AiAggressivePlayerStrategy.class),
    AI_BENEVOLENT_STRATEGY(null),
    AI_RANDOM_STRATEGY(null),
    AI_CHEATER_STRATEGY(null);

    private Class<? extends AbstractPlayerStrategy> aClass;

    StrategyEnum(Class<? extends AbstractPlayerStrategy> instantiator) {
        this.aClass = instantiator;
    }

    public Class<? extends AbstractPlayerStrategy> getaClass() {
        return aClass;
    }

// Another example with java 8 functional programing
//    HUMAN_STRATEGY(HumanPlayerStrategy::new),
//    private Supplier<IPlayerStrategy> aClass;
//
//    StrategyEnum(Supplier<IPlayerStrategy> aClass) {
//        this.aClass = aClass;
//    }
//
//    public IPlayerStrategy getInstance() {
//         return aClass.get();
//    }
}