package core;

import core.entities.Lion;
import core.entities.Man;
import core.graph.NEWBigVertex;
import core.graph.NEWGraph;
import core.graph.NEWGraphController;
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
    private NEWGraphController graphController;
    private State state;

    public NEWGraphController setEmptyGraph() {
        this.graphController = new NEWGraphController();

        this.state = new State(this.graphController.getMen(), this.graphController.getLions());
        return this.graphController;
    }


    public NEWGraphController setDefaultGraph1() {
        this.graphController = new NEWGraphController();

        this.graphController.createVertex(new Point(50, 20));
        this.graphController.createVertex(new Point(190, 20));
        this.graphController.createVertex(new Point(220, 140));
        this.graphController.createVertex(new Point(120, 220));
        this.graphController.createVertex(new Point(20, 140));

        this.graphController.createVertex(new Point(120, 40));
        this.graphController.createVertex(new Point(160, 50));
        this.graphController.createVertex(new Point(190, 90));
        this.graphController.createVertex(new Point(190, 130));
        this.graphController.createVertex(new Point(160, 170));
        this.graphController.createVertex(new Point(120, 180));
        this.graphController.createVertex(new Point(80, 170));
        this.graphController.createVertex(new Point(50, 130));
        this.graphController.createVertex(new Point(50, 90));
        this.graphController.createVertex(new Point(80, 50));

        this.graphController.createVertex(new Point(120, 70));
        this.graphController.createVertex(new Point(150, 100));
        this.graphController.createVertex(new Point(140, 140));
        this.graphController.createVertex(new Point(100, 140));
        this.graphController.createVertex(new Point(90, 100));

        this.graphController.createEdge(this.graphController.getBigVertexById(0), this.graphController.getBigVertexById(1));
        this.graphController.createEdge(this.graphController.getBigVertexById(1), this.graphController.getBigVertexById(2));
        this.graphController.createEdge(this.graphController.getBigVertexById(2), this.graphController.getBigVertexById(3));
        this.graphController.createEdge(this.graphController.getBigVertexById(3), this.graphController.getBigVertexById(4));
        this.graphController.createEdge(this.graphController.getBigVertexById(4), this.graphController.getBigVertexById(0));

        this.graphController.createEdge(this.graphController.getBigVertexById(0), this.graphController.getBigVertexById(14));
        this.graphController.createEdge(this.graphController.getBigVertexById(1), this.graphController.getBigVertexById(6));
        this.graphController.createEdge(this.graphController.getBigVertexById(2), this.graphController.getBigVertexById(8));
        this.graphController.createEdge(this.graphController.getBigVertexById(3), this.graphController.getBigVertexById(10));
        this.graphController.createEdge(this.graphController.getBigVertexById(4), this.graphController.getBigVertexById(12));

        this.graphController.createEdge(this.graphController.getBigVertexById(5), this.graphController.getBigVertexById(6));
        this.graphController.createEdge(this.graphController.getBigVertexById(6), this.graphController.getBigVertexById(7));
        this.graphController.createEdge(this.graphController.getBigVertexById(7), this.graphController.getBigVertexById(8));
        this.graphController.createEdge(this.graphController.getBigVertexById(8), this.graphController.getBigVertexById(9));
        this.graphController.createEdge(this.graphController.getBigVertexById(9), this.graphController.getBigVertexById(10));
        this.graphController.createEdge(this.graphController.getBigVertexById(10), this.graphController.getBigVertexById(11));
        this.graphController.createEdge(this.graphController.getBigVertexById(11), this.graphController.getBigVertexById(12));
        this.graphController.createEdge(this.graphController.getBigVertexById(12), this.graphController.getBigVertexById(13));
        this.graphController.createEdge(this.graphController.getBigVertexById(13), this.graphController.getBigVertexById(14));
        this.graphController.createEdge(this.graphController.getBigVertexById(14), this.graphController.getBigVertexById(15));

        this.graphController.createEdge(this.graphController.getBigVertexById(5), this.graphController.getBigVertexById(15));
        this.graphController.createEdge(this.graphController.getBigVertexById(7), this.graphController.getBigVertexById(16));
        this.graphController.createEdge(this.graphController.getBigVertexById(9), this.graphController.getBigVertexById(17));
        this.graphController.createEdge(this.graphController.getBigVertexById(11), this.graphController.getBigVertexById(18));
        this.graphController.createEdge(this.graphController.getBigVertexById(13), this.graphController.getBigVertexById(19));

        this.graphController.createEdge(this.graphController.getBigVertexById(15), this.graphController.getBigVertexById(16));
        this.graphController.createEdge(this.graphController.getBigVertexById(16), this.graphController.getBigVertexById(17));
        this.graphController.createEdge(this.graphController.getBigVertexById(17), this.graphController.getBigVertexById(18));
        this.graphController.createEdge(this.graphController.getBigVertexById(18), this.graphController.getBigVertexById(19));
        this.graphController.createEdge(this.graphController.getBigVertexById(19), this.graphController.getBigVertexById(15));

        this.graphController.setMan(new Man(this.graphController.getBigVertexById(0), new StrategyRunAwayGreedy()));
        this.graphController.setLion(new Lion(this.graphController.getBigVertexById(1), new StrategyAggroGreedy()));
        this.graphController.setLion(new Lion(this.graphController.getBigVertexById(18), new StrategyAggroGreedy()));
        this.graphController.setLion(new Lion(this.graphController.getBigVertexById(13), new StrategyAggroGreedy()));

        this.state = new State(this.graphController.getMen(), this.graphController.getLions());


        return this.graphController;
    }


    public NEWGraphController setDefaultGraph2() {
        this.graphController = new NEWGraphController();

        this.graphController.createVertex(new Point(50, 20));
        this.graphController.createVertex(new Point(190, 20));
        this.graphController.createVertex(new Point(220, 140));
        this.graphController.createVertex(new Point(120, 220));
        this.graphController.createVertex(new Point(20, 140));

        this.graphController.createVertex(new Point(120, 40));
        this.graphController.createVertex(new Point(160, 50));
        this.graphController.createVertex(new Point(190, 90));
        this.graphController.createVertex(new Point(190, 130));
        this.graphController.createVertex(new Point(160, 170));
        this.graphController.createVertex(new Point(120, 180));
        this.graphController.createVertex(new Point(80, 170));
        this.graphController.createVertex(new Point(50, 130));
        this.graphController.createVertex(new Point(50, 90));
        this.graphController.createVertex(new Point(80, 50));

        this.graphController.createVertex(new Point(120, 70));
        this.graphController.createVertex(new Point(150, 100));
        this.graphController.createVertex(new Point(140, 140));
        this.graphController.createVertex(new Point(100, 140));
        this.graphController.createVertex(new Point(90, 100));

        this.graphController.createEdge(this.graphController.getBigVertexById(0), this.graphController.getBigVertexById(1));
        this.graphController.createEdge(this.graphController.getBigVertexById(1), this.graphController.getBigVertexById(2));
        this.graphController.createEdge(this.graphController.getBigVertexById(2), this.graphController.getBigVertexById(3));
        this.graphController.createEdge(this.graphController.getBigVertexById(3), this.graphController.getBigVertexById(4));
        this.graphController.createEdge(this.graphController.getBigVertexById(4), this.graphController.getBigVertexById(0));

        this.graphController.createEdge(this.graphController.getBigVertexById(0), this.graphController.getBigVertexById(14));
        this.graphController.createEdge(this.graphController.getBigVertexById(1), this.graphController.getBigVertexById(6));
        this.graphController.createEdge(this.graphController.getBigVertexById(2), this.graphController.getBigVertexById(8));
        this.graphController.createEdge(this.graphController.getBigVertexById(3), this.graphController.getBigVertexById(10));
        this.graphController.createEdge(this.graphController.getBigVertexById(4), this.graphController.getBigVertexById(12));

        this.graphController.createEdge(this.graphController.getBigVertexById(5), this.graphController.getBigVertexById(6));
        this.graphController.createEdge(this.graphController.getBigVertexById(6), this.graphController.getBigVertexById(7));
        this.graphController.createEdge(this.graphController.getBigVertexById(7), this.graphController.getBigVertexById(8));
        this.graphController.createEdge(this.graphController.getBigVertexById(8), this.graphController.getBigVertexById(9));
        this.graphController.createEdge(this.graphController.getBigVertexById(9), this.graphController.getBigVertexById(10));
        this.graphController.createEdge(this.graphController.getBigVertexById(10), this.graphController.getBigVertexById(11));
        this.graphController.createEdge(this.graphController.getBigVertexById(11), this.graphController.getBigVertexById(12));
        this.graphController.createEdge(this.graphController.getBigVertexById(12), this.graphController.getBigVertexById(13));
        this.graphController.createEdge(this.graphController.getBigVertexById(13), this.graphController.getBigVertexById(14));
        this.graphController.createEdge(this.graphController.getBigVertexById(14), this.graphController.getBigVertexById(15));

        this.graphController.createEdge(this.graphController.getBigVertexById(5), this.graphController.getBigVertexById(15));
        this.graphController.createEdge(this.graphController.getBigVertexById(7), this.graphController.getBigVertexById(16));
        this.graphController.createEdge(this.graphController.getBigVertexById(9), this.graphController.getBigVertexById(17));
        this.graphController.createEdge(this.graphController.getBigVertexById(11), this.graphController.getBigVertexById(18));
        this.graphController.createEdge(this.graphController.getBigVertexById(13), this.graphController.getBigVertexById(19));

        this.graphController.createEdge(this.graphController.getBigVertexById(15), this.graphController.getBigVertexById(16));
        this.graphController.createEdge(this.graphController.getBigVertexById(16), this.graphController.getBigVertexById(17));
        this.graphController.createEdge(this.graphController.getBigVertexById(17), this.graphController.getBigVertexById(18));
        this.graphController.createEdge(this.graphController.getBigVertexById(18), this.graphController.getBigVertexById(19));
        this.graphController.createEdge(this.graphController.getBigVertexById(19), this.graphController.getBigVertexById(15));

        this.graphController.setMan(new Man(this.graphController.getBigVertexById(0), new StrategyRunAwayGreedy()));
        this.graphController.setLion(new Lion(this.graphController.getBigVertexById(1), new StretegyAggroClever()));
        this.graphController.setLion(new Lion(this.graphController.getBigVertexById(18), new StretegyAggroClever()));
        this.graphController.setLion(new Lion(this.graphController.getBigVertexById(13), new StretegyAggroClever()));

        this.state = new State(this.graphController.getMen(), this.graphController.getLions());

        return this.graphController;
    }

    public NEWGraphController setDefaultGraph3() {
        this.graphController = new NEWGraphController();

        // this.graphController.createVertex(new Point(5, 5));
        this.graphController.createVertex(new Point(19, 9));
        this.graphController.createVertex(new Point(9, 4));
        this.graphController.createEdge(this.graphController.getBigVertexById(0), this.graphController.getBigVertexById(1));

        this.graphController.setMan(new Man(this.graphController.getBigVertexById(0), new StrategyRandom()));
        this.graphController.setLion(new Lion(this.graphController.getBigVertexById(1), new StrategyRandom()));

        this.state = new State(this.graphController.getMen(), this.graphController.getLions());

        return this.graphController;
    }

    public NEWGraphController setDefaultGraph4() {
        return this.setDefaultGraph1();
    }

    public NEWGraphController setDefaultGraph5() {


        this.graphController = new NEWGraphController();

        graphController.createVertex(new Point(10, 10));
        graphController.createVertex(new Point(80, 40));

        graphController.createEdge(this.graphController.getBigVertexById(0), this.graphController.getBigVertexById(1), 4);

        graphController.createVertex(new Point(20, 100));

        graphController.relocateVertex(this.graphController.getBigVertexById(0), new Point(0, -100));


        System.out.println(graphController.debugGraph());

        graphController.createEdge(this.graphController.getBigVertexById(0), this.graphController.getBigVertexById(2), 2);
        graphController.removeEdge(this.graphController.getBigVertexById(0),this.graphController.getBigVertexById(1));


        System.out.println("#############################");
        System.out.println(graphController.debugGraph());



        return this.setDefaultGraph1();
    }


    public NEWGraphController setRandomGraph() {
        // TODO: implement random graph algorithm
        return this.setDefaultGraph1();
    }


    public NEWGraphController setGraphFromFile(File file) throws Exception {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            br.readLine();  //Skip type
            int yDim = Integer.valueOf(br.readLine().substring(7));  //Read height
            int xDim = Integer.valueOf(br.readLine().substring(6));  //Read width
//            this.graph = new Graph(); //init Map without passable fields
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
            return graphController;
        } catch (Exception e) {
            // TODO: search up the right exception type
            throw new Exception("test");
        }
    }

    public void saveGraphToFile(File file) {
        // TODO: implement file saving
    }

    public State simulateStep() {
        for (Man man : this.graphController.getMen()) {
            man.goToNextPosition();
        }
        for (Lion lion : this.graphController.getLions()) {
            lion.goToNextPosition();
        }
        this.state = new State(this.graphController.getMen(), this.graphController.getLions());
        return this.state;
    }


    public State getState() {
        return state;
    }

}
