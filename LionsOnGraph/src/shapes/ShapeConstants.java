package shapes;

import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import visualization.Main;

public class ShapeConstants {
//    public static final Color COLOR_BACKGROUND = (Color) Paint.valueOf("#dbdbdb");

    public static final Color COLOR_BACKGROUND = (Color ) ((Region) Main.getScene().getRoot() ).getBackground().getFills().get(0).getFill() ;
    public static final Color COLOR_VERTEX = (Color) Paint.valueOf("#353535");
    public static final Color COLOR_SMALL_VERTEX = (Color) Paint.valueOf("#353535");
    public static final Color COLOR_EDGE = (Color) Paint.valueOf("#A1A1A1");
    public static final Color COLOR_MAN = (Color) Paint.valueOf("#0922e5");
    public static final Color COLOR_LION = (Color) Paint.valueOf("#e51408");
    public static final Color COLOR_RANGE = (Color) Paint.valueOf("#F28983");


    public static final double BIG_VERTEX_RADIUS = 3;
    public static final double SMALL_VERTEX_RADIUS = 2;
    public static final double ENTITY_RADIUS = 1.5;
    public static final double RANGE_RADIUS = 1.0;

    public static String getHexString(Color color) {
        String hex = String.format( "#%02X%02X%02X",
                (int)( color.getRed() * 255 ),
                (int)( color.getGreen() * 255 ),
                (int)( color.getBlue() * 255 ) );
        return hex;
    }
}
