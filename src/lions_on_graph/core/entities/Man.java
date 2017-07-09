package lions_on_graph.core.entities;

import lions_on_graph.core.CoreController;
import lions_on_graph.core.graph.Vertex;
import lions_on_graph.core.strategies.StrategyMan;

public class Man extends Entity {
    // TODO: implement Man class

    private static boolean keepDistanceExact = false;
    private static int distance = 0;
    private StrategyMan strategy;


    public Man(Vertex startPosition, CoreController coreController) {
        super(startPosition, coreController);
    }

    @Override
    protected Vertex calculateNextPosition() {
        this.nextPosition = strategy.getNextPosition();
        return this.nextPosition;
    }

    public Lion getNearestLion() {
        // TODO: could be useful for a lot of strategies
        return null;
    }

    public static void setDistance(int distance) {
        Man.distance = distance;
    }

    public static int getDistance() {
        return distance;
    }

    public static void removeDistance() {
        Man.distance = 0;
        Man.keepDistanceExact = false;
    }

    public static void setKeepDistanceExact(boolean keepDistanceExact) {
        Man.keepDistanceExact = keepDistanceExact;
    }

    public static boolean keepDistanceExact() {
        return keepDistanceExact;
    }

    @Override
    public String toString() {
        return "Man @ " + position;
    }

    public void setStrategy(StrategyMan strategy) {
        strategy.setMan(this);
        this.strategy = strategy;
    }

    public StrategyMan getStrategy() {
        return strategy;
    }
}
