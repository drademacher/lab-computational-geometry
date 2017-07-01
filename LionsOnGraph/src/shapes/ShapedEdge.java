package shapes;

import graph.CoreController;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.shape.Line;
import util.Point;

import static shapes.ShapeConstants.COLOR_EDGE;


public class ShapedEdge {
    private static Group shapeGroup = new Group();

    private CoreController coreController;
    private Line shape;
    private Point from;
    private Point to;

    public ShapedEdge(CoreController coreController, Point startFrom, Point startTo) {

        this.coreController = coreController;
        this.from = startFrom;
        this.to = startTo;

        shape = new Line(from.getX(), from.getY(), to.getX(), to.getY());
        shape.setStroke(COLOR_EDGE);
        shapeGroup.getChildren().add(shape);

        shape.setOnContextMenuRequested(event1 -> {
            event1.consume();

            if (!this.coreController.isEditMode())
                return;

            final ContextMenu contextMenu = ContextMenuHolder.getFreshContextMenu();
            MenuItem item0 = new MenuItem("Remove Edge");
            MenuItem item1 = new MenuItem("Change Edge Weight //TODO");
            MenuItem closeItem = new MenuItem("Close");


            item0.setOnAction(event2 -> {
                coreController.removeEdge(coreController.getBigVertexByCoordinate(from), coreController.getBigVertexByCoordinate(to));
            });

            item1.setOnAction(event2 -> {
                //TODO
            });

            contextMenu.getItems().addAll(item0, item1, closeItem);
            contextMenu.show(shape, event1.getScreenX(), event1.getScreenY());
        });
    }

    public static void setShapeGroup(Group shapeGroup) {
        ShapedEdge.shapeGroup = shapeGroup;
    }

    public void relocate(Point from, Point to) {
        this.from = from;
        this.to = to;
        shape.setStartX(from.getX());
        shape.setStartY(from.getY());
        shape.setEndX(to.getX());
        shape.setEndY(to.getY());
    }

    public void delete() {
        shapeGroup.getChildren().remove(shape);
    }
}
