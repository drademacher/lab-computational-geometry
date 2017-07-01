package strategy;

import entities.Entity;
import graph.*;

import java.util.ArrayList;

public class StrategyRunAwayGreedy extends StrategyMan {

    @Override
    public Vertex getNextPosition() {
        GraphHelper helper = GraphHelper.createGraphHelper(coreController);

        Vertex currentPosition = e.getCurrentPosition();

        int steps = 0;
        Vertex bestNextPosition = null;
        for (Connection connection : currentPosition.getConnections()) {
            Vertex possibleVertex = connection.getNeighbor(currentPosition);

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

    @Override
    public ArrayList<Vertex> calculatePossibleSteps() {
        //TODO 
        return null;
    }
}
