package core.graph;

import javafx.geometry.Point2D;

import java.util.ArrayList;

/**
 * Created by Danny on 13.05.2017.
 */
public class Vertex {

    private ArrayList<Edge> edges = new ArrayList<Edge>();
    private Point2D coord;

    public Vertex(Point2D coord){
        this.coord = coord;

    }


    public void registerEdge(Edge edge){
        this.edges.add(edge);
    }

    public ArrayList<Edge> getEdges(){
        return edges;
    }

    public Point2D getCoord() {
        return coord;
    }
}
