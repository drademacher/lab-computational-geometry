package shapes;

import graph.SmallVertex;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import util.Point;

import static shapes.ShapeConstants.COLOR_EDGE_STEPS;
import static shapes.ShapeConstants.SMALL_VERTEX_RADIUS;

public class ShapedSmallVertex extends SmallVertex {
    private static Group shapeGroup = new Group();

    private Circle shape;

    public ShapedSmallVertex(int id, Point coords) {
        super(id, coords);
        shape = new Circle(coords.getX(), coords.getY(), SMALL_VERTEX_RADIUS, COLOR_EDGE_STEPS);
        shapeGroup.getChildren().add(shape);
    }

    public static void setShapeGroup(Group shapeGroup) {
        ShapedSmallVertex.shapeGroup = shapeGroup;
    }
}
