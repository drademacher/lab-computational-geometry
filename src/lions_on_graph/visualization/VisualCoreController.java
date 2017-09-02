package lions_on_graph.visualization;

import lions_on_graph.core.CoreController;
import lions_on_graph.core.graph.Connection;
import util.Point;

import java.util.ArrayList;

import static lions_on_graph.visualization.Constants.*;

public class VisualCoreController extends CoreController {

    private ArrayList<Vertex> mapVertices = new ArrayList<>();
    private ArrayList<Entity> mapEntities = new ArrayList<>();
    private ArrayList<Edge> mapEdges = new ArrayList<>();
    private ArrayList<Range> mapManRange = new ArrayList<>();
    private ArrayList<Range> mapLionRange = new ArrayList<>();
    private ArrayList<Vertex> mapStepPreviews = new ArrayList<>();


    public VisualCoreController() {
        super();
    }

    public void cleanUp() {
        mapVertices.forEach(Vertex::delete);
        mapVertices.clear();

        mapEntities.forEach(Entity::delete);
        mapEntities.clear();

        mapEdges.forEach(Edge::delete);
        mapEdges.clear();

        mapManRange.forEach(Range::delete);
        mapManRange.clear();

        mapLionRange.forEach(Range::delete);
        mapLionRange.clear();

        mapStepPreviews.forEach(Vertex::delete);
        mapStepPreviews.clear();
    }

    /* ****************************
     *
     *   Graph Shapes
     *
     * ****************************/


    public void relocateVertex(Point vertexCoordinates, Point newCoordinate) {

        super.relocateVertex(vertexCoordinates, newCoordinate);

        mapVertices.stream().filter(vertex -> vertex.getPosition().equals(vertexCoordinates)).forEach(vertex -> vertex.relocate(newCoordinate));

        mapEdges.stream().filter(edge -> edge.getPositionFrom().equals(vertexCoordinates)).forEach(edge -> edge.relocate(newCoordinate, edge.getPositionTo()));
        mapEdges.stream().filter(edge -> edge.getPositionTo().equals(vertexCoordinates)).forEach(edge -> edge.relocate(edge.getPositionFrom(), newCoordinate));


//        for (lions_on_graph.core.graph.Edge edge : vertex.getEdges()) {
//            Edge edgeShape = mapEdges.get(edge);
//            edgeShape.relocate(edge.getVertices()[0].getCoordinates(), edge.getVertices()[1].getCoordinates());


//TODO
//        for (lions_on_graph.core.graph.SmallVertex smallVertex : edge.getEdgeVertices()) {
//            Vertex smalLVertexShape = mapVertices.get(smallVertex);
//            smalLVertexShape.relocate(smallVertex.getCoordinates());
//        }
//        }

        mapEntities.stream().filter(entity -> entity.getPosition().equals(vertexCoordinates)).forEach(entity -> entity.relocate(newCoordinate));
    }

    public void createVertex(Point coordinate) {
        super.createVertex(coordinate);

        mapVertices.add(new BigVertex(this, coordinate));
    }

    public void deleteVertex(Point vertexCoordinates) {

        mapVertices.stream().filter(vertex -> vertex.getPosition().equals(vertexCoordinates)).forEach(Vertex::delete);
        mapVertices.removeIf(vertex -> vertex.getPosition().equals(vertexCoordinates));

        mapEdges.stream().filter(edge -> (edge.getPositionTo().equals(vertexCoordinates)
                || edge.getPositionFrom().equals(vertexCoordinates))).forEach(Edge::delete);
        mapEdges.removeIf(edge -> (edge.getPositionTo().equals(vertexCoordinates)
                || edge.getPositionFrom().equals(vertexCoordinates)));


        for (lions_on_graph.core.graph.Edge edge : getBigVertexByCoordinate(vertexCoordinates).getEdges()) {
            for (lions_on_graph.core.graph.SmallVertex smallVertex : edge.getEdgeVertices()) {


                mapVertices.stream().filter(vertex -> vertex.equals(smallVertex)).forEach(Vertex::delete);
                mapVertices.removeIf(vertex -> vertex.equals(smallVertex));

            }
        }

        super.deleteVertex(vertexCoordinates);


        updateManRanges();
        updateLionRanges();
    }

    public void createEdge(Point vertex1Coordinates, Point vertex2Coordinates) {
        createEdge(vertex1Coordinates, vertex2Coordinates, getDefaultEdgeWeight());
    }

    public void createEdge(Point vertex1Coordinates, Point vertex2Coordinates, int weight) {

        super.createEdge(vertex1Coordinates, vertex2Coordinates, weight);

        Edge edge = new Edge(this, vertex1Coordinates, vertex2Coordinates);
        mapEdges.add(edge);

        getEdgeByPoints(vertex1Coordinates, vertex2Coordinates).getEdgeVertices().forEach(smallVertex -> mapVertices.add(new SmallVertex(this, smallVertex.getCoordinates(), edge)));


        updateManRanges();
        updateLionRanges();
    }

    public void removeEdge(Point vertex1Coordinates, Point vertex2Coordinates) {

        super.removeEdge(vertex1Coordinates, vertex2Coordinates);

        mapEdges.stream().filter
                (edge ->
                        ((edge.getPositionTo().equals(vertex1Coordinates) && edge.getPositionFrom().equals(vertex2Coordinates))
                                || (edge.getPositionTo().equals(vertex2Coordinates) && edge.getPositionFrom().equals(vertex1Coordinates))))
                .forEach(Edge::delete);

        mapEdges.removeIf(edge ->
                ((edge.getPositionTo().equals(vertex1Coordinates) && edge.getPositionFrom().equals(vertex2Coordinates))
                        || (edge.getPositionTo().equals(vertex2Coordinates) && edge.getPositionFrom().equals(vertex1Coordinates))));


        for (lions_on_graph.core.graph.SmallVertex smallVertex : getEdgeByPoints(vertex1Coordinates, vertex2Coordinates).getEdgeVertices()) {
            mapVertices.stream().filter(vertex -> vertex.equals(smallVertex)).forEach(Vertex::delete);
            mapVertices.removeIf(vertex -> vertex.equals(smallVertex));

        }


        updateManRanges();
        updateLionRanges();
    }

    @Override
    public void changeEdgeWeight(Point vertex1Coordinates, Point vertex2Coordinates, int weight) {

        removeEdge(vertex1Coordinates, vertex2Coordinates);
        createEdge(vertex1Coordinates, vertex2Coordinates, weight);
//        super.changeEdgeWeight(vertex1Coordinates, vertex2Coordinates, weight);

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

        mapEntities.add(new Man(this, vertexCoorinate));
        updateManRanges();

        updateStepPreviewsAndChoicePoints();
    }

    public void setLion(Point vertexCoorinate) {

        super.setLion(vertexCoorinate);

        mapEntities.add(new Lion(this, vertexCoorinate));
        updateLionRanges();

        updateStepPreviewsAndChoicePoints();
    }

    public void relocateMan(Point manCoordinate, Point vertexCoordinate) {

        super.relocateMan(manCoordinate, vertexCoordinate);

        mapEntities.stream().filter(entity -> entity.getPosition().equals(manCoordinate)).forEach(entity -> entity.relocate(vertexCoordinate));
        updateManRanges();


        updateStepPreviewsAndChoicePoints();
    }

    public void relocateLion(Point lionCoordinate, Point vertexCoordinate) {

        super.relocateLion(lionCoordinate, vertexCoordinate);

        mapEntities.stream().filter(entity -> entity.getPosition().equals(lionCoordinate)).forEach(entity -> entity.relocate(vertexCoordinate));
        updateLionRanges();
        updateStepPreviewsAndChoicePoints();
    }

    public void removeMan(Point manCoordinate) {

        mapEntities.stream().filter(entity -> entity.getPosition().equals(manCoordinate)).forEach(Entity::delete);
        mapEntities.removeIf(entity -> entity.getPosition().equals(manCoordinate));

        super.removeMan(manCoordinate);
        updateStepPreviewsAndChoicePoints();
    }

    public void removeLion(Point lionCoordinate) {

        mapEntities.stream().filter(entity -> entity.getPosition().equals(lionCoordinate)).forEach(Entity::delete);
        mapEntities.removeIf(entity -> entity.getPosition().equals(lionCoordinate));

        super.removeLion(lionCoordinate);
        updateStepPreviewsAndChoicePoints();
    }

    @Override
    public void setManStrategy(Point manCoordinate, ManStrategy strategy) {
        super.setManStrategy(manCoordinate, strategy);

        updateStepPreviewsAndChoicePoints();
    }

    @Override
    public void setLionStrategy(Point lionCoordinate, LionStrategy strategy) {
        super.setLionStrategy(lionCoordinate, strategy);

        updateStepPreviewsAndChoicePoints();
    }

    @Override
    public void setAllManStrategy(ManStrategy strategy) {
        super.setAllManStrategy(strategy);

        updateStepPreviewsAndChoicePoints();
    }

    @Override
    public void setAllLionStrategy(LionStrategy strategy) {
        super.setAllLionStrategy(strategy);

        updateStepPreviewsAndChoicePoints();
    }

    @Override
    public void setManRange(Point manCoordinate, int range) {
        super.setManRange(manCoordinate, range);

        updateManRanges();
    }

    private void updateManRanges() {

        mapManRange.forEach(Range::delete);
        mapManRange.clear();

        getMen().forEach(man -> man.getRangeVertices().forEach(vertex -> mapManRange.add(new Range(this, vertex.getCoordinates(), true))));
    }

    @Override
    public void setLionRange(Point lionCoordinate, int range) {
        super.setLionRange(lionCoordinate, range);

        updateLionRanges();
    }

    private void updateLionRanges() {

        mapLionRange.forEach(Range::delete);
        mapLionRange.clear();

        getLions().forEach(lion -> lion.getRangeVertices().forEach(vertex -> mapLionRange.add(new Range(this, vertex.getCoordinates()))));
    }


    private void updateStepPreviewsAndChoicePoints() {

        //TODO flush and create new....
        // step previews
        mapStepPreviews.forEach(Vertex::delete);
        mapStepPreviews.clear();

        getMen().forEach(man -> mapStepPreviews.add(new StepPreview(this, man.getNextPosition().getCoordinates())));
        getLions().forEach(lion -> mapStepPreviews.add(new StepPreview(this, lion.getNextPosition().getCoordinates())));


        // choice points
        ChoicePoint.clear();
        for (lions_on_graph.core.entities.Man man : getMenWithManualInput()) {
            new ChoicePoint(this, man, man.getCoordinates(), COLOR_MAN);
            for (Connection con : man.getCurrentPosition().getConnections()) {
                lions_on_graph.core.graph.Vertex choicePoint = con.getNeighbor(man.getCurrentPosition());
                new ChoicePoint(this, man, choicePoint.getCoordinates(), COLOR_CHOICEPOINT);
            }
        }

        for (lions_on_graph.core.entities.Lion lion : getLionsWithManualInput()) {
            new ChoicePoint(this, lion, lion.getCoordinates(), COLOR_LION);
            for (Connection con : lion.getCurrentPosition().getConnections()) {
                lions_on_graph.core.graph.Vertex choicePoint = con.getNeighbor(lion.getCurrentPosition());
                new ChoicePoint(this, lion, choicePoint.getCoordinates(), COLOR_CHOICEPOINT);
            }
        }
    }
}
