package lions_on_graph.visualization;

import javafx.scene.Group;
import javafx.scene.shape.Circle;
import lions_on_graph.core.CoreController;
import util.Point;

import static lions_on_graph.visualization.ShapeConstants.COLOR_RANGE;
import static lions_on_graph.visualization.ShapeConstants.RANGE_RADIUS;

/**
 * Created by Jens on 29.06.2017.
 */
public class ShapedRange implements ShapedVertex {
    private static Group shapeGroup = new Group();

    private Circle shape;
    private CoreController coreController;
    private Point coordinates;

    public ShapedRange(CoreController coreController, Point startCoordinates) {

        this.coreController = coreController;
        this.coordinates = startCoordinates;

        shape = new Circle(coordinates.getX(), coordinates.getY(), RANGE_RADIUS, COLOR_RANGE);
        shapeGroup.getChildren().add(shape);

    }

    public static void setShapeGroup(Group shapeGroup) {
        ShapedRange.shapeGroup = shapeGroup;
    }

    public void relocate(Point coordinates) {
        this.coordinates = coordinates;
        shape.relocate(coordinates.getX() - RANGE_RADIUS, coordinates.getY() - RANGE_RADIUS);
    }

    public void delete() {
        shapeGroup.getChildren().remove(shape);
    }
}
