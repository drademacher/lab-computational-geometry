package lions_and_men.applet_graph.algorithm.strategies;

import lions_and_men.applet_graph.algorithm.CoreController;
import lions_and_men.applet_graph.algorithm.entities.Lion;
import lions_and_men.applet_graph.algorithm.entities.Man;
import lions_and_men.applet_graph.algorithm.graph.Vertex;

import java.util.ArrayList;

/**
 * Created by Jens on 18.07.2017.
 */
public class CleverLion extends Strategy<Lion> {

    public CleverLion(CoreController coreController) {
        super(coreController);
    }

    @Override
    protected ArrayList<Vertex> calculatePossibleSteps() {
        //System.out.println("### " + lion);
        Vertex currentPosition = entity.getCurrentPosition();
        int steps = Integer.MAX_VALUE;
        int stepsToBigVertex = Integer.MAX_VALUE;
        ArrayList<Vertex> result = new ArrayList<>();
        for (Vertex possibleTarget : helper.getNeighborBigVertices(currentPosition)) {
            int calculatedSteps = helper.BFSToMen(possibleTarget) + helper.getDistanceBetween(currentPosition, possibleTarget);
            if (calculatedSteps < steps) {
                boolean checkLions = true;
                for (Lion otherLion : coreController.getLions()) {
                    //other lion
                    if (!entity.equals(otherLion)) {
                        if (helper.getDistanceBetween(possibleTarget, otherLion.getCurrentPosition()) < helper.getDistanceBetween(currentPosition, possibleTarget)) {
                            checkLions = false;
                        } else if (helper.getDistanceBetween(possibleTarget, otherLion.getCurrentPosition()) == helper.getDistanceBetween(currentPosition, possibleTarget)) {
                            Vertex calculatedPosition = otherLion.getCalculatedPosition();
                            if (calculatedPosition != null && helper.getDistanceBetween(possibleTarget, calculatedPosition) < helper.getDistanceBetween(calculatedPosition, otherLion.getCurrentPosition())) {
                                checkLions = false;
                            }
                        }
                    }

                }
                if (checkLions) {
                    result.add(0, helper.getPathBetween(currentPosition, possibleTarget).get(0));
                    steps = calculatedSteps;
                    stepsToBigVertex = helper.getDistanceBetween(currentPosition, possibleTarget);
                }
            }
        }

        for (Man man : coreController.getMen()) {
            if (helper.getDistanceBetween(currentPosition, man.getCurrentPosition()) < stepsToBigVertex) {
                result.add(0, helper.getPathBetween(currentPosition, man.getCurrentPosition()).get(0));
            }
        }

        int minimalDistanceToMan = Integer.MAX_VALUE;
        for (Man man : coreController.getMen()) {
            boolean endGamePath = true;
            int distanceToMan = helper.getDistanceBetween(currentPosition, man.getCurrentPosition());

            if (distanceToMan < minimalDistanceToMan) {
                minimalDistanceToMan = distanceToMan;

                for (Lion otherLion : coreController.getLions()) {
                    if (!currentPosition.equals(otherLion.getCurrentPosition())) {
                        int distanceToOtherLion = helper.getDistanceBetween(currentPosition, otherLion.getCurrentPosition());
                        if (distanceToMan > distanceToOtherLion) {
                            endGamePath = false;
                        }

                        int distanceToLionInManDirection = helper.BFSToLion(currentPosition, helper.getPathBetween(currentPosition, man.getCurrentPosition()).get(0));
                        int distanceToBigVertexInManDirection = helper.getDistanceBetween(currentPosition, helper.getNeighborBigVertices(currentPosition, helper.getPathBetween(currentPosition, man.getCurrentPosition()).get(0)).get(0));
                        if (distanceToLionInManDirection > distanceToBigVertexInManDirection) {
                            endGamePath = false;
                        }
                    }
                }

                if (endGamePath) {
                    result.add(0, helper.getPathBetween(currentPosition, man.getCurrentPosition()).get(0));
                }
            }
        }


        return result;
    }
}
