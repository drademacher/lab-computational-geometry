package lions_and_men.applet_plane.visualization;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import lions_and_men.util.Point;

import java.util.ArrayList;

/**
 * Polygonal path to display the path the man runs during animation.
 */
public class ManPath extends Shape {
    private static Group group1 = new Group(), group2 = new Group();
    private static Polyline currentShape;

    ManPath(ArrayList<Point> points) {
        currentShape = new Polyline();

        currentShape.setStroke(Color.BLUE.deriveColor(1, 1, 1, 0.6));
        for (Point p : points) {
            currentShape.getPoints().addAll(p.getX(), p.getY());
        }

        group1.getChildren().add(currentShape);
    }

    public static void setGroup1(Group group1) {
        ManPath.group1 = group1;
    }

    public static void setGroup2(Group group2) {
        ManPath.group2 = group2;
    }

    public static void clear() {
        group1.getChildren().clear();
        group2.getChildren().clear();
        currentShape = null;
    }

    static void transfer() {
        group1.getChildren().clear();
        group2.getChildren().clear();

        if (currentShape != null) {
            currentShape.setStroke(Color.BLUE.deriveColor(1, 1, 1, 0.3));
            group2.getChildren().add(currentShape);
            currentShape = null;
        }
    }
}
