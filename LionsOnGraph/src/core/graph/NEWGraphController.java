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

    public NEWBigVertex createVertex(Point coordinate){
        return this.graph.createVertex(coordinate);
    }

    public boolean relocateVertex(NEWBigVertex vertex, Point newCoordinate){
        return this.graph.relocateVertex(vertex, newCoordinate);
    }

    public boolean deleteVertex(NEWBigVertex vertex){
        return this.graph.deleteVertex(vertex);
    }

    public boolean createEdge(NEWBigVertex vertex1, NEWBigVertex vertex2, int weight){
        return this.graph.createEdge(vertex1, vertex2, weight);
    }

    public boolean removeEdge(NEWBigVertex vertex1, NEWBigVertex vertex2){
        return this.graph.removeEdge(vertex1, vertex2);
    }

    public NEWVertex getVertexByCoord(Point coordinate) {
        return this.graph.getVertexByCoordinate(coordinate);
    }


    public String debugGraph(){
        return graph.debugGraph();
    }

}
