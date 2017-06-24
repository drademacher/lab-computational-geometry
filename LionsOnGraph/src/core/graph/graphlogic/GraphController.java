package core.graph.graphlogic;

import core.entities.Lion;
import core.entities.Man;
import core.graph.graphshapes.BigVertexShape;
import core.graph.graphshapes.EdgeShape;
import core.graph.graphshapes.SmallVertexShape;
import core.util.Point;

import java.util.ArrayList;

/**
 * Created by Jens on 20.06.2017.
 */
public class GraphController {

    private ArrayList<Lion> lions = new ArrayList<>();
    private ArrayList<Man> men = new ArrayList<>();

    private Graph graph;

    public GraphController(){
        this.graph = new Graph();
    }

    /* ****************************
     *
     *   GRAPH API
     *
     * ****************************/

    public boolean createVertex(Point coordinate){
        if(coordinate == null){
            return false;
        }
        return this.graph.createVertex(coordinate);
    }

//    public boolean relocateVertex(BigVertex vertex, Point newCoordinate){
//        if(vertex == null || newCoordinate == null){
//            return false;
//        }
//        return this.graph.relocateVertex(vertex, newCoordinate);
//    }

    public boolean deleteVertex(BigVertexShape vertex){
        if(vertex == null){
            return false;
        }
        //TODO Entity?
        return this.graph.deleteVertex(vertex);
    }

    public boolean createEdge(BigVertexShape vertex1, BigVertexShape vertex2){
        if(vertex1 == null ||vertex2 == null){
            return false;
        }
        return createEdge(vertex1, vertex2, 4);
    }
    public boolean createEdge(BigVertexShape vertex1, BigVertexShape vertex2, int weight){
        if(vertex1 == null || vertex2 == null || weight < 0){
            return false;
        }
        return this.graph.createEdge(vertex1, vertex2, weight);
    }

    public boolean removeEdge(BigVertexShape vertex1, BigVertexShape vertex2){
        if(vertex1 == null || vertex2 == null){
            return false;
        }
        //TODO Entity?
        return this.graph.removeEdge(vertex1, vertex2);
    }

    public BigVertexShape getBigVertexByCoordinate(Point coordinate) {
        return this.graph.getBigVertexByCoordinate(coordinate);
    }


    public String debugGraph(){
        return graph.debugGraph();
    }

    public Graph getGraph() {
        return graph;
    }

    public BigVertexShape getBigVertexById(int id){
        return this.graph.getBigVertexById(id);
    }

    public ArrayList<BigVertexShape> getBigVertices() {
        return this.graph.getBigVertices();
    }

    public ArrayList<SmallVertexShape> getSmallVertices() {
        return this.graph.getSmallVertices();
    }

    public ArrayList<EdgeShape> getEdges() {
        return this.graph.getEdges();
    }

    /* ****************************
     *
     *   ENTITY API
     *
     * ****************************/

    public boolean setMan(Man man){
        return men.add(man);
    }

    public boolean setLion(Lion lion){
        return lions.add(lion);
    }

    public boolean removeMan(Man man){
        return men.remove(man);
    }

    public boolean removeLion(Lion lion){
        return lions.remove(lion);
    }

    public ArrayList<Man> getMen() {
        return men;
    }

    public ArrayList<Lion> getLions() {
        return lions;
    }
}
