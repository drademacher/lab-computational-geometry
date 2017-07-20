package lions_in_plane.core.plane;

import util.Point;

public class Lion extends Entity {
    private double range;

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
}
