package lions_and_men.applet_plane.core.plane;

import lions_and_men.applet_plane.core.strategies.lion.Strategy;
import lions_and_men.util.Point;

public class Lion extends Entity {
    private double range;
    private Strategy strategy;

    Lion(Point position) {
        //speed default 1
        super(position, 1);
        this.range = 5;
    }

    public double getRange() {
        return range;
    }

    @Override
    public String toString() {
        return "lion@" + getPosition();
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

}
