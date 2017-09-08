package lions_and_men.applet_two.visualization;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lions_and_men.util.Point;

import static lions_and_men.applet_two.visualization.Constants.ENTITY_RADIUS;

public class InvisiblePoints extends Shape {
    private static Group group = new Group();

    private Point position;
    private Circle shape;

    InvisiblePoints(Point position) {
        this.position = position;
        this.shape = new Circle(position.getX(), position.getY(), ENTITY_RADIUS, Color.TRANSPARENT);
        group.getChildren().add(shape);
    }

    public static void setGroup(Group group) {
        InvisiblePoints.group = group;
    }

    public static void clear() {
        group.getChildren().clear();
    }
}
