package lions_on_graph.visualization;

import javafx.scene.Group;
import javafx.scene.shape.Circle;
import lions_on_graph.core.CoreController;
import util.Point;

import static lions_on_graph.visualization.Constants.COLOR_MAN_RANGE;
import static lions_on_graph.visualization.Constants.COLOR_LION_RANGE;
import static lions_on_graph.visualization.Constants.RANGE_RADIUS;

/**
 * Created by Jens on 29.06.2017.
 */
public class Range implements Vertex {
    private static Group shapeGroup = new Group();

    private Circle shape;
    private CoreController coreController;
    private Point coordinates;

    public Range(CoreController coreController, Point startCoordinates) {
        this(coreController, startCoordinates, false);
    }
    public Range(CoreController coreController, Point startCoordinates, boolean manRange) {

        this.coreController = coreController;
        this.coordinates = startCoordinates;

        if(manRange){
            shape = new Circle(coordinates.getX(), coordinates.getY(), RANGE_RADIUS, COLOR_MAN_RANGE);
        }else{
            shape = new Circle(coordinates.getX(), coordinates.getY(), RANGE_RADIUS, COLOR_LION_RANGE);
        }
        shapeGroup.getChildren().add(shape);

    }

    public static void setShapeGroup(Group shapeGroup) {
        Range.shapeGroup = shapeGroup;
    }

    public void relocate(Point coordinates) {
        this.coordinates = coordinates;
        shape.relocate(coordinates.getX() - RANGE_RADIUS, coordinates.getY() - RANGE_RADIUS);
    }

    public void delete() {
        shapeGroup.getChildren().remove(shape);
    }
}
