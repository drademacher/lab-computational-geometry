package lions_and_men.applet_one_graph.core.strategies.LionStrategies;

import lions_and_men.applet_one_graph.core.CoreController;
import lions_and_men.applet_one_graph.core.graph.Connection;
import lions_and_men.applet_one_graph.core.graph.Vertex;
import lions_and_men.applet_one_graph.core.strategies.StrategyLion;
import lions_and_men.util.Random;

import java.util.ArrayList;

/**
 * Created by Jens on 01.07.2017.
 */
public class LionStrategyRandom extends StrategyLion {


    public LionStrategyRandom(CoreController coreController, CoreController.LionStrategy strategyEnum) {
        super(coreController, strategyEnum);
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