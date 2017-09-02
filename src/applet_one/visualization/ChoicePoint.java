package applet_one.visualization;


import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import applet_one.core.CoreController;
import applet_one.core.entities.Entity;
import util.Point;

public class ChoicePoint {
    private static Group shapeGroup = new Group();

    private Circle shape;
    private CoreController coreController;
    private Point choice;
    private Entity entity;

    public ChoicePoint(CoreController coreController, Entity entity, Point startCoordinates, Color color) {
        this.coreController = coreController;
        this.entity = entity;
        this.choice = startCoordinates;

        shape = new Circle(choice.getX(), choice.getY(), Constants.CHOICEPOINT_RADIUS, color);
        shapeGroup.getChildren().add(shape);

        shape.setOnMouseClicked(event -> {
            entity.setNextPosition(coreController.getVertexByCoordinate(choice));
            coreController.getVisualCoreController().updateStepPreviewsAndChoicePoints();
        });
    }


    public static void setShapeGroup(Group shapeGroup) {
        ChoicePoint.shapeGroup = shapeGroup;
    }

    public static void clear() {
        shapeGroup.getChildren().clear();
    }
}
