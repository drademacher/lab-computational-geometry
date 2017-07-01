package graph;

import entities.Lion;
import entities.Man;
import shapes.ShapeController;
import strategy.Strategy;
import strategy.LionStrategies.StrategyAggroGreedy;
import strategy.ManStrategies.StrategyRunAwayGreedy;
import util.Point;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class CoreController implements Api {
    private boolean editMode = true;

    private ArrayList<Lion> lions = new ArrayList<>();
    private ArrayList<Man> men = new ArrayList<>();

    private GraphController graph;
    private ShapeController shapeController;

    public CoreController() {
        this.graph = new GraphController();
        this.shapeController = new ShapeController(this);
    }

    /* ****************************
     *
     *   GRAPH API
     *
     * ****************************/

    @Override
    public BigVertex createVertex(Point coordinate) {
        if (coordinate == null) {
            return null;
        }
        BigVertex vertex = this.graph.createVertex(coordinate);

        this.shapeController.createVertex(coordinate);
        return vertex;//TODO
    }

    @Override
    public BigVertex relocateVertex(BigVertex vertex, Point newCoordinate) {
        if (vertex == null || newCoordinate == null) {
            return null;
        }

        vertex = this.graph.relocateVertex(vertex, newCoordinate);

        this.shapeController.relocateVertex(vertex, newCoordinate);
        return vertex;//TODO
    }

    @Override
    public BigVertex deleteVertex(BigVertex vertex) {
        if (vertex == null) {
            return null;
        }

        this.shapeController.deleteVertex(vertex);
        //TODO Entity?
        vertex = this.graph.deleteVertex(vertex);
        this.shapeController.updateAllLionRanges(lions);

        return vertex; //TODO
    }

    public Edge createEdge(BigVertex vertex1, BigVertex vertex2) {
        if (vertex1 == null || vertex2 == null) {
            return null;
        }

        return createEdge(vertex1, vertex2, 4);
    }

    @Override
    public Edge createEdge(BigVertex vertex1, BigVertex vertex2, int weight) {
        if (vertex1 == null || vertex2 == null || weight < 0) {
            return null;
        }
        Edge edge = this.graph.createEdge(vertex1, vertex2, weight);

        this.shapeController.createEdge(edge);
        this.shapeController.updateAllLionRanges(lions);
        return edge;//TODO
    }

    @Override
    public Edge removeEdge(BigVertex vertex1, BigVertex vertex2) {
        if (vertex1 == null || vertex2 == null) {
            return null;
        }
        //TODO Entity?
        Edge edge = this.graph.removeEdge(vertex1, vertex2);

        this.shapeController.removeEdge(edge);
        this.shapeController.updateAllLionRanges(lions);
        return edge;//TODO
    }

    @Override
    public Edge changeEdgeWeight(BigVertex vertex1, BigVertex vertex2, int weight) {

        if (vertex1 == null || vertex2 == null || weight < 0) {
            return null;
        }
        Edge edge = this.graph.changeEdgeWeight(vertex1, vertex2, weight);

        this.shapeController.changeEdgeWeight(edge);
        this.shapeController.updateAllLionRanges(lions);
        return edge;
    }

    public BigVertex getBigVertexByCoordinate(Point coordinate) {
        return this.graph.getBigVertexByCoordinate(coordinate);
    }

    public SmallVertex getSmallVertexByCoordinate(Point coordinate) {
        return this.graph.getSmallVertexByCoordinate(coordinate);
    }

    public Vertex getVertexByCoordinate(Point coordinate) {
        return this.graph.getVertexByCoordinate(coordinate);
    }

    public Edge getEdgeByVertices(BigVertex vertex1, BigVertex vertex2) {
        return this.graph.getEdgeByVertices(vertex1, vertex2);
    }


    public void debugGraph() {
        graph.debugGraph();
    }

    public GraphController getGraph() {
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

    public boolean setMan(Vertex vertex) {
        if(vertex == null){
            return false;
        }
        Man man = new Man(vertex, this);
        man.setStrategy(new StrategyRunAwayGreedy(this, man));
        shapeController.createMan(man);
        return men.add(man);
    }

    public boolean isManOnVertex(Vertex vertex) {
        if(vertex == null){
            return false;
        }
        for (Man man : men) {
            if (man.getCurrentPosition().equals(vertex)) {
                return true;
            }
        }
        return false;
    }

    public boolean setLion(Vertex vertex){
        if(vertex == null){
            return false;
        }
        Lion lion = new Lion(vertex, this);
        lion.setStrategy(new StrategyAggroGreedy(this, lion));
        shapeController.createLion(lion);
        return lions.add(lion);
    }

    public boolean isLionOnVertex(Vertex vertex) {
        if(vertex == null){
            return false;
        }
        for (Lion lion : lions) {
            if (lion.getCurrentPosition().equals(vertex)) {
                return true;
            }
        }
        return false;
    }

    public boolean removeMan(Man man) {
        if(man == null){
            return false;
        }
        shapeController.removeMan(man);
        return men.remove(man);
    }

    public boolean removeLion(Lion lion) {
        if(lion == null){
            return false;
        }
        shapeController.removeLion(lion);
        return lions.remove(lion);
    }

    public void relocateMan(Man man, Vertex vertex) {
        if(man == null || vertex == null){
            return;
        }
        man.setPosition(vertex);
        shapeController.relocateMan(man);
    }

    public void relocateLion(Lion lion, Vertex vertex) {
        if(lion == null || vertex == null){
            return;
        }
        lion.setPosition(vertex);
        shapeController.relocateLion(lion);
    }

    public ArrayList<Man> getMen() {
        return men;
    }

    public ArrayList<Lion> getLions() {
        return lions;
    }

    public void setManStrategy(Man man, Strategy strategy) {
        if(man == null || strategy == null){
            return;
        }
        man.setStrategy(strategy);
    }

    public void setLionStrategy(Lion lion, Strategy strategy) {
        if(lion == null || strategy == null){
            return;
        }
        lion.setStrategy(strategy);
    }

    public void setAllManStrategy(Strategy strategy) {
        if(strategy == null){
            return;
        }
        for (Man man : men) {
            man.setStrategy(strategy);
        }
    }

    public void setAllLionStrategy(Strategy strategy) {
        if(strategy == null){
            return;
        }
        for (Lion lion : lions) {
            lion.setStrategy(strategy);
        }
    }

    public Man getManByCoordinate(Point coordinates) {
        if(coordinates == null){
            return null;
        }
        for (Man man : men) {
            if (man.getCoordinates().equals(coordinates)) {
                return man;
            }
        }
        return null;
    }

    public Lion getLionByCoordinate(Point coordinates) {
        if(coordinates == null){
            return null;
        }
        for (Lion lion : lions) {
            if (lion.getCoordinates().equals(coordinates)) {
                return lion;
            }
        }
        return null;
    }

    public void setLionRange(Lion lion, int range){
        if(lion == null || range < 0){
            return;
        }
        lion.setRange(range);
        shapeController.updateLionRange(lion);
    }

    public void setManDistance(Man man, int distance, boolean keepExactDistance){
        if(man == null || distance < 1){
            return;
        }
        man.setDistance(distance);
        man.setKeepDistanceExact(keepExactDistance);
    }












    /* ****************************
     *
     *   EDIT MODE
     *
     * ****************************/

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }




    /* ****************************
     *
     *   GRAPH MANIPULATION
     *
     * ****************************/

    public void setEmptyGraph() {
        this.graph = new GraphController();
    }


    public void setDefaultGraph1() {
        this.graph = new GraphController();

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
        this.setLion(this.getBigVertexByCoordinate(new Point(50, 90)));

    }


    public void setDefaultGraph2() {
        this.graph = new GraphController();

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

        this.setMan(this.getBigVertexByCoordinate(new Point(50, 20)));
        this.setLion(this.getBigVertexByCoordinate(new Point(190, 20)));
        this.setLion(this.getBigVertexByCoordinate(new Point(100, 140)));
        this.setLion(this.getBigVertexByCoordinate(new Point(50, 90)));
    }

    public void setDefaultGraph3() {
        this.graph = new GraphController();

        // this.createVertex(new Point(5, 5));
        this.createVertex(new Point(40, 20));
        this.createVertex(new Point(0, 0));

        this.createVertex(new Point(10, 30));

        this.createEdge(this.getBigVertexByCoordinate(new Point(40, 20)), this.getBigVertexByCoordinate(new Point(0, 0)));
        this.createEdge(this.getBigVertexByCoordinate(new Point(10, 30)), this.getBigVertexByCoordinate(new Point(0, 0)));

        this.setLion(this.getBigVertexByCoordinate(new Point(40, 20)));
        Lion lion = this.getLions().get(0);
        lion.setRange(3);

        debugGraph();

        this.removeEdge(this.getBigVertexByCoordinate(new Point(40, 20)), this.getBigVertexByCoordinate(new Point(0, 0)));

        debugGraph();

    }

    public void setDefaultGraph4() {
        this.setDefaultGraph1();
    }

    public void setDefaultGraph5() {

        this.graph = new GraphController();

        this.createVertex(new Point(10, 10));
        this.createVertex(new Point(80, 40));

        this.createEdge(this.getBigVertexByCoordinate(new Point(50, 20)), this.getBigVertexByCoordinate(new Point(190, 20)), 4);

        this.createVertex(new Point(20, 100));

//        this.relocateVertex(this.getBigVertexByCoordinate(new Point(50, 20)), new Point(0, -100));



        this.createEdge(this.getBigVertexByCoordinate(new Point(50, 20)), this.getBigVertexByCoordinate(new Point(220, 140)), 2);


        this.setLion(this.getBigVertexByCoordinate(new Point(50, 20)));
        Lion lion = this.getLions().get(0);
        lion.setRange(3);



        System.out.println("#############################");
        this.debugGraph();


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
            shapeController.relocateMan(man);
        }
        for (Lion lion : this.getLions()) {
            lion.goToNextPosition();
            shapeController.relocateLion(lion);
        }
//        this.state = new State(this.getMen(), this.getLions());
//        return this.state;
    }


//    public State getState() {
//        return state;
//    }
}
