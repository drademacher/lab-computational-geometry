package lions_and_men.applet_graph.visualization;


import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lions_and_men.util.Point;

/**
 * Possible choices for the manuel modus (to choose where to go next)
 */
public class ChoicePoint {
    private static Group shapeGroup = new Group();

    private Point choice;

    ChoicePoint(VisualizedCoreController coreController, Point entity, Point startCoordinates, Color color) {
        this.choice = startCoordinates;

        Circle shape = new Circle(choice.getX(), choice.getY(), Constants.CHOICEPOINT_RADIUS, color);
        shapeGroup.getChildren().add(shape);

        shape.setOnMouseClicked(event -> coreController.setNextEntityStep(entity, choice));
    }


    public static void setShapeGroup(Group shapeGroup) {
        ChoicePoint.shapeGroup = shapeGroup;
    }

    public static void clear() {
        shapeGroup.getChildren().clear();
    }


}
