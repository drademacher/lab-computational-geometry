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


            System.out.println("##################");
            System.out.println(result);
            if (calculatedSteps > steps) {
                System.out.println("case #1");
                steps = calculatedSteps;
                result.add(0, possibleVertex);
            } else {
                System.out.println("case #2");
                result.add(possibleVertex);
            }
            System.out.println(result);
        }

        return result;
    }
}
