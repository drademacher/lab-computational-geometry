package graph;

import entities.Lion;
import entities.Man;
import strategy.StrategyAggroGreedy;
import strategy.StrategyRunAwayGreedy;
import strategy.StretegyAggroClever;
import util.Point;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class GraphController {

    private ArrayList<Lion> lions = new ArrayList<>();
    private ArrayList<Man> men = new ArrayList<>();

    private Graph graph;

    public GraphController() {
        this.graph = new Graph(this);
    }

    /* ****************************
     *
     *   GRAPH API
     *
     * ****************************/

    public boolean createVertex(Point coordinate) {
        if (coordinate == null) {
            return false;
        }
        boolean bool = this.graph.createVertex(coordinate);

        return bool;
    }

    public boolean relocateVertex(BigVertex vertex, Point newCoordinate) {
        if (vertex == null || newCoordinate == null) {
            return false;
        }
        return this.graph.relocateVertex(vertex, newCoordinate);
    }

    public boolean deleteVertex(BigVertex vertex) {
        if (vertex == null) {
            System.out.println("false #1");
            return false;
        }
        //TODO Entity?
        System.out.println("ok #1");
        return this.graph.deleteVertex(vertex);
    }

    public boolean createEdge(BigVertex vertex1, BigVertex vertex2) {
        if (vertex1 == null || vertex2 == null) {
            return false;
        }
        return createEdge(vertex1, vertex2, 4);
    }

    public boolean createEdge(BigVertex vertex1, BigVertex vertex2, int weight) {
        if (vertex1 == null || vertex2 == null || weight < 0) {
            return false;
        }
        return this.graph.createEdge(vertex1, vertex2, weight);
    }

    public boolean removeEdge(BigVertex vertex1, BigVertex vertex2) {
        if (vertex1 == null || vertex2 == null) {
            return false;
        }
        //TODO Entity?
        return this.graph.removeEdge(vertex1, vertex2);
    }

    public BigVertex getBigVertexByCoordinate(Point coordinate) {
        return this.graph.getBigVertexByCoordinate(coordinate);
    }


    public String debugGraph() {
        return graph.debugGraph();
    }

    public Graph getGraph() {
        return graph;
    }

    public BigVertex getBigVertexById(int id) {
        return this.graph.getBigVertexById(id);
    }

    public ArrayList<BigVertex> getBigVertices() {
        return this.graph.getBigVertices();
    }

    public ArrayList<SmallVertex> getSmallVertices() {
        return this.graph.getSmallVertices();
    }

    public ArrayList<Edge> getEdges() {
        return this.graph.getEdges();
    }

    /* ****************************
     *
     *   ENTITY API
     *
     * ****************************/

    public boolean setMan(Man man) {
        return men.add(man);
    }

    public boolean setLion(Lion lion) {
        return lions.add(lion);
    }

    public boolean removeMan(Man man) {
        return men.remove(man);
    }

    public boolean removeLion(Lion lion) {
        return lions.remove(lion);
    }

    public ArrayList<Man> getMen() {
        return men;
    }

    public ArrayList<Lion> getLions() {
        return lions;
    }













     /* ****************************
     *
     *   GRAPH MANIPULATION
     *
     * ****************************/

    public void setEmptyGraph() {
        this.graph = new Graph(this);
    }


    public void setDefaultGraph1() {
        this.graph = new Graph(this);

        this.createVertex(new Point(50, 20));
        this.createVertex(new Point(190, 20));
        this.createVertex(new Point(220, 140));
        this.createVertex(new Point(120, 220));
        this.createVertex(new Point(20, 140));

        this.createVertex(new Point(120, 40));
        this.createVertex(new Point(160, 50));
        this.createVertex(new Point(190, 90));
        this.createVertex(new Point(190, 130));
        this.createVertex(new Point(160, 170));
        this.createVertex(new Point(120, 180));
        this.createVertex(new Point(80, 170));
        this.createVertex(new Point(50, 130));
        this.createVertex(new Point(50, 90));
        this.createVertex(new Point(80, 50));

        this.createVertex(new Point(120, 70));
        this.createVertex(new Point(150, 100));
        this.createVertex(new Point(140, 140));
        this.createVertex(new Point(100, 140));
        this.createVertex(new Point(90, 100));

        this.createEdge(this.getBigVertexByCoordinate(new Point(50, 20)), this.getBigVertexByCoordinate(new Point(190, 20)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(190, 20)), this.getBigVertexByCoordinate(new Point(220, 140)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(220, 140)), this.getBigVertexByCoordinate(new Point(120, 220)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(120, 220)), this.getBigVertexByCoordinate(new Point(20, 140)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(20, 140)), this.getBigVertexByCoordinate(new Point(50, 20)));

        this.createEdge(this.getBigVertexByCoordinate(new Point(50, 20)), this.getBigVertexByCoordinate(new Point(80, 50)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(190, 20)), this.getBigVertexByCoordinate(new Point(160, 50)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(220, 140)), this.getBigVertexByCoordinate(new Point(190, 130)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(120, 220)), this.getBigVertexByCoordinate(new Point(120, 180)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(20, 140)), this.getBigVertexByCoordinate(new Point(50, 130)));

        this.createEdge(this.getBigVertexByCoordinate(new Point(120, 40)), this.getBigVertexByCoordinate(new Point(160, 50)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(160, 50)), this.getBigVertexByCoordinate(new Point(190, 90)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(190, 90)), this.getBigVertexByCoordinate(new Point(190, 130)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(190, 130)), this.getBigVertexByCoordinate(new Point(160, 170)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(160, 170)), this.getBigVertexByCoordinate(new Point(120, 180)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(120, 180)), this.getBigVertexByCoordinate(new Point(80, 170)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(80, 170)), this.getBigVertexByCoordinate(new Point(50, 130)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(50, 130)), this.getBigVertexByCoordinate(new Point(50, 90)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(50, 90)), this.getBigVertexByCoordinate(new Point(80, 50)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(80, 50)), this.getBigVertexByCoordinate(new Point(120, 40)));

        this.createEdge(this.getBigVertexByCoordinate(new Point(120, 40)), this.getBigVertexByCoordinate(new Point(120, 70)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(190, 90)), this.getBigVertexByCoordinate(new Point(150, 100)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(160, 170)), this.getBigVertexByCoordinate(new Point(140, 140)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(80, 170)), this.getBigVertexByCoordinate(new Point(100, 140)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(50, 90)), this.getBigVertexByCoordinate(new Point(90, 100)));

        this.createEdge(this.getBigVertexByCoordinate(new Point(120, 70)), this.getBigVertexByCoordinate(new Point(150, 100)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(150, 100)), this.getBigVertexByCoordinate(new Point(140, 140)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(140, 140)), this.getBigVertexByCoordinate(new Point(100, 140)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(100, 140)), this.getBigVertexByCoordinate(new Point(90, 100)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(90, 100)), this.getBigVertexByCoordinate(new Point(120, 70)));
        this.setLion(new Lion(this.getBigVertexByCoordinate(new Point(50, 90)), new StrategyAggroGreedy()));

    }


    public void setDefaultGraph2() {
        this.graph = new Graph(this);

        this.createVertex(new Point(50, 20));
        this.createVertex(new Point(190, 20));
        this.createVertex(new Point(220, 140));
        this.createVertex(new Point(120, 220));
        this.createVertex(new Point(20, 140));

        this.createVertex(new Point(120, 40));
        this.createVertex(new Point(160, 50));
        this.createVertex(new Point(190, 90));
        this.createVertex(new Point(190, 130));
        this.createVertex(new Point(160, 170));
        this.createVertex(new Point(120, 180));
        this.createVertex(new Point(80, 170));
        this.createVertex(new Point(50, 130));
        this.createVertex(new Point(50, 90));
        this.createVertex(new Point(80, 50));

        this.createVertex(new Point(120, 70));
        this.createVertex(new Point(150, 100));
        this.createVertex(new Point(140, 140));
        this.createVertex(new Point(100, 140));
        this.createVertex(new Point(90, 100));

        this.createEdge(this.getBigVertexByCoordinate(new Point(50, 20)), this.getBigVertexByCoordinate(new Point(190, 20)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(190, 20)), this.getBigVertexByCoordinate(new Point(220, 140)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(220, 140)), this.getBigVertexByCoordinate(new Point(120, 220)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(120, 220)), this.getBigVertexByCoordinate(new Point(20, 140)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(20, 140)), this.getBigVertexByCoordinate(new Point(50, 20)));

        this.createEdge(this.getBigVertexByCoordinate(new Point(50, 20)), this.getBigVertexByCoordinate(new Point(80, 50)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(190, 20)), this.getBigVertexByCoordinate(new Point(160, 50)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(220, 140)), this.getBigVertexByCoordinate(new Point(190, 130)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(120, 220)), this.getBigVertexByCoordinate(new Point(120, 180)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(20, 140)), this.getBigVertexByCoordinate(new Point(50, 130)));

        this.createEdge(this.getBigVertexByCoordinate(new Point(120, 40)), this.getBigVertexByCoordinate(new Point(160, 50)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(160, 50)), this.getBigVertexByCoordinate(new Point(190, 90)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(190, 90)), this.getBigVertexByCoordinate(new Point(190, 130)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(190, 130)), this.getBigVertexByCoordinate(new Point(160, 170)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(160, 170)), this.getBigVertexByCoordinate(new Point(120, 180)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(120, 180)), this.getBigVertexByCoordinate(new Point(80, 170)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(80, 170)), this.getBigVertexByCoordinate(new Point(50, 130)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(50, 130)), this.getBigVertexByCoordinate(new Point(50, 90)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(50, 90)), this.getBigVertexByCoordinate(new Point(80, 50)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(80, 50)), this.getBigVertexByCoordinate(new Point(120, 40)));

        this.createEdge(this.getBigVertexByCoordinate(new Point(120, 40)), this.getBigVertexByCoordinate(new Point(120, 70)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(190, 90)), this.getBigVertexByCoordinate(new Point(150, 100)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(160, 170)), this.getBigVertexByCoordinate(new Point(140, 140)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(80, 170)), this.getBigVertexByCoordinate(new Point(100, 140)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(50, 90)), this.getBigVertexByCoordinate(new Point(90, 100)));

        this.createEdge(this.getBigVertexByCoordinate(new Point(120, 70)), this.getBigVertexByCoordinate(new Point(150, 100)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(150, 100)), this.getBigVertexByCoordinate(new Point(140, 140)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(140, 140)), this.getBigVertexByCoordinate(new Point(100, 140)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(100, 140)), this.getBigVertexByCoordinate(new Point(90, 100)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(90, 100)), this.getBigVertexByCoordinate(new Point(120, 70)));

        this.setMan(new Man(this.getBigVertexByCoordinate(new Point(50, 20)), new StrategyRunAwayGreedy()));
        this.setLion(new Lion(this.getBigVertexByCoordinate(new Point(190, 20)), new StretegyAggroClever()));
        this.setLion(new Lion(this.getBigVertexByCoordinate(new Point(100, 140)), new StretegyAggroClever()));
        this.setLion(new Lion(this.getBigVertexByCoordinate(new Point(50, 90)), new StretegyAggroClever()));
    }

    public void setDefaultGraph3() {
        this.graph = new Graph(this);

        // this.createVertex(new Point(5, 5));
        this.createVertex(new Point(40, 20));
        this.createVertex(new Point(0, 0));

        this.createVertex(new Point(10, 30));

        this.createEdge(this.getBigVertexByCoordinate(new Point(40, 20)), this.getBigVertexByCoordinate(new Point(0, 0)));

    }

    public void setDefaultGraph4() {
        this.setDefaultGraph1();
    }

    public void setDefaultGraph5() {


        this.graph = new Graph(this);

        this.createVertex(new Point(10, 10));
        this.createVertex(new Point(80, 40));

        this.createEdge(this.getBigVertexByCoordinate(new Point(50, 20)), this.getBigVertexByCoordinate(new Point(190, 20)), 4);

        this.createVertex(new Point(20, 100));

//        this.relocateVertex(this.getBigVertexByCoordinate(new Point(50, 20)), new Point(0, -100));


        System.out.println(this.debugGraph());

        this.createEdge(this.getBigVertexByCoordinate(new Point(50, 20)), this.getBigVertexByCoordinate(new Point(220, 140)), 2);
        this.removeEdge(this.getBigVertexByCoordinate(new Point(50, 20)), this.getBigVertexByCoordinate(new Point(190, 20)));


        System.out.println("#############################");
        System.out.println(this.debugGraph());


        this.setDefaultGraph1();
    }


    public void setRandomGraph() {
        // TODO: implement random graph algorithm
        this.setDefaultGraph1();
    }


    public void setGraphFromFile(File file) throws Exception {
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
        } catch (Exception e) {
            // TODO: search up the right exception type
            throw new Exception("test");
        }
    }

    public void saveGraphToFile(File file) {
        // TODO: implement file saving
    }

    public void simulateStep() {
        for (Man man : this.getMen()) {
            man.goToNextPosition();
        }
        for (Lion lion : this.getLions()) {
            lion.goToNextPosition();
        }
//        this.state = new State(this.getMen(), this.getLions());
//        return this.state;
    }


//    public State getState() {
//        return state;
//    }
}
