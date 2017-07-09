package lions_on_graph.visualization;

import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.shape.Circle;
import lions_on_graph.core.CoreController;
import util.ContextMenuHolder;
import util.Point;
import util.ZoomScrollPane;

/**
 * Created by Jens on 09.07.2017.
 */
public class ShapeStepPreview implements ShapedVertex {
    private static ZoomScrollPane mainPane;
    private static Group shapeGroup = new Group();

    private Circle shape;
    private CoreController coreController;
    private Point coordinates;

    public ShapeStepPreview(CoreController coreController, Point startCoordinates) {

        this.coreController = coreController;
        this.coordinates = startCoordinates;

        shape = new Circle(coordinates.getX(), coordinates.getY(), ShapeConstants.ENTITY_RADIUS, ShapeConstants.PREVIEW);
        shapeGroup.getChildren().add(shape);

    }

    public static void setMainPane(ZoomScrollPane mainPane) {
        ShapeStepPreview.mainPane = mainPane;
    }

    public static void setShapeGroup(Group shapeGroup) {
        ShapeStepPreview.shapeGroup = shapeGroup;
    }

    @Override
    public void relocate(Point coordinates) {
        this.coordinates = coordinates;
        shape.relocate(coordinates.getX() - ShapeConstants.ENTITY_RADIUS, coordinates.getY() - ShapeConstants.ENTITY_RADIUS);
    }

    public void delete() {
        shapeGroup.getChildren().remove(shape);
    }
}

