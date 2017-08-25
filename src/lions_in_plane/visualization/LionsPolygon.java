package lions_in_plane.visualization;

import javafx.scene.Group;
import javafx.scene.paint.Color;


public class LionsPolygon extends Shape {
    private static Group group = new Group();

    private Lion[] lions;
    private javafx.scene.shape.Polygon polygon;

    LionsPolygon(Lion[] points) {
        this.lions = points;
        this.polygon = new javafx.scene.shape.Polygon();
        polygon.setStroke(Color.GREEN);
        polygon.setFill(Color.TRANSPARENT);

        for (int i = 0; i < lions.length; i++) {
            final int j = i;
            polygon.getPoints().addAll(lions[i].xPosProperty().get(), lions[i].yPosProperty().get());
            lions[i].xPosProperty().addListener((observable, oldValue, newValue) -> polygon.getPoints().set(2 * j, newValue.doubleValue()));
            lions[i].yPosProperty().addListener((observable, oldValue, newValue) -> polygon.getPoints().set(2 * j + 1, newValue.doubleValue()));

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
