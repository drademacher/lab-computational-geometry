package lions_and_men.applet_one_graph.core.entities;

import lions_and_men.applet_one_graph.core.CoreController;
import lions_and_men.applet_one_graph.core.graph.GraphHelper;
import lions_and_men.applet_one_graph.core.graph.Vertex;
import lions_and_men.applet_one_graph.core.strategies.ManStrategies.ManStrategyManually;
import lions_and_men.applet_one_graph.core.strategies.StrategyMan;

import java.util.ArrayList;


public class Man extends Entity {

    private static int minimumDistance = 0;
    private static CoreController.ManStrategy defaultStrategy = CoreController.ManStrategy.Paper;
    private static int defaultRange = 0;
    private StrategyMan strategy;
    private int range = defaultRange;


    public Man(Vertex startPosition, CoreController coreController) {
        super(startPosition, coreController);
    }

    public static int getMinimumDistance() {
        return minimumDistance;
    }

    public static void setMinimumDistance(int distance) {
        Man.minimumDistance = distance;
    }

    public static void removeMinimumDistance() {
        Man.minimumDistance = 0;
    }

    public static CoreController.ManStrategy getDefaultStrategy() {
        return Man.defaultStrategy;
    }

    public static void setDefaultStrategy(CoreController.ManStrategy defaultStrategy) {
        Man.defaultStrategy = defaultStrategy;
    }

    public static void setDefaultRange(int defaultRange) {
        Man.defaultRange = defaultRange;
    }

    public static int getDefaultLionRange() {
        return Man.defaultRange;
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
        return "Man @ " + position;
    }

    @Override
    public boolean needManualStepInput() {
        return (strategy.getClass() == ManStrategyManually.class) && !didManualStep;
    }

    public StrategyMan getStrategy() {
        return strategy;
    }

    public void setStrategy(StrategyMan strategy) {
        strategy.setMan(this);
        this.strategy = strategy;
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
}
