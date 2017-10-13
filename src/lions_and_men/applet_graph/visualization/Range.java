package lions_and_men.applet_graph.visualization;

import javafx.scene.Group;
import javafx.scene.shape.Circle;
import lions_and_men.util.Point;

import static lions_and_men.applet_graph.visualization.Constants.*;

/**
 * Shape of the man or lion ranges.
 */
public class Range implements Vertex {
    private static Group shapeGroup = new Group();

    private Circle shape;
    private Point coordinates;

    public Range(Point startCoordinates) {
        this(startCoordinates, false);
    }


    public Range(Point startCoordinates, boolean manRange) {

        this.coordinates = startCoordinates;

        if (manRange) {
            shape = new Circle(coordinates.getX(), coordinates.getY(), RANGE_RADIUS, COLOR_MAN_RANGE);
        } else {
            shape = new Circle(coordinates.getX(), coordinates.getY(), RANGE_RADIUS, COLOR_LION_RANGE);
        }
        shapeGroup.getChildren().add(shape);

    }

    public static void setShapeGroup(Group shapeGroup) {
        Range.shapeGroup = shapeGroup;
    }

    public void relocate(Point coordinates) {
        this.coordinates = coordinates;
        shape.relocate(coordinates.getX() - RANGE_RADIUS, coordinates.getY() - RANGE_RADIUS);
    }

    public void delete() {
        shapeGroup.getChildren().remove(shape);
    }

    public Point getPosition() {
        return coordinates;
    }
}
