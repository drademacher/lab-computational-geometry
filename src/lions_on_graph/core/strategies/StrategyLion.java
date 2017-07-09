package lions_on_graph.core.strategies;

import lions_on_graph.core.entities.Lion;
import lions_on_graph.core.CoreController;
import lions_on_graph.core.graph.GraphHelper;
import lions_on_graph.core.graph.Vertex;

import java.util.ArrayList;

/**
 * Created by Jens on 01.07.2017.
 */

public abstract class StrategyLion implements Strategy, Cloneable{


    protected CoreController coreController;
    protected Lion lion;
    protected GraphHelper helper;

    public StrategyLion(CoreController coreController) {
        this.coreController = coreController;
        this.helper = GraphHelper.createGraphHelper(coreController);
    }


    @Override
    public Vertex getNextPosition() {
        //we can implement here more conditions for the returned vertex

        //fallback
        ArrayList<Vertex> result = calculatePossibleSteps();
        if (result.size() > 0) {
            return calculatePossibleSteps().get(0) != null ? calculatePossibleSteps().get(0) : lion.getCurrentPosition();
        }
        return lion.getCurrentPosition();
    }

    protected abstract ArrayList<Vertex> calculatePossibleSteps();

    public void setLion(Lion lion) {
        this.lion = lion;
    }

}
