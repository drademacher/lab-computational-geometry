package core.strategy;

import core.entities.Entity;
import core.graph.Position;
import core.graph.Vertex;

/**
 * Created by Danny on 13.05.2017.
 */
public interface Strategy {
    // TODO: think about interface for strategy pattern

    public Position getNextPosition(Entity e);

}
