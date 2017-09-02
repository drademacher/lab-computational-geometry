package lions_and_men.applet_one.core.strategies.ManStrategies;

import lions_and_men.applet_one.core.CoreController;
import lions_and_men.applet_one.core.graph.Connection;
import lions_and_men.applet_one.core.graph.Vertex;
import lions_and_men.applet_one.core.strategies.StrategyMan;
import lions_and_men.util.Random;

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
