package core.graph.graphshapes;

import core.graph.graphlogic.SmallVertex;
import core.util.Point;
import javafx.scene.Group;
import javafx.scene.shape.Circle;

import static core.graph.graphshapes.ShapeConstants.SMALL_VERTEX_RADIUS;
import static visualization.VisConstants.COLOR_EDGE_STEPS;

/**
 * Created by Jens on 24.06.2017.
 */
public class SmallVertexShape extends SmallVertex {
    private static Group shapeGroup = new Group();

    private Circle shape;

    public SmallVertexShape(int id, Point coords) {
        super(id, coords);
        shape = new Circle(coords.getX(), coords.getY(), SMALL_VERTEX_RADIUS, COLOR_EDGE_STEPS);
        shapeGroup.getChildren().add(shape);
    }

    public static void setShapeGroup(Group shapeGroup) {
        SmallVertexShape.shapeGroup = shapeGroup;
    }
}
