package lions_on_graph.visualization;

import lions_on_graph.core.CoreController;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.shape.Circle;
import util.ContextMenuHolder;
import util.Point;

public class ShapedSmallVertex implements ShapedVertex {
    private static Group shapeGroup = new Group();

    private Circle shape;
    private CoreController coreController;
    private Point coordinates;

    public ShapedSmallVertex(CoreController coreController, Point startCoordinates) {

        this.coreController = coreController;
        this.coordinates = startCoordinates;

        shape = new Circle(coordinates.getX(), coordinates.getY(), ShapeConstants.SMALL_VERTEX_RADIUS);
        shape.setStrokeWidth(ShapeConstants.SMALL_VERTEX_RADIUS / 5);
        shape.setStroke(ShapeConstants.COLOR_SMALL_VERTEX);
        shape.setFill(ShapeConstants.COLOR_BACKGROUND);
        shapeGroup.getChildren().add(shape);

        shape.setOnContextMenuRequested(event1 -> {
            event1.consume();

            if (!this.coreController.isEditMode())
                return;

            final ContextMenu contextMenu = ContextMenuHolder.getFreshContextMenu();
            MenuItem item0 = new MenuItem("Add Man");
            MenuItem item1 = new MenuItem("Add Lion");
            MenuItem closeItem = new MenuItem("Close");


            item0.setOnAction(event2 -> {
                System.out.println("Add Man");
                // TODO: something like new Man(vertex.getCoordinates());
                coreController.setMan(coordinates);
            });

            item1.setOnAction(event2 -> {
                System.out.println("Add Lion");
                // TODO: something like new Lion(vertex.getCoordinates());
                coreController.setLion(coordinates);
            });

            contextMenu.getItems().addAll(item0, item1, new SeparatorMenuItem(), closeItem);
            contextMenu.show(shape, event1.getScreenX(), event1.getScreenY());
        });
    }

    public static void setShapeGroup(Group shapeGroup) {
        ShapedSmallVertex.shapeGroup = shapeGroup;
    }

    public void relocate(Point coordinates) {
        this.coordinates = coordinates;
        shape.relocate(coordinates.getX() - ShapeConstants.SMALL_VERTEX_RADIUS, coordinates.getY() - ShapeConstants.SMALL_VERTEX_RADIUS);
    }

    public void delete() {
        shapeGroup.getChildren().remove(shape);
    }
}
