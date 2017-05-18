package core.graph;

import core.util.Point;

import java.util.ArrayList;

/**
 * Created by Danny on 13.05.2017.
 */
public class Graph {

    private ArrayList<Edge> edges = new ArrayList<>();
    private ArrayList<Vertex> vertices = new ArrayList<>();

    public Graph(){
        Vertex startVertex = new Vertex(new Point(1, 5));
        this.vertices.add(startVertex);
    }


    /**
     * Create new Vertex
     * to create a new vertex, chose a baseVertex and new coordinate for the new vertex,
     * because every new vertex needs a edge connection to a existing vertex (only one component!)
     *
     * */
    public boolean registerVertex(Point newVertexCoord){

        Vertex newVertex = new Vertex(newVertexCoord);

        this.vertices.add(newVertex);

        return true;
    }

    /**
     * Delete Vertex
     * delete a vertex and all incident edges
     *
     * */
    public boolean deleteVertex(Vertex vertex){

        for(int i = vertex.getEdges().size() - 1; i >= 0; i--){
            Edge edge = vertex.getEdges().get(i);
            deleteEdge(edge);
        }
        return vertices.remove(vertex);

    }

    /**
     * Create new Edge
     * specify two vertices between the new edge should be created
     *
     * */
    public boolean registerEdge(Vertex baseVertex, Vertex targetVertex){

        Edge newEdge = Edge.createNewEdge(baseVertex, targetVertex);

        this.edges.add(newEdge);

        return true;
    }


    /**
     * Delete Edge
     * unregister and delete edge
     *
     * */
    public boolean deleteEdge(Edge edge){

        if(edge.deleteEdge()){
            return edges.remove(edge);
        }
        return false;
    }


    public ArrayList<Edge> getEdges(){
        return edges;
    }

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }


    public int getXRange() {
        // TODO: fix this
        return 10;
    }

    public int getYRange() {
        // TODO: fix this
        return 10;
    }

}
