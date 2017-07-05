package lions_on_graph.entities;


import lions_on_graph.graph.CoreController;
import lions_on_graph.graph.GraphHelper;
import lions_on_graph.graph.Vertex;

import java.util.ArrayList;

public class Lion extends Entity {
    // TODO: implement Lion class

    private int range = 0;


    public Lion(Vertex startPosition, CoreController coreController) {
        this(startPosition, 0, coreController);
    }

    public Lion(Vertex startPosition, int range, CoreController coreController) {
        super(startPosition, coreController);
        this.range = range;
    }

    @Override
    public String toString() {
        return "Lion @ " + position;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public ArrayList<Vertex> getRangeVertices() {
        GraphHelper graphHelper = GraphHelper.createGraphHelper(coreController);
        return graphHelper.BFSgetAllVerticesTill(position, getRange());
    }
}
