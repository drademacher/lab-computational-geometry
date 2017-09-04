package lions_and_men.applet_two.core.plane;

import lions_and_men.applet_two.core.strategies.lion.Strategy;
import lions_and_men.util.Point;

public class Lion extends Entity {
    private double range;
    private Strategy strategy;

    Lion(Point position, double speed, double range) {
        super(position, speed);
        this.range = range;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
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