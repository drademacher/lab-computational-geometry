package lions_in_plane.core.entities;


import util.Point;

public class Entity {
    private Point position;
    private double speed;

    Entity(Point position, double speed) {
        this.position = position;
        this.speed = speed;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
