package graph;

import util.Point;

import java.util.ArrayList;

public abstract class Vertex {

    protected ArrayList<Edge> edges = new ArrayList<>();
    protected Point coordinates;
    protected int id;

    public Vertex(int id, Point coordinates) {
        this.coordinates = coordinates;
        this.id = id;
    }


    public boolean registerEdge(Edge edge) {
        return this.edges.add(edge);
    }

    public boolean unregisterEdge(Edge edge) {
        return this.edges.remove(edge);
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    @Override
    public boolean equals(Object o) {
        // self check
        if (this == o)
            return true;
        // null check
        if (o == null)
            return false;
        // type check and cast
        if (getClass() != o.getClass())
            return false;
        Vertex vertex = (Vertex) o;
        // field comparison
        return vertex.getId() == getId() && vertex.getCoordinates().equals(getCoordinates());
    }

    @Override
    public String toString() {
        return getId()+"";
    }
}
