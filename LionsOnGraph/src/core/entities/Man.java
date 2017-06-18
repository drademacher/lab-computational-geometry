package core.entities;

import core.graph.Position;
import core.strategy.Strategy;

/**
 * Created by Danny on 13.05.2017.
 */
public class Man extends Entity {
    // TODO: implement Man class

    public Man(Position startPosition, Strategy strategy) {
        super(startPosition, strategy);
        men.add(this);
    }

    Lion getNearestLion() {
        // TODO: could be useful for a lot of strategies
        return null;
    }
}
