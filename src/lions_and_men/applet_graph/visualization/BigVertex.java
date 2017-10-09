package lions_and_men.applet_graph.visualization;

import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.shape.Circle;
import lions_and_men.util.ContextMenuHolder;
import lions_and_men.util.Point;
import lions_and_men.util.ZoomScrollPane;

/**
 * Shape of a big vertex
 */
public class BigVertex implements Vertex {
    private static ZoomScrollPane mainPane;
    private static Group shapeGroup;

    private Circle shape;
    private VisualizedCoreController coreController;
    private Point coordinates;


    BigVertex(VisualizedCoreController coreController, Point startCoordinates) {
        this.coreController = coreController;
        this.coordinates = startCoordinates;


        shape = new Circle(coordinates.getX(), coordinates.getY(), Constants.BIG_VERTEX_RADIUS);
        shape.setStrokeWidth(Constants.BIG_VERTEX_RADIUS / 5);
        shape.setStroke(Constants.COLOR_VERTEX);
        shape.setFill(Constants.COLOR_BACKGROUND);
        shapeGroup.getChildren().add(shape);

        shape.setOnContextMenuRequested(event1 -> {
            event1.consume();

            if (!this.coreController.isEditMode())
                return;

            ContextMenu contextMenu = ContextMenuHolder.getFreshContextMenu();
            MenuItem item0 = new MenuItem("Create Edge");
            MenuItem item1 = new MenuItem("Remove Edge");
            MenuItem item2 = new MenuItem("Relocate Node");
            MenuItem item5 = new MenuItem("Remove Node");
            MenuItem item3 = new MenuItem("Add Man");
            MenuItem item4 = new MenuItem("Add Lion");
            MenuItem closeItem = new MenuItem("Close");

            item0.setOnAction(event2 -> mainPane.setOnMouseClicked(event3 -> {

                mainPane.setOnMouseClicked(null);

                coreController.createEdge(coordinates, mainPane.getLocalCoordinates(event3.getX(), event3.getY()));

            }));

            item1.setOnAction(event2 -> mainPane.setOnMouseClicked(event3 -> {

                mainPane.setOnMouseClicked(null);

                coreController.removeEdge(coordinates, mainPane.getLocalCoordinates(event3.getX(), event3.getY()));

            }));

            item2.setOnAction(event2 -> mainPane.setOnMouseClicked(event3 -> {

                mainPane.setOnMouseClicked(null);

                coreController.relocateVertex(coordinates, mainPane.getLocalCoordinates(event3.getX(), event3.getY()));

            }));

            item5.setOnAction(event2 -> coreController.deleteVertex(coordinates));

            item3.setOnAction(event2 -> coreController.setMan(coordinates));

            item4.setOnAction(event2 -> coreController.setLion(coordinates));

            contextMenu.getItems().addAll(item2, item5, new SeparatorMenuItem(), item0, item1, new SeparatorMenuItem(), item3, item4, new SeparatorMenuItem(), closeItem);
            contextMenu.show(shape, event1.getScreenX(), event1.getScreenY());
        });

    }

    public static void setMainPane(ZoomScrollPane mainPane) {
        BigVertex.mainPane = mainPane;
    }

    public static void setShapeGroup(Group shapeGroup) {
        BigVertex.shapeGroup = shapeGroup;
    }

    public void relocate(Point coordinates) {
        this.coordinates = coordinates;
        shape.relocate(coordinates.getX() - Constants.BIG_VERTEX_RADIUS, coordinates.getY() - Constants.BIG_VERTEX_RADIUS);
    }

    public void delete() {
        shapeGroup.getChildren().remove(shape);
    }

    public Point getPosition() {
        return coordinates;
    }
}
