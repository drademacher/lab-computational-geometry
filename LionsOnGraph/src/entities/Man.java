package entities;

import graph.GraphController;
import graph.Vertex;
import shapes.ShapedMan;
import strategy.Strategy;

public class Man extends Entity {
    // TODO: implement Man class


    public Man(Vertex startPosition, Strategy strategy, GraphController graphController) {
        super(startPosition, strategy, graphController);
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
