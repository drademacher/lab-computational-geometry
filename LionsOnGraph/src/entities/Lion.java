package entities;


import graph.CoreController;
import graph.Vertex;
import strategy.Strategy;

public class Lion extends Entity {
    // TODO: implement Lion class


    public Lion(Vertex startPosition, Strategy strategy, CoreController coreController) {
        super(startPosition, strategy, coreController);
    }

    @Override
    public String toString() {
        return "Lion @ " + position;
    }
}
