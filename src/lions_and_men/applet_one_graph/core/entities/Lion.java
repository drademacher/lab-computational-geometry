package lions_and_men.applet_one_graph.core.entities;


import lions_and_men.applet_one_graph.core.CoreController;
import lions_and_men.applet_one_graph.core.graph.GraphHelper;
import lions_and_men.applet_one_graph.core.graph.Vertex;
import lions_and_men.applet_one_graph.core.strategies.LionStrategies.LionStrategyManually;
import lions_and_men.applet_one_graph.core.strategies.StrategyLion;

import java.util.ArrayList;

public class Lion extends Entity {
    private static int defaultRange = 0;
    private static CoreController.LionStrategy defaultStrategy = CoreController.LionStrategy.Clever;
    private int range = Lion.defaultRange;
    private StrategyLion strategy;


    public Lion(Vertex startPosition, CoreController coreController) {
        this(startPosition, defaultRange, coreController);
    }

    public Lion(Vertex startPosition, int range, CoreController coreController) {
        super(startPosition, coreController);
        this.range = range;
    }

    public static void setDefaultRange(int defaultRange) {
        Lion.defaultRange = defaultRange;
    }

    public static int getDefaultLionRange() {
        return Lion.defaultRange;
    }

    public static CoreController.LionStrategy getDefaultStrategy() {
        return Lion.defaultStrategy;
    }

    public static void setDefaultStrategy(CoreController.LionStrategy defaultStrategy) {
        Lion.defaultStrategy = defaultStrategy;
    }

    @Override
    protected Vertex calculateNextPosition() {
        this.nextPosition = strategy.getNextPosition();
        return this.nextPosition;
    }

    @Override
    public void setNextPosition(Vertex nextPosition) {
        if (strategy.vertexIsValidStep(nextPosition)) {
            this.nextPosition = nextPosition;
            this.didManualStep = true;
        }
    }

    @Override
    public String toString() {
        return "Lion @ " + position;
    }

    @Override
    public boolean needManualStepInput() {
        return (strategy.getClass() == LionStrategyManually.class) && !didManualStep;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public ArrayList<Vertex> getRangeVertices() {
        GraphHelper graphHelper = GraphHelper.createGraphHelper(coreController);
        return graphHelper.BFSgetAllVerticesTill(position, getRange());
    }

    public StrategyLion getStrategy() {
        return strategy;
    }

    public void setStrategy(StrategyLion strategy) {
        strategy.setEntity(this);
        this.strategy = strategy;
    }
}
