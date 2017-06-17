package core.strategy;

import core.entities.Entity;
import core.entities.Lion;
import core.entities.Man;
import core.graph.GraphHelper;
import core.graph.Position;

import java.util.ArrayList;

/**
 * Created by Jens on 25.05.2017.
 */
public class StrategyRunAwayGreedy implements Strategy{
    @Override
    public Position getNextPosition(Entity e, ArrayList<Man> men, ArrayList<Lion> lions) {
        GraphHelper helper = GraphHelper.createGraphHelper();

        Position currentPosition = e.getCurrentPosition();

        int bestSteps = helper.bestBFS(currentPosition, true);

        for(Position neighborPosition : currentPosition.getAllNeighborPositions()){
            int steps = helper.BFS(currentPosition, neighborPosition);
            if(bestSteps == steps){
                return neighborPosition;
            }
        }


        return null;
    }
}
