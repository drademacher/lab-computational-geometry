package lions_and_men.applet_graph.algorithm.strategies;

import lions_and_men.applet_graph.algorithm.CoreController;
import lions_and_men.applet_graph.algorithm.entities.Man;
import lions_and_men.applet_graph.algorithm.graph.Connection;
import lions_and_men.applet_graph.algorithm.graph.Vertex;

import java.util.ArrayList;

/**
 * Man Strategy, run away from lion greedy
 */
public class RunAwayGreedyMan extends Strategy<Man> {

    RunAwayGreedyMan(CoreController coreController) {
        super(coreController);
    }

    @Override
    public ArrayList<Vertex> calculatePossibleSteps() {
        Vertex currentPosition = entity.getCurrentPosition();

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
