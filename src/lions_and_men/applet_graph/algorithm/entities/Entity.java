package lions_and_men.applet_graph.algorithm.entities;

import lions_and_men.applet_graph.algorithm.CoreController;
import lions_and_men.applet_graph.algorithm.graph.Vertex;
import lions_and_men.util.Point;

public abstract class Entity {


    protected Vertex position;
    protected CoreController coreController;
    protected Vertex nextPosition;
    protected boolean didManualStep = false;

    public Entity(Vertex startPosition, CoreController coreController) {
        this.position = startPosition;
        this.coreController = coreController;
    }

    protected abstract Vertex calculateNextPosition();

    public Vertex goToNextPosition() {
        position = getNextPosition();
        nextPosition = null;
        didManualStep = false;
        return position;
    }

    public Vertex getCurrentPosition() {
        return position;
    }

    public void setCurrentPosition(Vertex vertex) {
        this.position = vertex;
        this.nextPosition = null;
    }

    public Vertex getNextPosition() {
        if (nextPosition == null || !didManualStep) {
            nextPosition = calculateNextPosition();
        }
        return nextPosition;
    }

    public abstract void setNextPosition(Vertex nextposition);

    public Vertex getCalculatedPosition() {
        return nextPosition;
    }

    public Point getCoordinates() {
        return position.getCoordinates();
    }

    @Override
    public String toString() {
        return "Entity @ " + position;
    }

    public boolean didManualStep() {
        return didManualStep;
    }

    public abstract boolean needManualStepInput();

    public abstract boolean vertexIsValidStep(Vertex vertex);
}
