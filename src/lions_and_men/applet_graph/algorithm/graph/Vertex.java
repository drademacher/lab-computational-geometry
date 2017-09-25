package lions_and_men.applet_graph.algorithm.graph;

import lions_and_men.util.Point;

import java.util.ArrayList;

public abstract class Vertex {

    protected Point coordinates;
    private ArrayList<Connection> connections = new ArrayList<>();
    private int id;

    public Vertex(int id, Point coordinates) {
        this.coordinates = coordinates;
        this.id = id;
    }


    void registerConnection(Connection connection) {
        this.connections.add(connection);
    }

    void unregisterConnection(Connection connection) {
        this.connections.remove(connection);
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }

    int getId() {
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
