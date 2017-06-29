package shapes;

import graph.GraphController;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.shape.Circle;
import strategy.Strategy;
import strategy.StrategyAggroGreedy;
import strategy.StrategyDoNothing;
import strategy.StrategyRandom;
import util.Point;
import visualization.ZoomScrollPane;

import static shapes.ShapeConstants.COLOR_LION;
import static shapes.ShapeConstants.ENTITY_RADIUS;

/**
 * Created by Jens on 25.06.2017.
 */
public class ShapedLion  implements ShapedEntity{
    private static ZoomScrollPane mainPane;
    private static Group shapeGroup = new Group();

    private Circle shape;
    private GraphController graphController;

    public ShapedLion(GraphController graphController, Point coordinates) {

        this.graphController = graphController;

        shape = new Circle(coordinates.getX(), coordinates.getY(), ENTITY_RADIUS, COLOR_LION);
        shapeGroup.getChildren().add(shape);

        shape.setOnContextMenuRequested(event1 -> {
            event1.consume();

            if (!this.graphController.isEditMode())
                return;

            final ContextMenu contextMenu = ContextMenuHolder.getFreshContextMenu();
            MenuItem item0 = new MenuItem("Remove Lion");
            MenuItem item1 = new MenuItem("Relocate Lion");
            MenuItem closeItem = new MenuItem("Close");

            Menu strategyMenu = new Menu("Set Strategy");
            MenuItem item2 = new MenuItem("Wait");
            MenuItem item3 = new MenuItem("Greedy");
            MenuItem item4 = new MenuItem("Random");
            strategyMenu.getItems().addAll(item2, item3, item4);


            item0.setOnAction(event2 -> {
                graphController.removeLion(graphController.getLionByCoordinate(coordinates));
            });

            item1.setOnAction(event2 -> {
                mainPane.setOnMouseClicked(event3 -> {

                    mainPane.setOnMouseClicked(null);

//                    System.out.println(mainPane.getLocalCoordinates(event3.getX(), event3.getY()));
                    graphController.relocateLion(graphController.getLionByCoordinate(coordinates), graphController.getVertexByCoordinate(mainPane.getLocalCoordinates(event3.getX(), event3.getY())));

                });
            });

            item2.setOnAction(event2 -> {
                Strategy strategy = new StrategyDoNothing();
                graphController.setLionStrategy(graphController.getLionByCoordinate(coordinates), strategy);
            });

            item3.setOnAction(event2 -> {
                Strategy strategy = new StrategyAggroGreedy();
                graphController.setLionStrategy(graphController.getLionByCoordinate(coordinates), strategy);
            });

            item4.setOnAction(event2 -> {
                Strategy strategy = new StrategyRandom();
                graphController.setLionStrategy(graphController.getLionByCoordinate(coordinates), strategy);
            });

            contextMenu.getItems().addAll(item0, item1, strategyMenu, new SeparatorMenuItem(), closeItem);
            contextMenu.show(shape, event1.getScreenX(), event1.getScreenY());
        });
    }

    public static void setMainPane(ZoomScrollPane mainPane) {
        ShapedLion.mainPane = mainPane;
    }

    public static void setShapeGroup(Group shapeGroup) {
        ShapedLion.shapeGroup = shapeGroup;
    }

    public void relocate(Point coordinates) {
        // TODO: jens, start und ziel angeben, dann kann man das auch schön machen

//        Path path = new Path();
//        path.getElements().add(new MoveTo(shape.getCenterX(), shape.getCenterY()));
//        path.getElements().add(new LineTo(lion.getCoordinates().getX() - ENTITY_RADIUS, lion.getCoordinates().getY() - ENTITY_RADIUS));
//        PathTransition pathTransition = new PathTransition();
//        pathTransition.setDuration(Duration.millis(250));
//        pathTransition.setPath(path);
//        pathTransition.setNode(shape);

        shape.relocate(coordinates.getX() - ENTITY_RADIUS, coordinates.getY() - ENTITY_RADIUS);
//        pathTransition.play();
    }

    public void delete() {
        shapeGroup.getChildren().remove(shape);
    }
}
