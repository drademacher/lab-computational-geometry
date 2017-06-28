package shapes;

import graph.*;
import util.Point;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jens on 28.06.2017.
 */
public class ShapeController {

    Map<GraphObject, Shape> map = new HashMap<>();
    private GraphController graphController;

    public ShapeController(GraphController graphController){
        this.graphController = graphController;
    }


    public void relocateVertex(BigVertex vertex, Point newCoordinate) {
        Shape shape = map.get(vertex);
        shape.relocate(newCoordinate);

        for(Edge edge : vertex.getEdges()){
            Shape edgeShape = map.get(edge);
            if(edgeShape instanceof ShapedEdge){
                //TODO how to solve the interface problem? -> ShapedEdges needs a special relocate() function
                ((ShapedEdge)edgeShape).relocate(edge.getVertices()[0].getCoordinates(), edge.getVertices()[1].getCoordinates());
            }else{
                throw new IllegalArgumentException("Should be a ShapedEdge");
            }

            for(SmallVertex smallVertex : edge.getEdgeVertices()){
                Shape smalLVertexShape = map.get(smallVertex);
                smalLVertexShape.relocate(smallVertex.getCoordinates());
            }
        }
    }

    public void createVertex(Point coordinate) {
        ShapedBigVertex shape = new ShapedBigVertex(graphController, coordinate);
        map.put(graphController.getBigVertexByCoordinate(coordinate), shape);
    }

    public void deleteVertex(BigVertex vertex) {
        Shape shape = map.get(vertex);
        shape.delete();
        map.remove(vertex);

        System.out.println("delete Vertex,...");
        System.out.println("edges.... "+vertex.getEdges());
        for(Edge edge : vertex.getEdges()){
            Shape edgeShape = map.get(edge);
            map.remove(edge);
            edgeShape.delete();

            for(SmallVertex smallVertex : edge.getEdgeVertices()){
                Shape smalLVertexShape = map.get(smallVertex);
                map.remove(smallVertex);
                smalLVertexShape.delete();
            }
        }
    }

    public void createEdge(Edge edge) {
        ShapedEdge shape = new ShapedEdge(graphController, edge.getStartCoordinates(), edge.getEndCoordinates());
        map.put(edge, shape);

        for(SmallVertex smallVertex : edge.getEdgeVertices()){
            ShapedSmallVertex smallVertexShape = new ShapedSmallVertex(graphController, smallVertex.getCoordinates());
            map.put(smallVertex, smallVertexShape);
        }
    }

    public void removeEdge(Edge edge) {
        Shape shape = map.get(edge);
        shape.delete();
        map.remove(edge);
        for(SmallVertex smallVertex : edge.getEdgeVertices()){
            Shape smalLVertexShape = map.get(smallVertex);
            map.remove(smallVertex);
            smalLVertexShape.delete();
        }
    }

    public void changeEdgeWeight(Edge edge) {
        //TODO
    }
}
