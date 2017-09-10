package lions_and_men.applet_one_graph.visualization;


import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lions_and_men.applet_one_graph.core.entities.Entity;
import lions_and_men.util.Point;

public class ChoicePoint {
    private static Group shapeGroup = new Group();

    private Circle shape;
    private VisualCoreController coreController;
    private Point choice;
    private Entity entity;

    public ChoicePoint(VisualCoreController coreController, Entity entity, Point startCoordinates, Color color) {
        this.coreController = coreController;
        this.entity = entity;
        this.choice = startCoordinates;

        shape = new Circle(choice.getX(), choice.getY(), Constants.CHOICEPOINT_RADIUS, color);
        shapeGroup.getChildren().add(shape);

        shape.setOnMouseClicked(event -> {
            entity.setNextPosition(coreController.getVertexByCoordinate(choice));
            coreController.updateStepPreviewsAndChoicePoints();
        });
    }


    public static void setShapeGroup(Group shapeGroup) {
        ChoicePoint.shapeGroup = shapeGroup;
    }

    public static void clear() {
        shapeGroup.getChildren().clear();
    }


}
