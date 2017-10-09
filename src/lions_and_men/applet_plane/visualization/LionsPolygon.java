package lions_and_men.applet_plane.visualization;

import javafx.scene.Group;
import javafx.scene.paint.Color;

/**
 * polygon with all lions inside
 */
public class LionsPolygon extends Shape {
    private static Group group = new Group();

    private javafx.scene.shape.Polygon polygon;

    LionsPolygon(Lion[] points) {
        this.polygon = new javafx.scene.shape.Polygon();
        polygon.setStroke(Color.GREEN);
        polygon.setFill(Color.TRANSPARENT);

        for (int i = 0; i < points.length; i++) {
            final int j = i;
            this.polygon.getPoints().addAll(points[i].xPosProperty().get(), points[i].yPosProperty().get());
            points[i].xPosProperty().addListener((observable, oldValue, newValue) -> polygon.getPoints().set(2 * j, newValue.doubleValue()));
            points[i].yPosProperty().addListener((observable, oldValue, newValue) -> polygon.getPoints().set(2 * j + 1, newValue.doubleValue()));

        }


        group.getChildren().add(polygon);

    }

    // public getPoints()

    public static void setGroup(Group group) {
        LionsPolygon.group = group;
    }


    static void clear() {
        group.getChildren().clear();
    }
}
