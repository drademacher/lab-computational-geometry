package lions_and_men.applet_graph.algorithm.entities;

import lions_and_men.applet_graph.algorithm.CoreController;
import lions_and_men.applet_graph.algorithm.graph.Connection;
import lions_and_men.applet_graph.algorithm.graph.GraphHelper;
import lions_and_men.applet_graph.algorithm.graph.Vertex;
import lions_and_men.applet_graph.algorithm.strategies.Manual;
import lions_and_men.applet_graph.algorithm.strategies.Strategy;

import java.util.ArrayList;


public class Man extends Entity {

    private static int minimumDistance = 0;
    private static CoreController.ManStrategy defaultStrategy = CoreController.ManStrategy.PaperMan;
    private static int defaultRange = 0;
    private Strategy strategy;
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
        if (vertexIsValidStep(nextPosition)) {
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
        return (strategy.getClass() == Manual.class) && !didManualStep;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        strategy.setEntity(this);
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

    public boolean vertexIsValidStep(Vertex vertex) {

        if (this.coreController.isLionDangerOnVertex(vertex.getCoordinates())) {
            return false;
        }

        boolean isValidVertex = false;
        if (getCurrentPosition().equals(vertex)) {
            isValidVertex = true;
        }

        for (Connection neighborConnection : getCurrentPosition().getConnections())
            if (neighborConnection.getNeighbor(getCurrentPosition()).equals(vertex)) {
                isValidVertex = true;
            }

        if (!isValidVertex) {
            return false;
        }


        for (Man otherMan : coreController.getMen()) {
            if (!otherMan.equals(this)) {

                if (Man.getMinimumDistance() >= coreController.getDistanceBetween(vertex, otherMan.getCurrentPosition())) {
                    isValidVertex = false;
                }
            }
        }

        if (coreController.getMen().size() < 2) {
            return true;
        }
        return isValidVertex;
    }
}
