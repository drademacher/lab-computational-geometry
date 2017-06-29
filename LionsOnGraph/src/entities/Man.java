package entities;

import graph.CoreController;
import graph.Vertex;
import strategy.Strategy;

public class Man extends Entity {
    // TODO: implement Man class


    public Man(Vertex startPosition, Strategy strategy, CoreController coreController) {
        super(startPosition, strategy, coreController);
    }

    public Lion getNearestLion() {
        // TODO: could be useful for a lot of strategies
        return null;
    }

    @Override
    public String toString() {
        return "Man @ " + position;
    }
}
