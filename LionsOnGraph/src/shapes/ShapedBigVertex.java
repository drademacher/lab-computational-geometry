package shapes;

import graph.BigVertex;
import graph.GraphController;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.shape.Circle;
import util.Point;
import visualization.ZoomScrollPane;

import static shapes.ShapeConstants.*;

public class ShapedBigVertex {
    private static ZoomScrollPane mainPane;
    private static Group shapeGroup;

    private Circle shape;
    private BigVertex vertex;
    private GraphController graphController;


    public ShapedBigVertex(GraphController graphController, BigVertex vertex) {
        this.vertex = vertex;
        this.graphController = graphController;



        shape = new Circle(vertex.getCoordinates().getX(), vertex.getCoordinates().getY(), BIG_VERTEX_RADIUS);
        shape.setStrokeWidth(BIG_VERTEX_RADIUS / 5);
        shape.setStroke(COLOR_VERTEX);
        shape.setFill(COLOR_BACKGROUND);
        shapeGroup.getChildren().add(shape);

//        shape.setOnMouseClicked(event -> event.consume());
        shape.setOnContextMenuRequested(event1 -> {
            event1.consume();

            if (!this.graphController.isEditMode())
                return;

            ContextMenu contextMenu = ContextMenuHolder.getFreshContextMenu();
            MenuItem item0 = new MenuItem("Create Edge");
            MenuItem item1 = new MenuItem("Remove Edge");
            MenuItem item2 = new MenuItem("Relocate Node");
            MenuItem item5 = new MenuItem("Remove Node");
            MenuItem item3 = new MenuItem("Add Man");
            MenuItem item4 = new MenuItem("Add Lion");
            MenuItem closeItem = new MenuItem("Close");

            item0.setOnAction(event2 -> {
                mainPane.setOnMouseClicked(event3 -> {

                    mainPane.setOnMouseClicked(null);

                    graphController.createEdge(vertex, graphController.getBigVertexByCoordinate(mainPane.getLocalCoordinates(event3.getX(), event3.getY())));

                });
            });

            item1.setOnAction(event2 -> {
                mainPane.setOnMouseClicked(event3 -> {

                    mainPane.setOnMouseClicked(null);

                    graphController.removeEdge(vertex, graphController.getBigVertexByCoordinate(mainPane.getLocalCoordinates(event3.getX(), event3.getY())));

                });
            });

            item2.setOnAction(event2 -> {
                mainPane.setOnMouseClicked(event3 -> {

                    mainPane.setOnMouseClicked(null);

//                    System.out.println(mainPane.getLocalCoordinates(event3.getX(), event3.getY()));
                    graphController.relocateVertex(vertex, mainPane.getLocalCoordinates(event3.getX(), event3.getY()));

                });
            });

            item5.setOnAction(event2 -> {
                graphController.deleteVertex(vertex);
//                shapeGroup.getChildren().remove(shape);
            });

            item3.setOnAction(event2 -> {
                System.out.println("Add Man");
                // TODO: something like new Man(vertex.getCoordinates());
                graphController.setMan(vertex);
            });

            item4.setOnAction(event2 -> {
                System.out.println("Add Lion");
                // TODO: something like new Lion(vertex.getCoordinates());
                graphController.setLion(vertex);
            });

            contextMenu.getItems().addAll(item2, item5,  new SeparatorMenuItem(), item0, item1,new SeparatorMenuItem(), item3, item4, new SeparatorMenuItem(), closeItem);
            contextMenu.show(shape, event1.getScreenX(), event1.getScreenY());
        });

    }

    public static void setMainPane(ZoomScrollPane mainPane) {
        ShapedBigVertex.mainPane = mainPane;
    }

    public static void setShapeGroup(Group shapeGroup) {
        ShapedBigVertex.shapeGroup = shapeGroup;
    }

    public void relocate() {
        shape.relocate(vertex.getCoordinates().getX() - BIG_VERTEX_RADIUS, vertex.getCoordinates().getY() - BIG_VERTEX_RADIUS);
    }

    public void delete() {
        shapeGroup.getChildren().remove(shape);
    }
}
