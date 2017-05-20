package core.entities;

import core.graph.Position;
import core.strategy.Strategy;

/**
 * Created by Danny on 13.05.2017.
 */
public abstract class Entity {

    protected Position position;
    protected Strategy strategy;

    public Entity(Position startPosition, Strategy strategy){
        this.position = startPosition;
        this.strategy = strategy;
        this.position.registerEntity(this);
    }

    public Position getNextPosition() {
        return strategy.getNextPosition(this);
    }

    public Position goToNextPosition(){
        position.unregisterEntity(this);
        position = getNextPosition();
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

    @Override
    public String toString() {
        return super.toString();
    }
}
