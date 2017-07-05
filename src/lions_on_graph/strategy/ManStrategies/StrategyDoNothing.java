package lions_on_graph.strategy.ManStrategies;

import lions_on_graph.entities.Man;
import lions_on_graph.graph.CoreController;
import lions_on_graph.graph.Vertex;
import lions_on_graph.strategy.StrategyMan;

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
