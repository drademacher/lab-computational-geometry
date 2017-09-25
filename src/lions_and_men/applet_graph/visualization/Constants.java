package lions_and_men.applet_graph.visualization;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Constants {

    public static final double BIG_VERTEX_RADIUS = 3;
    public static final double SMALL_VERTEX_RADIUS = 2;
    static final Color COLOR_BACKGROUND = Color.WHITE; // (Color) ((Region) lions_and_men.Main.getScene().getRoot()).getBackground().getFills().get(0).getFill();
    static final Color COLOR_VERTEX = (Color) Paint.valueOf("#353535");
    static final Color COLOR_SMALL_VERTEX = (Color) Paint.valueOf("#353535");
    static final Color COLOR_EDGE = (Color) Paint.valueOf("#A1A1A1");
    static final Color COLOR_MAN = (Color) Paint.valueOf("#0922e5");
    static final Color COLOR_LION = (Color) Paint.valueOf("#e51408");
    static final Color COLOR_MAN_RANGE = (Color) Paint.valueOf("#0922e5");
    static final Color COLOR_LION_RANGE = (Color) Paint.valueOf("#F28983");
    static final Color COLOR_PREVIEW = (Color) Paint.valueOf("#FFFF00");
    static final Color COLOR_CHOICEPOINT = (Color) Paint.valueOf("#7a7a7a99");
    static final double ENTITY_RADIUS = 1.5;
    static final double CHOICEPOINT_RADIUS = 1.5;
    static final double RANGE_RADIUS = 1.0;
    public static int ANIMATION_DURATION = 200;

    public static String getHexString(Color color) {
        String hex = String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
        return hex;
    }
}
