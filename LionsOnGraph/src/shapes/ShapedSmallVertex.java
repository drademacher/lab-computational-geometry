package shapes;

import graph.GraphController;
import graph.SmallVertex;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import util.Point;

import static shapes.ShapeConstants.COLOR_EDGE_STEPS;
import static shapes.ShapeConstants.SMALL_VERTEX_RADIUS;

public class ShapedSmallVertex {
    private static Group shapeGroup = new Group();

    private Circle shape;
    private SmallVertex vertex;
    private GraphController graphController;

    public ShapedSmallVertex(GraphController graphController, SmallVertex vertex) {

        this.graphController = graphController;
        this.vertex = vertex;

        shape = new Circle(vertex.getCoordinates().getX(), vertex.getCoordinates().getY(), SMALL_VERTEX_RADIUS, COLOR_EDGE_STEPS);
        shapeGroup.getChildren().add(shape);
    }

    public void relocate() {
        shape.relocate(vertex.getCoordinates().getX(), vertex.getCoordinates().getY());
    }

    public void delete() {
        shapeGroup.getChildren().remove(shape);
    }

    public static void setShapeGroup(Group shapeGroup) {
        ShapedSmallVertex.shapeGroup = shapeGroup;
    }
}
