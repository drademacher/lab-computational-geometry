package lions_in_plane.visualization;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import util.Point;


public class Polygon extends Shape {
    private static Group group = new Group();

    private Point[] position;
    private javafx.scene.shape.Polygon shape;

    Polygon(Point[] points) {
        this.position = points;
        this.shape = new javafx.scene.shape.Polygon();
        shape.setStroke(Color.GREEN);
        shape.setFill(Color.TRANSPARENT);
        for (Point p : points) {
            shape.getPoints().addAll(p.getX(), p.getY());
        }

        group.getChildren().add(shape);

    }

    public static void setGroup(Group group) {
        Polygon.group = group;
    }


    static void clear() {
        group.getChildren().clear();
    }
}
