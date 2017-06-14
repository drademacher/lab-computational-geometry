package visualization;


import core.State;
import core.entities.Entity;
import core.exeception.InvalidCoordinateException;
import core.graph.Edge;
import core.graph.Graph;
import core.graph.Vertex;
import core.util.Point;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import static visualization.VisConstants.*;


public class GraphVisPane extends Pane {
    private final Canvas baseCanvas, entityCanvas;


    private Point cameraDim;
    private Point cameraPos = new Point(0, 0);

    private Graph graph;
    private State state;

    // private StackPane pane;

    // private OnMouseClickedCallback onMouseClickedCallback;
    private int fieldSize = 30;
    private int padding = (fieldSize > 5) ? fieldSize / 10 : 0;


    private boolean editMode = true; // TODO: important variable!!!



    private boolean hasDraggedStarted = false;
    private Point dragStart = null;

    GraphVisPane(StackPane superPane) { // Canvas canvas, Canvas edgeLengthCanvas, Canvas edgeStepsActiveCanvas, Canvas edgeStepsAllCanvas, Canvas shortestDistanceCanvas, Canvas shortestPathCanvas
        super();



        superPane.getChildren().add(this);


        this.baseCanvas = new Canvas(10, 10);
        this.entityCanvas = new Canvas(10, 10);
        getChildren().addAll(baseCanvas, entityCanvas);
        this.entityCanvas.setMouseTransparent(true);

        this.baseCanvas.setOnScroll(event -> {
            if (event.getDeltaY() == 0) return;
            if (event.getDeltaY() > 0 && fieldSize + ZOOM_FACTOR <= ZOOM_MAX) this.fieldSize += ZOOM_FACTOR;
            if (event.getDeltaY() < 0 && fieldSize - ZOOM_FACTOR >= ZOOM_MIN) this.fieldSize -= ZOOM_FACTOR;
            padding = (fieldSize > 5) ? fieldSize / 10 : 0;
            refreshMap();
        });


//        ( (StackPane) canvas.getParent()).heightProperty().addListener((observable, oldValue, newValue) -> {
//            if (graph == null) return;
//            System.out.println(newValue.intValue());
//            cameraDim.setY(Math.min(((newValue.intValue() - 10) / fieldSize), graph.getYRange()) );
//            updateCamera();
//            renderMap();
//        });

    }


    @Override
    protected void layoutChildren() {
        final double x = snappedLeftInset();
        final double y = snappedTopInset();
        final double w = snapSize(getWidth()) - x - snappedRightInset();
        final double h = snapSize(getHeight()) - y - snappedBottomInset();
        baseCanvas.setLayoutX(x);
        baseCanvas.setLayoutY(y);
        baseCanvas.setWidth(w);
        baseCanvas.setHeight(h);
        entityCanvas.setLayoutX(x);
        entityCanvas.setLayoutY(y);
        entityCanvas.setWidth(w);
        entityCanvas.setHeight(h);
    }




    /* ------- Getter & Setter ------- */

    void setGraph(Graph graph) {

        this.graph = graph;
        cameraDim = new Point(graph.getXRange(), graph.getYRange());
        refreshMap();
        setGraphEditMode();
    }


    public void setState(State state) {
        this.state = state;
        refreshMap();
    }

    private Point getPoint(double x, double y) {
        int pX = new Double((x - 1) / this.fieldSize).intValue();
        int pY = new Double((y - 2) / this.fieldSize).intValue();
        return new Point(pX, pY).add(cameraPos);
    }

    void setGraphEditMode() {
        this.baseCanvas.setOnDragDetected(event -> {
            hasDraggedStarted = true;
            dragStart = getPoint(event.getX(), event.getY());

            refreshMap();
            GraphicsContext gc = this.baseCanvas.getGraphicsContext2D();
            gc.setFill(Color.RED);
            gc.fillOval(event.getX() - 5, event.getY() - 5, 10, 10);
            event.consume();
        });

        this.baseCanvas.setOnMouseReleased(event -> {

            if (!hasDraggedStarted) {
                Point p1 = getPoint(event.getX(), event.getY());

                if (event.getButton() == MouseButton.PRIMARY) {
                    addNode(p1);
                }

                if (event.getButton() == MouseButton.SECONDARY) {
                    removeNode(p1);
                }

            } else {
                hasDraggedStarted = false;

                Point dragEnd = getPoint(event.getX(), event.getY());
                if (!graph.isVertex(dragEnd)) {
                    relocateNode(dragStart, dragEnd);
                } else {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        addEdge(dragStart, dragEnd);
                    }

                    if (event.getButton() == MouseButton.SECONDARY) {
                        removeEdge(dragStart, dragEnd);

                    }
                }


                GraphicsContext gc = this.baseCanvas.getGraphicsContext2D();
                gc.setFill(Color.RED);
                gc.fillOval(event.getX() - 5, event.getY() - 5, 10, 10);
                event.consume();
            }
        });

    }

    void setLionEditMode() {

    }

    private void addNode(Point coordinate) {
        if (graph.registerVertex(coordinate)) {
            this.refreshMap();
        }
    }

    private void removeNode(Point coordinate) {
        if (graph.deleteVertex(coordinate)) {
            this.refreshMap();
        }
    }

    private void relocateNode(Point start, Point end){
        if (graph.relocateVertex(graph.getVertexByCoord(start), end)) {
            this.refreshMap();
        }
    }

    private void addEdge(Point start, Point end) {
        if (graph.registerEdge(start, end)) {
            this.refreshMap();
        }
    }

    private void removeEdge(Point start, Point end) {
        if (graph.deleteEdge(start, end)) {
            this.refreshMap();
        }
    }





    void setOnMouseClickedCallback(OnMouseClickedCallback callback) {
       //  this.onMouseClickedCallback = callback;
        // TODO: remove this dummy method
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

        GraphicsContext gc = this.baseCanvas.getGraphicsContext2D();
        gc.setFill(COLOR_BACKGROUND);
        gc.fillRect(0, 0, this.baseCanvas.getWidth(), this.baseCanvas.getHeight());


        for(Edge edge : graph.getEdges()){
            renderEdge(baseCanvas, edge.getCoordStart(), edge.getCoordEnd());
        }

        for(Vertex vertex : graph.getVertices()){
            renderNode(baseCanvas, vertex.getCoord());
        }

        gc.setFill(COLOR_MAN);

        if (state == null) return;

        gc.setFill(COLOR_MAN);
        for (Entity man : state.getMen()) {
            renderSingleEdgeSteps(baseCanvas,
                                  man.getCurrentGraphPosition().getVertexStart().getCoord(),
                                  man.getCurrentGraphPosition().getVertexEnd().getCoord(),
                                  man.getCurrentGraphPosition().getStepsOnEdge());
        }

        gc.setFill(COLOR_LION);
        for (Entity lion : state.getLions()) {
            renderSingleEdgeSteps(baseCanvas,
                                lion.getCurrentGraphPosition().getVertexStart().getCoord(),
                                lion.getCurrentGraphPosition().getVertexEnd().getCoord(),
                                lion.getCurrentGraphPosition().getStepsOnEdge());
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

        canvas.getGraphicsContext2D().setFill(COLOR_EDGE_STEPS);
        for (int i = 1; i <= count; i++) {
             renderSingleEdgeSteps(canvas, from, to, i);
        }
    }

    private void renderSingleEdgeSteps(Canvas canvas, Point from, Point to,int i) {
        int stepSize = fieldSize / 3;
        int count = 4 - 1;

        Point edgeAtOrigin = to.mul(this.fieldSize).sub(from.mul(this.fieldSize));

        GraphicsContext gc = canvas.getGraphicsContext2D();


        Point p = edgeAtOrigin.mul((i) / (1.0 + count));
        gc.fillOval(from.getX() * this.fieldSize + p.getX() + (this.fieldSize - padding) / 2 - stepSize / 2,
                from.getY() * this.fieldSize + p.getY() + (this.fieldSize - padding) / 2 - stepSize / 2, stepSize, stepSize);
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
                (int) Math.min(((( (Pane) this.getParent()).getWidth() - 1) / fieldSize), graph.getXRange()),
                (int) Math.min(((( (Pane) this.getParent()).getHeight() - 1) / fieldSize), graph.getYRange()));

        cameraPos = new Point(
                Math.min(Math.max(0, cameraPos.getX()), graph.getXRange() - cameraDim.getX()),
                Math.min(Math.max(0, cameraPos.getY()), graph.getYRange() - cameraDim.getY()));

    }





    /* ------- Callback Type ------- */

    interface OnMouseClickedCallback {
        void call(Point coordinate) throws InvalidCoordinateException;
    }







}
