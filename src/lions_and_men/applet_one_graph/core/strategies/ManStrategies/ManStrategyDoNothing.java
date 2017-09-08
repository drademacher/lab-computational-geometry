package lions_and_men.applet_one_graph.core.strategies.ManStrategies;

import lions_and_men.applet_one_graph.core.CoreController;
import lions_and_men.applet_one_graph.core.graph.Vertex;
import lions_and_men.applet_one_graph.core.strategies.StrategyMan;

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