package core.strategy;

import core.entities.Entity;
import core.entities.Lion;
import core.entities.Man;
import core.graph.Vertex;
import core.util.Random;

import java.util.ArrayList;

/**
 * Created by Jens on 23.05.2017.
 */
public class StrategyDoNothing implements Strategy {
    @Override
    public Vertex getNextPosition(Entity e, ArrayList<Man> men, ArrayList<Lion> lions) {
        //TODO implement (at the moment random)
        ArrayList<Vertex> neighborPositions = e.getCurrentPosition().getAdjacentVertices();
        int rndInt = Random.getRandomInteger(neighborPositions.size());
        return neighborPositions.get(rndInt);
    }
//    @Override
//    public Position getNextPosition(Entity e, ArrayList<Man> men, ArrayList<Lion> lions) {
//        return e.getCurrentPosition();
//    }
}
