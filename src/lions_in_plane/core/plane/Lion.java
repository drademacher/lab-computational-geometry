package lions_in_plane.core.plane;

import lions_in_plane.core.strategies.lion.Strategy;
import util.Point;

public class Lion extends Entity {
    private double range;
    private Strategy strategy;
    private Point calcedPoint;

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
        return "lion@"+getPosition();
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public Point getCalcedPoint() {
        System.out.println("?? calced "+calcedPoint);
        if(calcedPoint == null){
            System.out.println("?? no calced point "+getPosition());
            return getPosition();
        }
        System.out.println("?? calced point"+calcedPoint);
        return calcedPoint;
    }

    public void setCalcedPoint(Point calcedPoint) {
        System.out.println("!! calced point "+calcedPoint);
        System.out.println("!! start position "+getPosition());
        this.calcedPoint = calcedPoint;
    }
}
