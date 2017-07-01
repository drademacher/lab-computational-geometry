package shapes;

import entities.Lion;
import graph.CoreController;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.shape.Circle;
import strategy.LionStrategies.StrategyDoNothing;
import strategy.LionStrategies.StrategyRandom;
import strategy.Strategy;
import strategy.LionStrategies.StrategyAggroGreedy;
import util.ContextMenuHolder;
import util.Point;
import util.ZoomScrollPane;

import java.util.Optional;

import static shapes.ShapeConstants.COLOR_LION;
import static shapes.ShapeConstants.ENTITY_RADIUS;

/**
 * Created by Jens on 25.06.2017.
 */
public class ShapedLion implements ShapedEntity {
    private static ZoomScrollPane mainPane;
    private static Group shapeGroup = new Group();

    private Circle shape;
    private CoreController coreController;
    private Point coordinates;

    public ShapedLion(CoreController coreController, Point startCcoordinates) {

        this.coreController = coreController;
        this.coordinates = startCcoordinates;

        shape = new Circle(coordinates.getX(), coordinates.getY(), ENTITY_RADIUS, COLOR_LION);
        shapeGroup.getChildren().add(shape);

        shape.setOnContextMenuRequested(event1 -> {
            event1.consume();

            if (!this.coreController.isEditMode())
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


            Menu edgeMenu = new Menu("Lion Range");

            MenuItem iteme1 = new MenuItem("Increment");
            MenuItem iteme2 = new MenuItem("Decrement");
            MenuItem iteme3 = new MenuItem("Set");


            iteme1.setOnAction(event2 -> {
                Lion lion = coreController.getLionByCoordinate(coordinates);
                coreController.incrementLionRange(coordinates);
            });

            iteme2.setOnAction(event2 -> {
                Lion lion = coreController.getLionByCoordinate(coordinates);
                coreController.decrementLionRange(coordinates);
            });

            iteme3.setOnAction(event2 -> {
                Lion lion = coreController.getLionByCoordinate(coordinates);

                TextInputDialog dialog = new TextInputDialog("" + lion.getRange());
                dialog.setTitle("Set Lion Range");
                dialog.setHeaderText("Enter the new range of the lion.");

                Optional<String> result = dialog.showAndWait();

                if (result.isPresent()) {
                    int newWeight = Integer.parseInt(result.get());
                    coreController.setLionRange(coordinates, newWeight);
                }
            });

            edgeMenu.getItems().addAll(iteme1, iteme2, iteme3);


            item0.setOnAction(event2 -> {
                coreController.removeLion(coordinates);
            });

            item1.setOnAction(event2 -> {
                mainPane.setOnMouseClicked(event3 -> {

                    mainPane.setOnMouseClicked(null);

//                    System.out.println(mainPane.getLocalCoordinates(event3.getX(), event3.getY()));
                    coreController.relocateLion(coordinates, mainPane.getLocalCoordinates(event3.getX(), event3.getY()));

                });
            });

            item2.setOnAction(event2 -> {
                Lion lion = coreController.getLionByCoordinate(coordinates);
                Strategy strategy = new StrategyDoNothing(coreController, lion);
                coreController.setLionStrategy(coordinates, strategy);
            });

            item3.setOnAction(event2 -> {
                Lion lion = coreController.getLionByCoordinate(coordinates);
                Strategy strategy = new StrategyAggroGreedy(coreController, lion);
                coreController.setLionStrategy(coordinates, strategy);
            });

            item4.setOnAction(event2 -> {
                Lion lion = coreController.getLionByCoordinate(coordinates);
                Strategy strategy = new StrategyRandom(coreController, lion);
                coreController.setLionStrategy(coordinates, strategy);
            });

            contextMenu.getItems().addAll(item0, item1, strategyMenu, edgeMenu, new SeparatorMenuItem(), closeItem);
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

//        Path path = new Path();
//        path.getElements().add(new MoveTo(shape.getCenterX(), shape.getCenterY()));
//        path.getElements().add(new LineTo(lion.getCoordinates().getX() - ENTITY_RADIUS, lion.getCoordinates().getY() - ENTITY_RADIUS));
//        PathTransition pathTransition = new PathTransition();
//        pathTransition.setDuration(Duration.millis(250));
//        pathTransition.setPath(path);
//        pathTransition.setNode(shape);

        this.coordinates = coordinates;
        shape.relocate(coordinates.getX() - ENTITY_RADIUS, coordinates.getY() - ENTITY_RADIUS);
//        pathTransition.play();
    }

    public void delete() {
        shapeGroup.getChildren().remove(shape);
    }
}
