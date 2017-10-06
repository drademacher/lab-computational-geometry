package lions_and_men.applet_graph.visualization;

import lions_and_men.applet_graph.algorithm.CoreController;
import lions_and_men.applet_graph.algorithm.graph.Connection;
import lions_and_men.applet_graph.algorithm.strategies.StrategyEnumLion;
import lions_and_men.applet_graph.algorithm.strategies.StrategyEnumMan;
import lions_and_men.util.Point;

import java.util.ArrayList;

import static lions_and_men.applet_graph.visualization.Constants.*;

public class VisualizedCoreController extends CoreController {

    private ArrayList<Vertex> verticesBig = new ArrayList<>();
    private ArrayList<Vertex> verticesSmall = new ArrayList<>();
    private ArrayList<Entity> entities = new ArrayList<>();
    private ArrayList<Edge> edges = new ArrayList<>();
    private ArrayList<Range> rangesMan = new ArrayList<>();
    private ArrayList<Range> rangesLion = new ArrayList<>();
    private ArrayList<Vertex> stepPreviews = new ArrayList<>();

    public VisualizedCoreController() {
        super();
    }

    private void refrresh() {
        // clean up
        verticesBig.forEach(Vertex::delete);
        verticesBig.clear();

        verticesSmall.forEach(Vertex::delete);
        verticesSmall.clear();

        entities.forEach(Entity::delete);
        entities.clear();

        edges.forEach(Edge::delete);
        edges.clear();

        rangesMan.forEach(Range::delete);
        rangesMan.clear();

        rangesLion.forEach(Range::delete);
        rangesLion.clear();

        stepPreviews.forEach(Vertex::delete);
        stepPreviews.clear();

        // draw big vertices, edges, small vertices
        graph.getBigVertices().forEach(bigVertex -> verticesBig.add(new BigVertex(this, bigVertex.getCoordinates())));
        graph.getEdges().forEach(edge -> edges.add(new Edge(this, edge.getStartCoordinates(), edge.getEndCoordinates())));
        graph.getSmallVertices().forEach(smallVertex -> verticesSmall.add(new SmallVertex(this, smallVertex.getCoordinates())));

        // draw entities
        getMen().forEach(man -> entities.add(new Man(this, man.getCoordinates())));
        getLions().forEach(lion -> entities.add(new Lion(this, lion.getCoordinates())));

        updateLionRanges();
        updateManRanges();
        updateStepPreviewsAndChoicePoints();
    }

    @Override
    public void setEmptyGraph() {
        super.setEmptyGraph();

        refrresh();
    }

    /* ****************************
     *
     *   Graph Shapes
     *
     * ****************************/

    public void relocateVertex(Point vertexCoordinates, Point newCoordinate) {

        super.relocateVertex(vertexCoordinates, newCoordinate);

        refrresh();
    }

    public void createVertex(Point coordinate) {
        super.createVertex(coordinate);

        refrresh();

    }

    public void deleteVertex(Point vertexCoordinates) {


        super.deleteVertex(vertexCoordinates);


        refrresh();
    }

    public void createEdge(Point vertex1Coordinates, Point vertex2Coordinates) {
        createEdge(vertex1Coordinates, vertex2Coordinates, getDefaultEdgeWeight());
    }

    public void createEdge(Point vertex1Coordinates, Point vertex2Coordinates, int weight) {

        super.createEdge(vertex1Coordinates, vertex2Coordinates, weight);

        refrresh();
    }

    public void removeEdge(Point vertex1Coordinates, Point vertex2Coordinates) {

        super.removeEdge(vertex1Coordinates, vertex2Coordinates);

        refrresh();
    }

    @Override
    public void changeEdgeWeight(Point vertex1Coordinates, Point vertex2Coordinates, int weight) {

        super.changeEdgeWeight(vertex1Coordinates, vertex2Coordinates, weight);

        refrresh();
    }

    /* ****************************
     *
     *   ENTITY Shapes
     *
     * ****************************/

    public void setMan(Point vertexCoorinate) {

        super.setMan(vertexCoorinate);

        refrresh();
    }

    public void setLion(Point vertexCoorinate) {

        super.setLion(vertexCoorinate);

        refrresh();
    }

    public void relocateMan(Point manCoordinate, Point vertexCoordinate) {

        super.relocateMan(manCoordinate, vertexCoordinate);

        refrresh();
    }

    public void relocateLion(Point lionCoordinate, Point vertexCoordinate) {

        super.relocateLion(lionCoordinate, vertexCoordinate);

        refrresh();
    }

    public void removeMan(Point manCoordinate) {
        super.removeMan(manCoordinate);

        refrresh();
    }

    public void removeLion(Point lionCoordinate) {
        super.removeLion(lionCoordinate);

        refrresh();
    }

    @Override
    protected Point manGoToNextPosition(Point oldPoint) {
        Point newPoint = super.manGoToNextPosition(oldPoint);
        entities.stream().filter(entity -> entity.getPosition().equals(oldPoint)).forEach(entity -> entity.relocate(newPoint));

        return newPoint;
    }

    @Override
    protected Point lionGoToNextPosition(Point oldPoint) {
        Point newPoint = super.lionGoToNextPosition(oldPoint);
        entities.stream().filter(entity -> entity.getPosition().equals(oldPoint)).forEach(entity -> entity.relocate(newPoint));

        return newPoint;
    }

    @Override
    public void setManStrategy(Point manCoordinate, StrategyEnumMan strategy) {
        super.setManStrategy(manCoordinate, strategy);

        updateStepPreviewsAndChoicePoints();
    }

    @Override
    public void setLionStrategy(Point lionCoordinate, StrategyEnumLion strategy) {
        super.setLionStrategy(lionCoordinate, strategy);

        updateStepPreviewsAndChoicePoints();
    }

    @Override
    public void setAllManStrategy(StrategyEnumMan strategy) {
        super.setAllManStrategy(strategy);

        updateStepPreviewsAndChoicePoints();
    }

    @Override
    public void setAllLionStrategy(StrategyEnumLion strategy) {
        super.setAllLionStrategy(strategy);

        updateStepPreviewsAndChoicePoints();
    }

    @Override
    public void setManRange(Point manCoordinate, int range) {
        super.setManRange(manCoordinate, range);

        updateManRanges();
    }

    @Override
    public void setNextEntityStep(Point entityCoordinates, Point nextStepCoordinates) {

        super.setNextEntityStep(entityCoordinates, nextStepCoordinates);

        updateStepPreviewsAndChoicePoints();
    }

    private void updateManRanges() {

        rangesMan.forEach(Range::delete);
        rangesMan.clear();

        getMen().forEach(man -> man.getRangeVertices().forEach(vertex -> rangesMan.add(new Range(vertex.getCoordinates(), true))));
    }

    @Override
    public void setLionRange(Point lionCoordinate, int range) {
        super.setLionRange(lionCoordinate, range);

        updateLionRanges();
    }

    private void updateLionRanges() {

        rangesLion.forEach(Range::delete);
        rangesLion.clear();

        getLions().forEach(lion -> lion.getRangeVertices().forEach(vertex -> rangesLion.add(new Range(vertex.getCoordinates()))));
    }


    private void updateStepPreviewsAndChoicePoints() {
        // step previews
        stepPreviews.forEach(Vertex::delete);
        stepPreviews.clear();

        getMen().forEach(man -> stepPreviews.add(new StepPreview(man.getNextPosition().getCoordinates())));
        getLions().forEach(lion -> stepPreviews.add(new StepPreview(lion.getNextPosition().getCoordinates())));


        // choice points
        ChoicePoint.clear();
        for (lions_and_men.applet_graph.algorithm.graph.Vertex vertex : getMenWithManualInput()) {
            new ChoicePoint(this, vertex.getCoordinates(), vertex.getCoordinates(), COLOR_MAN);
            for (Connection con : vertex.getConnections()) {
                lions_and_men.applet_graph.algorithm.graph.Vertex choicePoint = con.getNeighbor(vertex);
                new ChoicePoint(this, vertex.getCoordinates(), choicePoint.getCoordinates(), COLOR_CHOICEPOINT);
            }
        }

        for (lions_and_men.applet_graph.algorithm.graph.Vertex vertex : getLionsWithManualInput()) {
            new ChoicePoint(this, vertex.getCoordinates(), vertex.getCoordinates(), COLOR_LION);
            for (Connection con : vertex.getConnections()) {
                lions_and_men.applet_graph.algorithm.graph.Vertex choicePoint = con.getNeighbor(vertex);
                new ChoicePoint(this, vertex.getCoordinates(), choicePoint.getCoordinates(), COLOR_CHOICEPOINT);
            }
        }
    }

    @Override
    public boolean simulateStep() {
        boolean bool = super.simulateStep();

        updateStepPreviewsAndChoicePoints();
        updateManRanges();
        updateLionRanges();

        return bool;
    }
}
