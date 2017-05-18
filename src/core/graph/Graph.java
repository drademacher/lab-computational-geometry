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

        //TODO check if valid
        return true;
    }

    /**
     * Delete Vertex
     * delete a vertex and all incident edges
     *
     * */
    public boolean deleteVertex(Point point){
        return deleteVertex(getVertexByCoord(point));
    }
    public boolean deleteVertex(Vertex vertex){
        if(vertex == null){
            return false;
        }

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
    public boolean registerEdge(Point pointBaseVertex, Point pointTargetVertex){
        return registerEdge(getVertexByCoord(pointBaseVertex), getVertexByCoord(pointTargetVertex));
    }
    public boolean registerEdge(Vertex baseVertex, Vertex targetVertex){

        if(baseVertex == null || targetVertex == null){
            return false;
        }

        Edge newEdge = Edge.createNewEdge(baseVertex, targetVertex);

        this.edges.add(newEdge);

        //TODO check if valid
        return true;
    }


    /**
     * Delete Edge
     * unregister and delete edge
     *
     * */
    public boolean deleteEdge(Point coordStart, Point coordEnd){
        return deleteEdge(getEdgeByCoord(coordStart, coordEnd));
    }
    public boolean deleteEdge(Edge edge){

        if(edge.deleteEdge()){
            return edges.remove(edge);
        }
        return false;
    }

    /**
     * get Vertex by Coord
     *
     * */
    public Vertex getVertexByCoord(Point coord){
        for(Vertex vertex : vertices){
            if (vertex.getCoord().equals(coord)) {
                return vertex;
            }
        }
        return null;
    }

    /**
     * get Edge by Coord
     *
     * */
    public Edge getEdgeByCoord(Point coordStart, Point coordEnd){
        for(Edge edge : edges){
            //both possibilities
            if(edge.getCoordStart().equals(coordStart) && edge.getCoordEnd().equals(coordEnd)){
                return edge;
            }
            if(edge.getCoordStart().equals(coordEnd) && edge.getCoordEnd().equals(coordStart)){
                return edge;
            }
        }
        return null;
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
