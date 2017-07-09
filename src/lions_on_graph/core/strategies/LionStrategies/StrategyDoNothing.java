package lions_on_graph.core.strategies.LionStrategies;


import lions_on_graph.core.entities.Lion;
import lions_on_graph.core.CoreController;
import lions_on_graph.core.graph.Vertex;
import lions_on_graph.core.strategies.StrategyLion;

import java.util.ArrayList;

/**
 * Created by Jens on 01.07.2017.
 */
public class StrategyDoNothing extends StrategyLion {

    public StrategyDoNothing(CoreController coreController, Lion lion) {
        super(coreController, lion);
    }

    @Override
    public ArrayList<Vertex> calculatePossibleSteps() {
        ArrayList<Vertex> result = new ArrayList<>();
        result.add(lion.getCurrentPosition());
        return result;
    }
}