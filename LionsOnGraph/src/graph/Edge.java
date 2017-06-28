package graph;


import java.util.ArrayList;

public class Edge {

    private BigVertex[] vertices = new BigVertex[2];
    private ArrayList<SmallVertex> edgeVertices = new ArrayList<>();
    private int weight;

    public Edge(BigVertex start, BigVertex end, ArrayList<SmallVertex> edgeVertices, int weight) {
        this.vertices[0] = start;
        this.vertices[1] = end;
        this.edgeVertices = edgeVertices;
        this.weight = weight;
    }

    public ArrayList<SmallVertex> getEdgeVertices() {
        return edgeVertices;
    }

    public int getEdgeWeight() {
        return weight;
    }

    public boolean unregisterAll(BigVertex vertex) {
        for (SmallVertex edgeVertex : edgeVertices) {
            //TODO ?
        }
        return getNeighbor(vertex).unregisterEdge(this);
    }

    public boolean contains(BigVertex vertex) {
        return vertices[0].equals(vertex) || vertices[1].equals(vertex);
    }

    public BigVertex getNeighbor(BigVertex vertex) {
        if (vertices[0].equals(vertex)) {
            return vertices[1];
        }
        if (vertices[1].equals(vertex)) {
            return vertices[0];
        }
        return null;
    }

    public BigVertex[] getVertices() {
        return vertices;
    }

    @Override
    public String toString() {
        return "Edge between vertex: " + vertices[0].getId() + " and vertex: " + vertices[1].getId();
    }
}
