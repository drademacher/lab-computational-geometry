package strategy;

import entities.Entity;
import entities.Lion;
import entities.Man;
import graph.Edge;
import graph.GraphController;
import graph.Vertex;
import util.Random;

import java.util.ArrayList;

public class StrategyDoNothing implements Strategy {

    @Override
    public Vertex getNextPosition(GraphController graphController, Entity e) {
        return  e.getCurrentPosition();
    }
}
