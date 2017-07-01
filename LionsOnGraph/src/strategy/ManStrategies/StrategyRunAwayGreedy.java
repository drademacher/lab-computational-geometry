package strategy.ManStrategies;

import entities.Entity;
import entities.Man;
import graph.*;
import strategy.StrategyMan;

import java.util.ArrayList;

public class StrategyRunAwayGreedy extends StrategyMan {

    public StrategyRunAwayGreedy(CoreController coreController, Man man) {
        super(coreController, man);
    }

    @Override
    public ArrayList<Vertex> calculatePossibleSteps() {
        Vertex currentPosition = man.getCurrentPosition();

        int steps = 0;
        ArrayList<Vertex> result = new ArrayList<>();
        for (Connection connection : currentPosition.getConnections()) {
            Vertex possibleVertex = connection.getNeighbor(currentPosition);

            int calculatedSteps = helper.BFSToLion(currentPosition, possibleVertex);
            if (calculatedSteps > steps) {
                steps = calculatedSteps;
                result.add(0, possibleVertex);
            } else {
                result.add(possibleVertex);
            }
        }

        return result;
    }
}
