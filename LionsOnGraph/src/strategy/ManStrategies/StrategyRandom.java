package strategy.ManStrategies;

import entities.Man;
import graph.Connection;
import graph.CoreController;
import graph.Vertex;
import strategy.StrategyMan;
import util.Random;

import java.util.ArrayList;

public class StrategyRandom extends StrategyMan {


    public StrategyRandom(CoreController coreController, Man man) {
        super(coreController, man);
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
