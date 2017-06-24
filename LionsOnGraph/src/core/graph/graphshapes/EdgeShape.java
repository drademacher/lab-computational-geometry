package core.graph.graphshapes;

import core.graph.graphlogic.Edge;
import core.graph.graphlogic.Vertex;
import javafx.scene.Group;
import javafx.scene.shape.Line;

import static visualization.VisConstants.COLOR_EDGE;

/**
 * Created by Jens on 24.06.2017.
 */
public class EdgeShape extends Edge {
    private static Group shapeGroup = new Group();


    private Line shape;

    public EdgeShape(Vertex start, Vertex end) {
        super(start, end);
        shape = new Line(start.getCoordinates().getX(), start.getCoordinates().getY(), end.getCoordinates().getX(), end.getCoordinates().getY());
        shape.setStroke(COLOR_EDGE);
        shapeGroup.getChildren().add(shape);
    }

    public static void setShapeGroup(Group shapeGroup) {
        EdgeShape.shapeGroup = shapeGroup;
    }
}
