package lions_on_graph.exeception;

import javafx.geometry.Point2D;

public class InvalidCoordinateException extends Exception {
    public InvalidCoordinateException(Point2D coordinate) {
        super("Field is not part of the map: (" + coordinate.getX() + "," + coordinate.getY() + ")");
    }
}
