package shapes;

import graph.GraphController;
import graph.SmallVertex;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.shape.Circle;

import static shapes.ShapeConstants.COLOR_SMALL_VERTEX;
import static shapes.ShapeConstants.SMALL_VERTEX_RADIUS;

public class ShapedSmallVertex {
    private static Group shapeGroup = new Group();

    private Circle shape;
    private SmallVertex vertex;
    private GraphController graphController;

    public ShapedSmallVertex(GraphController graphController, SmallVertex vertex) {

        this.graphController = graphController;
        this.vertex = vertex;

        shape = new Circle(vertex.getCoordinates().getX(), vertex.getCoordinates().getY(), SMALL_VERTEX_RADIUS, COLOR_SMALL_VERTEX);
        shapeGroup.getChildren().add(shape);

        shape.setOnContextMenuRequested(event1 -> {
            if (!this.graphController.isEditMode())
                return;

            final ContextMenu contextMenu = new ContextMenu();
            MenuItem item0 = new MenuItem("Add Man");
            MenuItem item1 = new MenuItem("Add Lion");
            MenuItem closeItem = new MenuItem("Close");


            item0.setOnAction(event2 -> {
                System.out.println("Add Man");
                // TODO: something like new Man(vertex.getCoordinates());
                graphController.setMan(vertex);
            });

            item1.setOnAction(event2 -> {
                System.out.println("Add Lion");
                // TODO: something like new Lion(vertex.getCoordinates());
                graphController.setLion(vertex);
            });

            contextMenu.getItems().addAll(item0, item1, new SeparatorMenuItem(), closeItem);
            contextMenu.show(shape, event1.getScreenX(), event1.getScreenY());
        });
    }

    public static void setShapeGroup(Group shapeGroup) {
        ShapedSmallVertex.shapeGroup = shapeGroup;
    }

    public void relocate() {
        shape.relocate(vertex.getCoordinates().getX() - SMALL_VERTEX_RADIUS, vertex.getCoordinates().getY() - SMALL_VERTEX_RADIUS);
    }

    public void delete() {
        shapeGroup.getChildren().remove(shape);
    }
}
