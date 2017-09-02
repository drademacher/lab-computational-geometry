package applet_two.visualization;


import applet_two.core.CoreController;
import util.ZoomScrollPane;

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
