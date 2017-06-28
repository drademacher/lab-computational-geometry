package graph;


import util.Point;

import java.util.ArrayList;

public class Edge implements Drawable {

    private BigVertex[] vertices = new BigVertex[2];
    private ArrayList<SmallVertex> edgeVertices = new ArrayList<>();
    private int weight;

    public Edge(BigVertex start, BigVertex end, ArrayList<SmallVertex> edgeVertices, int weight) {
        this.vertices[0] = start;
        this.vertices[1] = end;
        this.edgeVertices = edgeVertices;
        this.weight = weight;

        if (start.equals(end)) {
            throw new IllegalArgumentException("Edge need two different vertices");
        }

    }

    public ArrayList<SmallVertex> getEdgeVertices() {
        return edgeVertices;
    }

    public int getEdgeWeight() {
        return weight;
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

    public Point getStartCoordinates(){
        return vertices[0].getCoordinates();
    }

    public Point getEndCoordinates(){
        return vertices[1].getCoordinates();
    }

    @Override
    public String toString() {
        return "Edge between vertex: " + vertices[0].getId() + " and vertex: " + vertices[1].getId();
    }
}
