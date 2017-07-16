package lions_on_graph.core.strategies.ManStrategies;

import lions_on_graph.core.CoreController;
import lions_on_graph.core.graph.Vertex;
import lions_on_graph.core.strategies.StrategyMan;

import java.util.ArrayList;

public class ManStrategyManually extends StrategyMan {

    public ManStrategyManually(CoreController coreController, CoreController.ManStrategy strategyEnum) {
        super(coreController, strategyEnum);
    }

    @Override
    public ArrayList<Vertex> calculatePossibleSteps() {
        ArrayList<Vertex> result = new ArrayList<>();
        result.add(man.getCurrentPosition());
        return result;
    }
}
