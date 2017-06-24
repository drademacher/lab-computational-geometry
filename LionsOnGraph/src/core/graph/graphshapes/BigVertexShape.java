package core.graph.graphshapes;

import core.graph.graphlogic.BigVertex;
import core.util.Point;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.shape.Circle;

import static core.graph.graphshapes.ShapeConstants.BIG_VERTEX_RADIUS;
import static visualization.VisConstants.COLOR_NODE;

/**
 * Created by Danny on 23.06.2017.
 */
public class BigVertexShape extends BigVertex {
    private static Group shapeGroup;

    private Circle shape;


    public BigVertexShape(int id, Point coords) {
        super(id, coords);
        shape = new Circle(coords.getX(), coords.getY(), BIG_VERTEX_RADIUS, COLOR_NODE);
        shapeGroup.getChildren().add(shape);

        shape.setOnMouseClicked(event -> {
//            System.out.println("vertex: " + event.getX() + " - " + event.getY());

            if (event.getButton() == MouseButton.SECONDARY) {
                this.deleteVertex();
            }
        });
    }

    public static void setShapeGroup(Group shapeGroup) {
        BigVertexShape.shapeGroup = shapeGroup;
    }
}
