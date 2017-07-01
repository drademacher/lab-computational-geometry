package entities;

import graph.CoreController;
import graph.Vertex;
import strategy.Strategy;
import util.Point;

public abstract class Entity {


    protected Vertex position;
    protected Strategy strategy;
    protected CoreController coreController;

    public Entity(Vertex startPosition, CoreController coreController) {
        this.position = startPosition;
        this.strategy = strategy;
        this.coreController = coreController;
    }

    public Vertex getNextPosition() {
        return strategy.getNextPosition();
    }

    public Vertex goToNextPosition() {
        position = getNextPosition();
        return position;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
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
