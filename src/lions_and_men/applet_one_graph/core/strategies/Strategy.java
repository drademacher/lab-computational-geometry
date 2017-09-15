package lions_and_men.applet_one_graph.core.strategies;

import lions_and_men.applet_one_graph.core.CoreController;
import lions_and_men.applet_one_graph.core.entities.Entity;
import lions_and_men.applet_one_graph.core.graph.GraphHelper;
import lions_and_men.applet_one_graph.core.graph.Vertex;

import java.util.ArrayList;

public abstract class Strategy<T extends Entity> implements Cloneable {
    protected CoreController coreController;
    protected T entity;
    protected GraphHelper helper;
//    protected CoreController.LionStrategy strategyEnum;

    public Strategy(CoreController coreController) {
        this.coreController = coreController;
        this.helper = GraphHelper.createGraphHelper(coreController);
//        this.strategyEnum = strategyEnum;
    }

    public Vertex getNextPosition() {
        for (Vertex vertex : calculatePossibleSteps()) {
            if (vertexIsValidStep(vertex)) {
                return vertex;
            }
        }

        //fallback
        return entity.getCurrentPosition();
    }

    abstract boolean vertexIsValidStep(Vertex vertex);

    protected abstract ArrayList<Vertex> calculatePossibleSteps();

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public abstract String getName();

//    public String getName() {
//        return strategyEnum.name();
//    }

}
