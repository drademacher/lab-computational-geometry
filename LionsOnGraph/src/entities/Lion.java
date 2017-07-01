package entities;


import graph.CoreController;
import graph.GraphHelper;
import graph.Vertex;
import strategy.Strategy;
import strategy.StrategyLion;
import strategy.StrategyMan;

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
