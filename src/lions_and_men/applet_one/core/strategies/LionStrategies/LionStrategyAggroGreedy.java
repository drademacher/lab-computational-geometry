package lions_and_men.applet_one.core.strategies.LionStrategies;

import lions_and_men.applet_one.core.CoreController;
import lions_and_men.applet_one.core.graph.Connection;
import lions_and_men.applet_one.core.graph.Vertex;
import lions_and_men.applet_one.core.strategies.StrategyLion;

import java.util.ArrayList;

public class LionStrategyAggroGreedy extends StrategyLion {

    public LionStrategyAggroGreedy(CoreController coreController, CoreController.LionStrategy strategyEnum) {
        super(coreController, strategyEnum);
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
