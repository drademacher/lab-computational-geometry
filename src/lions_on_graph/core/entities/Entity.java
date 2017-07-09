package lions_on_graph.core.entities;

import lions_on_graph.core.CoreController;
import lions_on_graph.core.graph.Vertex;
import lions_on_graph.core.strategies.Strategy;
import util.Point;

public abstract class Entity {


    protected Vertex position;
    protected CoreController coreController;

    public Entity(Vertex startPosition, CoreController coreController) {
        this.position = startPosition;
        this.coreController = coreController;
    }

    public abstract Vertex getNextPosition();

    public Vertex goToNextPosition() {
        position = getNextPosition();
        return position;
    }

    public void setPosition(Vertex vertex) {
        this.position = vertex;
    }

    public void setCurentPosition(Vertex currentPosition) {
        this.position = currentPosition;
    }

    public Vertex getCurrentPosition() {
        return position;
    }

    public Point getCoordinates() {
        return position.getCoordinates();
    }

    @Override
    public String toString() {
        return "Entity @ " + position;
    }
}
