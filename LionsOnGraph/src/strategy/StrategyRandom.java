package strategy;

import entities.Entity;
import entities.Lion;
import entities.Man;
import graph.Edge;
import graph.GraphController;
import graph.Vertex;
import util.Random;

import java.util.ArrayList;

public class StrategyRandom implements Strategy {


    @Override
    public Vertex getNextPosition(GraphController graphController, Entity e) {
        ArrayList<Edge> neighborPositions = e.getCurrentPosition().getEdges();
        if(neighborPositions.size() > 0){
            int rndInt = Random.getRandomInteger(neighborPositions.size());
            return neighborPositions.get(rndInt).getNeighbor(e.getCurrentPosition());
        } else{
            return e.getCurrentPosition();
        }
    }
}
