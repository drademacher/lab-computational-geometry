package lions_on_graph.visualization;


import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lions_on_graph.core.CoreController;
import lions_on_graph.core.entities.Entity;
import util.Point;

public class ShapedChoicePoint {
    private static Group shapeGroup = new Group();

    private Circle shape;
    private CoreController coreController;
    private Point choice;
    private Entity entity;

    public ShapedChoicePoint(CoreController coreController, Entity entity, Point startCoordinates, Color color) {
        this.coreController = coreController;
        this.entity = entity;
        this.choice = startCoordinates;

        shape = new Circle(choice.getX(), choice.getY(), ShapeConstants.CHOICEPOINT_RADIUS, color);
        shapeGroup.getChildren().add(shape);

        shape.setOnMouseClicked(event -> {
            entity.setNextPosition(coreController.getVertexByCoordinate(choice));
            coreController.getShapeController().updateStepPreviewsAndChoicePoints();
        });
    }


    public static void setShapeGroup(Group shapeGroup) {
        ShapedChoicePoint.shapeGroup = shapeGroup;
    }

    public static void clear() {
        shapeGroup.getChildren().clear();
    }
}
