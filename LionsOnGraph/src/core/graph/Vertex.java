package core.graph;

import core.util.Point;

import java.util.ArrayList;

/**
 * Created by Jens on 20.06.2017.
 */
public abstract class Vertex {

    protected ArrayList<Vertex> adjacentVertices = new ArrayList<>();
    protected Point coordinates;
    protected int id;

    public Vertex(int id, Point coordinates){
        this.coordinates = coordinates;
        this.id = id;
    }


    public boolean registerAdjacentVertex(Vertex vertex){
        return this.adjacentVertices.add(vertex);
    }

    public boolean unregisterAdjacentVertex(Vertex vertex){
        return this.adjacentVertices.remove(vertex);
    }

    public void setCoordinates(Point coordinates){
        this.coordinates = coordinates;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public int getId(){
        return id;
    }

    public ArrayList<Vertex> getAdjacentVertices() {
        return adjacentVertices;
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
}
