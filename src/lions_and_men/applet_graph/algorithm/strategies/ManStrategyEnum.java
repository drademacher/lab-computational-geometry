package lions_and_men.applet_graph.algorithm.strategies;

import lions_and_men.applet_graph.algorithm.CoreController;

public enum ManStrategyEnum {
    DoNothing, Manual, PaperMan, RandomChoice, RunAwayGreedyMan;

    public Strategy getStrategy(CoreController coreController) {
        switch (this) {
            case DoNothing:
                return new DoNothing(coreController);
            case PaperMan:
                return new PaperMan(coreController);
            case RandomChoice:
                return new RandomChoice(coreController);
            case Manual:
                return new Manual(coreController);
            case RunAwayGreedyMan:
                return new RunAwayGreedyMan(coreController);
            default:
                throw new IllegalArgumentException("invalid input: " + this);
        }
    }
}
