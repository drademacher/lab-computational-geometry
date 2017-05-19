package visualization;


import core.exeception.InvalidCoordinateException;
import core.graph.Edge;
import core.graph.Graph;
import core.graph.Vertex;
import core.util.Point;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import static visualization.VisConstants.*;


public class GraphHolder {
    private Point cameraDim;
    private Point cameraPos = new Point(0, 0);

    private Graph graph;

    private StackPane pane;

    private Canvas canvas;
    private OnMouseClickedCallback onMouseClickedCallback;
    private int fieldSize = 10;
    private int padding = (fieldSize > 5) ? fieldSize / 10 : 0;

    GraphHolder(StackPane pane) { // Canvas canvas, Canvas edgeLengthCanvas, Canvas edgeStepsActiveCanvas, Canvas edgeStepsAllCanvas, Canvas shortestDistanceCanvas, Canvas shortestPathCanvas
        this.pane = pane;


        CanvasPane canvasPane = new CanvasPane();
        this.pane.getChildren().add(canvasPane);
        this.canvas = canvasPane.getCanvas();

        this.canvas.setOnScroll(event -> {
            if (event.getDeltaY() == 0) return;
            if (event.getDeltaY() > 0 && fieldSize + ZOOM_FACTOR <= ZOOM_MAX) this.fieldSize += ZOOM_FACTOR;
            if (event.getDeltaY() < 0 && fieldSize - ZOOM_FACTOR >= ZOOM_MIN) this.fieldSize -= ZOOM_FACTOR;
            padding = (fieldSize > 5) ? fieldSize / 10 : 0;
            refreshMap();
        });


        this.canvas.setOnMouseClicked(event -> {
            if (onMouseClickedCallback == null) return;
            int x = new Double((event.getX() - 1) / this.fieldSize).intValue();
            int y = new Double((event.getY() - 2) / this.fieldSize).intValue();
            try {
                this.onMouseClickedCallback.call(new Point(x, y).add(cameraPos));
            } catch (InvalidCoordinateException e) {
                e.printStackTrace();
            }
        });


//        ( (StackPane) canvas.getParent()).heightProperty().addListener((observable, oldValue, newValue) -> {
//            if (graph == null) return;
//            System.out.println(newValue.intValue());
//            cameraDim.setY(Math.min(((newValue.intValue() - 10) / fieldSize), graph.getYRange()) );
//            updateCamera();
//            renderMap();
//        });

    }




    /* ------- Getter & Setter ------- */

    void setNode(Point coordinate) {
        if (graph.registerVertex(coordinate)) {
            this.renderNode(canvas, coordinate);
        }
    }

    void removeNode(Point coordinate) {
        if (graph.deleteVertex(coordinate)) {
            this.refreshMap();
        }
    }

    void setEdge(Point from, Point to) {
        if (graph.registerEdge(from, to)) {
            this.renderEdge(canvas, from, to);
        }
    }

    void removeEdge(Point from, Point to) {
        if (graph.deleteEdge(from, to)) {
            this.refreshMap();
        }
    }

    void setGraph(Graph graph) {

        this.graph = graph;
        cameraDim = new Point(graph.getXRange(), graph.getYRange());
        refreshMap();
    }


    void setOnMouseClickedCallback(OnMouseClickedCallback callback) {
        this.onMouseClickedCallback = callback;
    }



    /* ------- Functions for rendering canvas ------- */

    void refreshMap() {
        // TODO: reset the lions / men
        adjustCamera();
        renderMap();
    }

    private void renderMap() {
        if (graph == null) {
            return;
        }

        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        gc.setFill(COLOR_BACKGROUND);
        gc.fillRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());


        for(Edge edge : graph.getEdges()){
            renderEdge(canvas, edge.getCoordStart(), edge.getCoordEnd());
        }

        for(Vertex vertex : graph.getVertices()){
            renderNode(canvas, vertex.getCoord());
        }

    }

    private void renderNode(Canvas canvas, Point coordinate) {
        coordinate = coordinate.sub(cameraPos);
        if (coordinate.getX() < 0 || coordinate.getY() < 0 || cameraDim.getX() < coordinate.getX() || cameraDim.getY() < coordinate.getY()) {
            return;
        }

        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(COLOR_NODE);
        gc.setLineWidth(this.padding + 1);
        gc.fillOval(coordinate.getX() * this.fieldSize, coordinate.getY() * this.fieldSize, this.fieldSize - padding, this.fieldSize - padding);

    }

    private void renderEdge(Canvas canvas, Point from, Point to) {
        from = from.sub(cameraPos);
        to = to.sub(cameraPos);

//        Boolean oneVisibleNode = false;
//        if (!(from.getX() < 0 || from.getY() < 0 || cameraDim.getX() < from.getX() || cameraDim.getY() < from.getY())) {
//            oneVisibleNode = true;
//        }
//
//        if (!(to.getX() < 0 || to.getY() < 0 || cameraDim.getX() < to.getX() || cameraDim.getY() < to.getY())) {
//            oneVisibleNode = true;
//        }
//
//        if (!oneVisibleNode) {
//            return;
//        }

        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setStroke(COLOR_EDGE);
        gc.setLineWidth(this.padding + 1);

        // gc.fillOval(coordinate.getX() * this.fieldSize + 1, coordinate.getY() * this.fieldSize + 1, this.fieldSize - padding, this.fieldSize - padding);
        gc.strokeLine(from.getX() * this.fieldSize + (this.fieldSize - padding) / 2,
                from.getY() * this.fieldSize + (this.fieldSize - padding) / 2,
                to.getX() * this.fieldSize + (this.fieldSize - padding) / 2,
                to.getY() * this.fieldSize + (this.fieldSize - padding) / 2);


        // MINI POINTS!!!!
         renderEdgeSteps(canvas, from, to);

    }

    void renderEdgeSteps(Canvas canvas, Point from, Point to) {
        // TODO: make this "4" a variable parameter
        int count = 4 - 1;
        Point edgeAtOrigin = to.mul(this.fieldSize).sub(from.mul(this.fieldSize));

        // double dist = 1 / count;
        int stepSize = fieldSize / 3;

        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(COLOR_EDGE_STEPS);

        for (int i = 1; i <= count; i++) {
            Point p = edgeAtOrigin.mul((i) / (1.0 + count));
            // gc.strokeLine();
            gc.fillOval(from.getX() * this.fieldSize + p.getX() + (this.fieldSize - padding) / 2 - stepSize / 2,
                    from.getY() * this.fieldSize + p.getY() + (this.fieldSize - padding) / 2 - stepSize / 2, stepSize, stepSize);
        }
    }




    /* ------- Camera logic ------- */

    public void moveCamera(Point diff) {
        cameraPos = cameraPos.add(diff);
        adjustCamera();
        renderMap();
    }

    /**
     * Adjusts the camera in case the camera position does not match the windows or graph boundaries.
     */
    private void adjustCamera() {
        if (graph == null) {
            return;
        }

        cameraDim = new Point(
                (int) Math.min(((pane.getWidth() - 1) / fieldSize), graph.getXRange()),
                (int) Math.min(((pane.getHeight() - 1) / fieldSize), graph.getYRange()));

        cameraPos = new Point(
                Math.min(Math.max(0, cameraPos.getX()), graph.getXRange() - cameraDim.getX()),
                Math.min(Math.max(0, cameraPos.getY()), graph.getYRange() - cameraDim.getY()));

    }




    /* ------- Callback Type ------- */

    interface OnMouseClickedCallback {
        void call(Point coordinate) throws InvalidCoordinateException;
    }









    /* ------- CanvasPane special class ------- */


    private static class CanvasPane extends Pane {

        private final Canvas canvas;

        public CanvasPane() {
            canvas = new Canvas(10, 10); // this.getWidth(), this.getHeight()
            getChildren().add(canvas);
        }

        public Canvas getCanvas() {
            return canvas;
        }

        @Override
        protected void layoutChildren() {
            final double x = snappedLeftInset();
            final double y = snappedTopInset();
            final double w = snapSize(getWidth()) - x - snappedRightInset();
            final double h = snapSize(getHeight()) - y - snappedBottomInset();
            canvas.setLayoutX(x);
            canvas.setLayoutY(y);
            canvas.setWidth(w);
            canvas.setHeight(h);
        }
    }
}
