package lions_on_graph.visualization;

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
import lions_on_graph.core.CoreController;
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
            MenuItem waitStrategyButton = new MenuItem("Wait");
            MenuItem greedyStrategyButton = new MenuItem("Greedy");
            MenuItem randomStrategyButton = new MenuItem("Random");
            MenuItem manualStrategyButton = new MenuItem("Manual");
            MenuItem paperStrategyButton = new MenuItem("Paper");
            strategyMenu.getItems().addAll(waitStrategyButton, greedyStrategyButton, randomStrategyButton, manualStrategyButton, paperStrategyButton);


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
                coreController.getShapeController().updateStepPreviewsAndChoicePoints();
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
        if (this.coordinates == coordinates) return;

        Path path = new Path();
        path.getElements().add(new MoveTo(this.coordinates.getX(), this.coordinates.getY()));
        path.getElements().add(new LineTo(coordinates.getX(), coordinates.getY()));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(250));
        pathTransition.setPath(path);
        pathTransition.setNode(shape);
        pathTransition.play();

        this.coordinates = coordinates;
    }


//    @Override
//    public void relocate(Point coordinates) {
//        this.coordinates = coordinates;
//        shape.relocate(coordinates.getX() - ShapeConstants.ENTITY_RADIUS, coordinates.getY() - ShapeConstants.ENTITY_RADIUS);
//    }


    public void delete() {
        shapeGroup.getChildren().remove(shape);
    }
}
