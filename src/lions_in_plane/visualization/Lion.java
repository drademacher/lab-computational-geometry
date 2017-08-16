package lions_in_plane.visualization;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.shape.Circle;
import util.ContextMenuHolder;
import util.Point;

import static lions_in_plane.visualization.Constants.COLOR_LION;
import static lions_in_plane.visualization.Constants.ENTITY_RADIUS;

public class Lion extends Shape {
    private static Group group = new Group();

    private DoubleProperty xPos;
    private DoubleProperty yPos;
    private Circle shape;

    Lion(Point position) {
        this.xPos = new SimpleDoubleProperty(position.getX());
        this.yPos = new SimpleDoubleProperty(position.getY());
        this.shape = new Circle(position.getX(), position.getY(), ENTITY_RADIUS, COLOR_LION);

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


            removeButton.setOnAction(event2 -> {
                coreController.removeLion(new Point(xPos.getValue(), yPos.getValue()));
            });

            relocateButton.setOnAction(event2 -> {
                pane.setOnMouseClicked(event3 -> {
                    pane.setOnMouseClicked(null);
                    coreController.relocateLion(new Point(xPos.getValue(), yPos.getValue()), pane.getLocalCoordinates(event3.getX(), event3.getY()));

                });
            });

            contextMenu.getItems().addAll(removeButton, relocateButton, strategyMenu, new SeparatorMenuItem(), closeButton);
            contextMenu.show(shape, event1.getScreenX(), event1.getScreenY());
        });
    }

    public DoubleProperty xPosProperty() {
        return xPos;
    }

    public DoubleProperty yPosProperty() {
        return yPos;
    }

    public static void setGroup(Group group) {
        Lion.group = group;
    }

    public Point getPosition() {
        return new Point(xPos.getValue(), yPos.getValue());
    }

    void setPosition(Point position) {
        this.xPos.set(position.getX());
        this.yPos.set(position.getY());

        shape.relocate(position.getX() - ENTITY_RADIUS, position.getY() - ENTITY_RADIUS);
    }

    void clear() {
        group.getChildren().remove(shape);
    }

    public Circle getShape() {
        // TODO: REMOVE GETTER
        return shape;
    }
}
