package entities;


import graph.Vertex;
import strategy.Strategy;

public class Lion extends Entity {
    // TODO: implement Lion class

    public Lion(Vertex startPosition, Strategy strategy) {
        super(startPosition, strategy);
        lions.add(this);
    }
}
