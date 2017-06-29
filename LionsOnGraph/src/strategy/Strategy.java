package strategy;

import entities.Entity;
import graph.CoreController;
import graph.Vertex;

public interface Strategy {
    // TODO: think about interface for strategy pattern

    Vertex getNextPosition(CoreController coreController, Entity e);

}
