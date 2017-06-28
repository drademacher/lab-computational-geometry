package entities;


import graph.GraphController;
import graph.Vertex;
import shapes.ShapedLion;
import strategy.Strategy;

public class Lion extends Entity {
    // TODO: implement Lion class


    public Lion(Vertex startPosition, Strategy strategy, GraphController graphController) {
        super(startPosition, strategy, graphController);
    }

    @Override
    public String toString() {
        return "Lion @ " + position;
    }
}
