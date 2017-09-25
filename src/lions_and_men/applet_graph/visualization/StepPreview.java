package lions_and_men.applet_graph.visualization;

import javafx.scene.Group;
import javafx.scene.shape.Circle;
import lions_and_men.util.Point;

public class StepPreview implements Vertex {
    private static Group shapeGroup = new Group();

    private Circle shape;
    private Point coordinates;

    StepPreview(Point startCoordinates) {
        this.coordinates = startCoordinates;

        shape = new Circle(coordinates.getX(), coordinates.getY(), Constants.ENTITY_RADIUS, Constants.COLOR_PREVIEW);
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

