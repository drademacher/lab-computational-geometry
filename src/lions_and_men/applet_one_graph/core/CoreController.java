package lions_and_men.applet_one_graph.core;

import lions_and_men.applet_one_graph.core.entities.Entity;
import lions_and_men.applet_one_graph.core.entities.Lion;
import lions_and_men.applet_one_graph.core.entities.Man;
import lions_and_men.applet_one_graph.core.graph.*;
import lions_and_men.applet_one_graph.core.strategies.AggroGreedyLion;
import lions_and_men.applet_one_graph.core.strategies.CleverLion;
import lions_and_men.applet_one_graph.core.strategies.PaperMan;
import lions_and_men.applet_one_graph.core.strategies.RunAwayGreedyMan;
import lions_and_men.applet_one_graph.core.strategies.Strategy;
import lions_and_men.applet_one_graph.core.strategies.DoNothing;
import lions_and_men.applet_one_graph.core.strategies.Manual;
import lions_and_men.applet_one_graph.core.strategies.RandomChoice;
import lions_and_men.util.Global;
import lions_and_men.util.Point;

import java.io.*;
import java.util.ArrayList;

public class CoreController {

    protected GraphController graph;
    private boolean editMode = true;
    private ArrayList<Lion> lions = new ArrayList<>();
    private ArrayList<Man> men = new ArrayList<>();

    public CoreController() {
        this.graph = new GraphController();
    }

    public void createVertex(Point coordinate) {
        if (coordinate == null) {
            return;
        }
        this.graph.createVertex(coordinate);
    }

    public void relocateVertex(Point vertexCoordinates, Point newCoordinate) {
        if (vertexCoordinates == null || newCoordinate == null) {
            return;
        }
        BigVertex vertex = getBigVertexByCoordinate(vertexCoordinates);
        if (vertex == null) {
            return;
        }

        this.graph.relocateVertex(vertex, newCoordinate);
    }

    /* ****************************
     *
     *   GRAPH API
     *
     * ****************************/

    public void deleteVertex(Point vertexCoordinates) {
        if (vertexCoordinates == null) {
            return;
        }
        BigVertex vertex = getBigVertexByCoordinate(vertexCoordinates);
        if (vertex == null) {
            return;
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
        this.graph.deleteVertex(vertex);

    }

    public void createEdge(Point vertex1Coordinates, Point vertex2Coordinates) {
        createEdge(vertex1Coordinates, vertex2Coordinates, getDefaultEdgeWeight());
    }

    protected void createEdge(Point vertex1Coordinates, Point vertex2Coordinates, int weight) {

        if (vertex1Coordinates == null || vertex2Coordinates == null || weight < 0) {
            return;
        }
        BigVertex vertex1 = getBigVertexByCoordinate(vertex1Coordinates);
        BigVertex vertex2 = getBigVertexByCoordinate(vertex2Coordinates);
        if (vertex1 == null || vertex2 == null) {
            return;
        }


        this.graph.createEdge(vertex1, vertex2, weight);
    }

    public void removeEdge(Point vertex1Coordinates, Point vertex2Coordinates) {
        if (vertex1Coordinates == null || vertex2Coordinates == null) {
            return;
        }
        BigVertex vertex1 = getBigVertexByCoordinate(vertex1Coordinates);
        BigVertex vertex2 = getBigVertexByCoordinate(vertex2Coordinates);
        if (vertex1 == null || vertex2 == null) {
            return;
        }

        Edge edge = this.graph.removeEdge(vertex1, vertex2);

        //remove Entities
        ArrayList<Man> menToDelete = getMenOnEdge(edge);
        removeAllMen(menToDelete);

        ArrayList<Lion> lionsToDelete = getLionsOnEdge(edge);
        removeAllLions(lionsToDelete);

    }

    public void changeEdgeWeight(Point vertex1Coordinates, Point vertex2Coordinates, int weight) {


        if (vertex1Coordinates == null || vertex2Coordinates == null || weight < 1) {
            return;
        }
        BigVertex vertex1 = getBigVertexByCoordinate(vertex1Coordinates);
        BigVertex vertex2 = getBigVertexByCoordinate(vertex2Coordinates);
        if (vertex1 == null || vertex2 == null) {
            return;
        }

        Edge edge = getEdgeByVertices(vertex1, vertex2);

        if (edge.getEdgeWeight() > weight) {
            ArrayList<Man> menToDelete = getMenOnEdge(edge);
            removeAllMen(menToDelete);

            ArrayList<Lion> lionsToDelete = getLionsOnEdge(edge);
            removeAllLions(lionsToDelete);
        }

        this.graph.changeEdgeWeight(vertex1, vertex2, weight);

//        System.out.println("man size "+men.size() );
//        relocateAllLions();
//        relocateAllMen();
    }

    public BigVertex getBigVertexByCoordinate(Point coordinate) {
        return this.graph.getBigVertexByCoordinate(coordinate);
    }

    public Vertex getVertexByCoordinate(Point coordinate) {
        return this.graph.getVertexByCoordinate(coordinate);
    }

    public Edge getEdgeByVertices(BigVertex vertex1, BigVertex vertex2) {
        return this.graph.getEdgeByVertices(vertex1, vertex2);
    }

    protected Edge getEdgeByPoints(Point point1, Point point2) {
        return getEdgeByVertices(getBigVertexByCoordinate(point1), getBigVertexByCoordinate(point2));
    }

    protected GraphController getGraph() {
        return graph;
    }

    public int getDefaultEdgeWeight() {
        return GraphController.getDefaultEdgeWeight();
    }

    public void setAllEdgeWeight(int weight) {
        GraphController.setDefaultEdgeWeight(weight);
        for (Edge edge : this.graph.getEdges()) {
            changeEdgeWeight(edge.getStartCoordinates(), edge.getEndCoordinates(), weight);
        }
    }

    /* ****************************
     *
     *   ENTITY API
     *
     * ****************************/

    public void setMan(Point vertexCoorinate) {
        if (vertexCoorinate == null) {
            return;
        }
        Vertex vertex = getVertexByCoordinate(vertexCoorinate);
        if (vertex == null) {
            return;
        }

        Man man = new Man(vertex, this);
        men.add(man);
        setManStrategy(man.getCoordinates(), Man.getDefaultStrategy());
    }

    public void setNextEntityStep(Point entityCoordinates, Point nextStepCoordinates) {
        if (entityCoordinates == null || nextStepCoordinates == null) {
            return;
        }
        Vertex vertex = getVertexByCoordinate(nextStepCoordinates);
        if (vertex == null) {
            return;
        }
        Entity entity = null;
        Man man = getManByCoordinate(entityCoordinates);
        if (man != null) {
            entity = man;
        }
        Lion lion = getLionByCoordinate(entityCoordinates);
        if (lion != null) {
            entity = lion;
        }
        if (entity == null) {
            return;
        }
        entity.setNextPosition(vertex);
    }

    public boolean isManRangeOnVertex(Point vertexCoorinate) {
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
            for (Vertex rangeVertex : man.getRangeVertices()) {
                if (rangeVertex.equals(vertex)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setLion(Point vertexCoorinate) {
        if (vertexCoorinate == null) {
            return;
        }
        Vertex vertex = getVertexByCoordinate(vertexCoorinate);
        if (vertex == null) {
            return;
        }

        Lion lion = new Lion(vertex, this);
        lions.add(lion);
        setLionStrategy(lion.getCoordinates(), Lion.getDefaultStrategy());
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

    public boolean isLionDangerOnVertex(Point vertexCoorinate) {
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

    public void removeMan(Point manCoordinate) {
        if (manCoordinate == null) {
            return;
        }
        Man man = getManByCoordinate(manCoordinate);
        if (man == null) {
            return;
        }

        men.remove(man);

    }

    private void removeAllMen(ArrayList<Man> menToDelete) {
        for (Man man : menToDelete) {
            removeMan(man.getCoordinates());
        }
    }

    public void removeLion(Point lionCoordinate) {
        if (lionCoordinate == null) {
            return;
        }
        Lion lion = getLionByCoordinate(lionCoordinate);
        if (lion == null) {
            return;
        }

        lions.remove(lion);

    }

    private void removeAllLions(ArrayList<Lion> lionsToDelete) {
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

        man.setCurrentPosition(vertex);
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

        lion.setCurrentPosition(vertex);

    }

    public ArrayList<Man> getMen() {
        return men;
    }

    public ArrayList<Lion> getLions() {
        return lions;
    }

    public void setManStrategy(Point manCoordinate, ManStrategy strategy) {
        if (manCoordinate == null || strategy == null) {
            return;
        }
        Man man = getManByCoordinate(manCoordinate);
        if (man == null) {
            return;
        }

        man.setStrategy(strategy.getStrategy(this));

    }

    public void setLionStrategy(Point lionCoordinate, LionStrategy strategy) {
        System.out.println("set lion strategy");
        if (lionCoordinate == null || strategy == null) {
            return;
        }
        Lion lion = getLionByCoordinate(lionCoordinate);
        if (lion == null) {
            return;
        }
        System.out.println(strategy);
        System.out.println(strategy.getStrategy(this));
        lion.setStrategy(strategy.getStrategy(this));

    }

    public void setAllManStrategy(ManStrategy strategy) {
        if (strategy == null) {
            return;
        }
        Man.setDefaultStrategy(strategy);
        for (Man man : men) {
            setManStrategy(man.getCoordinates(), strategy);
        }

    }

    public void setAllLionStrategy(LionStrategy strategy) {
        if (strategy == null) {
            return;
        }
        Lion.setDefaultStrategy(strategy);
        for (Lion lion : lions) {
            setLionStrategy(lion.getCoordinates(), strategy);
        }

    }

    public ArrayList<Vertex> getMenWithManualInput() {
        ArrayList<Vertex> result = new ArrayList<>();
        for (Man man : men) {
            if (man.needManualStepInput()) {
                result.add(man.getCurrentPosition());
            }
        }
        return result;
    }

    public ArrayList<Vertex> getLionsWithManualInput() {
        ArrayList<Vertex> result = new ArrayList<>();
        for (Lion lion : lions) {
            if (lion.needManualStepInput()) {
                result.add(lion.getCurrentPosition());
            }
        }
        return result;
    }

    private ArrayList<Man> getMenByCoordinate(Point coordinates) {
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

    private ArrayList<Lion> getLionsByCoordinate(Point coordinates) {
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

    }

    public void setAllLionRange(int range) {
        Lion.setDefaultRange(range);
        for (Lion lion : lions) {
            setLionRange(lion.getCoordinates(), range);
        }
    }

    public int getDefaultLionRange() {
        return Lion.getDefaultLionRange();
    }

    public void incrementManRange(Point manCoordinate) {
        if (manCoordinate == null) {
            return;
        }
        Man man = getManByCoordinate(manCoordinate);
        if (man == null) {
            return;
        }

        setManRange(manCoordinate, man.getRange() + 1);
    }

    public void decrementManRange(Point manCoordinate) {
        if (manCoordinate == null) {
            return;
        }
        Man man = getManByCoordinate(manCoordinate);
        if (man == null) {
            return;
        }

        setManRange(manCoordinate, man.getRange() - 1);
    }

    public void setManRange(Point manCoordinate, int range) {
        if (manCoordinate == null || range < 0) {
            return;
        }
        Man man = getManByCoordinate(manCoordinate);
        if (man == null) {
            return;
        }

        man.setRange(range);

    }

    public void setAllManRange(int range) {
        Man.setDefaultRange(range);
        for (Man man : men) {
            setManRange(man.getCoordinates(), range);
        }
    }

    public int getDefaultManRange() {
        return Man.getDefaultLionRange();
    }

    public int getMinimumManDistance() {
        return Man.getMinimumDistance();
    }

    public void setMinimumManDistance(int distance) {
        if (distance < 0) {
            return;
        }
        Man.setMinimumDistance(distance);
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }






    /* ****************************
     *
     *   EDIT MODE
     *
     * ****************************/

    public void cleanUp() {

    }

    public void setEmptyGraph() {
        this.graph = new GraphController();
        cleanUp();
        this.men = new ArrayList<>();
        this.lions = new ArrayList<>();
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
        this.createEdge(new Point(190, 20), new Point(160, 50));
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

        Point lion1 = new Point(190, 20);
        Point lion2 = new Point(100, 140);
        Point lion3 = new Point(50, 90);
        this.setLion(lion1);
        this.setLionStrategy(lion1, LionStrategy.CleverLion);
        this.setLion(lion2);
        this.setLionStrategy(lion2, LionStrategy.CleverLion);
        this.setLion(lion3);
        this.setLionStrategy(lion3, LionStrategy.CleverLion);

        this.setMan(this.graph.getSmallVertices().get(0).getCoordinates());
        this.setManStrategy(this.graph.getSmallVertices().get(0).getCoordinates(), ManStrategy.PaperMan);

    }




    /* ****************************
     *
     *   GRAPH MANIPULATION
     *
     * ****************************/

    public void setDefaultGraph2() {
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
        this.createEdge(new Point(190, 20), new Point(160, 50));
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

        this.setLion(this.graph.getSmallVertices().get(3).getCoordinates());
        this.setLion(this.graph.getSmallVertices().get(17).getCoordinates());
        this.setMan(this.graph.getSmallVertices().get(0).getCoordinates());

        this.setAllManStrategy(ManStrategy.PaperMan);
        this.setAllLionStrategy(LionStrategy.AggroGreedyLion);
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

    public void setGraphFromFile(File file) throws Exception {
        setEmptyGraph();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String currentLine;

            //config line
            currentLine = br.readLine();
            if (currentLine == null) {
                throw new Error("wrong file input version: " + Global.API_VERSION + " expected");
            }
            String[] lineElements = currentLine.split("##");
            if (!lineElements[2].equals(Global.API_VERSION)) {
                throw new Error("wrong file  input version: " + Global.API_VERSION + " expected");
            }

            // valid version, read file
            while ((currentLine = br.readLine()) != null) { //Read in MapRow
//                System.out.println(currentLine);

                lineElements = currentLine.split("##");

                switch (lineElements[0]) {
                    case "S":
                        Man.setMinimumDistance(Integer.parseInt(lineElements[1]));
//                        Man.setKeepDistanceExact(Boolean.parseBoolean(lineElements[2]));
                        Man.setDefaultStrategy(ManStrategy.valueOf(lineElements[2]));
                        Lion.setDefaultRange(Integer.parseInt((lineElements[3])));
                        Lion.setDefaultStrategy(LionStrategy.valueOf(lineElements[4]));
                        GraphController.setDefaultEdgeWeight(Integer.parseInt((lineElements[5])));
                        break;
                    case "V":
                        this.createVertex(new Point(Double.parseDouble(lineElements[1]), Double.parseDouble(lineElements[2])));
                        break;
                    case "E":
                        this.createEdge(new Point(Double.parseDouble(lineElements[1]), Double.parseDouble(lineElements[2])),
                                new Point(Double.parseDouble(lineElements[3]), Double.parseDouble(lineElements[4])),
                                Integer.parseInt((lineElements[5])));
                        break;
                    case "M":
                        this.setMan(new Point(Double.parseDouble(lineElements[1]), Double.parseDouble(lineElements[2])));
                        System.out.println(".."+lineElements[3]);
                        System.out.println(".."+ManStrategy.valueOf(lineElements[3]));
                        this.setManStrategy(new Point(Double.parseDouble(lineElements[1]), Double.parseDouble(lineElements[2])), ManStrategy.valueOf(lineElements[3]));
                        break;
                    case "L":
                        this.setLion(new Point(Double.parseDouble(lineElements[1]), Double.parseDouble(lineElements[2])));
                        this.setLionStrategy(new Point(Double.parseDouble(lineElements[1]), Double.parseDouble(lineElements[2])), LionStrategy.valueOf(lineElements[4]));
                        this.setLionRange(new Point(Double.parseDouble(lineElements[1]), Double.parseDouble(lineElements[2])), Integer.parseInt(lineElements[3]));
                        break;
                    default:
                        throw new IllegalArgumentException("invalid input: " + lineElements[0]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("test");
        }

        System.out.println("done.");

    }

    public void saveGraphToFile(File file) {

        BufferedWriter bufferedWriter;
        try {

            bufferedWriter = new BufferedWriter(new FileWriter(file));

            bufferedWriter.write("C##>>>>>Configuration for LionsOnGraph Applet<<<<<##" + Global.API_VERSION);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            bufferedWriter.write("S##" + Man.getMinimumDistance() + /*"##" + Man.keepDistanceExact() +*/ "##" + Man.getDefaultStrategy().name() + "##" + Lion.getDefaultLionRange() + "##" + Lion.getDefaultStrategy().name() + "##" + GraphController.getDefaultEdgeWeight());
            bufferedWriter.newLine();
            bufferedWriter.flush();

            for (BigVertex vertex : this.graph.getBigVertices()) {
                bufferedWriter.write("V##" + vertex.getCoordinates().getX() + "##" + vertex.getCoordinates().getY());
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }

            for (Edge edge : this.graph.getEdges()) {
                bufferedWriter.write("E##" + edge.getStartCoordinates().getX() + "##" + edge.getStartCoordinates().getY() + "##" + edge.getEndCoordinates().getX() + "##" + edge.getEndCoordinates().getY() + "##" + edge.getEdgeWeight());
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }

            for (Man man : men) {
                bufferedWriter.write("M##" + man.getCoordinates().getX() + "##" + man.getCoordinates().getY() + "##" + man.getStrategy().getName());
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }

            for (Lion lion : lions) {
                bufferedWriter.write("L##" + lion.getCoordinates().getX() + "##" + lion.getCoordinates().getY() + "##" + lion.getRange() + "##" + lion.getStrategy().getName());
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }


            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Point manGoToNextPosition(Point oldPoint) {
        Man man = getManByCoordinate(oldPoint);
        if (man == null) {
            return null;
        }
        return man.goToNextPosition().getCoordinates();
    }

    protected Point lionGoToNextPosition(Point oldPoint) {
        Lion lion = getLionByCoordinate(oldPoint);
        if (lion == null) {
            return null;
        }
        return lion.goToNextPosition().getCoordinates();
    }

    public boolean simulateStep() {

        for (Man man : this.getMen()) {
            System.out.println("man "+man);
            manGoToNextPosition(man.getCoordinates());
        }
        for (Lion lion : this.getLions()) {
            System.out.println("lion +"+lion);
            lionGoToNextPosition(lion.getCoordinates());
        }


        if (lionsHaveWon()) {
            removeDeadMan();
            return true;
        }
        return false;
    }

    private boolean lionsHaveWon() {
        for (Man man : getMen()) {
            for (Lion lion : getLions()) {
                //lion and man
                if (man.getCurrentPosition().equals(lion.getCurrentPosition())) {
                    return true;
                }
                for (Vertex manRangeVertex : man.getRangeVertices()) {
                    ///lion and man range
                    if (manRangeVertex.equals(lion.getCurrentPosition())) {
                        return true;
                    }
                }


                for (Vertex lionRangeVertex : lion.getRangeVertices()) {
                    //lion range and man
                    if (man.getCurrentPosition().equals(lionRangeVertex)) {
                        return true;
                    }
                    for (Vertex manRangeVertex : man.getRangeVertices()) {
                        //lion range and man range
                        if (manRangeVertex.equals(lionRangeVertex)) {
                            return true;
                        }
                    }
                }

            }
        }
        return false;
    }

    private void removeDeadMan() {
        for (int i = getMen().size() - 1; i >= 0; i--) {
            Man man = getMen().get(i);
            for (Lion lion : getLions()) {
                if (man.getCurrentPosition().equals(lion.getCurrentPosition())) {
                    removeMan(man.getCoordinates());
                }
                for (Vertex rangeVertex : lion.getRangeVertices()) {
                    if (man.getCurrentPosition().equals(rangeVertex)) {
                        removeMan(man.getCoordinates());
                    }
                }
            }
        }
    }

    public enum ManStrategy {
        DoNothing, Manual, PaperMan, RandomChoice, RunAwayGreedyMan;

        public Strategy getStrategy(CoreController coreController) {
            switch (this) {
                case DoNothing:
                    return new DoNothing(coreController);
                case PaperMan:
                    return new PaperMan(coreController);
                case RandomChoice:
                    return new RandomChoice(coreController);
                case Manual:
                    return new Manual(coreController);
                case RunAwayGreedyMan:
                    return new RunAwayGreedyMan(coreController);
                default:
                    throw new IllegalArgumentException("invalid input: " + this);
            }
        }
    }

    public enum LionStrategy {
        DoNothing, Manual, RandomChoice, AggroGreedyLion, CleverLion;

        public Strategy getStrategy(CoreController coreController) {
            switch (this) {
                case RandomChoice:
                    return new RandomChoice(coreController);
                case Manual:
                    return new Manual(coreController);
                case DoNothing:
                    return new DoNothing(coreController);
                case AggroGreedyLion:
                    return new AggroGreedyLion(coreController);
                case CleverLion:
                    return new CleverLion(coreController);
                default:
                    throw new IllegalArgumentException("invalid input: " + this);
            }
        }
    }




    // TODO: THIS IS NEW
    public int getDistanceBetween(Vertex vertex1, Vertex vertex2) {
        return GraphHelper.createGraphHelper(this).getDistanceBetween(vertex1, vertex2);
    }
}
