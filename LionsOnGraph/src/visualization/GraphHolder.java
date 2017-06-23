package visualization;


import core.State;
import core.graph.*;
import core.util.Point;
import javafx.scene.Group;
import javafx.scene.shape.Circle;

import static visualization.VisConstants.COLOR_NODE;


public class GraphHolder {

    private GraphController graphController;
    private State state;

    private Group vertexShapes, edgeShapes, entityShapes;


    private boolean editMode = true; // TODO: important variable!!!


    GraphHolder(ZoomScrollPane superPane) {
        super();

        vertexShapes = superPane.getVertexShapes();
        edgeShapes = superPane.getEdgeShapes();
        entityShapes = superPane.getEntityShapes();
    }




    /* ------- Getter & Setter ------- */

    void setGraph(GraphController graphController) {

        this.graphController = graphController;

        refreshGraph();
        setGraphEditMode();
    }


    public void setState(State state) {
        this.state = state;
//        refreshMap();
    }


    void setGraphEditMode() {
//        this.baseCanvas.setOnDragDetected(event -> {
//            hasDraggedStarted = true;
//            dragStart = getPoint(event.getX(), event.getY());
//
//            refreshMap();
//            GraphicsContext gc = this.baseCanvas.getGraphicsContext2D();
//            gc.setFill(Color.RED);
//            gc.fillOval(event.getX() - 5, event.getY() - 5, 10, 10);
//        });
//
//        this.baseCanvas.setOnMouseReleased(event -> {
//
//            if (!hasDraggedStarted) {
//                Point p1 = getPoint(event.getX(), event.getY());
//
//                if (event.getButton() == MouseButton.PRIMARY) {
//                    addNode(p1);
//                }
//
//                if (event.getButton() == MouseButton.SECONDARY) {
//                    removeNode(p1);
//                }
//
//            } else {
//                hasDraggedStarted = false;
//
//                Point dragEnd = getPoint(event.getX(), event.getY());
//                if (!graphController.isVertex(dragEnd)) {
//                    relocateNode(dragStart, dragEnd);
//                } else {
//                    if (event.getButton() == MouseButton.PRIMARY) {
//                        addEdge(dragStart, dragEnd);
//                    }
//
//                    if (event.getButton() == MouseButton.SECONDARY) {
//                        removeEdge(dragStart, dragEnd);
//
//                    }
//                }
//
//
//                GraphicsContext gc = this.baseCanvas.getGraphicsContext2D();
//                gc.setFill(Color.RED);
//                gc.fillOval(event.getX() - 5, event.getY() - 5, 10, 10);
//            }
//        });

    }

    void setEntityEditMode() {
//        this.baseCanvas.setOnDragDetected(event -> {});
//        this.baseCanvas.setOnMouseReleased(event -> {});
    }

    void setPlayMode() {
//        this.baseCanvas.setOnDragDetected(event -> {});
//        this.baseCanvas.setOnMouseReleased(event -> {});
    }

    private void addNode(Point coordinate) {
        if (graphController.createVertex(coordinate)) {
//            this.refreshMap();
        }
    }

    private void removeNode(Point coordinate) {
        if (graphController.deleteVertex(graphController.getBigVertexByCoordinate(coordinate))) {
//            this.refreshMap();
        }
    }

    private void relocateNode(Point start, Point end) {
        if (graphController.relocateVertex(graphController.getBigVertexByCoordinate(start), end)) {
//            this.refreshMap();
        }
    }

    private void addEdge(Point start, Point end) {
        if (graphController.createEdge(graphController.getBigVertexByCoordinate(start), graphController.getBigVertexByCoordinate(end))) {
//            this.refreshMap();
        }
    }

    private void removeEdge(Point start, Point end) {
        if (graphController.removeEdge(graphController.getBigVertexByCoordinate(start), graphController.getBigVertexByCoordinate(end))) {
//            this.refreshMap();
        }
    }







    /* ------- Functions for rendering canvas ------- */


    private void refreshGraph() {
        if (graphController == null) {
            return;
        }
        vertexShapes.getChildren().clear();
        edgeShapes.getChildren().clear();
        entityShapes.getChildren().clear();

        for (BigVertex vertex : graphController.getBigVertices()) {
            Circle elem = new Circle(vertex.getCoordinates().getX(), vertex.getCoordinates().getY(), 5, COLOR_NODE);
            elem.setOnMouseClicked(event -> {
                System.out.println(vertex.getCoordinates());
            });
            vertexShapes.getChildren().add(elem);
        }

        for (SmallVertex vertex : graphController.getSmallVertices()) {
            Circle elem = new Circle(vertex.getCoordinates().getX(), vertex.getCoordinates().getY(), 2, COLOR_NODE);
            elem.setOnMouseClicked(event -> {
                System.out.println(vertex.getCoordinates());
            });
            vertexShapes.getChildren().add(elem);
        }

//        for (Edge edge : graphController.getEdges()) {
//            Line elem = new Line(edge.getCoordStart().getX(), edge.getCoordStart().getY(), edge.getCoordEnd().getX(), edge.getCoordEnd().getY());
//            elem.setOnMouseClicked(event -> System.out.println("wow"));
//            edgeShapes.getChildren().add(elem);
//        }

//        for (Entity man : state.getMen()) {
//            Circle elem = new Circle(man.getCoordinates().getX(), man.getCoordinates().getY(), 2, COLOR_MAN);
//            elem.setOnMouseClicked(event -> {
//                System.out.println(man.getCoordinates());
//            });
//            vertexShapes.getChildren().add(elem);
//        }
//
//        for (Entity lion : state.getLions()) {
//            // TODO: render lions
//        }
//
//
//
    }


}
