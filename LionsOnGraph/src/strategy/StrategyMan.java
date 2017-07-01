package strategy;

import entities.Entity;
import entities.Man;
import graph.CoreController;
import graph.GraphHelper;
import graph.Vertex;

import java.util.ArrayList;

/**
 * Created by Jens on 01.07.2017.
 */
public abstract class StrategyMan implements Strategy {

    protected CoreController coreController;
    protected Man man;
    protected GraphHelper helper;

    public StrategyMan(CoreController coreController, Man man) {
        this.coreController = coreController;
        this.man = man;
        this.helper = GraphHelper.createGraphHelper(coreController);
    }

    @Override
    public Vertex getNextPosition() {


        for (Vertex vertex : calculatePossibleSteps()) {
            for (Man otherMan : coreController.getMen()) {
                if(!otherMan.equals(man)) {
                    if (man.keepDistanceExact()) {
                        if (man.getDistance() == helper.getDistanceBetween(vertex, otherMan.getCurrentPosition())) {
                            return vertex;
                        }
                    } else {
                        if (man.getDistance() < helper.getDistanceBetween(vertex, otherMan.getCurrentPosition())) {
                            return vertex;
                        }
                    }
                }
            }

            if(coreController.getMen().size() < 2){
                return vertex;
            }
        }

        //fallback
        return man.getCurrentPosition();
    }

    protected abstract ArrayList<Vertex> calculatePossibleSteps();
}
