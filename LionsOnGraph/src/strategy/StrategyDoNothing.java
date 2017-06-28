package strategy;

import entities.Entity;
import graph.GraphController;
import graph.Vertex;

public class StrategyDoNothing implements Strategy {

    @Override
    public Vertex getNextPosition(GraphController graphController, Entity e) {
        return e.getCurrentPosition();
    }
}
