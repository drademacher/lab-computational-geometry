package lions_and_men.applet_one_graph.core.strategies;

import lions_and_men.applet_one_graph.core.CoreController;
import lions_and_men.applet_one_graph.core.entities.Entity;
import lions_and_men.applet_one_graph.core.graph.Vertex;

import java.util.ArrayList;

public class StrategyManually<T extends Entity> extends Strategy<T> {

    public StrategyManually(CoreController coreController) {
        super(coreController);
    }

    @Override
    public ArrayList<Vertex> calculatePossibleSteps() {
        ArrayList<Vertex> result = new ArrayList<>();
        result.add(entity.getCurrentPosition());
        return result;
    }

    @Override
    public String getName() {
        return "TODO: CRUCIAL ERROR";
    }
}
