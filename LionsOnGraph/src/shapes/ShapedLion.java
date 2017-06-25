package shapes;

import entities.Lion;
import graph.GraphController;
import javafx.animation.PathTransition;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import strategy.Strategy;
import strategy.StrategyAggroGreedy;
import strategy.StrategyDoNothing;
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
            if (!this.graphController.isEditMode())
                return;

            final ContextMenu contextMenu = new ContextMenu();
            MenuItem item0 = new MenuItem("Remove Lion");
            MenuItem closeItem = new MenuItem("Close");

            Menu strategyMenu = new Menu("Set Strategy");
            MenuItem item1 = new MenuItem("Wait");
            MenuItem item2 = new MenuItem("Greedy");
            MenuItem item3 = new MenuItem("Random");
            strategyMenu.getItems().addAll(item1, item2, item3);


            item0.setOnAction(event2 -> {
                graphController.removeLion(lion);
            });

            item1.setOnAction(event2 -> {
                Strategy strategy = new StrategyDoNothing();
                graphController.setLionStrategy(lion, strategy);
            });

            item2.setOnAction(event2 -> {
                Strategy strategy = new StrategyAggroGreedy();
                graphController.setLionStrategy(lion, strategy);
            });

            item3.setOnAction(event2 -> {
                Strategy strategy = new StrategyRandom();
                graphController.setLionStrategy(lion, strategy);
            });

            contextMenu.getItems().addAll(item0, strategyMenu, new SeparatorMenuItem(), closeItem);
            contextMenu.show(shape, event1.getScreenX(), event1.getScreenY());
        });
    }

    public static void setShapeGroup(Group shapeGroup) {
        ShapedLion.shapeGroup = shapeGroup;
    }

    public void relocate() {
        // TODO: jens, start und ziel angeben, dann kann man das auch schön machen

//        Path path = new Path();
//        path.getElements().add(new MoveTo(shape.getCenterX(), shape.getCenterY()));
//        path.getElements().add(new LineTo(lion.getCoordinates().getX() - ENTITY_RADIUS, lion.getCoordinates().getY() - ENTITY_RADIUS));
//        PathTransition pathTransition = new PathTransition();
//        pathTransition.setDuration(Duration.millis(250));
//        pathTransition.setPath(path);
//        pathTransition.setNode(shape);

        shape.relocate(lion.getCoordinates().getX() - ENTITY_RADIUS, lion.getCoordinates().getY() - ENTITY_RADIUS);
//        pathTransition.play();
    }

    public void delete() {
        shapeGroup.getChildren().remove(shape);
    }
}
