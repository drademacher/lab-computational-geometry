package shapes;

import graph.*;
import util.Point;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jens on 28.06.2017.
 */
public class ShapeController implements Api {

    Map<GraphObject, Shape> map = new HashMap<>();
    private GraphController graphController;

    public ShapeController(GraphController graphController){
        this.graphController = graphController;
    }


    @Override
    public BigVertex relocateVertex(BigVertex vertex, Point newCoordinate) {
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
        return null;
    }

    @Override
    public BigVertex createVertex(Point coordinate) {
        ShapedBigVertex shape = new ShapedBigVertex(graphController, coordinate);
        map.put(graphController.getBigVertexByCoordinate(coordinate), shape);
        return null;
    }

    @Override
    public BigVertex deleteVertex(BigVertex vertex) {
        Shape shape = map.get(vertex);
        shape.delete();
        map.remove(vertex);

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
        return null;
    }

    @Override
    public Edge createEdge(BigVertex vertex1, BigVertex vertex2, int weight) {
        Edge edge = graphController.getEdgeByVertices(vertex1, vertex2);
        ShapedEdge shape = new ShapedEdge(graphController, vertex1.getCoordinates(), vertex2.getCoordinates());
        map.put(edge, shape);

        for(SmallVertex smallVertex : edge.getEdgeVertices()){
            ShapedSmallVertex smallVertexShape = new ShapedSmallVertex(graphController, smallVertex.getCoordinates());
            map.put(edge, shape);
        }
        return null;
    }

    @Override
    public Edge removeEdge(BigVertex vertex1, BigVertex vertex2) {
        Shape shape = map.get(graphController.getEdgeByVertices(vertex1, vertex2));
        shape.delete();
        map.remove(graphController.getEdgeByVertices(vertex1, vertex2));
        return null;
    }

    @Override
    public Edge changeEdgeWeight(BigVertex vertex1, BigVertex vertex2, int weight) {
        //TODO
        return null;
    }
}
