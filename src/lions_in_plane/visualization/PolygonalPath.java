package lions_in_plane.visualization;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import util.Point;

public class PolygonalPath extends Shape {
    private static Group group = new Group();

    private Point[] points;
    private Polyline shape;

    PolygonalPath(Point[] points, Color color) {
        this.points = points;
        this.shape = new Polyline();

        shape.setStroke(color);
        for (Point p : points) {
            shape.getPoints().addAll(p.getX(), p.getY());
        }

        group.getChildren().add(shape);
    }

    public static void setGroup(Group group) {
        PolygonalPath.group = group;
    }


    static void clear() {
        group.getChildren().clear();
    }
}
