package lions_and_men.applet_plane.visualization;

import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.shape.Circle;
import lions_and_men.util.ContextMenuHolder;
import lions_and_men.util.Point;

import static lions_and_men.applet_plane.visualization.Constants.COLOR_MAN;
import static lions_and_men.applet_plane.visualization.Constants.ENTITY_RADIUS;

/**
 * Shape of a man point.
 */
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
            MenuItem paperStrategyButton = new MenuItem("Paper");
            strategyMenu.getItems().addAll(paperStrategyButton);


            removeButton.setOnAction(event2 -> coreController.removeMan(this.position));

            relocateButton.setOnAction(event2 -> pane.setOnMouseClicked(event3 -> {
                pane.setOnMouseClicked(null);
                coreController.createMan(pane.getLocalCoordinates(event3.getX(), event3.getY()));

            }));

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
        group.getChildren().remove(shape);
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
