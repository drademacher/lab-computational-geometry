package core.exeception;

import javafx.geometry.Point2D;

/**
 * Created by Danny on 17.05.2017.
 */
public class InvalidCoordinateException extends Exception {
    public InvalidCoordinateException(Point2D coordinate) {
        super("Field is not part of the map: (" + coordinate.getX() + "," + coordinate.getY() + ")");
    }
}
