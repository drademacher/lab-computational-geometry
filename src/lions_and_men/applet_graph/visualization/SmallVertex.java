package lions_and_men.applet_graph.visualization;

import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.shape.Circle;
import lions_and_men.util.ContextMenuHolder;
import lions_and_men.util.Point;

/**
 * Shape of a small vertex.
 */
public class SmallVertex implements Vertex {
    private static Group shapeGroup = new Group();

    private Circle shape;
    private VisualizedCoreController coreController;
    private Point coordinates;

    SmallVertex(VisualizedCoreController coreController, Point startCoordinates) {

        this.coreController = coreController;
        this.coordinates = startCoordinates;

        shape = new Circle(coordinates.getX(), coordinates.getY(), Constants.SMALL_VERTEX_RADIUS);
        shape.setStrokeWidth(Constants.SMALL_VERTEX_RADIUS / 5);
        shape.setStroke(Constants.COLOR_SMALL_VERTEX);
        shape.setFill(Constants.COLOR_BACKGROUND);
        shapeGroup.getChildren().add(shape);

        shape.setOnContextMenuRequested(event1 -> {
            event1.consume();

            if (!this.coreController.isEditMode())
                return;

            final ContextMenu contextMenu = ContextMenuHolder.getFreshContextMenu();
            MenuItem item0 = new MenuItem("Add Man");
            MenuItem item1 = new MenuItem("Add Lion");
            MenuItem closeItem = new MenuItem("Close");


            item0.setOnAction(event2 -> coreController.setMan(coordinates));

            item1.setOnAction(event2 -> coreController.setLion(coordinates));

            contextMenu.getItems().addAll(item0, item1, new SeparatorMenuItem(), closeItem);
            contextMenu.show(shape, event1.getScreenX(), event1.getScreenY());
        });
    }

    public static void setShapeGroup(Group shapeGroup) {
        SmallVertex.shapeGroup = shapeGroup;
    }

    public void relocate(Point coordinates) {
        this.coordinates = coordinates;
        shape.relocate(coordinates.getX() - Constants.SMALL_VERTEX_RADIUS, coordinates.getY() - Constants.SMALL_VERTEX_RADIUS);
    }

    public void delete() {
        shapeGroup.getChildren().remove(shape);
    }

    public Point getPosition() {
        return coordinates;
    }

}
