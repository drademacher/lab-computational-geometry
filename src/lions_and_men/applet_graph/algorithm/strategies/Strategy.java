package lions_and_men.applet_graph.algorithm.strategies;

import lions_and_men.applet_graph.algorithm.CoreController;
import lions_and_men.applet_graph.algorithm.entities.Entity;
import lions_and_men.applet_graph.algorithm.graph.GraphHelper;
import lions_and_men.applet_graph.algorithm.graph.Vertex;

import java.util.ArrayList;

/**
 * Abstract Class for Lion and Man strategies
 */
public abstract class Strategy<T extends Entity> implements Cloneable {
    protected CoreController coreController;
    protected T entity;
    GraphHelper helper;

    public Strategy(CoreController coreController) {
        this.coreController = coreController;
        this.helper = GraphHelper.createGraphHelper(coreController);
    }

    public Vertex getNextPosition() {
        for (Vertex vertex : calculatePossibleSteps()) {
            if (entity.vertexIsValidStep(vertex)) {
                return vertex;
            }
        }

        //fallback
        return entity.getCurrentPosition();
    }

    protected abstract ArrayList<Vertex> calculatePossibleSteps();

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public String getName() {
        int lastOccurrenceOfPoint = this.getClass().getName().lastIndexOf(".");
        return this.getClass().getName().substring(lastOccurrenceOfPoint + 1);
    }

    public void reset() {

    }
}
