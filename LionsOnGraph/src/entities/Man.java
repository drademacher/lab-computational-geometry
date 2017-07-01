package entities;

import graph.CoreController;
import graph.Vertex;
import strategy.Strategy;

public class Man extends Entity {
    // TODO: implement Man class

    private boolean keepDistanceExact = false;
    private int distance = Integer.MAX_VALUE;


    public Man(Vertex startPosition, Strategy strategy, CoreController coreController) {
        super(startPosition, strategy, coreController);
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

    public void removeDistance(){
        this.distance = Integer.MAX_VALUE;
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
