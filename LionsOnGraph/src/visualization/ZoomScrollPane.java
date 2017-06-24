package visualization;

import core.graph.graphshapes.BigVertexShape;
import core.graph.graphshapes.EdgeShape;
import core.graph.graphshapes.SmallVertexShape;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;

/***
 * Based on Source Code:
 * https://stackoverflow.com/questions/16680295/javafx-correct-scaling
 */
public class ZoomScrollPane extends ScrollPane {
    private Group vertexShapes = new Group();
    private Group edgeShapes = new Group();
    private Group entityShapes = new Group();

    public ZoomScrollPane() {
        final double SCALE_DELTA = 1.1;
        final StackPane zoomPane = new StackPane();

        zoomPane.getChildren().addAll(edgeShapes, vertexShapes, entityShapes);
        BigVertexShape.setShapeGroup(vertexShapes);
        SmallVertexShape.setShapeGroup(vertexShapes);
        EdgeShape.setShapeGroup(edgeShapes);

        final Group scrollContent = new Group(zoomPane);
        this.setContent(scrollContent);

        this.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
            zoomPane.setMinSize(newValue.getWidth(), newValue.getHeight());
        });

        this.setPrefViewportWidth(256);
        this.setPrefViewportHeight(256);

        zoomPane.setOnScroll(event -> {
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

            vertexShapes.setScaleX(vertexShapes.getScaleX() * scaleFactor);
            vertexShapes.setScaleY(vertexShapes.getScaleY() * scaleFactor);
            edgeShapes.setScaleX(edgeShapes.getScaleX() * scaleFactor);
            edgeShapes.setScaleY(edgeShapes.getScaleY() * scaleFactor);
            entityShapes.setScaleX(entityShapes.getScaleX() * scaleFactor);
            entityShapes.setScaleY(entityShapes.getScaleY() * scaleFactor);

            // move viewport so that old center remains in the center after the
            // scaling
            repositionScroller(scrollContent, scroller, scaleFactor, scrollOffset);

        });

        // Panning via drag....
        final ObjectProperty<Point2D> lastMouseCoordinates = new SimpleObjectProperty<>();
        scrollContent.setOnMousePressed(event -> lastMouseCoordinates.set(new Point2D(event.getX(), event.getY())));

        scrollContent.setOnMouseDragged(event -> {
            ZoomScrollPane zoomScrollPane = ZoomScrollPane.this;

            double deltaX = event.getX() - lastMouseCoordinates.get().getX();
            double extraWidth = scrollContent.getLayoutBounds().getWidth() - zoomScrollPane.getViewportBounds().getWidth();
            double deltaH = deltaX * (zoomScrollPane.getHmax() - zoomScrollPane.getHmin()) / extraWidth;
            double desiredH = (Double.isNaN(zoomScrollPane.getHvalue()) ? 0 : zoomScrollPane.getHvalue()) - deltaH;
            zoomScrollPane.setHvalue(Math.max(0, Math.min(zoomScrollPane.getHmax(), desiredH)));

            double deltaY = event.getY() - lastMouseCoordinates.get().getY();
            double extraHeight = scrollContent.getLayoutBounds().getHeight() - zoomScrollPane.getViewportBounds().getHeight();
            double deltaV = deltaY * (zoomScrollPane.getVmax() - zoomScrollPane.getVmin()) / extraHeight;
            double desiredV = (Double.isNaN(zoomScrollPane.getVvalue()) ? 0 : zoomScrollPane.getVvalue()) - deltaV;
            zoomScrollPane.setVvalue(Math.max(0, Math.min(zoomScrollPane.getVmax(), desiredV)));
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

    public void clear() {
        vertexShapes.getChildren().clear();
        edgeShapes.getChildren().clear();
        entityShapes.getChildren().clear();
    }

}
