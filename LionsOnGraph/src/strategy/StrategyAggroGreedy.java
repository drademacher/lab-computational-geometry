package strategy;

import entities.Entity;
import graph.*;

public class StrategyAggroGreedy implements Strategy {
    @Override
    public Vertex getNextPosition(CoreController coreController, Entity e) {

        GraphHelper helper = GraphHelper.createGraphHelper(coreController);

        Vertex currentPosition = e.getCurrentPosition();

        int steps = Integer.MAX_VALUE;
        Vertex bestNextPosition = null;
        for (Connection connection : currentPosition.getConnections()) {
            Vertex possibleVertex = connection.getNeighbor(currentPosition);

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
