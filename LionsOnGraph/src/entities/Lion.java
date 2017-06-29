package entities;


import graph.CoreController;
import graph.GraphHelper;
import graph.Vertex;
import strategy.Strategy;

import java.util.ArrayList;

public class Lion extends Entity {
    // TODO: implement Lion class

    private int range = 0;


    public Lion(Vertex startPosition, Strategy strategy, CoreController coreController) {
        this(startPosition, strategy, 0, coreController);
    }
    public Lion(Vertex startPosition, Strategy strategy, int range, CoreController coreController) {
        super(startPosition, strategy, coreController);
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

    public ArrayList<Vertex> getRangeVertices(){
        GraphHelper graphHelper = GraphHelper.createGraphHelper(coreController);
        return graphHelper.BFSgetAllVerticesTill(position, getRange());
    }
}
