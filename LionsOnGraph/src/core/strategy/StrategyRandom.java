package core.strategy;

import core.entities.Entity;
import core.entities.Lion;
import core.entities.Man;
import core.graph.Vertex;
import core.util.Random;

import java.util.ArrayList;

/**
 * Created by Jens on 20.05.2017.
 */
public class StrategyRandom implements Strategy {
    @Override
    public Vertex getNextPosition(Entity e, ArrayList<Man> men, ArrayList<Lion> lions) {
        ArrayList<Vertex> neighborPositions = e.getCurrentPosition().getAdjacentVertices();
        int rndInt = Random.getRandomInteger(neighborPositions.size());
        return neighborPositions.get(rndInt);
    }
//    @Override
//    public Position getNextPosition(Entity e, ArrayList<Man> men, ArrayList<Lion> lions) {
//        ArrayList<Position> neighborPositions = e.getCurrentPosition().getAllNeighborPositions();
//        int rndInt = Random.getRandomInteger(neighborPositions.size());
//        return neighborPositions.get(rndInt);
//    }
}