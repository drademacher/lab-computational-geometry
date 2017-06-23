package core.graph;

import core.entities.Lion;
import core.entities.Man;
import core.util.Point;

import java.util.ArrayList;

/**
 * Created by Jens on 20.06.2017.
 */
public class NEWGraphController {

    private ArrayList<Lion> lions = new ArrayList<>();
    private ArrayList<Man> men = new ArrayList<>();

    private NEWGraph graph;

    public NEWGraphController(){
        this.graph = new NEWGraph();
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

    public boolean relocateVertex(NEWBigVertex vertex, Point newCoordinate){
        if(vertex == null || newCoordinate == null){
            return false;
        }
        return this.graph.relocateVertex(vertex, newCoordinate);
    }

    public boolean deleteVertex(NEWBigVertex vertex){
        if(vertex == null){
            return false;
        }
        //TODO Entity?
        return this.graph.deleteVertex(vertex);
    }

    public boolean createEdge(NEWBigVertex vertex1, NEWBigVertex vertex2){
        if(vertex1 == null ||vertex2 == null){
            return false;
        }
        return createEdge(vertex1, vertex2, 4);
    }
    public boolean createEdge(NEWBigVertex vertex1, NEWBigVertex vertex2, int weight){
        if(vertex1 == null || vertex2 == null || weight < 0){
            return false;
        }
        return this.graph.createEdge(vertex1, vertex2, weight);
    }

    public boolean removeEdge(NEWBigVertex vertex1, NEWBigVertex vertex2){
        if(vertex1 == null || vertex2 == null){
            return false;
        }
        //TODO Entity?
        return this.graph.removeEdge(vertex1, vertex2);
    }

    public NEWBigVertex getVertexByCoordinate(Point coordinate) {
        return this.graph.getVertexByCoordinate(coordinate);
    }


    public String debugGraph(){
        return graph.debugGraph();
    }

    public NEWGraph getGraph() {
        return graph;
    }

    public NEWBigVertex getBigVertexById(int id){
        return this.graph.getBigVertexById(id);
    }

    public ArrayList<NEWBigVertex> getBigVertices() {
        return this.graph.getBigVertices();
    }

    public ArrayList<NEWSmallVertex> getSmallVertices() {
        return this.graph.getSmallVertices();
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
