package core.entities;


import core.graph.Position;
import core.strategy.Strategy;

/**
 * Created by Danny on 13.05.2017.
 */
public class Lion extends Entity {
    // TODO: implement Lion class

    public Lion(Position startPosition, Strategy strategy) {
        super(startPosition, strategy);
        lions.add(this);
    }
}
