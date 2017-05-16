package core.graph;

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
        edge.registerEdges(edge);
        return edge;
    }

    private Edge(Vertex vertexStart, Vertex vertexEnd, int vertexWeight){
        this.vertices[0] = vertexStart;
        this.vertices[1] = vertexEnd;
        this.vertexWeight = vertexWeight;

    }

    private void registerEdges(Edge edge){

        Vertex[] vertices = edge.getVertices();

        //register the edge with the vertices
        vertices[0].registerEdge(edge);
        vertices[1].registerEdge(edge);
    }


    public Vertex[] getVertices(){
        return vertices;
    }

    public int getVertexWeight() {
        return vertexWeight;
    }
}
