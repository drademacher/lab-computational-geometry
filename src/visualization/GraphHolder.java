package visualization;


import core.exeception.InvalidCoordinateException;
import core.graph.Edge;
import core.graph.Graph;
import core.graph.Vertex;
import core.util.Point;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import static visualization.VisConstants.*;


public class GraphHolder {
    private Point cameraDim;
    private Point cameraPos = new Point(0, 0);

    private Graph graph;


    private Canvas baseCanvas, edgeLengthCanvas, edgeStepsActiveCanvas, edgeStepsAllCanvas, shortestDistanceCanvas, shortestPathCanvas;
    private OnMouseClickedCallback onMouseClickedCallback;
    private int fieldSize = 10;
    private int padding = (fieldSize > 5) ? fieldSize / 10 : 0;

    GraphHolder(Canvas baseCanvas, Canvas edgeLengthCanvas, Canvas edgeStepsActiveCanvas, Canvas edgeStepsAllCanvas, Canvas shortestDistanceCanvas, Canvas shortestPathCanvas) {
        this.baseCanvas = baseCanvas;
        this.edgeLengthCanvas = edgeLengthCanvas;
        this.edgeStepsActiveCanvas = edgeStepsActiveCanvas;
        this.edgeStepsAllCanvas = edgeStepsAllCanvas;
        this.shortestDistanceCanvas = shortestDistanceCanvas;
        this.shortestPathCanvas = shortestPathCanvas;


        baseCanvas.setOnScroll(event -> {
            if (event.getDeltaY() == 0) return;
            if (event.getDeltaY() > 0 && fieldSize + ZOOM_FACTOR <= ZOOM_MAX) this.fieldSize += ZOOM_FACTOR;
            if (event.getDeltaY() < 0 && fieldSize - ZOOM_FACTOR >= ZOOM_MIN) this.fieldSize -= ZOOM_FACTOR;
            padding = (fieldSize > 5) ? fieldSize / 10 : 0;
            updateCamera();
            renderMap();
        });


        baseCanvas.setOnMouseClicked(event -> {
            if (onMouseClickedCallback == null) return;
            int x = new Double((event.getX() - 1) / this.fieldSize).intValue();
            int y = new Double((event.getY() - 2) / this.fieldSize).intValue();
            try {
                this.onMouseClickedCallback.call(new Point(x, y).add(cameraPos));
            } catch (InvalidCoordinateException e) {
                e.printStackTrace();
            }
        });
    }



    /* ------- Getter & Setter ------- */

    void setNode(Point coordinate) {
        if (graph.registerVertex(coordinate)) {
            this.renderNode(baseCanvas, coordinate);
        }
    }

    void removeNode(Point coordinate) {
        if (graph.deleteVertex(coordinate)) {
            this.refreshMap();
        }
    }

    void setEdge(Point from, Point to) {
        if (graph.registerEdge(from, to)) {
            this.renderEdge(baseCanvas, from, to);
        }
    }

    void removeEdge(Point from, Point to) {
        if (graph.deleteEdge(from, to)) {
            this.refreshMap();
        }
    }

    void setGraph(Graph graph) {
        this.graph = graph;
        updateCamera();
        refreshMap();
    }


    void setOnMouseClickedCallback(OnMouseClickedCallback callback) {
        this.onMouseClickedCallback = callback;
    }



    /* ------- Functions for rendering canvas ------- */

    void refreshMap() {
        // TODO: reset the lions / men
        renderMap();
    }

    private void renderMap() {
        if (graph == null) return;

        baseCanvas.getGraphicsContext2D().clearRect(0, 0, this.baseCanvas.getWidth(), this.baseCanvas.getHeight());
        edgeLengthCanvas.getGraphicsContext2D().clearRect(0, 0, this.edgeLengthCanvas.getWidth(), this.edgeLengthCanvas.getHeight());
        edgeStepsActiveCanvas.getGraphicsContext2D().clearRect(0, 0, this.edgeStepsActiveCanvas.getWidth(), this.edgeStepsActiveCanvas.getHeight());
        edgeStepsAllCanvas.getGraphicsContext2D().clearRect(0, 0, this.edgeStepsAllCanvas.getWidth(), this.edgeStepsAllCanvas.getHeight());
        shortestDistanceCanvas.getGraphicsContext2D().clearRect(0, 0, this.shortestDistanceCanvas.getWidth(), this.shortestDistanceCanvas.getHeight());
        shortestPathCanvas.getGraphicsContext2D().clearRect(0, 0, this.shortestPathCanvas.getWidth(), this.shortestPathCanvas.getHeight());


        GraphicsContext gc = this.baseCanvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, this.baseCanvas.getWidth(), this.baseCanvas.getHeight());


        for(Vertex vertex : graph.getVertices()){
            renderNode(baseCanvas, vertex.getCoord());
        }
        for(Edge edge : graph.getEdges()){
            renderEdge(baseCanvas, edge.getCoordStart(), edge.getCoordEnd());
        }
    }

    private void renderNode(Canvas canvas, Point coordinate) {
        coordinate = coordinate.sub(cameraPos);
        if (coordinate.getX() < 0 || coordinate.getY() < 0 || cameraDim.getX() < coordinate.getX() || cameraDim.getY() < coordinate.getY()) {
            return;
        }

        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.BLACK);
        gc.setLineWidth(this.padding + 1);
        gc.fillOval(coordinate.getX() * this.fieldSize, coordinate.getY() * this.fieldSize, this.fieldSize - padding, this.fieldSize - padding);

        // System.out.println("coord " + coordinate);


        // System.out.println("nice " + coordinate.getX() * this.fieldSize + 1);
        // System.out.println("nice " + baseCanvas.getWidth());


    }

    private void renderEdge(Canvas canvas, Point from, Point to) {
        from = from.sub(cameraPos);
        to = to.sub(cameraPos);

        Boolean oneVisibleNode = false;

        if (!(from.getX() < 0 || from.getY() < 0 || cameraDim.getX() < from.getX() || cameraDim.getY() < from.getY())) {
            oneVisibleNode = true;
        }

        if (!(to.getX() < 0 || to.getY() < 0 || cameraDim.getX() < to.getX() || cameraDim.getY() < to.getY())) {
            oneVisibleNode = true;
        }

        if (!oneVisibleNode) {
            return;
        }

        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(this.padding + 1);

        // gc.fillOval(coordinate.getX() * this.fieldSize + 1, coordinate.getY() * this.fieldSize + 1, this.fieldSize - padding, this.fieldSize - padding);
        gc.strokeLine(from.getX() * this.fieldSize + (this.fieldSize - padding) / 2,
                from.getY() * this.fieldSize + (this.fieldSize - padding) / 2,
                to.getX() * this.fieldSize + (this.fieldSize - padding) / 2,
                to.getY() * this.fieldSize + (this.fieldSize - padding) / 2);


        // MINI POINTS!!!!
        renderEdgeSteps(edgeStepsAllCanvas, from, to);

    }

    void renderEdgeSteps(Canvas canvas, Point from, Point to) {
        // TODO: make this "4" a variable parameter
        int count = 4 - 1;
        Point edgeAtOrigin = to.mul(this.fieldSize).sub(from.mul(this.fieldSize));

        // double dist = 1 / count;
        int stepSize = fieldSize / 3;

        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.DARKRED);
        gc.setStroke(Color.DARKRED);

        for (int i = 1; i <= count; i++) {
            Point p = edgeAtOrigin.mul((i) / (1.0 + count));
            // gc.strokeLine();
            gc.fillOval(from.getX() * this.fieldSize + p.getX() + (this.fieldSize - padding) / 2 - stepSize / 2,
                    from.getY() * this.fieldSize + p.getY() + (this.fieldSize - padding) / 2 - stepSize / 2, stepSize, stepSize);
        }
    }




    /* ------- Camera logic ------- */

    public void moveCamera(Point diff) {
        int factor = 1; // TODO: implement logic for a nice multiplier
        cameraPos = cameraPos.add(diff.mul(factor));
        adjustCamera();
        renderMap();
    }

    private void updateCamera() {
        StackPane pane = (StackPane) baseCanvas.getParent();

        cameraDim = new Point((int) Math.min(((pane.getWidth() - 1) / fieldSize), graph.getXRange()), (int) Math.min(((pane.getHeight() - 1) / fieldSize), graph.getYRange()));
        // System.out.println("pane: " + pane.getWidth());
        cameraDim = new Point(10, 10);

        this.baseCanvas.setWidth(this.fieldSize * cameraDim.getX() + 1);
        this.baseCanvas.setHeight(this.fieldSize * cameraDim.getY() + 1);
        this.edgeLengthCanvas.setWidth(this.fieldSize * cameraDim.getX() + 1);
        this.edgeLengthCanvas.setHeight(this.fieldSize * cameraDim.getY() + 1);
        this.edgeStepsActiveCanvas.setWidth(this.fieldSize * cameraDim.getX() + 1);
        this.edgeStepsActiveCanvas.setHeight(this.fieldSize * cameraDim.getY() + 1);
        this.edgeStepsAllCanvas.setWidth(this.fieldSize * cameraDim.getX() + 1);
        this.edgeStepsAllCanvas.setHeight(this.fieldSize * cameraDim.getY() + 1);
        this.shortestDistanceCanvas.setWidth(this.fieldSize * cameraDim.getX() + 1);
        this.shortestDistanceCanvas.setHeight(this.fieldSize * cameraDim.getY() + 1);
        this.shortestPathCanvas.setWidth(this.fieldSize * cameraDim.getX() + 1);
        this.shortestPathCanvas.setHeight(this.fieldSize * cameraDim.getY() + 1);

        // System.out.println("pane: " + pane.getWidth());

        adjustCamera();
    }

    /**
     * Adjusts the camera in case the camera position does not match the windows or graph boundaries.
     */
    private void adjustCamera() {
        if (graph == null) {
            return;
        }

        cameraPos = new Point(
                Math.min(Math.max(0, cameraPos.getX()), graph.getXRange() - cameraDim.getX()),
                Math.min(Math.max(0, cameraPos.getY()), graph.getYRange() - cameraDim.getY()));
    }




    /* ------- Callback Type ------- */

    interface OnMouseClickedCallback {
        void call(Point coordinate) throws InvalidCoordinateException;
    }
}
