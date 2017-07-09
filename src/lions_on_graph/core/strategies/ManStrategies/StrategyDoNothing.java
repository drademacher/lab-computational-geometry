package lions_on_graph.core.strategies.ManStrategies;

import lions_on_graph.core.entities.Man;
import lions_on_graph.core.CoreController;
import lions_on_graph.core.graph.Vertex;
import lions_on_graph.core.strategies.StrategyMan;

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
