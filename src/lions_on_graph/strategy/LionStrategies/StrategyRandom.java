package lions_on_graph.strategy.LionStrategies;

import lions_on_graph.entities.Lion;
import lions_on_graph.graph.Connection;
import lions_on_graph.graph.CoreController;
import lions_on_graph.graph.Vertex;
import lions_on_graph.strategy.StrategyLion;
import util.Random;

import java.util.ArrayList;

/**
 * Created by Jens on 01.07.2017.
 */
public class StrategyRandom extends StrategyLion {


    public StrategyRandom(CoreController coreController, Lion lion) {
        super(coreController, lion);
    }

    @Override
    protected ArrayList<Vertex> calculatePossibleSteps() {
        ArrayList<Vertex> result = new ArrayList<>();
        ArrayList<Connection> connections = lion.getCurrentPosition().getConnections();
        if (connections.size() > 0) {
            int rndInt = Random.getRandomInteger(connections.size());
            result.add(connections.get(rndInt).getNeighbor(lion.getCurrentPosition()));
        }
        return result;
    }
}