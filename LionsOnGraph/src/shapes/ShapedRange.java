package shapes;

import graph.CoreController;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.shape.Circle;
import util.Point;

import static shapes.ShapeConstants.*;

/**
 * Created by Jens on 29.06.2017.
 */
public class ShapedRange implements ShapedVertex{
    private static Group shapeGroup = new Group();

    private Circle shape;
    private CoreController coreController;

    public ShapedRange(CoreController coreController, Point coordinates) {

        this.coreController = coreController;

        shape = new Circle(coordinates.getX(), coordinates.getY(), ENTITY_RADIUS*2/3, COLOR_RANGE);
        shapeGroup.getChildren().add(shape);

    }

    public static void setShapeGroup(Group shapeGroup) {
        ShapedRange.shapeGroup = shapeGroup;
    }

    public void relocate(Point coordinates) {
        shape.relocate(coordinates.getX() - ENTITY_RADIUS, coordinates.getY() - ENTITY_RADIUS);
    }

    public void delete() {
        shapeGroup.getChildren().remove(shape);
    }
}
