package strategy;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import entities.Lion;
import graph.CoreController;
import graph.GraphHelper;
import graph.Vertex;

import java.util.ArrayList;

/**
 * Created by Jens on 01.07.2017.
 */

public abstract class StrategyLion implements Strategy {


    protected CoreController coreController;
    protected Lion lion;
    protected GraphHelper helper;

    public StrategyLion(CoreController coreController, Lion lion) {
        this.coreController = coreController;
        this.lion = lion;
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
}
