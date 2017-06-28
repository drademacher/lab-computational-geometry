package graph;

import util.Point;

import java.util.ArrayList;

/**
 * Created by Jens on 20.06.2017.
 */
public class BigVertex extends Vertex implements Drawable {

    protected ArrayList<Edge> NEWedges = new ArrayList();

    public BigVertex(int id, Point coordinates) {
        super(id, coordinates);
    }

    public boolean registerEdge(Edge edge) {
        return this.NEWedges.add(edge);
    }

    public boolean NEWunregisterEdge(Edge edge){
        return this.NEWedges.remove(edge);
    }

    public ArrayList<Edge> getEdges() {
        return NEWedges;
    }

}
