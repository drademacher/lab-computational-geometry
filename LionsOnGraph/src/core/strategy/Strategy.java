package core.strategy;

import core.entities.Entity;
import core.entities.Lion;
import core.entities.Man;
import core.graph.NEWVertex;

import java.util.ArrayList;

/**
 * Created by Danny on 13.05.2017.
 */
public interface Strategy {
    // TODO: think about interface for strategy pattern

    NEWVertex getNextPosition(Entity e, ArrayList<Man> men, ArrayList<Lion> lions);

}
