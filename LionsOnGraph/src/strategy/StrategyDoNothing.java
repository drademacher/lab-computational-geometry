package strategy;

import entities.Entity;
import graph.CoreController;
import graph.Vertex;

public class StrategyDoNothing implements Strategy {

    @Override
    public Vertex getNextPosition(CoreController coreController, Entity e) {
        return e.getCurrentPosition();
    }
}
