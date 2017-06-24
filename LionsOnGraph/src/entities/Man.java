package entities;

import graph.Vertex;
import strategy.Strategy;

public class Man extends Entity {
    // TODO: implement Man class

    public Man(Vertex startPosition, Strategy strategy) {
        super(startPosition, strategy);
        men.add(this);
    }

    Lion getNearestLion() {
        // TODO: could be useful for a lot of strategies
        return null;
    }
}
