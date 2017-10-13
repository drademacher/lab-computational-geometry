package lions_and_men.applet_plane.visualization;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import lions_and_men.util.Point;

import java.util.ArrayList;

/**
 * Polygonal path to display the path a lion runs during animation.
 */
public class LionPath extends Shape {
    private static Group group1 = new Group(), group2 = new Group();

    LionPath(ArrayList<Point> points) {
        Polyline latestShape = new Polyline();

        latestShape.setStroke(Color.RED.deriveColor(1, 1, 1, 0.1));
        for (Point p : points) {
            latestShape.getPoints().addAll(p.getX(), p.getY());
        }

        group2.getChildren().addAll(group1.getChildren());
        group1.getChildren().clear();

        group1.getChildren().add(latestShape);
    }

    public static void setGroup1(Group group1) {
        LionPath.group1 = group1;
    }

    public static void setGroup2(Group group2) {
        LionPath.group2 = group2;
    }

    public static void clear() {
        group1.getChildren().clear();
        group2.getChildren().clear();
    }
}
