package core.entities;

import core.graph.Position;

/**
 * Created by Danny on 13.05.2017.
 */
public abstract class Entity {
    // TODO: implement abstract class which can be inherited from (lions and men are entities)
    private Position position;

    Position getSuccesor() {
        return null;
    }
}
