package shapes;

import entities.Lion;
import entities.Man;
import graph.*;
import util.Point;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jens on 28.06.2017.
 */
public class ShapeController {

    Map<Drawable, Shape> map = new HashMap<>();
    private GraphController graphController;

    public ShapeController(GraphController graphController){
        this.graphController = graphController;
    }

    /* ****************************
     *
     *   Graph Shapes
     *
     * ****************************/


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

        for(Edge edge : vertex.getEdges()){
            Shape edgeShape = map.get(edge);
            map.remove(edge);
            edgeShape.delete();

            for(SmallVertex smallVertex : edge.getEdgeVertices()){
                Shape smallVertexShape = map.get(smallVertex);
                map.remove(smallVertex);
                smallVertexShape.delete();
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

    /* ****************************
     *
     *   ENTITY Shapes
     *
     * ****************************/

    public void createMan(Man man){
        ShapedMan shape = new ShapedMan(graphController, man.getCoordinates());
        map.put(man, shape);
    }

    public void createLion(Lion lion){
        ShapedLion shape = new ShapedLion(graphController, lion.getCoordinates());
        map.put(lion, shape);
    }

    public void relocateMan(Man man){
        Shape shape = map.get(man);
        shape.relocate(man.getCoordinates());
    }

    public void relocateLion(Lion lion){
        Shape shape = map.get(lion);
        shape.relocate(lion.getCoordinates());
    }

    public void removeMan(Man man){
        Shape shape = map.get(man);
        shape.delete();
    }

    public void removeLion(Lion lion){
        Shape shape = map.get(lion);
        shape.delete();
    }

}
