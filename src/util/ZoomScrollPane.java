package util;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;


/***
 * Ideas from
 * https://stackoverflow.com/questions/16680295/javafx-correct-scaling
 * With custom extensions.
 */
public class ZoomScrollPane extends ScrollPane {
    private final StackPane mainPane = new StackPane();
    private Group mainGroup = new Group();

//    private Point2D scrollOffset = new Point2D(0, 0);


    public ZoomScrollPane() {
        final double SCALE_DELTA = 1.1;

        ZoomScrollPane.this.setBackground(new Background(new BackgroundFill(null, null, null)));

        mainPane.getChildren().add(mainGroup);

        final Group scrollContent = new Group(mainPane);
        this.setContent(scrollContent);

        this.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
            mainPane.setMinSize(newValue.getWidth(), newValue.getHeight());
        });

        this.setPrefViewportWidth(256);
        this.setPrefViewportHeight(256);

        mainPane.setOnScroll(event -> {
            ZoomScrollPane scroller = ZoomScrollPane.this;

            event.consume();

            if (event.getDeltaY() == 0) {
                return;
            }

            double scaleFactor = (event.getDeltaY() > 0) ? SCALE_DELTA
                    : 1 / SCALE_DELTA;

            // amount of scrolling in each direction in scrollContent coordinate
            // units
            Point2D scrollOffset = figureScrollOffset(scrollContent, scroller);

            mainGroup.setScaleX(mainGroup.getScaleX() * scaleFactor);
            mainGroup.setScaleY(mainGroup.getScaleY() * scaleFactor);


            // move viewport so that old center remains in the center after the
            // scaling
            repositionScroller(scrollContent, scroller, scaleFactor, scrollOffset);

        });

        // Panning via drag....
        final ObjectProperty<Point2D> lastMouseCoordinates = new SimpleObjectProperty<>();
        scrollContent.setOnMousePressed(event -> lastMouseCoordinates.set(new Point2D(event.getX(), event.getY())));

        scrollContent.setOnMouseDragged(event -> {
            ZoomScrollPane scroller = ZoomScrollPane.this;

            double deltaX = event.getX() - lastMouseCoordinates.get().getX();
            double extraWidth = scrollContent.getLayoutBounds().getWidth() - scroller.getViewportBounds().getWidth();
            double deltaH = deltaX * (scroller.getHmax() - scroller.getHmin()) / extraWidth;
            double desiredH = (Double.isNaN(scroller.getHvalue()) ? 0 : scroller.getHvalue()) - deltaH;
            scroller.setHvalue(Math.max(0, Math.min(scroller.getHmax(), desiredH)));


            double deltaY = event.getY() - lastMouseCoordinates.get().getY();
            double extraHeight = scrollContent.getLayoutBounds().getHeight() - scroller.getViewportBounds().getHeight();
            double deltaV = deltaY * (scroller.getVmax() - scroller.getVmin()) / extraHeight;
            double desiredV = (Double.isNaN(scroller.getVvalue()) ? 0 : scroller.getVvalue()) - deltaV;
            scroller.setVvalue(Math.max(0, Math.min(scroller.getVmax(), desiredV)));
        });

    }

    private Point2D figureScrollOffset(Node scrollContent, ScrollPane scroller) {
        double extraWidth = scrollContent.getLayoutBounds().getWidth() - scroller.getViewportBounds().getWidth();
        double hScrollProportion = ((Double.isNaN(scroller.getHvalue()) ? 0 : scroller.getHvalue()) - scroller.getHmin()) / (scroller.getHmax() - scroller.getHmin());
        double scrollXOffset = hScrollProportion * Math.max(0, extraWidth);
        double extraHeight = scrollContent.getLayoutBounds().getHeight() - scroller.getViewportBounds().getHeight();
        double vScrollProportion = ((Double.isNaN(scroller.getVvalue()) ? 0 : scroller.getVvalue()) - scroller.getVmin()) / (scroller.getVmax() - scroller.getVmin());
        double scrollYOffset = vScrollProportion * Math.max(0, extraHeight);
        return new Point2D(scrollXOffset, scrollYOffset);
    }

    private void repositionScroller(Node scrollContent, ScrollPane scroller, double scaleFactor, Point2D scrollOffset) {
        double scrollXOffset = scrollOffset.getX();
        double scrollYOffset = scrollOffset.getY();
        double extraWidth = scrollContent.getLayoutBounds().getWidth() - scroller.getViewportBounds().getWidth();
        if (extraWidth > 0) {
            double halfWidth = scroller.getViewportBounds().getWidth() / 2;
            double newScrollXOffset = (scaleFactor - 1) * halfWidth + scaleFactor * scrollXOffset;
            scroller.setHvalue(scroller.getHmin() + newScrollXOffset * (scroller.getHmax() - scroller.getHmin()) / extraWidth);
        } else {
            scroller.setHvalue(scroller.getHmin());
        }
        double extraHeight = scrollContent.getLayoutBounds().getHeight() - scroller.getViewportBounds().getHeight();
        if (extraHeight > 0) {
            double halfHeight = scroller.getViewportBounds().getHeight() / 2;
            double newScrollYOffset = (scaleFactor - 1) * halfHeight + scaleFactor * scrollYOffset;
            scroller.setVvalue(scroller.getVmin() + newScrollYOffset * (scroller.getVmax() - scroller.getVmin()) / extraHeight);
        } else {
            scroller.setHvalue(scroller.getHmin());
        }
    }

    public void autoZoom() {
        double scaleX = mainPane.getWidth() / (mainGroup.getLayoutBounds().getWidth() * mainGroup.getScaleX());
        double scaleY = mainPane.getHeight() / (mainGroup.getLayoutBounds().getHeight() * mainGroup.getScaleY());
        mainGroup.setScaleX(mainGroup.getScaleX() * Math.min(scaleX, scaleY) * .9);
        mainGroup.setScaleY(mainGroup.getScaleY() * Math.min(scaleX, scaleY) * .9);
    }

    public ObservableList<Node> getNodesHolder() {
        return mainGroup.getChildren();
    }


    public Point getLocalCoordinates(double x, double y) {
        Point2D res = mainGroup.sceneToLocal(new Point2D(x, y));
        return new Point(res.getX(), res.getY());
    }


}
