package entities;

import graph.GraphController;
import graph.Vertex;
import shapes.ShapedMan;
import strategy.Strategy;

public class Man extends Entity {
    // TODO: implement Man class

    private ShapedMan shape;

    public static Man createMan(GraphController graphController, Vertex startPosition, Strategy strategy) {
        Man man = new Man(startPosition, strategy, graphController);
        man.shape = new ShapedMan(graphController, man);
        return man;
    }

    private Man(Vertex startPosition, Strategy strategy, GraphController graphController) {
        super(startPosition, strategy, graphController);
    }

    public Lion getNearestLion() {
        // TODO: could be useful for a lot of strategies
        return null;
    }

    public ShapedMan getShape() {
        return shape;
    }

    @Override
    public String toString() {
        return "Man @ " + position;
    }
}
