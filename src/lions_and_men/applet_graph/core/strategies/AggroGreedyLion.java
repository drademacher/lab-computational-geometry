package lions_and_men.applet_graph.core.strategies;

import lions_and_men.applet_graph.core.CoreController;
import lions_and_men.applet_graph.core.entities.Lion;
import lions_and_men.applet_graph.core.graph.Connection;
import lions_and_men.applet_graph.core.graph.Vertex;

import java.util.ArrayList;

public class AggroGreedyLion extends Strategy<Lion> {

    public AggroGreedyLion(CoreController coreController) {
        super(coreController);
    }

    @Override
    protected ArrayList<Vertex> calculatePossibleSteps() {
        Vertex currentPosition = entity.getCurrentPosition();
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
