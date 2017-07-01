package strategy;

import entities.Entity;
import entities.Man;
import graph.CoreController;
import graph.Vertex;

import java.util.ArrayList;

public class StrategyDoNothing extends StrategyMan {

    public StrategyDoNothing(CoreController coreController, Man man) {
        super(coreController, man);
    }

    @Override
    public Vertex getNextPosition(CoreController coreController, Entity e) {
        return e.getCurrentPosition();
    }

    @Override
    public ArrayList<Vertex> calculatePossibleSteps() {
        return man.getCurrentPosition();
    }
}
