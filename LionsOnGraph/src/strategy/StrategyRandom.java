package strategy;

import entities.Entity;
import entities.Lion;
import entities.Man;
import graph.Edge;
import graph.Vertex;
import util.Random;

import java.util.ArrayList;

public class StrategyRandom implements Strategy {
    @Override
    public Vertex getNextPosition(Entity e, ArrayList<Man> men, ArrayList<Lion> lions) {
        ArrayList<Edge> neighborPositions = e.getCurrentPosition().getEdges();
        int rndInt = Random.getRandomInteger(neighborPositions.size());
        return neighborPositions.get(rndInt).getNeighbor(e.getCurrentPosition());
    }
//    @Override
//    public Position getNextPosition(Entity e, ArrayList<Man> men, ArrayList<Lion> lions) {
//        ArrayList<Position> neighborPositions = e.getCurrentPosition().getAllNeighborPositions();
//        int rndInt = Random.getRandomInteger(neighborPositions.size());
//        return neighborPositions.get(rndInt);
//    }
}
