package visualization;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Created by Danny on 13.05.2017.
 */
public class VisConstants {
    // Colors
    public static final Color COLOR_BACKGROUND = (Color) Paint.valueOf("#FFFFFF");
    public static final Color COLOR_NODE = (Color) Paint.valueOf("#5b5b5b");
    public static final Color COLOR_EDGE = (Color) Paint.valueOf("#A1A1A1");
    public static final Color COLOR_EDGE_STEPS = (Color) Paint.valueOf("#5b5b5b");
    //Zoom
    static final int ZOOM_FACTOR = 2;
    static final int ZOOM_MAX = 60;
    static final int ZOOM_MIN = 5;
    //Todo: Window dimension okay?
    static final int WINDOW_WIDTH = 1200;
    static final int WINDOW_HEIGHT = 800;
    static final Color COLOR_MAN = (Color) Paint.valueOf("#5ae863");
    static final Color COLOR_LION = (Color) Paint.valueOf("#e8745a");
}
