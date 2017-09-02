package applet_one.core.strategies.ManStrategies;

import applet_one.core.CoreController;
import applet_one.core.graph.Connection;
import applet_one.core.graph.Vertex;
import applet_one.core.strategies.StrategyMan;
import util.Random;

import java.util.ArrayList;

public class ManStrategyRandom extends StrategyMan {


    public ManStrategyRandom(CoreController coreController, CoreController.ManStrategy strategyEnum) {
        super(coreController, strategyEnum);
    }

    @Override
    protected ArrayList<Vertex> calculatePossibleSteps() {
        ArrayList<Vertex> result = new ArrayList<>();
        ArrayList<Connection> connections = man.getCurrentPosition().getConnections();
        if (connections.size() > 0) {
            int rndInt = Random.getRandomInteger(connections.size());
            result.add(connections.get(rndInt).getNeighbor(man.getCurrentPosition()));
        }
        return result;
    }
}
