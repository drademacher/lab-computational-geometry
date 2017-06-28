package shapes;

import graph.GraphController;
import graph.SmallVertex;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.shape.Circle;
import util.Point;

import static shapes.ShapeConstants.*;

public class ShapedSmallVertex implements Shape {
    private static Group shapeGroup = new Group();

    private Circle shape;
    private GraphController graphController;

    public ShapedSmallVertex(GraphController graphController, Point coordinates) {

        this.graphController = graphController;

        shape = new Circle(coordinates.getX(), coordinates.getY(), SMALL_VERTEX_RADIUS);
        shape.setStrokeWidth(SMALL_VERTEX_RADIUS / 5);
        shape.setStroke(COLOR_SMALL_VERTEX);
        shape.setFill(COLOR_BACKGROUND);
        shapeGroup.getChildren().add(shape);

        shape.setOnContextMenuRequested(event1 -> {
            event1.consume();

            if (!this.graphController.isEditMode())
                return;

            final ContextMenu contextMenu = ContextMenuHolder.getFreshContextMenu();
            MenuItem item0 = new MenuItem("Add Man");
            MenuItem item1 = new MenuItem("Add Lion");
            MenuItem closeItem = new MenuItem("Close");


            item0.setOnAction(event2 -> {
                System.out.println("Add Man");
                // TODO: something like new Man(vertex.getCoordinates());
                graphController.setMan(graphController.getSmallVertexByCoordinate(coordinates));
            });

            item1.setOnAction(event2 -> {
                System.out.println("Add Lion");
                // TODO: something like new Lion(vertex.getCoordinates());
                graphController.setLion(graphController.getSmallVertexByCoordinate(coordinates));
            });

            contextMenu.getItems().addAll(item0, item1, new SeparatorMenuItem(), closeItem);
            contextMenu.show(shape, event1.getScreenX(), event1.getScreenY());
        });
    }

    public static void setShapeGroup(Group shapeGroup) {
        ShapedSmallVertex.shapeGroup = shapeGroup;
    }

    public void relocate(Point coordinates) {
        shape.relocate(coordinates.getX() - SMALL_VERTEX_RADIUS, coordinates.getY() - SMALL_VERTEX_RADIUS);
    }

    public void delete() {
        shapeGroup.getChildren().remove(shape);
    }
}
