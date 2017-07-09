package lions_on_graph.core.strategies.LionStrategies;

import lions_on_graph.core.entities.Lion;
import lions_on_graph.core.CoreController;
import lions_on_graph.core.graph.Vertex;
import lions_on_graph.core.graph.Connection;
import lions_on_graph.core.strategies.StrategyLion;

import java.util.ArrayList;

public class StrategyAggroGreedy extends StrategyLion {

    public StrategyAggroGreedy(CoreController coreController) {
        super(coreController);
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
