package lions_on_graph.core.entities;


import lions_on_graph.core.CoreController;
import lions_on_graph.core.graph.Vertex;
import lions_on_graph.core.graph.GraphHelper;
import lions_on_graph.core.strategies.Strategy;
import lions_on_graph.core.strategies.StrategyLion;

import java.util.ArrayList;

public class Lion extends Entity {
    private static int defaultRange = 0;
    private int range = Lion.defaultRange;
    private StrategyLion strategy;


    public Lion(Vertex startPosition, CoreController coreController) {
        this(startPosition, 0, coreController);
    }

    @Override
    public Vertex getNextPosition() {
        return strategy.getNextPosition();
    }

    public Lion(Vertex startPosition, int range, CoreController coreController) {
        super(startPosition, coreController);
        this.range = range;
    }

    public static void setDefaultRange(int defaultRange) {
        Lion.defaultRange = defaultRange;
    }

    @Override
    public String toString() {
        return "Lion @ " + position;
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
        strategy.setLion(this);
        this.strategy = strategy;
    }
}
