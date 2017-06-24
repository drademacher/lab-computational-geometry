package strategy;

import entities.Entity;
import entities.Lion;
import entities.Man;
import graph.Edge;
import graph.Vertex;
import util.Random;

import java.util.ArrayList;

public class StrategyAggroGreedy implements Strategy {
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
//        int bestSteps = helper.bestBFS(currentPosition);
//
//        for (Position neighborPosition : currentPosition.getAllNeighborPositions()) {
//            int steps = helper.BFS(currentPosition, neighborPosition);
//            if (bestSteps == steps) {
//                return neighborPosition;
//            }
//        }
//
//
//        return null;
//    }
}
