package visualization;


import core.exeception.InvalidCoordinateException;
import core.graph.Graph;
import core.util.Point;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import static visualization.VisConstants.*;


public class GraphHolder {
    private Point cameraDim;
    private Point cameraPos = new Point(0, 0);

    private Graph graph = Graph.getGraph();


    private Canvas baseCanvas, edgeLengthCanvas, edgeStepsActiveCanvas, edgeStepsOtherCanvas, shortestDistanceCanvas, shortestPathCanvas;
    private OnMouseClickedCallback onMouseClickedCallback;
    private int fieldSize = 10;
    private int padding = (fieldSize > 5) ? 1 : 0;

    GraphHolder(Canvas baseCanvas, Canvas edgeLengthCanvas, Canvas edgeStepsActiveCanvas, Canvas edgeStepsOtherCanvas, Canvas shortestDistanceCanvas, Canvas shortestPathCanvas) {
        this.baseCanvas = baseCanvas;
        this.edgeLengthCanvas = edgeLengthCanvas;
        this.edgeStepsActiveCanvas = edgeStepsActiveCanvas;
        this.edgeStepsOtherCanvas = edgeStepsOtherCanvas;
        this.shortestDistanceCanvas = shortestDistanceCanvas;
        this.shortestPathCanvas = shortestPathCanvas;


        updateCamera();
        refreshMap();

        System.out.println(baseCanvas.getHeight());

       baseCanvas.setOnScroll(event -> {
            if (event.getDeltaY() == 0) return;
            if (event.getDeltaY() > 0 && fieldSize + ZOOM_FACTOR <= ZOOM_MAX) this.fieldSize += ZOOM_FACTOR;
            if (event.getDeltaY() < 0 && fieldSize - ZOOM_FACTOR >= ZOOM_MIN) this.fieldSize -= ZOOM_FACTOR;
            padding = (fieldSize > 5) ? 1 : 0;
            updateCamera();
            renderMap();
        });

       /*

        baseCanvas.setOnMouseClicked(event -> {
            if (onMouseClickedCallback == null) return;
            int x = new Double((event.getX() - 1) / this.fieldSize).intValue();
            int y = new Double((event.getY() - 2) / this.fieldSize).intValue();
            try {
                this.onMouseClickedCallback.call(new Point(x, y).add(cameraPos));
            } catch (InvalidCoordinateException e) {
                e.printStackTrace();
            }
        });*/
    }



    /* ------- Getter & Setter ------- */

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
        edgeStepsOtherCanvas.getGraphicsContext2D().clearRect(0, 0, this.edgeStepsOtherCanvas.getWidth(), this.edgeStepsOtherCanvas.getHeight());
        shortestDistanceCanvas.getGraphicsContext2D().clearRect(0, 0, this.shortestDistanceCanvas.getWidth(), this.shortestDistanceCanvas.getHeight());
        shortestPathCanvas.getGraphicsContext2D().clearRect(0, 0, this.shortestPathCanvas.getWidth(), this.shortestPathCanvas.getHeight());


        GraphicsContext gc = this.baseCanvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, this.baseCanvas.getWidth(), this.baseCanvas.getHeight());

        renderNode(baseCanvas, new Point(0, 0));

        /*
        for (int x = 0; x < cameraDim.getX(); x++) {
            for (int y = 0; y < cameraDim.getY(); y++) {
                renderField(gridCanvas, new Vector(x, y).add(cameraPos), isPassable(new Vector(x, y).add(cameraPos)) ? GRID_POINT : OBSTACLE_POINT);
            }
        }
        */

        // gc.fillRect(1, 1, 5, 5);
    }

    private void renderNode(Canvas canvas, Point coordinate) {
        coordinate = coordinate.sub(cameraPos);
        if (coordinate.getX() < 0 || coordinate.getY() < 0 || cameraDim.getX() < coordinate.getX() || cameraDim.getY() < coordinate.getY())
            return;

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.YELLOW);
        gc.fillRect(coordinate.getX() * this.fieldSize + 1, coordinate.getY() * this.fieldSize + 1, this.fieldSize - padding, this.fieldSize - padding);

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(this.padding + 1);
        // gc.fillRect(coordinate.getX() * this.fieldSize + 1, coordinate.getY() * this.fieldSize + 1, this.fieldSize - padding, this.fieldSize - padding);
        gc.strokeOval(coordinate.getX() * this.fieldSize + 1, coordinate.getY() * this.fieldSize + 1, this.fieldSize - padding, this.fieldSize - padding);

        System.out.println("coord " + coordinate);


        System.out.println("nice " + coordinate.getX() * this.fieldSize + 1);
        System.out.println("nice " + baseCanvas.getWidth());

        System.out.println(cameraPos);
        adjustCamera();
        System.out.println(cameraPos);
    }




    /* ------- Camera logic ------- */

    public void moveCamera(Point diff) {
        int factor = 1; // TODO: implement logic for a nice multiplier
        // cameraPos = cameraPos.add(diff.mult(factor));
        adjustCamera();
        renderMap();
    }

    private void updateCamera() {
        StackPane pane = (StackPane) baseCanvas.getParent();

        cameraDim = new Point((int) Math.min(((pane.getWidth() - 1) / fieldSize), graph.getXRange()), (int) Math.min(((pane.getHeight() - 1) / fieldSize), graph.getYRange()));
        System.out.println("pane: " + pane.getWidth());
        cameraDim = new Point(10, 10);

        this.baseCanvas.setWidth(this.fieldSize * cameraDim.getX() + 1);
        this.baseCanvas.setHeight(this.fieldSize * cameraDim.getY() + 1);
        this.edgeLengthCanvas.setWidth(this.fieldSize * cameraDim.getX() + 1);
        this.edgeLengthCanvas.setHeight(this.fieldSize * cameraDim.getY() + 1);
        this.edgeStepsActiveCanvas.setWidth(this.fieldSize * cameraDim.getX() + 1);
        this.edgeStepsActiveCanvas.setHeight(this.fieldSize * cameraDim.getY() + 1);
        this.edgeStepsOtherCanvas.setWidth(this.fieldSize * cameraDim.getX() + 1);
        this.edgeStepsOtherCanvas.setHeight(this.fieldSize * cameraDim.getY() + 1);
        this.shortestDistanceCanvas.setWidth(this.fieldSize * cameraDim.getX() + 1);
        this.shortestDistanceCanvas.setHeight(this.fieldSize * cameraDim.getY() + 1);
        this.shortestPathCanvas.setWidth(this.fieldSize * cameraDim.getX() + 1);
        this.shortestPathCanvas.setHeight(this.fieldSize * cameraDim.getY() + 1);

        System.out.println("pane: " + pane.getWidth());

        adjustCamera();
    }

    /**
     * Adjusts the camera in case the camera position does not match the windows or graph boundaries.
     */
    private void adjustCamera() {
        System.out.println("test 1");
        if (graph == null) return;
        System.out.println("test 2");
        cameraPos = new Point(
                Math.min(Math.max(0, cameraPos.getX()), graph.getXRange() - cameraDim.getX()),
                Math.min(Math.max(0, cameraPos.getY()), graph.getYRange() - cameraDim.getY()));
    }




    /* ------- Callback Type ------- */

    interface OnMouseClickedCallback {
        void call(Point coordinate) throws InvalidCoordinateException;
    }
}
