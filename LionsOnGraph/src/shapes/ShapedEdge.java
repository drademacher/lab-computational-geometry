package shapes;

import graph.Edge;
import graph.GraphController;
import javafx.scene.Group;
import javafx.scene.shape.Line;
import util.Point;

import static shapes.ShapeConstants.COLOR_EDGE;


public class ShapedEdge implements Shape {
    private static Group shapeGroup = new Group();

    private GraphController graphController;
    private Line shape;

    public ShapedEdge(GraphController graphController, Point from, Point to) {

        this.graphController = graphController;

        shape = new Line(from.getX(), from.getY(), to.getX(), to.getY());
        shape.setStroke(COLOR_EDGE);
        shapeGroup.getChildren().add(shape);
    }

    public static void setShapeGroup(Group shapeGroup) {
        ShapedEdge.shapeGroup = shapeGroup;
    }

    public void relocate(Point from, Point to) {
        shape.setStartX(from.getX());
        shape.setStartY(from.getY());
        shape.setEndX(to.getX());
        shape.setEndY(to.getY());
    }

    @Override
    public void relocate(Point coordinates) {
        //TODO how to solve the interface problem? -> ShapedEdges needs a special relocate() function
        throw new IllegalArgumentException("ShapedEdges needs a special relocate() function");
    }

    public void delete() {
        shapeGroup.getChildren().remove(shape);
    }
}
