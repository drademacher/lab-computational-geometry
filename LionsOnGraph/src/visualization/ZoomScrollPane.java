package visualization;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import shapes.*;
import util.Point;

import static shapes.ShapeConstants.BIG_VERTEX_RADIUS;
import static shapes.ShapeConstants.COLOR_BACKGROUND;


/***
 * Based on Source Code:
 * https://stackoverflow.com/questions/16680295/javafx-correct-scaling
 */
public class ZoomScrollPane extends ScrollPane {
    private final StackPane zoomScrollPane = new StackPane();
    private Group mainGroup = new Group();
    private Group groundGround = new Group();

    private double scaleFactor = 1;
    private Point scrollOffset = new Point(0, 0);

    public ZoomScrollPane() {
        final double SCALE_DELTA = 1.1;

        zoomScrollPane.getChildren().add(mainGroup);

        final Group scrollContent = new Group(zoomScrollPane);
        this.setContent(scrollContent);

        this.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
            zoomScrollPane.setMinSize(newValue.getWidth(), newValue.getHeight());
        });

        this.setPrefViewportWidth(256);
        this.setPrefViewportHeight(256);

        zoomScrollPane.setOnMouseClicked(event1 -> {
            Bounds subValues = mainGroup.localToScene(mainGroup.getBoundsInLocal());
            Bounds addValues = zoomScrollPane.localToScene(mainGroup.getBoundsInLocal());


//            System.out.println(event1.getX() + " - " + subValues.getMinX() + " - " + addValues.getMinX());

            double positionX = event1.getX() - subValues.getMinX();
            positionX -= scrollOffset.getX();
            positionX /= mainGroup.getScaleX();

            double positionY = event1.getY() - subValues.getMinY();
            positionY -= scrollOffset.getY();
            positionY /= mainGroup.getScaleY();

            System.out.println("new " + new Point((int) (positionX + addValues.getMinX()), (int) (positionY + addValues.getMinY())));
        });

        zoomScrollPane.setOnScroll(event -> {
            ZoomScrollPane scroller = ZoomScrollPane.this;

            event.consume();

            if (event.getDeltaY() == 0) {
                return;
            }

            scaleFactor = (event.getDeltaY() > 0) ? SCALE_DELTA
                    : 1 / SCALE_DELTA;

            // amount of scrolling in each direction in scrollContent coordinate
            // units
            scrollOffset = figureScrollOffset(scrollContent, scroller);

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

    private Point figureScrollOffset(Node scrollContent, ScrollPane scroller) {
        double extraWidth = scrollContent.getLayoutBounds().getWidth() - scroller.getViewportBounds().getWidth();
        double hScrollProportion = ((Double.isNaN(scroller.getHvalue()) ? 0 : scroller.getHvalue()) - scroller.getHmin()) / (scroller.getHmax() - scroller.getHmin());
        double scrollXOffset = hScrollProportion * Math.max(0, extraWidth);
        double extraHeight = scrollContent.getLayoutBounds().getHeight() - scroller.getViewportBounds().getHeight();
        double vScrollProportion = ((Double.isNaN(scroller.getVvalue()) ? 0 : scroller.getVvalue()) - scroller.getVmin()) / (scroller.getVmax() - scroller.getVmin());
        double scrollYOffset = vScrollProportion * Math.max(0, extraHeight);
        return new Point((int) scrollXOffset, (int) scrollYOffset);
    }

    private void repositionScroller(Node scrollContent, ScrollPane scroller, double scaleFactor, Point scrollOffset) {
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
        double scaleX = zoomScrollPane.getWidth() / mainGroup.getLayoutX();
        double scaleY = zoomScrollPane.getHeight() / mainGroup.getLayoutY();
        mainGroup.setScaleX(mainGroup.getScaleX() * Math.min(scaleX, scaleY));
        mainGroup.setScaleY(mainGroup.getScaleY() * Math.min(scaleX, scaleY));
    }

    public ObservableList<Node> getNodesHolder() {
        return mainGroup.getChildren();
    }

    public Node getGround() {
        return groundGround; // .getChildren().get(0);
    }

    public void clear() {
//        groundPane.setStyle("-fx-background-color: #0000FF;");
        Rectangle groundRectangle = new Rectangle(0, 0, 0, 0);
        groundRectangle.setFill(COLOR_BACKGROUND);
        groundGround.getChildren().add(groundRectangle);
        Group vertexShapes = new Group();
        Group edgeShapes = new Group();
        Group entityShapes = new Group();

        getNodesHolder().clear();
        getNodesHolder().addAll(groundGround, edgeShapes, vertexShapes, entityShapes);





//        zoomScrollPane.setOnMouseClicked(event1 -> System.out.println("zoom " + new Point((int) event1.getX(), (int) event1.getY())));

        vertexShapes.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            final double PADDING_FACTOR = 0.1, PADDING_CONST = 20;
            double w = newValue.getWidth(), h = newValue.getHeight();
            groundRectangle.setWidth(w * (1 + 2 * PADDING_FACTOR) + 2 * PADDING_CONST);
            groundRectangle.setHeight(h * (1 + 2 * PADDING_FACTOR) + 2 * PADDING_CONST);
            groundRectangle.relocate(-(w * PADDING_FACTOR + PADDING_CONST + BIG_VERTEX_RADIUS), -(h * PADDING_FACTOR + PADDING_CONST + BIG_VERTEX_RADIUS));
        });


        ShapedBigVertex.setMainGroup(mainGroup);
        ShapedBigVertex.setShapeGroup(vertexShapes);
        ShapedSmallVertex.setShapeGroup(vertexShapes);
        ShapedEdge.setShapeGroup(edgeShapes);
        ShapedMan.setShapeGroup(entityShapes);
        ShapedLion.setShapeGroup(entityShapes);

    }

}
