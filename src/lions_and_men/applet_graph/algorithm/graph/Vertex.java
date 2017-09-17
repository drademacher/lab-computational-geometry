package lions_and_men.applet_graph.algorithm.graph;

import lions_and_men.util.Point;

import java.util.ArrayList;

public abstract class Vertex {

    protected ArrayList<Connection> connections = new ArrayList<>();
    protected Point coordinates;
    protected int id;

    public Vertex(int id, Point coordinates) {
        this.coordinates = coordinates;
        this.id = id;
    }


    public boolean registerConnection(Connection connection) {
        return this.connections.add(connection);
    }

    public boolean unregisterConnection(Connection connection) {
        return this.connections.remove(connection);
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

    public ArrayList<Connection> getConnections() {
        return connections;
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
        return getId() + "";
    }
}
