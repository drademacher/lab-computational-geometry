package applet_two.visualization;

import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.shape.Circle;
import util.ContextMenuHolder;
import util.Point;

import static applet_two.visualization.Constants.COLOR_MAN;
import static applet_two.visualization.Constants.ENTITY_RADIUS;

public class Man extends Shape {
    private static Group group = new Group();

    private Point position;
    private Circle shape;

    Man(Point position) {
        this.position = position;
        this.shape = new Circle(position.getX(), position.getY(), ENTITY_RADIUS, COLOR_MAN);

        group.getChildren().add(shape);
        shape.setOnContextMenuRequested(event1 -> {
            event1.consume();

            if (!coreController.isEditMode())
                return;

            final ContextMenu contextMenu = ContextMenuHolder.getFreshContextMenu();
            MenuItem removeButton = new MenuItem("Remove");
            MenuItem relocateButton = new MenuItem("Relocate");
            MenuItem closeButton = new MenuItem("Close");

            Menu strategyMenu = new Menu("Set Strategy");
            MenuItem waitStrategyButton = new MenuItem("Wait");
            MenuItem greedyStrategyButton = new MenuItem("Greedy");
            MenuItem randomStrategyButton = new MenuItem("Random");
            MenuItem manualStrategyButton = new MenuItem("Manual");
            MenuItem paperStrategyButton = new MenuItem("Paper");
            strategyMenu.getItems().addAll(waitStrategyButton, greedyStrategyButton, randomStrategyButton, manualStrategyButton, paperStrategyButton);


            removeButton.setOnAction(event2 -> coreController.removeMan(this.position));

            relocateButton.setOnAction(event2 -> {
                pane.setOnMouseClicked(event3 -> {
                    pane.setOnMouseClicked(null);
                    coreController.relocateMan(pane.getLocalCoordinates(event3.getX(), event3.getY()));

                });
            });

            contextMenu.getItems().addAll(removeButton, relocateButton, strategyMenu, new SeparatorMenuItem(), closeButton);
            contextMenu.show(shape, event1.getScreenX(), event1.getScreenY());
        });
    }

    public static void setGroup(Group group) {
        Man.group = group;
    }

    Point getPosition() {
        return position;
    }

    void setPosition(Point position) {
        this.position = position;
        shape.relocate(position.getX() - ENTITY_RADIUS, position.getY() - ENTITY_RADIUS);
    }

    void clear() {
        group.getChildren().clear();
    }

    public Circle getShape() {
        return shape;
    }

    @Override
    public String toString() {
        return "Man{" +
                "position=" + position +
                ", shape=" + shape +
                '}';
    }
}
