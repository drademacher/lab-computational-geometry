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

//        GraphHelper graphHelper = GraphHelper.createGraphHelper(coreController);
//
//        for (Vertex vertex : calculatePossibleSteps()) {
//            if (man.keepDistanceExact()) {
//                if (man.getDistance() == graphHelper.getDistanceBetween(vertex, man.getCurrentPosition())) {
//                    return vertex;
//                }
//            } else {
//                if (man.getDistance() < graphHelper.getDistanceBetween(vertex, man.getCurrentPosition())) {
//                    return vertex;
//                }
//            }
//        }

        ArrayList<Vertex> result = calculatePossibleSteps();
        if(result.size() > 0){
            System.out.println("return "+result.get(0));
            return result.get(0);
        }

        //fallback
        System.out.println("fallback");
        return man.getCurrentPosition();
    }

    protected abstract ArrayList<Vertex> calculatePossibleSteps();
}
