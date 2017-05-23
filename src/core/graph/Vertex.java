package core.graph;


import core.util.Point;

import java.util.ArrayList;

/**
 * Created by Danny on 13.05.2017.
 */
public class Vertex {

    private ArrayList<Edge> edges = new ArrayList<Edge>();
    private Point coord;

    public Vertex(Point coord){
        this.coord = coord;

    }


    public boolean registerEdge(Edge edge){
        return this.edges.add(edge);
    }

    public boolean unregisterEdge(Edge edge){
        return this.edges.remove(edge);
    }

    public ArrayList<Edge> getEdges(){
        return edges;
    }

    public Point getCoord() {
        return coord;
    }
}
