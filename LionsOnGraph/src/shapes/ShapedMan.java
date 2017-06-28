package shapes;

import entities.Man;
import graph.GraphController;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.shape.Circle;
import strategy.*;
import util.Point;
import visualization.ZoomScrollPane;

import static shapes.ShapeConstants.COLOR_MAN;
import static shapes.ShapeConstants.ENTITY_RADIUS;

/**
 * Created by Jens on 25.06.2017.
 */
public class ShapedMan implements Shape {
    private static ZoomScrollPane mainPane;
    private static Group shapeGroup = new Group();

    private Circle shape;
    private GraphController graphController;

    public ShapedMan(GraphController graphController, Point coordinates) {

        this.graphController = graphController;

        shape = new Circle(coordinates.getX(), coordinates.getY(), ENTITY_RADIUS, COLOR_MAN);
        shapeGroup.getChildren().add(shape);

        shape.setOnContextMenuRequested(event1 -> {
            event1.consume();

            if (!this.graphController.isEditMode())
                return;

            final ContextMenu contextMenu = ContextMenuHolder.getFreshContextMenu();
            MenuItem item0 = new MenuItem("Remove Man");
            MenuItem item1 = new MenuItem("Relocate Man");
            MenuItem closeItem = new MenuItem("Close");

            Menu strategyMenu = new Menu("Set Strategy");
            MenuItem item2 = new MenuItem("Wait");
            MenuItem item3 = new MenuItem("Greedy");
            MenuItem item4 = new MenuItem("Random");
            strategyMenu.getItems().addAll(item2, item3, item4);


            item2.setOnAction(event2 -> {
                Strategy strategy = new StrategyDoNothing();
                graphController.setManStrategy(graphController.getManByCoordinate(coordinates), strategy);
            });

            item3.setOnAction(event2 -> {
                Strategy strategy = new StrategyRunAwayGreedy();
                graphController.setManStrategy(graphController.getManByCoordinate(coordinates), strategy);
            });

            item4.setOnAction(event2 -> {
                Strategy strategy = new StrategyRandom();
                graphController.setManStrategy(graphController.getManByCoordinate(coordinates), strategy);
            });

            item0.setOnAction(event2 -> {
                graphController.removeMan(graphController.getManByCoordinate(coordinates));
            });

            item1.setOnAction(event2 -> {
                mainPane.setOnMouseClicked(event3 -> {

                    mainPane.setOnMouseClicked(null);

//                    System.out.println(mainPane.getLocalCoordinates(event3.getX(), event3.getY()));
                    graphController.relocateMan(graphController.getManByCoordinate(coordinates), graphController.getVertexByCoordinate(mainPane.getLocalCoordinates(event3.getX(), event3.getY())));

                });
            });

            contextMenu.getItems().addAll(item0, item1, strategyMenu, new SeparatorMenuItem(), closeItem);
            contextMenu.show(shape, event1.getScreenX(), event1.getScreenY());
        });
    }

    public static void setMainPane(ZoomScrollPane mainPane) {
        ShapedMan.mainPane = mainPane;
    }

    public static void setShapeGroup(Group shapeGroup) {
        ShapedMan.shapeGroup = shapeGroup;
    }

    @Override
    public void relocate(Point coordinates) {
        shape.relocate(coordinates.getX() - ENTITY_RADIUS, coordinates.getY() - ENTITY_RADIUS);
    }

    public void delete() {
        shapeGroup.getChildren().remove(shape);
    }
}
