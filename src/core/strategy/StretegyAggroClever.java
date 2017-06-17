package core.strategy;

import core.entities.Entity;
import core.entities.Lion;
import core.entities.Man;
import core.graph.GraphHelper;
import core.graph.Position;

import java.util.ArrayList;

/**
 * Created by Jens on 08.06.2017.
 */
public class StretegyAggroClever implements Strategy {


    @Override
    public Position getNextPosition(Entity e, ArrayList<Man> men, ArrayList<Lion> lions) {

        GraphHelper helper = GraphHelper.createGraphHelper();

        Position currentPosition = e.getCurrentPosition();

        int bestSteps = helper.bestBFS(currentPosition, false, Man.class, Lion.class);

        for(Position neighborPosition : currentPosition.getAllNeighborPositions()){
            int steps = helper.BFS(currentPosition, neighborPosition, Man.class, Lion.class);
            if(bestSteps == steps){
                if(neighborPosition.getAllEntities().size() > 0){
                    if(!(neighborPosition.getAllEntities().get(0) instanceof Lion)){
                        return neighborPosition;
                    }
                }else{
                    return neighborPosition;
                }
            }
        }


        return currentPosition;
    }
}
