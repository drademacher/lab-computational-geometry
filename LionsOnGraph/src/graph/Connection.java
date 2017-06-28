package graph;

public class Connection implements Drawable {

    private Vertex[] vertices = new Vertex[2];

    public Connection(Vertex start, Vertex end) {
        this.vertices[0] = start;
        this.vertices[1] = end;

        if (start.equals(end)) {
            throw new IllegalArgumentException("Connection need two different vertices");
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
        return "Connection between vertex: " + vertices[0].getId() + " and vertex: " + vertices[1].getId();
    }
}
