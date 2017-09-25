package lions_and_men.applet_graph.algorithm.graph;

import lions_and_men.util.Point;

import java.util.ArrayList;

public class BigVertex extends Vertex {

    private ArrayList<Edge> NEWedges = new ArrayList<>();

    BigVertex(int id, Point coordinates) {
        super(id, coordinates);
    }

    void registerEdge(Edge edge) {
        this.NEWedges.add(edge);
    }

    void NEWunregisterEdge(Edge edge) {
        this.NEWedges.remove(edge);
    }

    public ArrayList<Edge> getEdges() {
        return NEWedges;
    }

}
