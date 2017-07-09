package lions_on_graph.core;

import lions_on_graph.core.entities.Lion;
import lions_on_graph.core.entities.Man;
import lions_on_graph.core.graph.*;
import lions_on_graph.core.strategies.StrategyMan;
import lions_on_graph.visualization.ShapeController;
import lions_on_graph.core.strategies.Strategy;
import lions_on_graph.core.strategies.LionStrategies.StrategyAggroGreedy;
import lions_on_graph.core.strategies.ManStrategies.StrategyRunAwayGreedy;
import util.Point;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class CoreController {
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

    public BigVertex createVertex(Point coordinate) {
        if (coordinate == null) {
            return null;
        }
        BigVertex vertex = this.graph.createVertex(coordinate);

        this.shapeController.createVertex(coordinate);
        return vertex;//TODO
    }

    public BigVertex relocateVertex(Point vertexCoordinates, Point newCoordinate) {
        if (vertexCoordinates == null || newCoordinate == null) {
            return null;
        }
        BigVertex vertex = getBigVertexByCoordinate(vertexCoordinates);
        if (vertex == null) {
            return null;
        }

        vertex = this.graph.relocateVertex(vertex, newCoordinate);

        this.shapeController.relocateVertex(vertex, newCoordinate);
        return vertex;//TODO
    }

    public BigVertex deleteVertex(Point vertexCoordinates) {
        if (vertexCoordinates == null) {
            return null;
        }
        BigVertex vertex = getBigVertexByCoordinate(vertexCoordinates);
        if (vertex == null) {
            return null;
        }

        //remove Entities
        ArrayList<Man> menToDelete = new ArrayList<>();
        menToDelete.addAll(getMenByCoordinate(vertexCoordinates));
        for (Edge edge : vertex.getEdges()) {
            menToDelete.addAll(getMenOnEdge(edge));
        }
        removeAllMen(menToDelete);

        ArrayList<Lion> lionsToDelete = new ArrayList<>();
        lionsToDelete.addAll(getLionsByCoordinate(vertexCoordinates));
        for (Edge edge : vertex.getEdges()) {
            lionsToDelete.addAll(getLionsOnEdge(edge));
        }
        removeAllLions(lionsToDelete);

        //delete Vertex
        this.shapeController.deleteVertex(vertex);
        vertex = this.graph.deleteVertex(vertex);
        this.shapeController.updateAllLionRanges(lions);

        return vertex; //TODO
    }

    public Edge createEdge(Point vertex1Coordinates, Point vertex2Coordinates) {
        return createEdge(vertex1Coordinates, vertex2Coordinates, 4);
    }

    public Edge createEdge(Point vertex1Coordinates, Point vertex2Coordinates, int weight) {
        if (vertex1Coordinates == null || vertex2Coordinates == null || weight < 0) {
            return null;
        }
        BigVertex vertex1 = getBigVertexByCoordinate(vertex1Coordinates);
        BigVertex vertex2 = getBigVertexByCoordinate(vertex2Coordinates);
        if (vertex1 == null || vertex2 == null) {
            return null;
        }


        Edge edge = this.graph.createEdge(vertex1, vertex2, weight);

        this.shapeController.createEdge(edge);
        this.shapeController.updateAllLionRanges(lions);
        return edge;//TODO
    }

    public Edge removeEdge(Point vertex1Coordinates, Point vertex2Coordinates) {
        if (vertex1Coordinates == null || vertex2Coordinates == null) {
            return null;
        }
        BigVertex vertex1 = getBigVertexByCoordinate(vertex1Coordinates);
        BigVertex vertex2 = getBigVertexByCoordinate(vertex2Coordinates);
        if (vertex1 == null || vertex2 == null) {
            return null;
        }

        Edge edge = this.graph.removeEdge(vertex1, vertex2);

        //remove Entities
        ArrayList<Man> menToDelete = getMenOnEdge(edge);
        removeAllMen(menToDelete);

        ArrayList<Lion> lionsToDelete = getLionsOnEdge(edge);
        removeAllLions(lionsToDelete);

        //remove edge
        this.shapeController.removeEdge(edge);
        this.shapeController.updateAllLionRanges(lions);
        return edge;//TODO
    }

    public Edge changeEdgeWeight(Point vertex1Coordinates, Point vertex2Coordinates, int weight) {

        if (vertex1Coordinates == null || vertex2Coordinates == null || weight < 1) {
            return null;
        }
        BigVertex vertex1 = getBigVertexByCoordinate(vertex1Coordinates);
        BigVertex vertex2 = getBigVertexByCoordinate(vertex2Coordinates);
        if (vertex1 == null || vertex2 == null) {
            return null;
        }

        Edge edge = getEdgeByVertices(vertex1, vertex2);

        if (edge.getEdgeWeight() > weight) {
            ArrayList<Man> menToDelete = getMenOnEdge(edge);
            removeAllMen(menToDelete);

            ArrayList<Lion> lionsToDelete = getLionsOnEdge(edge);
            removeAllLions(lionsToDelete);
        }

        this.shapeController.removeEdge(edge);

        this.graph.changeEdgeWeight(vertex1, vertex2, weight);

        this.shapeController.createEdge(edge);
        this.shapeController.updateAllLionRanges(lions);
        relocateAllLions();
        relocateAllMen();
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

        System.out.println("##############");
        System.out.println("men: " + men);
        System.out.println("lions: " + lions);
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

    public void setAllEdgeWeight(int weight){
        this.graph.setDefaultEdgeWeight(weight);
    }

    /* ****************************
     *
     *   ENTITY API
     *
     * ****************************/

    private boolean isEntityOnEdge(Edge edge) {
        return isManOnEdge(edge) || isLionOnEdge(edge);
    }

    private boolean isEntityOnVertex(Point vertexCoordinate) {
        return isManOnVertex(vertexCoordinate) || isLionOnVertex(vertexCoordinate);
    }

    public boolean setMan(Point vertexCoorinate) {
        if (vertexCoorinate == null) {
            return false;
        }
        Vertex vertex = getVertexByCoordinate(vertexCoorinate);
        if (vertex == null) {
            return false;
        }

        Man man = new Man(vertex, this);
        man.setStrategy(new StrategyRunAwayGreedy(this, man));
        shapeController.createMan(man);
        return men.add(man);
    }

    public boolean isManOnVertex(Point vertexCoorinate) {
        if (vertexCoorinate == null) {
            return false;
        }
        Vertex vertex = getVertexByCoordinate(vertexCoorinate);
        if (vertex == null) {
            return false;
        }

        for (Man man : men) {
            if (man.getCurrentPosition().equals(vertex)) {
                return true;
            }
        }
        return false;
    }

    private boolean isManOnEdge(Edge edge) {
        if (edge == null) {
            return false;
        }

        for (Man man : men) {
            for (Vertex vertex : edge.getEdgeVertices()) {
                if (man.getCurrentPosition().equals(vertex)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean setLion(Point vertexCoorinate) {
        if (vertexCoorinate == null) {
            return false;
        }
        Vertex vertex = getVertexByCoordinate(vertexCoorinate);
        if (vertex == null) {
            return false;
        }

        Lion lion = new Lion(vertex, this);
        lion.setStrategy(new StrategyAggroGreedy(this, lion));
        shapeController.createLion(lion);
        return lions.add(lion);
    }

    public boolean isLionOnVertex(Point vertexCoorinate) {
        if (vertexCoorinate == null) {
            return false;
        }
        Vertex vertex = getVertexByCoordinate(vertexCoorinate);
        if (vertex == null) {
            return false;
        }

        for (Lion lion : lions) {
            if (lion.getCurrentPosition().equals(vertex)) {
                return true;
            }
        }
        return false;
    }

    private boolean isLionOnEdge(Edge edge) {
        if (edge == null) {
            return false;
        }

        for (Lion lion : lions) {
            for (Vertex vertex : edge.getEdgeVertices()) {
                if (lion.getCurrentPosition().equals(vertex)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isDangerOnVertex(Point vertexCoorinate) {
        if (vertexCoorinate == null) {
            return false;
        }
        Vertex vertex = getVertexByCoordinate(vertexCoorinate);
        if (vertex == null) {
            return false;
        }

        for (Lion lion : lions) {
            if (lion.getCurrentPosition().equals(vertex)) {
                return true;
            }
            for (Vertex dangerVertex : lion.getRangeVertices()) {
                if (dangerVertex.equals(vertex)) {
                    return true;
                }
            }
        }
        return false;
    }


    public boolean removeMan(Point manCoordinate) {
        if (manCoordinate == null) {
            return false;
        }
        Man man = getManByCoordinate(manCoordinate);
        if (man == null) {
            return false;
        }

        shapeController.removeMan(man);
        return men.remove(man);
    }

    public void removeAllMen(ArrayList<Man> menToDelete) {
        for (Man man : menToDelete) {
            removeMan(man.getCoordinates());
        }
    }

    public boolean removeLion(Point lionCoordinate) {
        if (lionCoordinate == null) {
            return false;
        }
        Lion lion = getLionByCoordinate(lionCoordinate);
        if (lion == null) {
            return false;
        }

        shapeController.removeLion(lion);
        return lions.remove(lion);
    }

    public void removeAllLions(ArrayList<Lion> lionsToDelete) {
        for (Lion lion : lionsToDelete) {
            removeLion(lion.getCoordinates());
        }
    }

    public void relocateMan(Point manCoordinate, Point vertexCoordinate) {
        if (manCoordinate == null || vertexCoordinate == null) {
            return;
        }
        Man man = getManByCoordinate(manCoordinate);
        Vertex vertex = getVertexByCoordinate(vertexCoordinate);
        if (man == null || vertex == null) {
            return;
        }

        man.setPosition(vertex);
        shapeController.relocateMan(man);
    }

    private void relocateAllMen() {
        for (Man man : men) {
            shapeController.relocateMan(man);
        }
    }

    public void relocateLion(Point lionCoordinate, Point vertexCoordinate) {
        if (lionCoordinate == null || vertexCoordinate == null) {
            return;
        }
        Lion lion = getLionByCoordinate(lionCoordinate);
        Vertex vertex = getVertexByCoordinate(vertexCoordinate);
        if (lion == null || vertex == null) {
            return;
        }

        lion.setPosition(vertex);
        shapeController.relocateLion(lion);
    }

    private void relocateAllLions() {
        for (Lion lion : lions) {
            shapeController.relocateLion(lion);
        }
    }

    public ArrayList<Man> getMen() {
        return men;
    }

    public ArrayList<Lion> getLions() {
        return lions;
    }

    public void setManStrategy(Point manCoordinate, Strategy strategy) {
        if (manCoordinate == null || strategy == null) {
            return;
        }
        Man man = getManByCoordinate(manCoordinate);
        if (man == null) {
            return;
        }

        man.setStrategy(strategy);
    }

    public void setLionStrategy(Point lionCoordinate, Strategy strategy) {
        if (lionCoordinate == null || strategy == null) {
            return;
        }
        Lion lion = getLionByCoordinate(lionCoordinate);
        if (lion == null) {
            return;
        }

        lion.setStrategy(strategy);
    }

//    public void setAllManStrategy(Class<? extends StrategyMan> myStrategy) {
////        if (strategy == null) {
////            return;
////        }
////        for (Man man : men) {
////            StrategyName strategy = new stra
////            man.setStrategy(strategy);
////        }
//        try {
//            StrategyMan str  = myStrategy.newInstance();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void setAllLionStrategy(Strategy strategy) {
//        if (strategy == null) {
//            return;
//        }
//        for (Lion lion : lions) {
//            lion.setStrategy(strategy);
//        }
//    }

    public ArrayList<Man> getMenByCoordinate(Point coordinates) {
        ArrayList<Man> menOnVertex = new ArrayList<>();
        if (coordinates == null) {
            return null;
        }
        for (Man man : men) {
            if (man.getCoordinates().equals(coordinates)) {
                menOnVertex.add(man);
            }
        }
        return menOnVertex;
    }

    public Man getManByCoordinate(Point coordinates) {
        ArrayList<Man> men = getMenByCoordinate(coordinates);
        if (men.size() > 0) {
            return men.get(0);
        }
        return null;
    }

    private ArrayList<Man> getMenOnEdge(Edge edge) {
        ArrayList<Man> menOnEdge = new ArrayList<>();
        for (Vertex vertex : edge.getEdgeVertices()) {
            menOnEdge.addAll(getMenByCoordinate(vertex.getCoordinates()));
        }
        return menOnEdge;
    }

    public ArrayList<Lion> getLionsByCoordinate(Point coordinates) {
        ArrayList<Lion> lionsOnVertex = new ArrayList<>();
        if (coordinates == null) {
            return null;
        }
        for (Lion lion : lions) {
            if (lion.getCoordinates().equals(coordinates)) {
                lionsOnVertex.add(lion);
            }
        }
        return lionsOnVertex;
    }

    public Lion getLionByCoordinate(Point coordinates) {
        ArrayList<Lion> lions = getLionsByCoordinate(coordinates);
        if (lions.size() > 0) {
            return lions.get(0);
        }
        return null;
    }

    private ArrayList<Lion> getLionsOnEdge(Edge edge) {
        ArrayList<Lion> lionsOnEdge = new ArrayList<>();
        for (Vertex vertex : edge.getEdgeVertices()) {
            lionsOnEdge.addAll(getLionsByCoordinate(vertex.getCoordinates()));
        }
        return lionsOnEdge;
    }

    public void incrementLionRange(Point lionCoordinate) {
        if (lionCoordinate == null) {
            return;
        }
        Lion lion = getLionByCoordinate(lionCoordinate);
        if (lion == null) {
            return;
        }

        setLionRange(lionCoordinate, lion.getRange() + 1);
    }

    public void decrementLionRange(Point lionCoordinate) {
        if (lionCoordinate == null) {
            return;
        }
        Lion lion = getLionByCoordinate(lionCoordinate);
        if (lion == null) {
            return;
        }

        setLionRange(lionCoordinate, lion.getRange() - 1);
    }

    public void setLionRange(Point lionCoordinate, int range) {
        if (lionCoordinate == null || range < 0) {
            return;
        }
        Lion lion = getLionByCoordinate(lionCoordinate);
        if (lion == null) {
            return;
        }

        lion.setRange(range);
        shapeController.updateLionRange(lion);
    }

    public void setAllLionRange(int range){
        Lion.setDefaultRange(range);
        for(Lion lion : lions){
            setLionRange(lion.getCoordinates(), range);
        }
    }

    public void setManDistance(int distance, boolean keepExactDistance) {
        if (distance < 1) {
            return;
        }
        Man.setDistance(distance);
        Man.setKeepDistanceExact(keepExactDistance);
    }

    public int getManDistance() {
        return Man.getDistance();
    }

    public void removeManDistance() {
        Man.removeDistance();
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
        this.shapeController = new ShapeController(this);
        this.men = new ArrayList<>();
        this. lions = new ArrayList<>();
    }


    public void setDefaultGraph1() {
        setEmptyGraph();


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

        this.createEdge(new Point(50, 20), new Point(190, 20));
        this.createEdge(new Point(190, 20), new Point(220, 140));
        this.createEdge(new Point(220, 140), new Point(120, 220));
        this.createEdge(new Point(120, 220), new Point(20, 140));
        this.createEdge(new Point(20, 140), new Point(50, 20));

        this.createEdge(new Point(50, 20), new Point(80, 50));
        this.createEdge(new Point(190, 20), new Point(160, 5));
        this.createEdge(new Point(220, 140), new Point(190, 130));
        this.createEdge(new Point(120, 220), new Point(120, 180));
        this.createEdge(new Point(20, 140), new Point(50, 130));

        this.createEdge(new Point(120, 40), new Point(160, 50));
        this.createEdge(new Point(160, 50), new Point(190, 90));
        this.createEdge(new Point(190, 90), new Point(190, 130));
        this.createEdge(new Point(190, 130), new Point(160, 170));
        this.createEdge(new Point(160, 170), new Point(120, 180));
        this.createEdge(new Point(120, 180), new Point(80, 170));
        this.createEdge(new Point(80, 170), new Point(50, 130));
        this.createEdge(new Point(50, 130), new Point(50, 90));
        this.createEdge(new Point(50, 90), new Point(80, 50));
        this.createEdge(new Point(80, 50), new Point(120, 40));

        this.createEdge(new Point(120, 40), new Point(120, 70));
        this.createEdge(new Point(190, 90), new Point(150, 100));
        this.createEdge(new Point(160, 170), new Point(140, 140));
        this.createEdge(new Point(80, 170), new Point(100, 140));
        this.createEdge(new Point(50, 90), new Point(90, 100));

        this.createEdge(new Point(120, 70), new Point(150, 100));
        this.createEdge(new Point(150, 100), new Point(140, 140));
        this.createEdge(new Point(140, 140), new Point(100, 140));
        this.createEdge(new Point(100, 140), new Point(90, 100));
        this.createEdge(new Point(90, 100), new Point(120, 70));

        this.setMan(new Point(50, 20));
        this.setLion(new Point(190, 20));
        this.setLion(new Point(100, 140));
        this.setLion(new Point(50, 90));

    }

    public void setDefaultGraph2() {
        setEmptyGraph();
        setDefaultGraph1();
    }

    public void setDefaultGraph3() {
        setEmptyGraph();

        // this.createVertex(new Point(5, 5));
        this.createVertex(new Point(40, 20));
        this.createVertex(new Point(0, 0));
        this.createVertex(new Point(10, 30));

        this.createEdge(new Point(40, 20), new Point(0, 0));
        this.createEdge(new Point(10, 30), new Point(0, 0));
        this.createEdge(new Point(10, 30), new Point(40, 20));

        this.setLion(new Point(40, 20));
        setLionRange(new Point(40, 20), 1);

        this.setMan(new Point(0, 0));


    }

    public void setDefaultGraph4() {
        setEmptyGraph();
        this.setDefaultGraph1();
    }

    public void setDefaultGraph5() {
        setEmptyGraph();

        this.setDefaultGraph1();
    }


    public void setRandomGraph() {
        setEmptyGraph();
        // TODO: implement random graph algorithm
        this.setDefaultGraph1();
    }


    public void setGraphFromFile(File file) throws Exception {
        setEmptyGraph();
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

    public boolean simulateStep() {

        if (lionsHaveWon()) {
            return true;
        }

        for (Man man : this.getMen()) {
            man.goToNextPosition();
            shapeController.relocateMan(man);
        }
        for (Lion lion : this.getLions()) {
            lion.goToNextPosition();
            shapeController.relocateLion(lion);
        }

        return lionsHaveWon();
    }

    private boolean lionsHaveWon() {
        for (Man man : getMen()) {
            for (Lion lion : getLions()) {
                if (man.getCurrentPosition().equals(lion.getCurrentPosition())) {
                    return true;
                }
                for (Vertex rangeVertex : lion.getRangeVertices()) {
                    if (man.getCurrentPosition().equals(rangeVertex)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
