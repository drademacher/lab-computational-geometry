package lions_in_plane.core.plane;

import lions_in_plane.core.strategies.man.Strategy;
import util.Point;

public class Man extends Entity {

    private Strategy strategy;
    private double epsilon;

    public Man(Point position, double speed) {
        super(position, speed);
        //TODO speed vs epsilon
        this.epsilon = speed -1;
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

    public double getEpsilon(){
        return epsilon;
    }
}
