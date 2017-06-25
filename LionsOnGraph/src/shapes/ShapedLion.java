package shapes;

import entities.Lion;
import graph.GraphController;
import graph.Vertex;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.shape.Circle;
import strategy.Strategy;
import strategy.StrategyRandom;

import static shapes.ShapeConstants.COLOR_LION;
import static shapes.ShapeConstants.ENTITY_RADIUS;

/**
 * Created by Jens on 25.06.2017.
 */
public class ShapedLion {
    private static Group shapeGroup = new Group();

    private Circle shape;
    private Lion lion;
    private GraphController graphController;

    public ShapedLion(GraphController graphController, Lion lion) {

        this.graphController = graphController;
        this.lion = lion;

        shape = new Circle(lion.getCoordinates().getX(), lion.getCoordinates().getY(), ENTITY_RADIUS, COLOR_LION);
        shapeGroup.getChildren().add(shape);

        shape.setOnContextMenuRequested(event1 -> {
            final ContextMenu contextMenu = new ContextMenu();
            MenuItem item0 = new MenuItem("Remove Lion");
            MenuItem item1 = new MenuItem("Change Strategy");
            MenuItem closeItem = new MenuItem("Close");



            item0.setOnAction(event2 -> {
                graphController.removeLion(lion);
            });

            item1.setOnAction(event2 -> {
                System.out.println("TODO change Strategy");
                //TODO DANNY choose strategy
                Strategy strategy = new StrategyRandom();
                graphController.setLionStrategy(lion, strategy);
            });

            contextMenu.getItems().addAll(item0, item1, new SeparatorMenuItem(), closeItem);
            contextMenu.show(shape, event1.getScreenX(), event1.getScreenY());
        });
    }

    public void relocate() {
        shape.relocate(lion.getCoordinates().getX()-ENTITY_RADIUS, lion.getCoordinates().getY()-ENTITY_RADIUS);
    }

    public void delete() {
        shapeGroup.getChildren().remove(shape);
    }

    public static void setShapeGroup(Group shapeGroup) {
        ShapedLion.shapeGroup = shapeGroup;
    }
}
