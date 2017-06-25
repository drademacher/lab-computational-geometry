package strategy;

import entities.Entity;
import graph.Edge;
import graph.GraphController;
import graph.GraphHelper;
import graph.Vertex;

public class StrategyAggroGreedy implements Strategy {
    @Override
    public Vertex getNextPosition(GraphController graphController, Entity e) {

        GraphHelper helper = GraphHelper.createGraphHelper(graphController);

        Vertex currentPosition = e.getCurrentPosition();

        int steps = Integer.MAX_VALUE;
        Vertex bestNextPosition = null;
        for (Edge edge : currentPosition.getEdges()) {
            Vertex possibleVertex = edge.getNeighbor(currentPosition);

            int calculatedSteps = helper.BFSToMen(currentPosition, possibleVertex);
            if (calculatedSteps < steps) {
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
