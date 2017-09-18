package lions_and_men.applet_graph.visualization;

import lions_and_men.applet_graph.algorithm.CoreController;
import lions_and_men.applet_graph.algorithm.graph.Connection;
import lions_and_men.applet_graph.algorithm.strategies.LionStrategyEnum;
import lions_and_men.applet_graph.algorithm.strategies.ManStrategyEnum;
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

    public void cleanUp() {
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
    }

    /* ****************************
     *
     *   Graph Shapes
     *
     * ****************************/

    public void relocateVertex(Point vertexCoordinates, Point newCoordinate) {

        super.relocateVertex(vertexCoordinates, newCoordinate);

        verticesBig.stream().filter(vertex -> vertex.getPosition().equals(vertexCoordinates)).forEach(vertex -> vertex.relocate(newCoordinate));

        edges.stream().filter(edge -> edge.getPositionFrom().equals(vertexCoordinates)).forEach(edge -> edge.relocate(newCoordinate, edge.getPositionTo()));
        edges.stream().filter(edge -> edge.getPositionTo().equals(vertexCoordinates)).forEach(edge -> edge.relocate(edge.getPositionFrom(), newCoordinate));

        verticesSmall.forEach(Vertex::delete);
        graph.getSmallVertices().forEach(smallVertex -> verticesSmall.add(new SmallVertex(this, smallVertex.getCoordinates())));

        entities.forEach(Entity::delete);
        entities.clear();
        getMen().forEach(man -> entities.add(new Man(this, man.getCoordinates())));
        getLions().forEach(lion -> entities.add(new Lion(this, lion.getCoordinates())));

        entities.stream().filter(entity -> entity.getPosition().equals(vertexCoordinates)).forEach(entity -> entity.relocate(newCoordinate));
    }

    public void createVertex(Point coordinate) {
        super.createVertex(coordinate);

        verticesBig.add(new BigVertex(this, coordinate));
    }

    public void deleteVertex(Point vertexCoordinates) {


        super.deleteVertex(vertexCoordinates);


        verticesBig.forEach(Vertex::delete);
        verticesBig.clear();
        graph.getBigVertices().forEach(bigVertex -> verticesBig.add(new BigVertex(this, bigVertex.getCoordinates())));

        edges.forEach(Edge::delete);
        edges.clear();
        graph.getEdges().forEach(edge -> edges.add(new Edge(this, edge.getStartCoordinates(), edge.getEndCoordinates())));

        verticesSmall.forEach(Vertex::delete);
        verticesSmall.clear();
        graph.getSmallVertices().forEach(smallVertex -> verticesSmall.add(new SmallVertex(this, smallVertex.getCoordinates())));


        updateManRanges();
        updateLionRanges();
    }

    public void createEdge(Point vertex1Coordinates, Point vertex2Coordinates) {
        createEdge(vertex1Coordinates, vertex2Coordinates, getDefaultEdgeWeight());
    }

    public void createEdge(Point vertex1Coordinates, Point vertex2Coordinates, int weight) {

        super.createEdge(vertex1Coordinates, vertex2Coordinates, weight);

        lions_and_men.applet_graph.algorithm.graph.Edge edge = getEdgeByPoints(vertex1Coordinates, vertex2Coordinates);
        if (edge == null) {
            return;
        }
        edges.add(new Edge(this, edge.getStartCoordinates(), edge.getEndCoordinates()));
        edge.getEdgeVertices().forEach(smallVertex -> verticesSmall.add(new SmallVertex(this, smallVertex.getCoordinates())));


        updateManRanges();
        updateLionRanges();
    }

    public void removeEdge(Point vertex1Coordinates, Point vertex2Coordinates) {

        super.removeEdge(vertex1Coordinates, vertex2Coordinates);

        //clear all edges
        this.edges.forEach(Edge::delete);
        this.edges.clear();
        this.verticesSmall.forEach(Vertex::delete);
        this.verticesSmall.clear();

        //create all edges still existing
        this.graph.getEdges().forEach(edge -> {
            edges.add(new Edge(this, edge.getStartCoordinates(), edge.getEndCoordinates()));
            edge.getEdgeVertices().forEach(smallVertex -> verticesSmall.add(new SmallVertex(this, smallVertex.getCoordinates())));
        });


        updateManRanges();
        updateLionRanges();
    }

    @Override
    public void changeEdgeWeight(Point vertex1Coordinates, Point vertex2Coordinates, int weight) {

        super.changeEdgeWeight(vertex1Coordinates, vertex2Coordinates, weight);

        verticesSmall.forEach(Vertex::delete);
        graph.getSmallVertices().forEach(smallVertex -> verticesSmall.add(new SmallVertex(this, smallVertex.getCoordinates())));

        entities.forEach(Entity::delete);
        entities.clear();
        getMen().forEach(man -> entities.add(new Man(this, man.getCoordinates())));
        getLions().forEach(lion -> entities.add(new Lion(this, lion.getCoordinates())));

        updateManRanges();
        updateLionRanges();
    }

    /* ****************************
     *
     *   ENTITY Shapes
     *
     * ****************************/

    public void setMan(Point vertexCoorinate) {

        super.setMan(vertexCoorinate);

        entities.add(new Man(this, vertexCoorinate));
        updateManRanges();

        updateStepPreviewsAndChoicePoints();
    }

    public void setLion(Point vertexCoorinate) {

        super.setLion(vertexCoorinate);

        entities.add(new Lion(this, vertexCoorinate));
        updateLionRanges();

        updateStepPreviewsAndChoicePoints();
    }

    public void relocateMan(Point manCoordinate, Point vertexCoordinate) {

        super.relocateMan(manCoordinate, vertexCoordinate);

        Point realVertexCoordinate = graph.getVertexByCoordinate(vertexCoordinate).getCoordinates();
        entities.stream().filter(entity -> entity.getPosition().equals(manCoordinate)).forEach(entity -> entity.relocate(realVertexCoordinate));
        updateManRanges();

        updateStepPreviewsAndChoicePoints();
    }

    public void relocateLion(Point lionCoordinate, Point vertexCoordinate) {

        super.relocateLion(lionCoordinate, vertexCoordinate);

        Point realVertexCoordinate = graph.getVertexByCoordinate(vertexCoordinate).getCoordinates();
        entities.stream().filter(entity -> entity.getPosition().equals(lionCoordinate)).forEach(entity -> entity.relocate(realVertexCoordinate));
        updateLionRanges();
        updateStepPreviewsAndChoicePoints();
    }

    public void removeMan(Point manCoordinate) {

        entities.stream().filter(entity -> entity.getPosition().equals(manCoordinate)).forEach(Entity::delete);
        entities.removeIf(entity -> entity.getPosition().equals(manCoordinate));

        super.removeMan(manCoordinate);
        updateStepPreviewsAndChoicePoints();
    }

    public void removeLion(Point lionCoordinate) {

        entities.stream().filter(entity -> entity.getPosition().equals(lionCoordinate)).forEach(Entity::delete);
        entities.removeIf(entity -> entity.getPosition().equals(lionCoordinate));

        super.removeLion(lionCoordinate);
        updateStepPreviewsAndChoicePoints();
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
    public void setManStrategy(Point manCoordinate, ManStrategyEnum strategy) {
        super.setManStrategy(manCoordinate, strategy);

        updateStepPreviewsAndChoicePoints();
    }

    @Override
    public void setLionStrategy(Point lionCoordinate, LionStrategyEnum strategy) {
        super.setLionStrategy(lionCoordinate, strategy);

        updateStepPreviewsAndChoicePoints();
    }

    @Override
    public void setAllManStrategy(ManStrategyEnum strategy) {
        super.setAllManStrategy(strategy);

        updateStepPreviewsAndChoicePoints();
    }

    @Override
    public void setAllLionStrategy(LionStrategyEnum strategy) {
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

        getMen().forEach(man -> man.getRangeVertices().forEach(vertex -> rangesMan.add(new Range(this, vertex.getCoordinates(), true))));
    }

    @Override
    public void setLionRange(Point lionCoordinate, int range) {
        super.setLionRange(lionCoordinate, range);

        updateLionRanges();
    }

    private void updateLionRanges() {

        rangesLion.forEach(Range::delete);
        rangesLion.clear();

        getLions().forEach(lion -> lion.getRangeVertices().forEach(vertex -> rangesLion.add(new Range(this, vertex.getCoordinates()))));
    }


    private void updateStepPreviewsAndChoicePoints() {

        //TODO flush and create new....
        // step previews
        stepPreviews.forEach(Vertex::delete);
        stepPreviews.clear();

        getMen().forEach(man -> stepPreviews.add(new StepPreview(this, man.getNextPosition().getCoordinates())));
        getLions().forEach(lion -> stepPreviews.add(new StepPreview(this, lion.getNextPosition().getCoordinates())));


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
