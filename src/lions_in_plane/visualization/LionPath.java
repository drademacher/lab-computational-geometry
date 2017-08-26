package lions_in_plane.visualization;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import util.Point;

import java.util.ArrayList;

public class LionPath extends Shape {
    private static Group group = new Group();

    private ArrayList<Point> points;
    private Polyline shape;

    LionPath(ArrayList<Point> points) {
        this.points = points;
        this.shape = new Polyline();

        shape.setStroke(Color.RED.deriveColor(1, 1, 1, 0.1));
        for (Point p : points) {
            shape.getPoints().addAll(p.getX(), p.getY());
        }

        group.getChildren().add(shape);
    }

    public static void setGroup(Group group) {
        LionPath.group = group;
    }

    public static void clear() {
        group.getChildren().clear();
    }
}
