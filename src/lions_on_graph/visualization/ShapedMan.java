package lions_on_graph.visualization;

import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.shape.Circle;
import lions_on_graph.core.entities.Man;
import lions_on_graph.core.CoreController;
import lions_on_graph.core.strategies.ManStrategies.StrategyDoNothing;
import lions_on_graph.core.strategies.ManStrategies.StrategyRandom;
import lions_on_graph.core.strategies.ManStrategies.StrategyRunAwayGreedy;
import lions_on_graph.core.strategies.Strategy;
import util.ContextMenuHolder;
import util.Point;
import util.ZoomScrollPane;

/**
 * Created by Jens on 25.06.2017.
 */
public class ShapedMan implements ShapedEntity {
    private static ZoomScrollPane mainPane;
    private static Group shapeGroup = new Group();

    private Circle shape;
    private CoreController coreController;
    private Point coordinates;

    public ShapedMan(CoreController coreController, Point startCoordinates) {

        this.coreController = coreController;
        this.coordinates = startCoordinates;

        shape = new Circle(coordinates.getX(), coordinates.getY(), ShapeConstants.ENTITY_RADIUS, ShapeConstants.COLOR_MAN);
        shapeGroup.getChildren().add(shape);

        shape.setOnContextMenuRequested(event1 -> {
            event1.consume();

            if (!this.coreController.isEditMode())
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
                Man man = coreController.getManByCoordinate(coordinates);
                Strategy strategy = new StrategyDoNothing(coreController, man);
                coreController.setManStrategy(coordinates, strategy);
            });

            item3.setOnAction(event2 -> {
                Man man = coreController.getManByCoordinate(coordinates);
                Strategy strategy = new StrategyRunAwayGreedy(coreController, man);
                coreController.setManStrategy(coordinates, strategy);
            });

            item4.setOnAction(event2 -> {
                Man man = coreController.getManByCoordinate(coordinates);
                Strategy strategy = new StrategyRandom(coreController, man);
                coreController.setManStrategy(coordinates, strategy);
            });

            item0.setOnAction(event2 -> {
                coreController.removeMan(coordinates);
            });

            item1.setOnAction(event2 -> {
                mainPane.setOnMouseClicked(event3 -> {

                    mainPane.setOnMouseClicked(null);

//                    System.out.println(mainPane.getLocalCoordinates(event3.getX(), event3.getY()));
                    System.out.println("SHAPE... call relocate");
                    coreController.relocateMan(coordinates, mainPane.getLocalCoordinates(event3.getX(), event3.getY()));

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
        this.coordinates = coordinates;
        shape.relocate(coordinates.getX() - ShapeConstants.ENTITY_RADIUS, coordinates.getY() - ShapeConstants.ENTITY_RADIUS);
    }

    public void delete() {
        shapeGroup.getChildren().remove(shape);
    }
}
