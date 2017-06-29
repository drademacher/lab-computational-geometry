package strategy;

import entities.Entity;
import graph.Connection;
import graph.CoreController;
import graph.Vertex;
import util.Random;

import java.util.ArrayList;

public class StrategyRandom implements Strategy {


    @Override
    public Vertex getNextPosition(CoreController coreController, Entity e) {
        ArrayList<Connection> connections = e.getCurrentPosition().getConnections();
        if (connections.size() > 0) {
            int rndInt = Random.getRandomInteger(connections.size());
            return connections.get(rndInt).getNeighbor(e.getCurrentPosition());
        } else {
            return e.getCurrentPosition();
        }
    }
}
