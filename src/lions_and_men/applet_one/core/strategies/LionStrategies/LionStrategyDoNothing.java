package lions_and_men.applet_one.core.strategies.LionStrategies;

import lions_and_men.applet_one.core.CoreController;
import lions_and_men.applet_one.core.graph.Vertex;
import lions_and_men.applet_one.core.strategies.StrategyLion;

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