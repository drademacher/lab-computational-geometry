package core;

import core.entities.Lion;
import core.entities.Man;
import core.graph.Graph;
import core.strategy.StrategyAggroGreedy;
import core.strategy.StrategyRandom;
import core.strategy.StrategyRunAwayGreedy;
import core.strategy.StretegyAggroClever;
import core.util.Point;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Created by Danny on 17.05.2017.
 */
public class CoreController {
    private Graph graph;
    private State state;
    ArrayList<Lion> lions = new ArrayList<>();
    ArrayList<Man> men = new ArrayList<>();

    public Graph setEmptyGraph() {
        this.graph = new Graph();

        men = new ArrayList<>();
        lions = new ArrayList<>();
        this.state = new State(men, lions);
        return this.graph;
    }


    public Graph setDefaultGraph1() {
        this.graph = new Graph();

        this.graph.registerVertex(new Point(5, 2));
        this.graph.registerVertex(new Point(19, 2));
        this.graph.registerVertex(new Point(22, 14));
        this.graph.registerVertex(new Point(12, 22));
        this.graph.registerVertex(new Point(2, 14));

        this.graph.registerVertex(new Point(12, 4));
        this.graph.registerVertex(new Point(16, 5));
        this.graph.registerVertex(new Point(19, 9));
        this.graph.registerVertex(new Point(19, 13));
        this.graph.registerVertex(new Point(16, 17));
        this.graph.registerVertex(new Point(12, 18));
        this.graph.registerVertex(new Point(8, 17));
        this.graph.registerVertex(new Point(5, 13));
        this.graph.registerVertex(new Point(5, 9));
        this.graph.registerVertex(new Point(8, 5));

        this.graph.registerVertex(new Point(12, 7));
        this.graph.registerVertex(new Point(15, 10));
        this.graph.registerVertex(new Point(14, 14));
        this.graph.registerVertex(new Point(10, 14));
        this.graph.registerVertex(new Point(9, 10));

        this.graph.registerEdge(this.graph.getVertices().get(0), this.graph.getVertices().get(1));
        this.graph.registerEdge(this.graph.getVertices().get(1), this.graph.getVertices().get(2));
        this.graph.registerEdge(this.graph.getVertices().get(2), this.graph.getVertices().get(3));
        this.graph.registerEdge(this.graph.getVertices().get(3), this.graph.getVertices().get(4));
        this.graph.registerEdge(this.graph.getVertices().get(4), this.graph.getVertices().get(0));

        this.graph.registerEdge(this.graph.getVertices().get(0), this.graph.getVertices().get(14));
        this.graph.registerEdge(this.graph.getVertices().get(1), this.graph.getVertices().get(6));
        this.graph.registerEdge(this.graph.getVertices().get(2), this.graph.getVertices().get(8));
        this.graph.registerEdge(this.graph.getVertices().get(3), this.graph.getVertices().get(10));
        this.graph.registerEdge(this.graph.getVertices().get(4), this.graph.getVertices().get(12));

        this.graph.registerEdge(this.graph.getVertices().get(5), this.graph.getVertices().get(6));
        this.graph.registerEdge(this.graph.getVertices().get(6), this.graph.getVertices().get(7));
        this.graph.registerEdge(this.graph.getVertices().get(7), this.graph.getVertices().get(8));
        this.graph.registerEdge(this.graph.getVertices().get(8), this.graph.getVertices().get(9));
        this.graph.registerEdge(this.graph.getVertices().get(9), this.graph.getVertices().get(10));
        this.graph.registerEdge(this.graph.getVertices().get(10), this.graph.getVertices().get(11));
        this.graph.registerEdge(this.graph.getVertices().get(11), this.graph.getVertices().get(12));
        this.graph.registerEdge(this.graph.getVertices().get(12), this.graph.getVertices().get(13));
        this.graph.registerEdge(this.graph.getVertices().get(13), this.graph.getVertices().get(14));
        this.graph.registerEdge(this.graph.getVertices().get(14), this.graph.getVertices().get(5));

        this.graph.registerEdge(this.graph.getVertices().get(5), this.graph.getVertices().get(15));
        this.graph.registerEdge(this.graph.getVertices().get(7), this.graph.getVertices().get(16));
        this.graph.registerEdge(this.graph.getVertices().get(9), this.graph.getVertices().get(17));
        this.graph.registerEdge(this.graph.getVertices().get(11), this.graph.getVertices().get(18));
        this.graph.registerEdge(this.graph.getVertices().get(13), this.graph.getVertices().get(19));

        this.graph.registerEdge(this.graph.getVertices().get(15), this.graph.getVertices().get(16));
        this.graph.registerEdge(this.graph.getVertices().get(16), this.graph.getVertices().get(17));
        this.graph.registerEdge(this.graph.getVertices().get(17), this.graph.getVertices().get(18));
        this.graph.registerEdge(this.graph.getVertices().get(18), this.graph.getVertices().get(19));
        this.graph.registerEdge(this.graph.getVertices().get(19), this.graph.getVertices().get(15));

        men = new ArrayList<>();
        lions = new ArrayList<>();
        men.add(new Man(this.graph.getVertices().get(0).getPosition(), new StrategyRunAwayGreedy()));
        lions.add(new Lion(this.graph.getVertices().get(1).getPosition(), new StrategyAggroGreedy()));
        lions.add(new Lion(this.graph.getVertices().get(18).getPosition(), new StrategyAggroGreedy()));
        lions.add(new Lion(this.graph.getVertices().get(13).getPosition(), new StrategyAggroGreedy()));

        this.state = new State(men, lions);


        return this.graph;
    }


    public Graph setDefaultGraph2() {
        this.graph = new Graph();

        this.graph.registerVertex(new Point(5, 2));
        this.graph.registerVertex(new Point(19, 2));
        this.graph.registerVertex(new Point(22, 14));
        this.graph.registerVertex(new Point(12, 22));
        this.graph.registerVertex(new Point(2, 14));

        this.graph.registerVertex(new Point(12, 4));
        this.graph.registerVertex(new Point(16, 5));
        this.graph.registerVertex(new Point(19, 9));
        this.graph.registerVertex(new Point(19, 13));
        this.graph.registerVertex(new Point(16, 17));
        this.graph.registerVertex(new Point(12, 18));
        this.graph.registerVertex(new Point(8, 17));
        this.graph.registerVertex(new Point(5, 13));
        this.graph.registerVertex(new Point(5, 9));
        this.graph.registerVertex(new Point(8, 5));

        this.graph.registerVertex(new Point(12, 7));
        this.graph.registerVertex(new Point(15, 10));
        this.graph.registerVertex(new Point(14, 14));
        this.graph.registerVertex(new Point(10, 14));
        this.graph.registerVertex(new Point(9, 10));

        this.graph.registerEdge(this.graph.getVertices().get(0), this.graph.getVertices().get(1));
        this.graph.registerEdge(this.graph.getVertices().get(1), this.graph.getVertices().get(2));
        this.graph.registerEdge(this.graph.getVertices().get(2), this.graph.getVertices().get(3));
        this.graph.registerEdge(this.graph.getVertices().get(3), this.graph.getVertices().get(4));
        this.graph.registerEdge(this.graph.getVertices().get(4), this.graph.getVertices().get(0));

        this.graph.registerEdge(this.graph.getVertices().get(0), this.graph.getVertices().get(14));
        this.graph.registerEdge(this.graph.getVertices().get(1), this.graph.getVertices().get(6));
        this.graph.registerEdge(this.graph.getVertices().get(2), this.graph.getVertices().get(8));
        this.graph.registerEdge(this.graph.getVertices().get(3), this.graph.getVertices().get(10));
        this.graph.registerEdge(this.graph.getVertices().get(4), this.graph.getVertices().get(12));

        this.graph.registerEdge(this.graph.getVertices().get(5), this.graph.getVertices().get(6));
        this.graph.registerEdge(this.graph.getVertices().get(6), this.graph.getVertices().get(7));
        this.graph.registerEdge(this.graph.getVertices().get(7), this.graph.getVertices().get(8));
        this.graph.registerEdge(this.graph.getVertices().get(8), this.graph.getVertices().get(9));
        this.graph.registerEdge(this.graph.getVertices().get(9), this.graph.getVertices().get(10));
        this.graph.registerEdge(this.graph.getVertices().get(10), this.graph.getVertices().get(11));
        this.graph.registerEdge(this.graph.getVertices().get(11), this.graph.getVertices().get(12));
        this.graph.registerEdge(this.graph.getVertices().get(12), this.graph.getVertices().get(13));
        this.graph.registerEdge(this.graph.getVertices().get(13), this.graph.getVertices().get(14));
        this.graph.registerEdge(this.graph.getVertices().get(14), this.graph.getVertices().get(5));

        this.graph.registerEdge(this.graph.getVertices().get(5), this.graph.getVertices().get(15));
        this.graph.registerEdge(this.graph.getVertices().get(7), this.graph.getVertices().get(16));
        this.graph.registerEdge(this.graph.getVertices().get(9), this.graph.getVertices().get(17));
        this.graph.registerEdge(this.graph.getVertices().get(11), this.graph.getVertices().get(18));
        this.graph.registerEdge(this.graph.getVertices().get(13), this.graph.getVertices().get(19));

        this.graph.registerEdge(this.graph.getVertices().get(15), this.graph.getVertices().get(16));
        this.graph.registerEdge(this.graph.getVertices().get(16), this.graph.getVertices().get(17));
        this.graph.registerEdge(this.graph.getVertices().get(17), this.graph.getVertices().get(18));
        this.graph.registerEdge(this.graph.getVertices().get(18), this.graph.getVertices().get(19));
        this.graph.registerEdge(this.graph.getVertices().get(19), this.graph.getVertices().get(15));

        men = new ArrayList<>();
        lions = new ArrayList<>();
        men.add(new Man(this.graph.getVertices().get(0).getPosition(), new StrategyRunAwayGreedy()));
        lions.add(new Lion(this.graph.getVertices().get(1).getPosition(), new StretegyAggroClever()));
        lions.add(new Lion(this.graph.getVertices().get(18).getPosition(), new StretegyAggroClever()));
        lions.add(new Lion(this.graph.getVertices().get(13).getPosition(), new StretegyAggroClever()));

        this.state = new State(men, lions);


        return this.graph;
    }

    public Graph setDefaultGraph3() {
        this.graph = new Graph();
        // this.graph.registerVertex(new Point(5, 5));
        this.graph.registerVertex(new Point(19, 9));
        this.graph.registerVertex(new Point(9, 4));
        this.graph.registerEdge(this.graph.getVertices().get(0), this.graph.getVertices().get(1));

        men = new ArrayList<>();
        lions = new ArrayList<>();
        men.add(new Man(this.graph.getVertices().get(0).getPosition(), new StrategyRandom()));
        lions.add(new Lion(this.graph.getVertices().get(1).getPosition(), new StrategyRandom()));

        this.state = new State(men, lions);

        return this.graph;
    }

    public Graph setDefaultGraph4() {
        return this.setDefaultGraph1();
    }

    public Graph setDefaultGraph5() {
        return this.setDefaultGraph1();
    }


    public Graph setRandomGraph() {
        // TODO: implement random graph algorithm
        return this.setDefaultGraph1();
    }


    public Graph setGraphFromFile(File file) throws Exception {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            br.readLine();  //Skip type
            int yDim = Integer.valueOf(br.readLine().substring(7));  //Read height
            int xDim = Integer.valueOf(br.readLine().substring(6));  //Read width
            this.graph = new Graph(); //init Map without passable fields
            br.readLine();  //Skip map
            String currentLine;
            for (int y = 0; (currentLine = br.readLine()) != null; y++) { //Read in MapRow
                for (int x = 0; x < currentLine.length(); x++) {
//                    if (currentLine.charAt(x) == '.' || currentLine.charAt(x) == 'G' || currentLine.charAt(x) == 'S') {
//                        map.switchPassable(new Vector(x, y));    //Mark passable fields
//                    }

                    // TODO: do something nice with the file input
                }
            }
            return graph;
        } catch (Exception e) {
            // TODO: search up the right exception type
            throw new Exception("test");
        }
    }

    public void saveGraphToFile(File file) {
        // TODO: implement file saving
    }

    public State simulateStep() {
        for(Man man : men){
            man.goToNextPosition();
        }
        for(Lion lion : lions){
            lion.goToNextPosition();
        }
        this.state = new State(men, lions);
        return this.state;
    }


    public State getState() {
        return state;
    }

}
