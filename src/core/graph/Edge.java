package core.graph;


import core.util.Point;

/**
 * Created by Danny on 13.05.2017.
 */
public class Edge {

    private Vertex[] vertices = new Vertex[2];
    private int vertexWeight;

    public static Edge createNewEdge(Vertex vertexStart, Vertex vertexEnd){
        //TODO specify default vertexWeight
        return Edge.createNewEdge(vertexStart, vertexEnd, 4);
    }
    public static Edge createNewEdge(Vertex vertexStart, Vertex vertexEnd, int vertexWeight){
        Edge edge = new Edge(vertexStart, vertexEnd, vertexWeight);
        edge.registerEdge();
        return edge;
    }

    private Edge(Vertex vertexStart, Vertex vertexEnd, int vertexWeight){
        this.vertices[0] = vertexStart;
        this.vertices[1] = vertexEnd;
        this.vertexWeight = vertexWeight;

    }

    private boolean registerEdge(){
        return vertices[0].registerEdge(this) && vertices[1].registerEdge(this);
    }

    public boolean deleteEdge(){
        return vertices[0].unregisterEdge(this) && vertices[1].unregisterEdge(this);
    }

    public Point getCoordStart(){
        return vertices[0].getCoord();
    }

    public Point getCoordEnd(){
        return vertices[1].getCoord();
    }

    public Vertex[] getVertices(){
        return vertices;
    }

    public int getVertexWeight() {
        return vertexWeight;
    }
}
