package entities;

import graph.CoreController;
import graph.Vertex;
import strategy.Strategy;
import strategy.StrategyMan;

public class Man extends Entity {
    // TODO: implement Man class

    private boolean keepDistanceExact = false;
    private int distance = 0;


    public Man(Vertex startPosition, CoreController coreController) {
        super(startPosition, coreController);
    }

    public Lion getNearestLion() {
        // TODO: could be useful for a lot of strategies
        return null;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDistance() {
        return distance;
    }

    public void removeDistance() {
        this.distance = 0;
        this.keepDistanceExact = false;
    }

    public void setKeepDistanceExact(boolean keepDistanceExact) {
        this.keepDistanceExact = keepDistanceExact;
    }

    public boolean keepDistanceExact() {
        return keepDistanceExact;
    }

    @Override
    public String toString() {
        return "Man @ " + position;
    }
}
