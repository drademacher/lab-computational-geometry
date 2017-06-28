package graph;

import shapes.ShapedBigVertex;
import util.Point;

import java.util.ArrayList;

/**
 * Created by Jens on 20.06.2017.
 */
public class BigVertex extends Vertex {

    protected ArrayList<Edge> NEWedges = new ArrayList();

    public BigVertex(int id, Point coordinates) {
        super(id, coordinates);
    }

    public boolean deleteVertex() {
        for (Edge edge : NEWedges) {
            edge.unregisterAll(this);
        }
        return false;
    }


    public boolean registerEdge(Edge edge) {
        return this.NEWedges.add(edge);
    }

    public boolean unregisterEdge(Edge removedEdge) {
        for (Edge edge : NEWedges) {
            if (edge.equals(removedEdge)) {

                if (edge.getEdgeVertices().size() <= 0) {
                    for (int i = connections.size() - 1; i >= 0; i--) {
                        if (connections.get(i).contains(removedEdge.getNeighbor(this))) {
                            unregisterConnection(connections.get(i));
                        }
                    }
                }
                for (SmallVertex vertex : edge.getEdgeVertices()) {
                    for (int i = vertex.getConnections().size() - 1; i >= 0; i--) {
                        Connection connection = vertex.getConnections().get(i);
                        vertex.unregisterConnection(connection);
                        if (connection.contains(this)) {
                            unregisterConnection(connection);
                        }
                        if (connection.contains(removedEdge.getNeighbor(this))) {
                            removedEdge.getNeighbor(this).unregisterConnection(connection);
                        }
                    }
                }
                return NEWedges.remove(edge);
            }
        }
        return false;
    }

    public ArrayList<Edge> getEdges() {
        return NEWedges;
    }

}
