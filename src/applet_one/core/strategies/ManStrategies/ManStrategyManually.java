package applet_one.core.strategies.ManStrategies;

import applet_one.core.CoreController;
import applet_one.core.graph.Vertex;
import applet_one.core.strategies.StrategyMan;

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
