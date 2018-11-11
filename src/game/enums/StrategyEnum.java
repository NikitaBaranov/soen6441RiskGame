package game.enums;

import game.strategies.AbstractStrategy;
import game.strategies.AiAggressiveStrategy;
import game.strategies.HumanStrategy;

public enum StrategyEnum {
    HUMAN_STRATEGY(HumanStrategy.class),
    AI_AGGRESSIVE_STRATEGY(AiAggressiveStrategy.class),
    AI_BENEVOLENT_STRATEGY(null),
    AI_RANDOM_STRATEGY(null),
    AI_CHEATER_STRATEGY(null);

    private Class<? extends AbstractStrategy> aClass;

    StrategyEnum(Class<? extends AbstractStrategy> instantiator) {
        this.aClass = instantiator;
    }

    public Class<? extends AbstractStrategy> getaClass() {
        return aClass;
    }

// Another example with java 8 functional programing
//    HUMAN_STRATEGY(HumanStrategy::new),
//    private Supplier<IStrategy> aClass;
//
//    StrategyEnum(Supplier<IStrategy> aClass) {
//        this.aClass = aClass;
//    }
//
//    public IStrategy getInstance() {
//         return aClass.get();
//    }
}