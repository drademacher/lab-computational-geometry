package lions_and_men.applet_graph.visualization;

import javafx.scene.Group;
import javafx.scene.shape.Circle;
import lions_and_men.util.Point;

/**
 * Shape of a step preview (to indicate where an entity will go next).
 */
public class StepPreview implements Vertex {
    private static Group shapeGroup = new Group();

    private Circle shape;
    private Point coordinates;

    StepPreview(Point startCoordinates, boolean isBigVertex, boolean isMan) {
        this.coordinates = startCoordinates;

        if (isBigVertex) {
            shape = new Circle(coordinates.getX(), coordinates.getY(), Constants.BIG_VERTEX_RADIUS);
            shape.setStrokeWidth(Constants.BIG_VERTEX_RADIUS / 5);
        } else {
            shape = new Circle(coordinates.getX(), coordinates.getY(), Constants.SMALL_VERTEX_RADIUS);
            shape.setStrokeWidth(Constants.SMALL_VERTEX_RADIUS / 5);
        }
        if (isMan) {
            shape.setStroke(Constants.COLOR_MAN);
        } else {
            shape.setStroke(Constants.COLOR_LION);
        }
        shape.setFill(Constants.COLOR_BACKGROUND);


        shapeGroup.getChildren().add(shape);
    }

    public static void setShapeGroup(Group shapeGroup) {
        StepPreview.shapeGroup = shapeGroup;
    }

    @Override
    public void relocate(Point coordinates) {
        this.coordinates = coordinates;
        shape.relocate(coordinates.getX() - Constants.ENTITY_RADIUS, coordinates.getY() - Constants.ENTITY_RADIUS);
    }

    public void delete() {
        shapeGroup.getChildren().remove(shape);
    }

    public Point getPosition() {
        return coordinates;
    }
}

