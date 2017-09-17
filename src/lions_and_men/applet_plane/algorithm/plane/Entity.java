package lions_and_men.applet_plane.algorithm.plane;


import lions_and_men.util.Point;

import java.util.ArrayList;

public abstract class Entity {
    private Point position;
    private double speed;
    private ArrayList<Point> calculatedPath = new ArrayList<>();

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

    protected void setSpeed(double speed) {
        this.speed = speed;
    }

    public ArrayList<Point> getCalculatedPath() {
        if (calculatedPath != null && calculatedPath.size() > 0) {
            return calculatedPath;
        }
        return new ArrayList<Point>() {{
            add(position);
        }};
    }

    public void setCalculatedPath(ArrayList<Point> calcedPath) {
        this.calculatedPath = calcedPath;
    }

    public void resetPath() {
        this.calculatedPath = new ArrayList<>();
    }

    public Point getCalculatedPositionAtTime(int t) {

        if (t < 0) t = 0;

        if (getCalculatedPath().size() > t) {
            return getCalculatedPath().get(t);
        }
        return null;
    }

    public Point getCalculatedLastPosition() {
        return getCalculatedPath().get(getCalculatedPath().size() - 1);
    }
}
