package lions_and_men.applet_graph.core.strategies;

import lions_and_men.applet_graph.core.CoreController;
import lions_and_men.applet_graph.core.entities.Entity;
import lions_and_men.applet_graph.core.graph.Connection;
import lions_and_men.applet_graph.core.graph.Vertex;

import java.util.ArrayList;

public class RandomChoice<T extends Entity> extends Strategy<T> {


    public RandomChoice(CoreController coreController) {
        super(coreController);
    }

    @Override
    protected ArrayList<Vertex> calculatePossibleSteps() {
        ArrayList<Vertex> result = new ArrayList<>();
        ArrayList<Connection> connections = entity.getCurrentPosition().getConnections();
        if (connections.size() > 0) {
            int rndInt = lions_and_men.util.Random.getRandomInteger(connections.size());
            result.add(connections.get(rndInt).getNeighbor(entity.getCurrentPosition()));
        }
        return result;
    }
}
