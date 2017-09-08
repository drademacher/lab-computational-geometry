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

import static lions_and_men.applet_one_graph.visualization.Constants.ANIMATION_DURATION;

/**
 * Created by Jens on 25.06.2017.
 */
public class Man implements Entity {
    private static ZoomScrollPane mainPane;
    private static Group shapeGroup = new Group();

    private Circle shape;
    private VisualCoreController coreController;
    private Point coordinates;

    public Man(VisualCoreController coreController, Point startCoordinates) {

        this.coreController = coreController;
        this.coordinates = startCoordinates;

        shape = new Circle(coordinates.getX(), coordinates.getY(), Constants.ENTITY_RADIUS, Constants.COLOR_MAN);
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
            MenuItem waitStrategyButton = new MenuItem("Wait");
            MenuItem greedyStrategyButton = new MenuItem("Greedy");
            MenuItem randomStrategyButton = new MenuItem("Random");
            MenuItem manualStrategyButton = new MenuItem("Manual");
            MenuItem paperStrategyButton = new MenuItem("Paper");
            strategyMenu.getItems().addAll(waitStrategyButton, greedyStrategyButton, randomStrategyButton, manualStrategyButton, paperStrategyButton);

            Menu edgeMenu = new Menu("Man Range");

            MenuItem iteme1 = new MenuItem("Increment");
            MenuItem iteme2 = new MenuItem("Decrement");
            MenuItem iteme3 = new MenuItem("Set");


            iteme1.setOnAction(event2 -> {
                lions_and_men.applet_one_graph.core.entities.Man man = coreController.getManByCoordinate(coordinates);
                coreController.incrementManRange(coordinates);
            });

            iteme2.setOnAction(event2 -> {
                lions_and_men.applet_one_graph.core.entities.Man man = coreController.getManByCoordinate(coordinates);
                coreController.decrementManRange(coordinates);
            });

            iteme3.setOnAction(event2 -> {
                lions_and_men.applet_one_graph.core.entities.Man man = coreController.getManByCoordinate(coordinates);

                TextInputDialog dialog = new TextInputDialog("" + man.getRange());
                dialog.setTitle("Set Man Range");
                dialog.setHeaderText("Enter the new range of the man.");

                Optional<String> result = dialog.showAndWait();

                if (result.isPresent()) {
                    int newWeight = Integer.parseInt(result.get());
                    coreController.setManRange(coordinates, newWeight);
                }
            });

            edgeMenu.getItems().addAll(iteme1, iteme2, iteme3);

            waitStrategyButton.setOnAction(event2 -> {
                coreController.setManStrategy(coordinates, CoreController.ManStrategy.DoNothing);
            });

            greedyStrategyButton.setOnAction(event2 -> {
                coreController.setManStrategy(coordinates, CoreController.ManStrategy.RunAwayGreedy);
            });

            randomStrategyButton.setOnAction(event2 -> {
                coreController.setManStrategy(coordinates, CoreController.ManStrategy.Random);
            });

            manualStrategyButton.setOnAction(event2 -> {
                coreController.setManStrategy(coordinates, CoreController.ManStrategy.Manually);
            });

            paperStrategyButton.setOnAction(event2 -> {
                coreController.setManStrategy(coordinates, CoreController.ManStrategy.Paper);
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

            contextMenu.getItems().addAll(item0, item1, strategyMenu, edgeMenu, new SeparatorMenuItem(), closeItem);
            contextMenu.show(shape, event1.getScreenX(), event1.getScreenY());
        });
    }

    public static void setMainPane(ZoomScrollPane mainPane) {
        Man.mainPane = mainPane;
    }

    public static void setShapeGroup(Group shapeGroup) {
        Man.shapeGroup = shapeGroup;
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


//    @Override
//    public void relocate(Point coordinates) {
//        this.coordinates = coordinates;
//        shape.relocate(coordinates.getX() - Global.ENTITY_RADIUS, coordinates.getY() - Global.ENTITY_RADIUS);
//    }


    public void delete() {
        shapeGroup.getChildren().remove(shape);
    }

    @Override
    public Point getPosition() {
        return coordinates;
    }
}
