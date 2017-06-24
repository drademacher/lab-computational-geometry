package entities;

import graph.Vertex;
import strategy.Strategy;
import util.Point;

import java.util.ArrayList;

public abstract class Entity {

    //TODO save men and lions in a better way / better place
    protected static ArrayList<Man> men = new ArrayList<>();
    protected static ArrayList<Lion> lions = new ArrayList<>();
    protected Vertex position;
    protected Strategy strategy;

    public Entity(Vertex startPosition, Strategy strategy) {
        this.position = startPosition;
        this.strategy = strategy;
    }

    public Vertex getNextPosition() {
        return strategy.getNextPosition(this, men, lions);
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
        return super.toString();
    }
}
