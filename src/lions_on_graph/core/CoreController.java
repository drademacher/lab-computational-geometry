package lions_on_graph.core;

import lions_on_graph.core.entities.Lion;
import lions_on_graph.core.entities.Man;
import lions_on_graph.core.graph.*;
import lions_on_graph.core.strategies.LionStrategies.*;
import lions_on_graph.core.strategies.ManStrategies.*;
import lions_on_graph.core.strategies.StrategyLion;
import lions_on_graph.core.strategies.StrategyMan;
import lions_on_graph.visualization.ShapeController;
import util.Point;

import java.io.*;
import java.util.ArrayList;

public class CoreController {

    public static final String API_VERSION = "v0.1";


    private boolean editMode = true;
    private ArrayList<Lion> lions = new ArrayList<>();
    private ArrayList<Man> men = new ArrayList<>();
    private GraphController graph;
    private ShapeController shapeController;

    public CoreController() {
        this.graph = new GraphController();
        this.shapeController = new ShapeController(this);
    }

    public ShapeController getShapeController() {
        return shapeController;
    }

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

    /* ****************************
     *
     *   GRAPH API
     *
     * ****************************/

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
        return createEdge(vertex1Coordinates, vertex2Coordinates, GraphController.getDefaultEdgeWeight());
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

    public Edge changeEdgeWeight(Point vertex1Coordinates, Point vertex2Coordinates) {
        return changeEdgeWeight(vertex1Coordinates, vertex2Coordinates, GraphController.getDefaultEdgeWeight());
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

    public int getDefaultEdgeWeight() {
        return GraphController.getDefaultEdgeWeight();
    }

    public void setAllEdgeWeight(int weight) {
        GraphController.setDefaultEdgeWeight(weight);
        for (Edge edge : this.graph.getEdges()) {
            changeEdgeWeight(edge.getStartCoordinates(), edge.getEndCoordinates());
        }
    }

    private boolean isEntityOnEdge(Edge edge) {
        return isManOnEdge(edge) || isLionOnEdge(edge);
    }

    private boolean isEntityOnVertex(Point vertexCoordinate) {
        return isManOnVertex(vertexCoordinate) || isLionOnVertex(vertexCoordinate);
    }

    /* ****************************
     *
     *   ENTITY API
     *
     * ****************************/

    public boolean setMan(Point vertexCoorinate) {
        if (vertexCoorinate == null) {
            return false;
        }
        Vertex vertex = getVertexByCoordinate(vertexCoorinate);
        if (vertex == null) {
            return false;
        }

        Man man = new Man(vertex, this);
        boolean bool = men.add(man);
        setManStrategy(man.getCoordinates(), ManStrategy.RunAwayGreedy);
        shapeController.createMan(man);
        this.shapeController.updateStepPreviewsAndChoicePoints();
        return bool;
    }

    public void setNextManStep(Point manCoordinates, Point nextStepCoordinates) {
        if (manCoordinates == null || nextStepCoordinates == null) {
            return;
        }
        Vertex vertex = getVertexByCoordinate(nextStepCoordinates);
        if (vertex == null) {
            return;
        }
        Man man = getManByCoordinate(manCoordinates);
        if (man == null) {
            return;
        }
        man.setNextPosition(vertex);
        this.shapeController.updateStepPreviewsAndChoicePoints();
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
        boolean bool = lions.add(lion);
        setLionStrategy(lion.getCoordinates(), LionStrategy.AggroGreedy);
        shapeController.createLion(lion);
        this.shapeController.updateStepPreviewsAndChoicePoints();
        return bool;
    }

    public void setNextLionStep(Point lionCoordinates, Point nextStepCoordinates) {
        if (lionCoordinates == null || nextStepCoordinates == null) {
            return;
        }
        Vertex vertex = getVertexByCoordinate(nextStepCoordinates);
        if (vertex == null) {
            return;
        }
        Lion lion = getLionByCoordinate(lionCoordinates);
        if (lion == null) {
            return;
        }
        lion.setNextPosition(vertex);
        this.shapeController.updateStepPreviewsAndChoicePoints();
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
        boolean bool = men.remove(man);
        this.shapeController.updateStepPreviewsAndChoicePoints();
        return bool;
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
        boolean bool = lions.remove(lion);
        this.shapeController.updateStepPreviewsAndChoicePoints();
        return bool;
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

        man.setCurrentPosition(vertex);
        shapeController.relocateMan(man);
        this.shapeController.updateStepPreviewsAndChoicePoints();
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

        lion.setCurrentPosition(vertex);
        shapeController.relocateLion(lion);
        this.shapeController.updateStepPreviewsAndChoicePoints();
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
        if (lionCoordinate == null || strategy == null) {
            return;
        }
        Lion lion = getLionByCoordinate(lionCoordinate);
        if (lion == null) {
            return;
        }

        lion.setStrategy(strategy.getStrategy(this));
    }

    public void setAllManStrategy(ManStrategy strategy) {
        if (strategy == null) {
            return;
        }
        for (Man man : men) {
            setManStrategy(man.getCoordinates(), strategy);
        }
    }

    public void setAllLionStrategy(LionStrategy strategy) {
        if (strategy == null) {
            return;
        }
        for (Lion lion : lions) {
            setLionStrategy(lion.getCoordinates(), strategy);
        }
    }

    public ArrayList<Man> getMenWithManualInput() {
        ArrayList<Man> result = new ArrayList<>();
        for (Man man : men) {
            if (man.needManualStepInput()) {
                result.add(man);
            }
        }
        return result;
    }

    public ArrayList<Lion> getLionsWithManualInput() {
        ArrayList<Lion> result = new ArrayList<>();
        for (Lion lion : lions) {
            if (lion.needManualStepInput()) {
                result.add(lion);
            }
        }
        return result;
    }

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

    public void setAllLionRange(int range) {
        Lion.setDefaultRange(range);
        for (Lion lion : lions) {
            setLionRange(lion.getCoordinates(), range);
        }
    }

    public int getDefaultLionRange() {
        return Lion.getDefaultLionRange();
    }

    public void setExactManDistance(int distance) {
        if (distance < 0) {
            return;
        }
        Man.setDistance(distance);
        Man.setKeepDistanceExact(true);
    }

    public void setMinimumManDistance(int distance) {
        if (distance < 0) {
            return;
        }
        Man.setDistance(distance);
        Man.setKeepDistanceExact(false);
    }

    public int getExactManDistance() {
        if (Man.keepDistanceExact()) {
            return Man.getDistance();
        }
        return 0;
    }

    public int getMinimumManDistance() {
        if (Man.keepDistanceExact()) {
            return 0;
        }
        return Man.getDistance();
    }

    public void removeManDistance() {
        Man.removeDistance();
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

    public void setEmptyGraph() {
        this.graph = new GraphController();
        this.shapeController.removeAllShapes();
        this.shapeController = new ShapeController(this);
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

        this.setMan(new Point(50, 20));
        Point lion1 = new Point(190, 20);
        Point lion2 = new Point(100, 140);
        Point lion3 = new Point(50, 90);
        this.setLion(lion1);
        this.setLionStrategy(lion1, LionStrategy.Clever);
        this.setLion(lion2);
        this.setLionStrategy(lion2, LionStrategy.Clever);
        this.setLion(lion3);
        this.setLionStrategy(lion3, LionStrategy.Clever);
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


        this.setMan(this.graph.getSmallVertices().get(0).getCoordinates());
        this.setLion(this.graph.getSmallVertices().get(3).getCoordinates());
        this.setLion(this.graph.getSmallVertices().get(17).getCoordinates());


        this.setAllManStrategy(ManStrategy.Paper);
        this.setAllLionStrategy(LionStrategy.AggroGreedy);
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


        debugGraph();
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

            String currentLine;

            //config line
            currentLine = br.readLine();
            if (currentLine == null) {
                throw new Error("wrong file input version: "+CoreController.API_VERSION+" expected");
            }
            String[] lineElements = currentLine.split("##");
            if (!lineElements[2].equals(CoreController.API_VERSION)) {
                throw new Error("wrong file  input version: "+CoreController.API_VERSION+" expected");
            }

            // valid version, read file
            for (int y = 0; (currentLine = br.readLine()) != null; y++) { //Read in MapRow
//                System.out.println(currentLine);

                lineElements = currentLine.split("##");

                switch (lineElements[0]) {
                    case "S":
                        Man.setDistance(Integer.parseInt(lineElements[1]));
                        Man.setKeepDistanceExact(Boolean.parseBoolean(lineElements[2]));
                        Lion.setDefaultRange(Integer.parseInt((lineElements[3])));
                        GraphController.setDefaultEdgeWeight(Integer.parseInt((lineElements[4])));
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
            // TODO: search up the right exception type
            throw new Exception("test");
        }

        System.out.println("done.");
        debugGraph();
    }

    public void saveGraphToFile(File file) {
        ArrayList<Point> bigVertexCoordinates = new ArrayList<>();
        for (BigVertex vertex : this.graph.getBigVertices()) {
            bigVertexCoordinates.add(vertex.getCoordinates());
        }
        ArrayList<Point[]> edgesCoordinates = new ArrayList<>();
        for (Edge edge : this.graph.getEdges()) {
            edgesCoordinates.add(new Point[]{edge.getStartCoordinates(), edge.getEndCoordinates()});
        }


        BufferedWriter bufferedWriter = null;
        try {

            bufferedWriter = new BufferedWriter(new FileWriter(file));

            bufferedWriter.write("C##>>>>>Configuration for LionsOnGraph Applet<<<<<##"+ CoreController.API_VERSION);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            bufferedWriter.write("S##" + Man.getDistance() + "##" + Man.keepDistanceExact() + "##" + Lion.getDefaultLionRange() + "##" + GraphController.getDefaultEdgeWeight());
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

    public boolean simulateStep() {

        for (Man man : this.getMen()) {
            man.goToNextPosition();
            shapeController.relocateMan(man);
        }
        for (Lion lion : this.getLions()) {
            lion.goToNextPosition();
            shapeController.relocateLion(lion);
        }
        this.shapeController.updateStepPreviewsAndChoicePoints();

        if (lionsHaveWon()) {
            removeDeadMan();
            return true;
        }
        return false;
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
        DoNothing, Manually, Paper, Random, RunAwayGreedy;

        public StrategyMan getStrategy(CoreController coreController) {
            switch (this) {
                case DoNothing:
                    return new ManStrategyDoNothing(coreController, this);
                case Paper:
                    return new ManStrategyPaper(coreController, this);
                case Random:
                    return new ManStrategyRandom(coreController, this);
                case Manually:
                    return new ManStrategyManually(coreController, this);
                case RunAwayGreedy:
                    return new ManStrategyRunAwayGreedy(coreController, this);
                default:
                    throw new IllegalArgumentException("invalid input: " + this);
            }
        }
    }

    public enum LionStrategy {
        DoNothing, Manually, Random, AggroGreedy, Clever;

        public StrategyLion getStrategy(CoreController coreController) {
            switch (this) {
                case Random:
                    return new LionStrategyRandom(coreController, this);
                case Manually:
                    return new LionStrategyManually(coreController, this);
                case DoNothing:
                    return new LionStrategyDoNothing(coreController, this);
                case AggroGreedy:
                    return new LionStrategyAggroGreedy(coreController, this);
                case Clever:
                    return new LionStrategyClever(coreController, this);
                default:
                    throw new IllegalArgumentException("invalid input: " + this);
            }
        }
    }

}
