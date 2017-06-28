package entities;


import graph.Drawable;
import graph.GraphController;
import graph.Vertex;
import shapes.ShapedLion;
import strategy.Strategy;

public class Lion extends Entity implements Drawable {
    // TODO: implement Lion class


    public Lion(Vertex startPosition, Strategy strategy, GraphController graphController) {
        super(startPosition, strategy, graphController);
    }

    @Override
    public String toString() {
        return "Lion @ " + position;
    }
}
