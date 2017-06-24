package shapes;

import graph.BigVertex;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.shape.Circle;
import util.Point;

import static shapes.ShapeConstants.BIG_VERTEX_RADIUS;
import static shapes.ShapeConstants.COLOR_NODE;

public class BigVertexShape extends BigVertex {
    private static Group shapeGroup;

    private Circle shape;


    public BigVertexShape(int id, Point coords) {
        super(id, coords);
        shape = new Circle(coords.getX(), coords.getY(), BIG_VERTEX_RADIUS, COLOR_NODE);
        shapeGroup.getChildren().add(shape);

        shape.setOnContextMenuRequested(event1 -> {
            final ContextMenu contextMenu = new ContextMenu();
            MenuItem item1 = new MenuItem("Remove Node");
            item1.setOnAction(event2 -> {

            });
//            MenuItem item2 = new MenuItem("Preferences");
//            item2.setOnAction(e -> System.out.println("Preferences"));
            contextMenu.getItems().addAll(item1);
            contextMenu.show(shape, event1.getScreenX(), event1.getScreenY());
        });
    }

    public static void setShapeGroup(Group shapeGroup) {
        BigVertexShape.shapeGroup = shapeGroup;
    }
}
