package shapes;

import graph.Edge;
import graph.GraphController;
import javafx.scene.Group;
import javafx.scene.shape.Line;

import static shapes.ShapeConstants.COLOR_EDGE;


public class ShapedEdge implements Shape {
    private static Group shapeGroup = new Group();

    private GraphController graphController;
    private Edge edge;
    private Line shape;

    public ShapedEdge(GraphController graphController, Edge edge) {

        this.graphController = graphController;
        this.edge = edge;

        shape = new Line(edge.getVertices()[0].getCoordinates().getX(), edge.getVertices()[0].getCoordinates().getY(),
                edge.getVertices()[1].getCoordinates().getX(), edge.getVertices()[1].getCoordinates().getY());
        shape.setStroke(COLOR_EDGE);
        shapeGroup.getChildren().add(shape);
    }

    public static void setShapeGroup(Group shapeGroup) {
        ShapedEdge.shapeGroup = shapeGroup;
    }

    public void relocate() {
        shape.setStartX(edge.getVertices()[0].getCoordinates().getX());
        shape.setStartY(edge.getVertices()[0].getCoordinates().getY());
        shape.setEndX(edge.getVertices()[1].getCoordinates().getX());
        shape.setEndY(edge.getVertices()[1].getCoordinates().getY());
    }

    public void delete() {
        shapeGroup.getChildren().remove(shape);
    }
}
