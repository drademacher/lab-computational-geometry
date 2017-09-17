package lions_and_men.applet_plane.algorithm.plane;

import lions_and_men.applet_plane.algorithm.strategies.man.Strategy;
import lions_and_men.util.Point;

public class Man extends Entity {

    private Strategy strategy;
    private double epsilon;

    public Man(Point position, double epsilon) {
        super(position, 1 + epsilon);
        this.epsilon = epsilon;
    }

    @Override
    public String toString() {
        return "man@" + getPosition();
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public double getEpsilon() {
        return epsilon;
    }

    public void setEpsilon(double epsilon) {
        super.setSpeed(1 + epsilon);
        this.epsilon = epsilon;
    }
}
