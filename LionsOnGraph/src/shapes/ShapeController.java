package shapes;

import entities.Entity;
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

    private Map<Vertex, ShapedVertex> mapVertices = new HashMap<>();
    private Map<Entity, ShapedEntity> mapEntities = new HashMap<>();
    private Map<Edge, ShapedEdge> mapEdges = new HashMap<>();
    private CoreController coreController;

    public ShapeController(CoreController coreController) {
        this.coreController = coreController;
    }

    /* ****************************
     *
     *   Graph Shapes
     *
     * ****************************/


    public void relocateVertex(BigVertex vertex, Point newCoordinate) {
        ShapedVertex shape = mapVertices.get(vertex);
        shape.relocate(newCoordinate);

        for (Edge edge : vertex.getEdges()) {
            ShapedEdge edgeShape = mapEdges.get(edge);
            edgeShape.relocate(edge.getVertices()[0].getCoordinates(), edge.getVertices()[1].getCoordinates());

            for (SmallVertex smallVertex : edge.getEdgeVertices()) {
                ShapedVertex smalLVertexShape = mapVertices.get(smallVertex);
                smalLVertexShape.relocate(smallVertex.getCoordinates());
            }
        }
    }

    public void createVertex(Point coordinate) {
        ShapedBigVertex shape = new ShapedBigVertex(coreController, coordinate);
        mapVertices.put(coreController.getBigVertexByCoordinate(coordinate), shape);
    }

    public void deleteVertex(BigVertex vertex) {
        ShapedVertex shape = mapVertices.get(vertex);
        shape.delete();
        mapVertices.remove(vertex);

        for (Edge edge : vertex.getEdges()) {
            ShapedEdge edgeShape = mapEdges.get(edge);
            mapEdges.remove(edge);
            edgeShape.delete();

            for (SmallVertex smallVertex : edge.getEdgeVertices()) {
                ShapedVertex smallVertexShape = mapVertices.get(smallVertex);
                mapVertices.remove(smallVertex);
                smallVertexShape.delete();
            }
        }
    }

    public void createEdge(Edge edge) {
        ShapedEdge shape = new ShapedEdge(coreController, edge.getStartCoordinates(), edge.getEndCoordinates());
        mapEdges.put(edge, shape);

        for (SmallVertex smallVertex : edge.getEdgeVertices()) {
            ShapedSmallVertex smallVertexShape = new ShapedSmallVertex(coreController, smallVertex.getCoordinates());
            mapVertices.put(smallVertex, smallVertexShape);
        }
    }

    public void removeEdge(Edge edge) {
        ShapedEdge shape = mapEdges.get(edge);
        shape.delete();
        mapEdges.remove(edge);
        for (SmallVertex smallVertex : edge.getEdgeVertices()) {
            ShapedVertex smalLVertexShape = mapVertices.get(smallVertex);
            mapVertices.remove(smallVertex);
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

    public void createMan(Man man) {
        ShapedMan shape = new ShapedMan(coreController, man.getCoordinates());
        mapEntities.put(man, shape);
    }

    public void createLion(Lion lion) {
        ShapedLion shape = new ShapedLion(coreController, lion.getCoordinates());
        mapEntities.put(lion, shape);
    }

    public void relocateMan(Man man) {
        ShapedEntity shape = mapEntities.get(man);
        shape.relocate(man.getCoordinates());
    }

    public void relocateLion(Lion lion) {
        ShapedEntity shape = mapEntities.get(lion);
        shape.relocate(lion.getCoordinates());
    }

    public void removeMan(Man man) {
        ShapedEntity shape = mapEntities.get(man);
        shape.delete();
    }

    public void removeLion(Lion lion) {
        ShapedEntity shape = mapEntities.get(lion);
        shape.delete();
    }

}
