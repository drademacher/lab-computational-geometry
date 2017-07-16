package lions_on_graph.core.strategies.LionStrategies;

import lions_on_graph.core.CoreController;
import lions_on_graph.core.graph.Vertex;
import lions_on_graph.core.strategies.StrategyLion;

import java.util.ArrayList;

/**
 * Created by Jens on 11.07.2017.
 */
public class LionStrategyDoNothing extends StrategyLion {

    public LionStrategyDoNothing(CoreController coreController, CoreController.LionStrategy strategyEnum) {
        super(coreController, strategyEnum);
    }

    @Override
    public ArrayList<Vertex> calculatePossibleSteps() {
        ArrayList<Vertex> result = new ArrayList<>();
        result.add(lion.getCurrentPosition());
        return result;
    }
}