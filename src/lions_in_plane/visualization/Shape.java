package lions_in_plane.visualization;


import javafx.scene.Group;
import util.ZoomScrollPane;

public abstract class Shape {
    protected static VisualizedCoreController coreController;
    protected static ZoomScrollPane pane;
    private static Group group = new Group();

    public static void setCoreController(VisualizedCoreController coreController) {
        Shape.coreController = coreController;
    }

    public static void setPane(ZoomScrollPane pane) {
        Shape.pane = pane;
    }

    public static void setGroup(Group group) {
        Shape.group = group;
    }
}
