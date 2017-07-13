package lions_on_graph.core.entities;

import lions_on_graph.core.CoreController;
import lions_on_graph.core.graph.Vertex;
import util.Point;

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

    public void setCurrentPosition(Vertex vertex) {
        this.position = vertex;
        this.nextPosition = null;
    }

    public abstract void setNextPosition(Vertex nextposition);

    public Vertex getCurrentPosition() {
        return position;
    }

    public Vertex getNextPosition() {
        if (nextPosition == null) {
            nextPosition = calculateNextPosition();
        }
        return nextPosition;
    }

    public Point getCoordinates() {
        return position.getCoordinates();
    }

    @Override
    public String toString() {
        return "Entity @ " + position;
    }

    public boolean didManualStep(){
        return didManualStep;
    }

    public abstract boolean needManualStepInput();
}
