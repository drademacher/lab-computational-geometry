package lions_in_plane.visualization;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import util.Point;


public class ConvexHull extends Shape{
    private static Group group = new Group();

    private Point[] position;
    private Polygon shape;

    ConvexHull(Point[] points) {
        this.position = points;
        this.shape = new Polygon();
        shape.setStroke(Color.GREEN);
        shape.setFill(Color.TRANSPARENT);
        for (Point p : points) {
            shape.getPoints().addAll(p.getX(), p.getY());
        }

        group.getChildren().add(shape);

    }

    public static void setGroup(Group group) {
        ConvexHull.group = group;
    }


    void clear() {
        group.getChildren().remove(shape);
    }
}
