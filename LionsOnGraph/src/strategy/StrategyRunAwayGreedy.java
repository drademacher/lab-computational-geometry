package strategy;

import entities.Entity;
import entities.Lion;
import entities.Man;
import graph.Edge;
import graph.GraphController;
import graph.GraphHelper;
import graph.Vertex;
import util.Random;

import java.util.ArrayList;

public class StrategyRunAwayGreedy implements Strategy {

    @Override
    public Vertex getNextPosition(GraphController graphController, Entity e) {
        GraphHelper helper = GraphHelper.createGraphHelper(graphController);

        Vertex currentPosition = e.getCurrentPosition();

        int steps = 0;
        Vertex bestNextPosition = null;
        for (Edge edge : currentPosition.getEdges()) {
            Vertex possibleVertex = edge.getNeighbor(currentPosition);

            int calculatedSteps = helper.BFSToLion(currentPosition, possibleVertex);
            if (calculatedSteps > steps) {
                steps = calculatedSteps;
                bestNextPosition = possibleVertex;
            }
        }

        if (bestNextPosition != null) {
            return bestNextPosition;
        } else {
            return currentPosition;
        }

    }
}
