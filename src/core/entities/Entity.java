package core.entities;

import core.graph.GraphPosition;
import core.graph.Position;
import core.strategy.Strategy;

import java.util.ArrayList;

/**
 * Created by Danny on 13.05.2017.
 */
public abstract class Entity {

    protected Position position;
    protected Position newposition;
    protected Strategy strategy;

    //TODO save men and lions in a better way / better place
    protected static ArrayList<Man> men = new ArrayList<>();
    protected static ArrayList<Lion> lions = new ArrayList<>();

    public Entity(Position startPosition, Strategy strategy){
        this.position = startPosition;
        this.strategy = strategy;
        this.position.registerEntity(this);
    }

    public Position getNextPosition() {
        return strategy.getNextPosition(this, men, lions);
    }

    public Position goToNextPosition(){
        newposition = getNextPosition();
        position.unregisterEntity(this);
        position = newposition;
        position.registerEntity(this);
        return position;
    }

    public void setStrategy(Strategy strategy){
        this.strategy = strategy;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setCurentPosition(Position currentPosition){
        this.position = currentPosition;
    }

    public Position getCurrentPosition(){
        return position;
    }

    public GraphPosition getCurrentGraphPosition(){
        return position.getGraphPosition();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
