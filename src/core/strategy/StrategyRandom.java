package core.strategy;

import core.entities.Entity;
import core.graph.Position;
import core.util.Random;

import java.util.ArrayList;

/**
 * Created by Jens on 20.05.2017.
 */
public class StrategyRandom implements Strategy {
    @Override
    public Position getNextPosition(Entity e) {
        ArrayList<Position> neighborPositions = e.getCurrentPosition().getAllNeighborPositions();
        int rndInt = Random.getRandomInteger(neighborPositions.size());
        return neighborPositions.get(rndInt);
    }
}
