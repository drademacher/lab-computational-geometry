//package lions_in_plane.visualization;
//
//import javafx.scene.Group;
//import javafx.scene.shape.Polyline;
//import util.Point;
//
//import java.util.ArrayList;
//
//public class PreviousManPath extends Shape {
//    private static Group group = new Group();
//
//    private ArrayList<Point> points;
//    private Polyline shape;
//
//    private PreviousManPath(ArrayList<Point> points) {
//        this.points = points;
//        this.shape = new Polyline();
//
//
//        for (Point p : points) {
//            shape.getPoints().addAll(p.getX(), p.getY());
//        }
//
//        group.getChildren().add(shape);
//    }
//
//    public static void setGroup(Group group) {
//        PreviousManPath.group = group;
//    }
//
//    public static void clear() {
//        group.getChildren().clear();
//    }
//
//}
