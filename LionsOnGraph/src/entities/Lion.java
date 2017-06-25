package entities;


import graph.GraphController;
import graph.Vertex;
import shapes.ShapedLion;
import strategy.Strategy;

public class Lion extends Entity {
    // TODO: implement Lion class

    private ShapedLion shape;

    public static Lion createLion(GraphController graphController, Vertex startPosition, Strategy strategy) {
        Lion lion = new Lion(startPosition, strategy, graphController);
        lion.shape = new ShapedLion(graphController, lion);
        return lion;
    }

    private Lion(Vertex startPosition, Strategy strategy, GraphController graphController) {
        super(startPosition, strategy, graphController);
    }

    public ShapedLion getShape() {
        return shape;
    }

    @Override
    public String toString() {
        return "Lion @ " + position;
    }
}
