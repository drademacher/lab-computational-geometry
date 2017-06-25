package shapes;

import entities.Man;
import graph.GraphController;
import graph.Vertex;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.shape.Circle;
import strategy.Strategy;
import strategy.StrategyRandom;

import static shapes.ShapeConstants.COLOR_MAN;
import static shapes.ShapeConstants.ENTITY_RADIUS;

/**
 * Created by Jens on 25.06.2017.
 */
public class ShapedMan {
    private static Group shapeGroup = new Group();

    private Circle shape;
    private Man man;
    private GraphController graphController;

    public ShapedMan(GraphController graphController, Man man) {

        this.graphController = graphController;
        this.man = man;

        shape = new Circle(man.getCoordinates().getX(), man.getCoordinates().getY(), ENTITY_RADIUS, COLOR_MAN);
        shapeGroup.getChildren().add(shape);

        shape.setOnContextMenuRequested(event1 -> {
            final ContextMenu contextMenu = new ContextMenu();
            MenuItem item0 = new MenuItem("Remove Man");
            MenuItem item1 = new MenuItem("Change Strategy");
            MenuItem closeItem = new MenuItem("Close");



            item0.setOnAction(event2 -> {
                graphController.removeMan(man);
            });

            item1.setOnAction(event2 -> {
                System.out.println("TODO change Strategy");
                //TODO DANNY choose strategy
                Strategy strategy = new StrategyRandom();
                graphController.setManStrategy(man, strategy);
            });

            contextMenu.getItems().addAll(item0, item1, new SeparatorMenuItem(), closeItem);
            contextMenu.show(shape, event1.getScreenX(), event1.getScreenY());
        });
    }

    public void relocate() {
        shape.relocate(man.getCoordinates().getX()-ENTITY_RADIUS, man.getCoordinates().getY()-ENTITY_RADIUS);
    }

    public void delete() {
        shapeGroup.getChildren().remove(shape);
    }

    public static void setShapeGroup(Group shapeGroup) {
        ShapedMan.shapeGroup = shapeGroup;
    }
}
