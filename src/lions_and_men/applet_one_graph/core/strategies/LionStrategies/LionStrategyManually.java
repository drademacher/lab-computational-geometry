package lions_and_men.applet_one_graph.core.strategies.LionStrategies;


import lions_and_men.applet_one_graph.core.CoreController;
import lions_and_men.applet_one_graph.core.graph.Vertex;
import lions_and_men.applet_one_graph.core.strategies.StrategyLion;

import java.util.ArrayList;

/**
 * Created by Jens on 01.07.2017.
 */
public class LionStrategyManually extends StrategyLion {

    public LionStrategyManually(CoreController coreController, CoreController.LionStrategy strategyEnum) {
        super(coreController, strategyEnum);
    }

    @Override
    public ArrayList<Vertex> calculatePossibleSteps() {
        ArrayList<Vertex> result = new ArrayList<>();
        result.add(entity.getCurrentPosition());
        return result;
    }
}