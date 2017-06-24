package shapes;

import graph.Edge;
import graph.Vertex;
import javafx.scene.Group;
import javafx.scene.shape.Line;

import static shapes.ShapeConstants.COLOR_EDGE;


public class ShapedEdge extends Edge {
    private static Group shapeGroup = new Group();


    private Line shape;

    public ShapedEdge(Vertex start, Vertex end) {
        super(start, end);
        shape = new Line(start.getCoordinates().getX(), start.getCoordinates().getY(), end.getCoordinates().getX(), end.getCoordinates().getY());
        shape.setStroke(COLOR_EDGE);
        shapeGroup.getChildren().add(shape);
    }

    public static void setShapeGroup(Group shapeGroup) {
        ShapedEdge.shapeGroup = shapeGroup;
    }
}
