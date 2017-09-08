package lions_and_men.applet_one.core.strategies.ManStrategies;

import lions_and_men.applet_one.core.CoreController;
import lions_and_men.applet_one.core.graph.Connection;
import lions_and_men.applet_one.core.graph.Vertex;
import lions_and_men.applet_one.core.strategies.StrategyMan;

import java.util.ArrayList;

public class ManStrategyRunAwayGreedy extends StrategyMan {

    public ManStrategyRunAwayGreedy(CoreController coreController, CoreController.ManStrategy strategyEnum) {
        super(coreController, strategyEnum);
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
