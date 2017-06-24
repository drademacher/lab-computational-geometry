package strategy;

import entities.Entity;
import entities.Lion;
import entities.Man;
import graph.Edge;
import graph.Vertex;
import util.Random;

import java.util.ArrayList;

public class StretegyAggroClever implements Strategy {

    @Override
    public Vertex getNextPosition(Entity e, ArrayList<Man> men, ArrayList<Lion> lions) {
        //TODO implement (at the moment random)
        ArrayList<Edge> neighborPositions = e.getCurrentPosition().getEdges();
        int rndInt = Random.getRandomInteger(neighborPositions.size());
        return neighborPositions.get(rndInt).getNeighbor(e.getCurrentPosition());
    }
//    @Override
//    public Position getNextPosition(Entity e, ArrayList<Man> men, ArrayList<Lion> lions) {
//
//        GraphHelper helper = GraphHelper.createGraphHelper();
//
//        Position currentPosition = e.getCurrentPosition();
//
//        int bestSteps = helper.bestBFS(currentPosition, false, Man.class, Lion.class);
//
//        for (Position neighborPosition : currentPosition.getAllNeighborPositions()) {
//            int steps = helper.BFS(currentPosition, neighborPosition, Man.class, Lion.class);
//            if (bestSteps == steps) {
//                if (neighborPosition.getAllEntities().size() > 0) {
//                    if (!(neighborPosition.getAllEntities().get(0) instanceof Lion)) {
//                        return neighborPosition;
//                    }
//                } else {
//                    return neighborPosition;
//                }
//            }
//        }
//
//
//        return currentPosition;
//    }
}
