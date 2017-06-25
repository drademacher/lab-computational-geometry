package shapes;

import entities.Man;
import graph.GraphController;
import graph.Vertex;
import javafx.scene.Group;
import javafx.scene.shape.Circle;

import static shapes.ShapeConstants.COLOR_MAN;
import static shapes.ShapeConstants.ENTITY_RADIUS;

/**
 * Created by Jens on 25.06.2017.
 */
public class ShapedMan {
    private static Group shapeGroup = new Group();

    private Circle shape;
    private Man man;
    private GraphController graphController;

    public ShapedMan(GraphController graphController, Man man) {

        this.graphController = graphController;
        this.man = man;

        shape = new Circle(man.getCoordinates().getX(), man.getCoordinates().getY(), ENTITY_RADIUS, COLOR_MAN);
        shapeGroup.getChildren().add(shape);
    }

    public void relocate() {
        shape.relocate(man.getCoordinates().getX()-ENTITY_RADIUS, man.getCoordinates().getY()-ENTITY_RADIUS);
    }

    public void delete() {
        shapeGroup.getChildren().remove(shape);
    }

    public static void setShapeGroup(Group shapeGroup) {
        ShapedMan.shapeGroup = shapeGroup;
    }
}
