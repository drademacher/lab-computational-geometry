package shapes;

import entities.Lion;
import graph.GraphController;
import graph.Vertex;
import javafx.scene.Group;
import javafx.scene.shape.Circle;

import static shapes.ShapeConstants.COLOR_LION;
import static shapes.ShapeConstants.ENTITY_RADIUS;

/**
 * Created by Jens on 25.06.2017.
 */
public class ShapedLion {
    private static Group shapeGroup = new Group();

    private Circle shape;
    private Lion lion;
    private GraphController graphController;

    public ShapedLion(GraphController graphController, Lion lion) {

        this.graphController = graphController;
        this.lion = lion;

        shape = new Circle(lion.getCoordinates().getX(), lion.getCoordinates().getY(), ENTITY_RADIUS, COLOR_LION);
        shapeGroup.getChildren().add(shape);
    }

    public void relocate() {
        shape.relocate(lion.getCoordinates().getX()-ENTITY_RADIUS, lion.getCoordinates().getY()-ENTITY_RADIUS);
    }

    public void delete() {
        shapeGroup.getChildren().remove(shape);
    }

    public static void setShapeGroup(Group shapeGroup) {
        ShapedLion.shapeGroup = shapeGroup;
    }
}
