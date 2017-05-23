package core.strategy;

import core.entities.Entity;
import core.entities.Lion;
import core.entities.Man;
import core.graph.Position;

import java.util.ArrayList;

/**
 * Created by Jens on 23.05.2017.
 */
public class StrategyDoNothing implements Strategy{
    @Override
    public Position getNextPosition(Entity e, ArrayList<Man> men, ArrayList<Lion> lions) {
        return e.getCurrentPosition();
    }
}
