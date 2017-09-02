package lions_in_plane.visualization;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import util.Point;

import static lions_in_plane.visualization.Constants.ENTITY_RADIUS;

public class InvisiblePoints extends Shape {
    private static Group group = new Group();

    private Point position;
    private Circle shape;

    InvisiblePoints(Point position) {
        this.position = position;
        this.shape = new Circle(position.getX(), position.getY(), ENTITY_RADIUS, Color.TRANSPARENT);
    }


    public static void setGroup(Group group) {
        InvisiblePoints.group = group;
    }

    public static void clear() {
        group.getChildren().clear();
    }
}