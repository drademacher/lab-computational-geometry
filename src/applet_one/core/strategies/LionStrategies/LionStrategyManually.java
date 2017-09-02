package applet_one.core.strategies.LionStrategies;


import applet_one.core.CoreController;
import applet_one.core.graph.Vertex;
import applet_one.core.strategies.StrategyLion;

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
        result.add(lion.getCurrentPosition());
        return result;
    }
}