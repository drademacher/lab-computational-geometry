package entities;

import graph.GraphController;
import graph.Vertex;
import strategy.Strategy;
import util.Point;

public abstract class Entity {


    protected Vertex position;
    protected Strategy strategy;
    protected GraphController graphController;

    public Entity(Vertex startPosition, Strategy strategy, GraphController graphController) {
        this.position = startPosition;
        this.strategy = strategy;
        this.graphController = graphController;
    }

    public Vertex getNextPosition() {
        return strategy.getNextPosition(graphController, this);
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
