package core.strategy;

import core.entities.Entity;
import core.entities.Lion;
import core.entities.Man;
import core.graph.graphlogic.Edge;
import core.graph.graphlogic.Vertex;
import core.util.Random;

import java.util.ArrayList;

/**
 * Created by Jens on 20.05.2017.
 */
public class StrategyRandom implements Strategy {
    @Override
    public Vertex getNextPosition(Entity e, ArrayList<Man> men, ArrayList<Lion> lions) {
        ArrayList<Edge> neighborPositions = e.getCurrentPosition().getEdges();
        int rndInt = Random.getRandomInteger(neighborPositions.size());
        return neighborPositions.get(rndInt).getNeighbor(e.getCurrentPosition());
    }
//    @Override
//    public Position getNextPosition(Entity e, ArrayList<Man> men, ArrayList<Lion> lions) {
//        ArrayList<Position> neighborPositions = e.getCurrentPosition().getAllNeighborPositions();
//        int rndInt = Random.getRandomInteger(neighborPositions.size());
//        return neighborPositions.get(rndInt);
//    }
}
