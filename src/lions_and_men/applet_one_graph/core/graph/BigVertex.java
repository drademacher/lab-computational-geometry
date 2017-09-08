package lions_and_men.applet_one_graph.core.graph;

import lions_and_men.util.Point;

import java.util.ArrayList;

/**
 * Created by Jens on 20.06.2017.
 */
public class BigVertex extends Vertex {

    protected ArrayList<Edge> NEWedges = new ArrayList();

    public BigVertex(int id, Point coordinates) {
        super(id, coordinates);
    }

    public boolean registerEdge(Edge edge) {
        return this.NEWedges.add(edge);
    }

    public boolean NEWunregisterEdge(Edge edge) {
        return this.NEWedges.remove(edge);
    }

    public ArrayList<Edge> getEdges() {
        return NEWedges;
    }

}
