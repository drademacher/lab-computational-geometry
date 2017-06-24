package core.graph.graphlogic;

/**
 * Created by Jens on 23.06.2017.
 */
public class Edge {

    private Vertex[] vertices = new Vertex[2];

    public Edge(Vertex start, Vertex end) {
        this.vertices[0] = start;
        this.vertices[1] = end;

        if (start.equals(end)) {
            throw new IllegalArgumentException("Edge need two different vertices");
        }
    }

    public boolean contains(Vertex vertex) {
        return vertices[0].equals(vertex) || vertices[1].equals(vertex);
    }

    public Vertex getNeighbor(Vertex vertex) {
        if (vertices[0].equals(vertex)) {
            return vertices[1];
        }
        if (vertices[1].equals(vertex)) {
            return vertices[0];
        }
        return null;
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    @Override
    public String toString() {
        return "Edge between vertex: " + vertices[0].getId() + " and vertex: " + vertices[1].getId();
    }
}
