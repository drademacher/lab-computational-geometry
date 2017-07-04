package entities;

import graph.CoreController;
import graph.Vertex;
import strategy.Strategy;
import strategy.StrategyMan;

public class Man extends Entity {
    // TODO: implement Man class

    private static boolean keepDistanceExact = false;
    private static int distance = 0;


    public Man(Vertex startPosition, CoreController coreController) {
        super(startPosition, coreController);
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
}
