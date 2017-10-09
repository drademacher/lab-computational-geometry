package lions_and_men.applet_plane.visualization;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lions_and_men.util.Point;

import static lions_and_men.applet_plane.visualization.Constants.ENTITY_RADIUS;

/**
 * Invisible points to describe the bounding box (to fix the optimal zoom) when all paths are animated
 */
public class InvisiblePoints extends Shape {
    private static Group group = new Group();

    InvisiblePoints(Point position) {
        Circle shape = new Circle(position.getX(), position.getY(), ENTITY_RADIUS, Color.TRANSPARENT);
        group.getChildren().add(shape);
    }

    public static void setGroup(Group group) {
        InvisiblePoints.group = group;
    }

    public static void clear() {
        group.getChildren().clear();
    }
}
