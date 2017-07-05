package lions_on_graph.strategy.LionStrategies;


import lions_on_graph.entities.Lion;
import lions_on_graph.graph.CoreController;
import lions_on_graph.graph.Vertex;
import lions_on_graph.strategy.StrategyLion;

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