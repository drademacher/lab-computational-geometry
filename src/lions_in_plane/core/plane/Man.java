package lions_in_plane.core.plane;

import lions_in_plane.core.strategies.man.Strategy;
import util.Point;

public class Man extends Entity {

    private Strategy strategy;
    private double epsilon;
    private double baseSpeed;

    public Man(Point position, double baseSpeed, double epsilon) {
        super(position, baseSpeed + epsilon);
        this.epsilon = epsilon;
        this.baseSpeed = baseSpeed;
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

    public void setEpsilon(double epsilon){
        super.setSpeed(baseSpeed + epsilon);
        this.epsilon = epsilon;
    }

    public double getEpsilon(){
        return epsilon;
    }

    @Override
    public void setSpeed(double baseSpeed) {
        super.setSpeed(baseSpeed + epsilon);
        this.baseSpeed = baseSpeed;
    }
}
