package strategy.LionStrategies;

import entities.Lion;
import graph.*;
import strategy.StrategyLion;

import java.util.ArrayList;

public class StrategyAggroGreedy extends StrategyLion {

    public StrategyAggroGreedy(CoreController coreController, Lion lion) {
        super(coreController, lion);
    }

    @Override
    protected ArrayList<Vertex> calculatePossibleSteps() {
        Vertex currentPosition = lion.getCurrentPosition();
        int steps = Integer.MAX_VALUE;
        ArrayList<Vertex> result = new ArrayList<>();
        for (Connection connection : currentPosition.getConnections()) {
            Vertex possibleVertex = connection.getNeighbor(currentPosition);

            int calculatedSteps = helper.BFSToMen(currentPosition, possibleVertex);
            if (calculatedSteps < steps) {
                steps = calculatedSteps;
                result.add(0, possibleVertex);
            } else {
                result.add(possibleVertex);
            }
        }
        return result;
    }

}
