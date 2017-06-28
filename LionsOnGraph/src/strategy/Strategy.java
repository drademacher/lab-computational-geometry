package strategy;

import entities.Entity;
import graph.GraphController;
import graph.Vertex;

public interface Strategy {
    // TODO: think about interface for strategy pattern

    Vertex getNextPosition(GraphController graphController, Entity e);

}
