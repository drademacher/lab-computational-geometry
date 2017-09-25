package lions_and_men.applet_graph.algorithm.strategies;

import lions_and_men.applet_graph.algorithm.CoreController;

/**
 * All provided Lion strategies
 */
public enum StrategyEnumLion {
    DoNothing, Manual, RandomChoice, AggroGreedyLion, CleverLion;

    public Strategy getStrategy(CoreController coreController) {
        switch (this) {
            case RandomChoice:
                return new RandomChoice(coreController);
            case Manual:
                return new Manual(coreController);
            case DoNothing:
                return new DoNothing(coreController);
            case AggroGreedyLion:
                return new AggroGreedyLion(coreController);
            case CleverLion:
                return new CleverLion(coreController);
            default:
                throw new IllegalArgumentException("invalid input: " + this);
        }
    }
}