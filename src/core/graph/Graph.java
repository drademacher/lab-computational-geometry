package core.graph;

import javafx.geometry.Point2D;

import java.util.ArrayList;

/**
 * Created by Danny on 13.05.2017.
 */
public class Graph {

    private static Graph graph;
    private ArrayList<Edge> edges = new ArrayList<Edge>();
    private ArrayList<Vertex> vertices = new ArrayList<Vertex>();

    //singleton
    public static Graph getGraph(){
        if(Graph.graph == null){
            Graph.graph = new Graph();
        }
        return Graph.graph;
    }
    private Graph(){
        Vertex startVertex = new Vertex(new Point2D(2, 2));
        this.vertices.add(startVertex);
    }


    /**
     * Create new Vertex
     * to create a new vertex, chose a baseVertex and new coordinate for the new vertex,
     * because every new vertex needs a edge connection to a existing vertex (only one component!)
     *
     * */
    public void registerVertice(Vertex baseVertex, Point2D newVertexCoord){

        Vertex newVertex = new Vertex(newVertexCoord);

        this.vertices.add(newVertex);

        registerEdge(baseVertex, newVertex);
    }

    /**
     * Create new Edge
     * specify two vertices between the new edge should be created
     *
     * */
    public void registerEdge(Vertex baseVertex, Vertex targetVertex){

        Edge newEdge = Edge.createNewEdge(baseVertex, targetVertex);

        this.edges.add(newEdge);
    }

    public ArrayList<Edge> getEdges(){
        return edges;
    }

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }
}
