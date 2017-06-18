package core.graph;


import core.util.Point;

import java.util.ArrayList;

/**
 * Created by Danny on 13.05.2017.
 */
public class Vertex implements GraphEntity {

    private int id;
    private ArrayList<Edge> edges = new ArrayList<Edge>();
    private Point coord;
    private Position vertexPosition;

    public Vertex(int id, Point coord) {
        this.coord = coord;
        this.vertexPosition = new Position(this);
        this.id = id;
    }


    public boolean registerEdge(Edge edge, Position edgeFacingPosition) {

        return vertexPosition.registerNeighborPosition(edgeFacingPosition) && edges.add(edge);
    }

    public boolean unregisterEdge(Edge edge, Position edgeFacingPosition) {
        return vertexPosition.unregisterNeighborPosition(edgeFacingPosition) && edges.remove(edge);
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public Point getCoord() {
        return coord;
    }

    public void setCoord(Point point) {
        this.coord = point;
        this.vertexPosition = new Position(this);
    }

    public Position getPosition() {
        return vertexPosition;
    }

    public int getId() {
        return id;
    }

    @Override
    public String getGraphEntityInfo() {
        return "Vertex_" + id;
    }

    @Override
    public String toString() {
        String edgeString = "";
        for (Edge edge : edges) {
            edgeString += edge.getGraphEntityInfo() + "  ";
        }
        return getGraphEntityInfo() + ": " + edgeString;
    }
}
