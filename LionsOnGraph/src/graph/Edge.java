package graph;

import shapes.ShapedEdge;

public class Edge {

    private Vertex[] vertices = new Vertex[2];

    private ShapedEdge shape;

    public static Edge createEdge(GraphController graphController, Vertex start, Vertex end) {
        Edge edge = new Edge(start, end);
        edge.shape = new ShapedEdge(graphController, edge);
        return edge;
    }

    private Edge(Vertex start, Vertex end) {
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

    public ShapedEdge getShape() {
        return shape;
    }

    @Override
    public String toString() {
        return "Edge between vertex: " + vertices[0].getId() + " and vertex: " + vertices[1].getId();
    }
}
