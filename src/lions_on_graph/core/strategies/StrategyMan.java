package lions_on_graph.core.strategies;

import lions_on_graph.core.CoreController;
import lions_on_graph.core.entities.Man;
import lions_on_graph.core.graph.Connection;
import lions_on_graph.core.graph.GraphHelper;
import lions_on_graph.core.graph.Vertex;

import java.util.ArrayList;

/**
 * Created by Jens on 01.07.2017.
 */
public abstract class StrategyMan implements Strategy {

    protected CoreController coreController;
    protected Man man;
    protected GraphHelper helper;
    protected CoreController.ManStrategy strategyEnum;

    public StrategyMan(CoreController coreController, CoreController.ManStrategy strategyEnum) {
        this.coreController = coreController;
        this.helper = GraphHelper.createGraphHelper(coreController);
        this.strategyEnum = strategyEnum;
    }

    @Override
    public Vertex getNextPosition() {

        for (Vertex vertex : calculatePossibleSteps()) {
            if (vertexIsValidStep(vertex)) {
                return vertex;
            }
        }

        //fallback
        return man.getCurrentPosition();
    }

    public boolean vertexIsValidStep(Vertex vertex) {

        if(this.coreController.isLionOnVertex(vertex.getCoordinates())){
            return false;
        }

        if (man.getCurrentPosition().equals(vertex)) {
            return true;
        }

        boolean isValidVertex = false;
        for (Connection neighborConnection : man.getCurrentPosition().getConnections())
            if (neighborConnection.getNeighbor(man.getCurrentPosition()).equals(vertex)) {
                isValidVertex = true;
            }

        if (!isValidVertex) {
            return false;
        }


        for (Man otherMan : coreController.getMen()) {
            if (!otherMan.equals(man)) {
                if (man.keepDistanceExact()) {
                    if (man.getDistance() == helper.getDistanceBetween(vertex, otherMan.getCurrentPosition())) {
                        return true;
                    }
                } else {
                    if (man.getDistance() < helper.getDistanceBetween(vertex, otherMan.getCurrentPosition())) {
                        return true;
                    }
                }
            }
        }

        if (coreController.getMen().size() < 2) {
            return true;
        }
        return false;
    }

    protected abstract ArrayList<Vertex> calculatePossibleSteps();

    public void setMan(Man man) {
        this.man = man;
    }

    public String getName(){
        return strategyEnum.name();
    }
}
