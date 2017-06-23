package core.entities;

import core.graph.NEWVertex;
import core.strategy.Strategy;
import core.util.Point;

import java.util.ArrayList;

/**
 * Created by Danny on 13.05.2017.
 */
public abstract class Entity {

    //TODO save men and lions in a better way / better place
    protected static ArrayList<Man> men = new ArrayList<>();
    protected static ArrayList<Lion> lions = new ArrayList<>();
    protected NEWVertex position;
    protected Strategy strategy;

    public Entity(NEWVertex startPosition, Strategy strategy) {
        this.position = startPosition;
        this.strategy = strategy;
    }

    public NEWVertex getNextPosition() {
        return strategy.getNextPosition(this, men, lions);
    }

    public NEWVertex goToNextPosition() {
        position = getNextPosition();
        return position;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public void setCurentPosition(NEWVertex currentPosition) {
        this.position = currentPosition;
    }

    public NEWVertex getCurrentPosition() {
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
