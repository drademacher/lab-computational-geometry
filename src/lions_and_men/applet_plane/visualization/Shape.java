package lions_and_men.applet_plane.visualization;


import lions_and_men.applet_plane.algorithm.CoreController;
import lions_and_men.util.ZoomScrollPane;

public abstract class Shape {
    protected static CoreController coreController;
    protected static ZoomScrollPane pane;

    public static void setCoreController(CoreController coreController) {
        Shape.coreController = coreController;
    }

    public static void setPane(ZoomScrollPane pane) {
        Shape.pane = pane;
    }
}
