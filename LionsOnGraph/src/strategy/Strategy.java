package strategy;

import entities.Entity;
import entities.Lion;
import entities.Man;
import graph.GraphController;
import graph.Vertex;

import java.util.ArrayList;

public interface Strategy {
    // TODO: think about interface for strategy pattern

    Vertex getNextPosition(GraphController graphController, Entity e);

}
