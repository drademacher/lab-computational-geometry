package strategy;

import entities.Entity;
import entities.Lion;
import entities.Man;
import graph.Edge;
import graph.GraphController;
import graph.GraphHelper;
import graph.Vertex;
import util.Random;

import java.util.ArrayList;

public class StrategyAggroGreedy implements Strategy {
    @Override
    public Vertex getNextPosition(GraphController graphController, Entity e) {

        System.out.println("AggroGreedy Strategy from "+e);

        GraphHelper helper = GraphHelper.createGraphHelper(graphController);

        Vertex currentPosition = e.getCurrentPosition();

        int steps = Integer.MAX_VALUE;
        Vertex bestNextPosition = null;
        for (Edge edge : currentPosition.getEdges()) {
            Vertex possibleVertex = edge.getNeighbor(currentPosition);

            int calculatedSteps = helper.BFSToMen(currentPosition, possibleVertex);
            System.out.println("steps "+steps+"  vs.  calcedStels: "+calculatedSteps);
            if (calculatedSteps < steps) {
                steps = calculatedSteps;
                bestNextPosition = possibleVertex;
            }
        }

        if(bestNextPosition != null){
            System.out.println("return "+bestNextPosition);
            return bestNextPosition;
        } else{
            System.out.println("return current "+currentPosition);
            return currentPosition;
        }

    }

}
