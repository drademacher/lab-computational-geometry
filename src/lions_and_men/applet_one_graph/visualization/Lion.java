package lions_and_men.applet_one_graph.visualization;

import javafx.animation.PathTransition;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import lions_and_men.applet_one_graph.core.CoreController;
import lions_and_men.util.ContextMenuHolder;
import lions_and_men.util.Point;
import lions_and_men.util.ZoomScrollPane;

import java.util.Optional;

import static lions_and_men.applet_one_graph.visualization.Constants.*;

/**
 * Created by Jens on 25.06.2017.
 */
public class Lion implements Entity {
    private static ZoomScrollPane mainPane;
    private static Group shapeGroup = new Group();

    private Circle shape;
    private VisualizedCoreController coreController;
    private Point coordinates;

    public Lion(VisualizedCoreController coreController, Point startCcoordinates) {

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
            MenuItem waitStrategyButton = new MenuItem("Wait");
            MenuItem greedyStrategyButton = new MenuItem("Greedy");
            MenuItem cleverStrategyButton = new MenuItem("Clever");
            MenuItem randomStrategyButton = new MenuItem("RandomChoice");
            MenuItem manualStrategyButton = new MenuItem("Manual");
            strategyMenu.getItems().addAll(waitStrategyButton, greedyStrategyButton, cleverStrategyButton, randomStrategyButton, manualStrategyButton);


            Menu edgeMenu = new Menu("Lion Range");

            MenuItem iteme1 = new MenuItem("Increment");
            MenuItem iteme2 = new MenuItem("Decrement");
            MenuItem iteme3 = new MenuItem("Set");


            iteme1.setOnAction(event2 -> {
                lions_and_men.applet_one_graph.core.entities.Lion lion = coreController.getLionByCoordinate(coordinates);
                coreController.incrementLionRange(coordinates);
            });

            iteme2.setOnAction(event2 -> {
                lions_and_men.applet_one_graph.core.entities.Lion lion = coreController.getLionByCoordinate(coordinates);
                coreController.decrementLionRange(coordinates);
            });

            iteme3.setOnAction(event2 -> {
                lions_and_men.applet_one_graph.core.entities.Lion lion = coreController.getLionByCoordinate(coordinates);

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

            waitStrategyButton.setOnAction(event2 -> {
                coreController.setLionStrategy(coordinates, CoreController.LionStrategy.DoNothing);
            });

            greedyStrategyButton.setOnAction(event2 -> {
                coreController.setLionStrategy(coordinates, CoreController.LionStrategy.AggroGreedyLion);
            });

            cleverStrategyButton.setOnAction(event2 -> {
                coreController.setLionStrategy(coordinates, CoreController.LionStrategy.CleverLion);
            });

            randomStrategyButton.setOnAction(event2 -> {
                coreController.setLionStrategy(coordinates, CoreController.LionStrategy.RandomChoice);
            });

            manualStrategyButton.setOnAction(event2 -> {
                coreController.setLionStrategy(coordinates, CoreController.LionStrategy.Manual);
            });

            contextMenu.getItems().addAll(item0, item1, strategyMenu, edgeMenu, new SeparatorMenuItem(), closeItem);
            contextMenu.show(shape, event1.getScreenX(), event1.getScreenY());
        });
    }

    public static void setMainPane(ZoomScrollPane mainPane) {
        Lion.mainPane = mainPane;
    }

    public static void setShapeGroup(Group shapeGroup) {
        Lion.shapeGroup = shapeGroup;
    }

    @Override
    public void relocate(Point coordinates) {
        if (this.coordinates == coordinates) return;

        Path path = new Path();
        path.getElements().add(new MoveTo(this.coordinates.getX(), this.coordinates.getY()));
        path.getElements().add(new LineTo(coordinates.getX(), coordinates.getY()));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(ANIMATION_DURATION));
        pathTransition.setPath(path);
        pathTransition.setNode(shape);
        pathTransition.play();

        this.coordinates = coordinates;
    }

//    public void relocate(Point coordinates) {
//        this.coordinates = coordinates;
//        shape.relocate(coordinates.getX() - ENTITY_RADIUS, coordinates.getY() - ENTITY_RADIUS);
//    }


    public void delete() {
        shapeGroup.getChildren().remove(shape);
    }

    @Override
    public Point getPosition() {
        return coordinates;
    }
}
