package applet_one.core.strategies.ManStrategies;

import applet_one.core.CoreController;
import applet_one.core.graph.Vertex;
import applet_one.core.strategies.StrategyMan;

import java.util.ArrayList;

/**
 * Created by Jens on 11.07.2017.
 */
public class ManStrategyDoNothing extends StrategyMan {

    public ManStrategyDoNothing(CoreController coreController, CoreController.ManStrategy strategyEnum) {
        super(coreController, strategyEnum);
    }

    @Override
    public ArrayList<Vertex> calculatePossibleSteps() {
        ArrayList<Vertex> result = new ArrayList<>();
        result.add(man.getCurrentPosition());
        return result;
    }
}