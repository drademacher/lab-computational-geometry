package shapes;

import graph.BigVertex;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.shape.Circle;
import util.Point;

import static shapes.ShapeConstants.BIG_VERTEX_RADIUS;
import static shapes.ShapeConstants.COLOR_NODE;

public class ShapedBigVertex extends BigVertex {
    private static Group mainGroup;
    private static Group shapeGroup;

    private Circle shape;


    public ShapedBigVertex(int id, Point coords) {
        super(id, coords);
        shape = new Circle(coords.getX(), coords.getY(), BIG_VERTEX_RADIUS, COLOR_NODE);
        shapeGroup.getChildren().add(shape);

        shape.setOnContextMenuRequested(event1 -> {
            final ContextMenu contextMenu = new ContextMenu();
            MenuItem item1 = new MenuItem("Relocate Node");
            MenuItem item4 = new MenuItem("Remove Node");
            MenuItem item2 = new MenuItem("Add Man");
            MenuItem item3 = new MenuItem("Add Lion");
            MenuItem closeItem = new MenuItem("Close");

            item1.setOnAction(event2 -> {
                mainGroup.setOnMouseClicked(event3 -> {
                    // this.setCoordinates(new Point((int) event3.getX(), (int) event3.getY()));
                    shape.relocate((int) event3.getX(), (int) event3.getY());
                    mainGroup.setOnMouseClicked(null);

                });
            });

            item4.setOnAction(event2 -> {
                shapeGroup.getChildren().remove(shape);
                this.deleteVertex();

            });

            item2.setOnAction(event2 -> {
                System.out.println("Add Man");
                // TODO: something like new Man(vertex.getCoordinates());
            });

            item3.setOnAction(event2 -> {
                System.out.println("Add Lion");
                // TODO: something like new Lion(vertex.getCoordinates());
            });

            contextMenu.getItems().addAll(item1, item4, new SeparatorMenuItem(), item2, item3, new SeparatorMenuItem(), closeItem);
            contextMenu.show(shape, event1.getScreenX(), event1.getScreenY());
        });

    }

    public static void setMainGroup(Group mainGroup) {
        ShapedBigVertex.mainGroup = mainGroup;
    }

    public static void setShapeGroup(Group shapeGroup) {
        ShapedBigVertex.shapeGroup = shapeGroup;
    }
}
