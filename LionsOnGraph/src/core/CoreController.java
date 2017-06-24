package core;

import core.entities.Lion;
import core.entities.Man;
import core.graph.graphlogic.GraphController;
import core.strategy.StrategyAggroGreedy;
import core.strategy.StrategyRandom;
import core.strategy.StrategyRunAwayGreedy;
import core.strategy.StretegyAggroClever;
import core.util.Point;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by Danny on 17.05.2017.
 */
public class CoreController {
    public GraphController getGraphController() {
        return graphController;
    }

    private GraphController graphController;
    private State state;

    public GraphController setEmptyGraph() {
        this.graphController = new GraphController();

        this.state = new State(this.graphController.getMen(), this.graphController.getLions());
        return this.graphController;
    }


    public GraphController setDefaultGraph1() {
        this.graphController = new GraphController();

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

        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(50, 20)), this.graphController.getBigVertexByCoordinate(new Point(190, 20)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(190, 20)), this.graphController.getBigVertexByCoordinate(new Point(220, 140)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(220, 140)), this.graphController.getBigVertexByCoordinate(new Point(120, 220)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(120, 220)), this.graphController.getBigVertexByCoordinate(new Point(20, 140)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(20, 140)), this.graphController.getBigVertexByCoordinate(new Point(50, 20)));

        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(50, 20)), this.graphController.getBigVertexByCoordinate(new Point(80, 50)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(190, 20)), this.graphController.getBigVertexByCoordinate(new Point(160, 50)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(220, 140)), this.graphController.getBigVertexByCoordinate(new Point(190, 130)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(120, 220)), this.graphController.getBigVertexByCoordinate(new Point(120, 180)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(20, 140)), this.graphController.getBigVertexByCoordinate(new Point(50, 130)));

        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(120, 40)), this.graphController.getBigVertexByCoordinate(new Point(160, 50)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(160, 50)), this.graphController.getBigVertexByCoordinate(new Point(190, 90)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(190, 90)), this.graphController.getBigVertexByCoordinate(new Point(190, 130)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(190, 130)), this.graphController.getBigVertexByCoordinate(new Point(160, 170)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(160, 170)), this.graphController.getBigVertexByCoordinate(new Point(120, 180)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(120, 180)), this.graphController.getBigVertexByCoordinate(new Point(80, 170)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(80, 170)), this.graphController.getBigVertexByCoordinate(new Point(50, 130)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(50, 130)), this.graphController.getBigVertexByCoordinate(new Point(50, 90)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(50, 90)), this.graphController.getBigVertexByCoordinate(new Point(80, 50)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(80, 50)), this.graphController.getBigVertexByCoordinate(new Point(120, 40)));

        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(120, 40)), this.graphController.getBigVertexByCoordinate(new Point(120, 70)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(190, 90)), this.graphController.getBigVertexByCoordinate(new Point(150, 100)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(160, 170)), this.graphController.getBigVertexByCoordinate(new Point(140, 140)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(80, 170)), this.graphController.getBigVertexByCoordinate(new Point(100, 140)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(50, 90)), this.graphController.getBigVertexByCoordinate(new Point(90, 100)));

        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(120, 70)), this.graphController.getBigVertexByCoordinate(new Point(150, 100)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(150, 100)), this.graphController.getBigVertexByCoordinate(new Point(140, 140)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(140, 140)), this.graphController.getBigVertexByCoordinate(new Point(100, 140)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(100, 140)), this.graphController.getBigVertexByCoordinate(new Point(90, 100)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(90, 100)), this.graphController.getBigVertexByCoordinate(new Point(120, 70)));
        this.graphController.setLion(new Lion(this.graphController.getBigVertexByCoordinate(new Point(50, 90)), new StrategyAggroGreedy()));

        this.state = new State(this.graphController.getMen(), this.graphController.getLions());

        return this.graphController;
    }


    public GraphController setDefaultGraph2() {
        this.graphController = new GraphController();

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

        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(50, 20)), this.graphController.getBigVertexByCoordinate(new Point(190, 20)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(190, 20)), this.graphController.getBigVertexByCoordinate(new Point(220, 140)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(220, 140)), this.graphController.getBigVertexByCoordinate(new Point(120, 220)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(120, 220)), this.graphController.getBigVertexByCoordinate(new Point(20, 140)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(20, 140)), this.graphController.getBigVertexByCoordinate(new Point(50, 20)));

        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(50, 20)), this.graphController.getBigVertexByCoordinate(new Point(80, 50)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(190, 20)), this.graphController.getBigVertexByCoordinate(new Point(160, 50)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(220, 140)), this.graphController.getBigVertexByCoordinate(new Point(190, 130)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(120, 220)), this.graphController.getBigVertexByCoordinate(new Point(120, 180)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(20, 140)), this.graphController.getBigVertexByCoordinate(new Point(50, 130)));

        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(120, 40)), this.graphController.getBigVertexByCoordinate(new Point(160, 50)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(160, 50)), this.graphController.getBigVertexByCoordinate(new Point(190, 90)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(190, 90)), this.graphController.getBigVertexByCoordinate(new Point(190, 130)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(190, 130)), this.graphController.getBigVertexByCoordinate(new Point(160, 170)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(160, 170)), this.graphController.getBigVertexByCoordinate(new Point(120, 180)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(120, 180)), this.graphController.getBigVertexByCoordinate(new Point(80, 170)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(80, 170)), this.graphController.getBigVertexByCoordinate(new Point(50, 130)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(50, 130)), this.graphController.getBigVertexByCoordinate(new Point(50, 90)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(50, 90)), this.graphController.getBigVertexByCoordinate(new Point(80, 50)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(80, 50)), this.graphController.getBigVertexByCoordinate(new Point(120, 40)));

        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(120, 40)), this.graphController.getBigVertexByCoordinate(new Point(120, 70)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(190, 90)), this.graphController.getBigVertexByCoordinate(new Point(150, 100)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(160, 170)), this.graphController.getBigVertexByCoordinate(new Point(140, 140)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(80, 170)), this.graphController.getBigVertexByCoordinate(new Point(100, 140)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(50, 90)), this.graphController.getBigVertexByCoordinate(new Point(90, 100)));

        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(120, 70)), this.graphController.getBigVertexByCoordinate(new Point(150, 100)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(150, 100)), this.graphController.getBigVertexByCoordinate(new Point(140, 140)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(140, 140)), this.graphController.getBigVertexByCoordinate(new Point(100, 140)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(100, 140)), this.graphController.getBigVertexByCoordinate(new Point(90, 100)));
        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(90, 100)), this.graphController.getBigVertexByCoordinate(new Point(120, 70)));

        this.graphController.setMan(new Man(this.graphController.getBigVertexByCoordinate(new Point(50, 20)), new StrategyRunAwayGreedy()));
        this.graphController.setLion(new Lion(this.graphController.getBigVertexByCoordinate(new Point(190, 20)), new StretegyAggroClever()));
        this.graphController.setLion(new Lion(this.graphController.getBigVertexByCoordinate(new Point(100, 140)), new StretegyAggroClever()));
        this.graphController.setLion(new Lion(this.graphController.getBigVertexByCoordinate(new Point(50, 90)), new StretegyAggroClever()));

        this.state = new State(this.graphController.getMen(), this.graphController.getLions());

        return this.graphController;
    }

    public GraphController setDefaultGraph3() {
        this.graphController = new GraphController();

        // this.graphController.createVertex(new Point(5, 5));
        this.graphController.createVertex(new Point(19, 9));
        this.graphController.createVertex(new Point(0, 0));
//        this.graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(50, 20)), this.graphController.getBigVertexByCoordinate(new Point(190, 20)));

//        this.graphController.setMan(new Man(this.graphController.getBigVertexByCoordinate(new Point(50, 20)), new StrategyRandom()));
//        this.graphController.setLion(new Lion(this.graphController.getBigVertexByCoordinate(new Point(190, 20)), new StrategyRandom()));

//        this.state = new State(this.graphController.getMen(), this.graphController.getLions());

        return this.graphController;
    }

    public GraphController setDefaultGraph4() {
        return this.setDefaultGraph1();
    }

    public GraphController setDefaultGraph5() {


        this.graphController = new GraphController();

        graphController.createVertex(new Point(10, 10));
        graphController.createVertex(new Point(80, 40));

        graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(50, 20)), this.graphController.getBigVertexByCoordinate(new Point(190, 20)), 4);

        graphController.createVertex(new Point(20, 100));

//        graphController.relocateVertex(this.graphController.getBigVertexByCoordinate(new Point(50, 20)), new Point(0, -100));


        System.out.println(graphController.debugGraph());

        graphController.createEdge(this.graphController.getBigVertexByCoordinate(new Point(50, 20)), this.graphController.getBigVertexByCoordinate(new Point(220, 140)), 2);
        graphController.removeEdge(this.graphController.getBigVertexByCoordinate(new Point(50, 20)), this.graphController.getBigVertexByCoordinate(new Point(190, 20)));


        System.out.println("#############################");
        System.out.println(graphController.debugGraph());


        return this.setDefaultGraph1();
    }


    public GraphController setRandomGraph() {
        // TODO: implement random graph algorithm
        return this.setDefaultGraph1();
    }


    public GraphController setGraphFromFile(File file) throws Exception {
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
