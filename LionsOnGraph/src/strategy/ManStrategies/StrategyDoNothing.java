package strategy.ManStrategies;

import entities.Man;
import graph.CoreController;
import graph.Vertex;
import strategy.StrategyMan;

import java.util.ArrayList;

public class StrategyDoNothing extends StrategyMan {

    public StrategyDoNothing(CoreController coreController, Man man) {
        super(coreController, man);
    }

    @Override
    public ArrayList<Vertex> calculatePossibleSteps() {
        ArrayList<Vertex> result = new ArrayList<>();
        result.add(man.getCurrentPosition());
        return result;
    }
}
