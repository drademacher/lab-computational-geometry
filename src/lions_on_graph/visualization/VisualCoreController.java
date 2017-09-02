package lions_on_graph.visualization;

import lions_on_graph.core.CoreController;
import lions_on_graph.core.graph.Connection;
import util.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static lions_on_graph.visualization.Constants.*;

public class VisualCoreController extends CoreController {

    private ArrayList<Vertex> mapVertices = new ArrayList<>();
    private ArrayList<Entity> mapEntities = new ArrayList<>();
    private ArrayList<Edge> mapEdges = new ArrayList<>();
    private Map<Man, ArrayList<Range>> mapManRange = new HashMap<>();
    private Map<Lion, ArrayList<Range>> mapLionRange = new HashMap<>();
    private Map<Entity, Vertex> mapStepPreviews = new HashMap<>();
//    private CoreController coreController;

    public VisualCoreController(/*CoreController coreController*/) {
        super();
//        this.coreController = coreController;
    }

    public void removeAllShapes() {
        mapVertices.forEach(vertex -> vertex.delete());
        mapVertices.clear();

        mapEntities.forEach(entity -> entity.delete());
        mapEntities.clear();

        mapEdges.forEach(edge -> edge.delete());
        mapEdges.clear();

        for (Map.Entry<lions_on_graph.core.entities.Man, ArrayList<Range>> entry : mapManRange.entrySet()) {
            ArrayList<Range> shapeList = entry.getValue();
            if (shapeList == null) {
                shapeList = new ArrayList<>();
            }
            for (Range shape : shapeList) {
                shape.delete();
            }
        }
        mapLionRange.clear();

        for (Map.Entry<lions_on_graph.core.entities.Lion, ArrayList<Range>> entry : mapLionRange.entrySet()) {
            ArrayList<Range> shapeList = entry.getValue();
            if (shapeList == null) {
                shapeList = new ArrayList<>();
            }
            for (Range shape : shapeList) {
                shape.delete();
            }
        }
        mapLionRange.clear();

        for (Map.Entry<lions_on_graph.core.entities.Entity, Vertex> entry : mapStepPreviews.entrySet()) {
            entry.getValue().delete();
        }
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
            for (lions_on_graph.core.graph.SmallVertex smallVertex : edge.getEdgeVertices()) {
                Vertex smalLVertexShape = mapVertices.get(smallVertex);
                smalLVertexShape.relocate(smallVertex.getCoordinates());
            }
//        }

        mapEntities.stream().filter(entity -> entity.getPosition().equals(vertexCoordinates)).forEach(entity -> entity.relocate(newCoordinate));
    }

    public void createVertex(Point coordinate) {
        super.createVertex(coordinate);

        mapVertices.add(new BigVertex(this, coordinate));
    }

    public void deleteVertex(Point vertexCoordinates) {

        mapVertices.stream().filter(vertex -> vertex.getPosition().equals(vertexCoordinates)).forEach(vertex -> vertex.delete());
        mapVertices.removeIf(vertex -> vertex.getPosition().equals(vertexCoordinates));


        for (lions_on_graph.core.graph.Edge edge : vertex.getEdges()) {
            Edge edgeShape = mapEdges.get(edge);
            mapEdges.remove(edge);
            edgeShape.delete();

            for (lions_on_graph.core.graph.SmallVertex smallVertex : edge.getEdgeVertices()) {
                Vertex smallVertexShape = mapVertices.get(smallVertex);
                mapVertices.remove(smallVertex);
                smallVertexShape.delete();
            }
        }

        super.deleteVertex(vertexCoordinates);


        this.visualCoreController.updateAllLionRanges(lions);
        this.visualCoreController.updateAllManRanges(men);
    }

    public void createEdge(Point vertex1Coordinates, Point vertex2Coordinates) {
        createEdge(vertex1Coordinates, vertex2Coordinates, getDefaultEdgeWeight());
    }

    public void createEdge(Point vertex1Coordinates, Point vertex2Coordinates, int weight) {

        super.createEdge(vertex1Coordinates, vertex2Coordinates, weight);

        mapEdges.add(new Edge(this, vertex1Coordinates, vertex2Coordinates));

        //TODO
        for (lions_on_graph.core.graph.SmallVertex smallVertex : edge.getEdgeVertices()) {
            SmallVertex smallVertexShape = new SmallVertex(this, smallVertex.getCoordinates());
            mapVertices.put(smallVertex, smallVertexShape);
        }

        this.visualCoreController.updateAllLionRanges(lions);
        this.visualCoreController.updateAllManRanges(men);
    }

    public void removeEdge(Point vertex1Coordinates, Point vertex2Coordinates) {

        super.removeEdge(vertex1Coordinates, vertex2Coordinates);

        mapEdges.stream().filter
                (edge ->
                        ((edge.getPositionTo().equals(vertex1Coordinates) && edge.getPositionFrom().equals(vertex2Coordinates))
                        || (edge.getPositionTo().equals(vertex2Coordinates) && edge.getPositionFrom().equals(vertex1Coordinates))))
                .forEach(edge -> edge.delete());

        mapEdges.removeIf(edge ->
                ((edge.getPositionTo().equals(vertex1Coordinates) && edge.getPositionFrom().equals(vertex2Coordinates))
                || (edge.getPositionTo().equals(vertex2Coordinates) && edge.getPositionFrom().equals(vertex1Coordinates))));


        //TODO
        for (lions_on_graph.core.graph.SmallVertex smallVertex : edge.getEdgeVertices()) {
            Vertex smalLVertexShape = mapVertices.get(smallVertex);
            mapVertices.remove(smallVertex);
            smalLVertexShape.delete();
        }

        this.visualCoreController.updateAllLionRanges(lions);
        this.visualCoreController.updateAllManRanges(men);
    }

    @Override
    public void changeEdgeWeight(Point vertex1Coordinates, Point vertex2Coordinates, int weight) {

        removeEdge(vertex1Coordinates, vertex2Coordinates);
        createEdge(vertex1Coordinates, vertex2Coordinates, weight);
//        super.changeEdgeWeight(vertex1Coordinates, vertex2Coordinates, weight);

        this.visualCoreController.updateAllLionRanges(lions);
        this.visualCoreController.updateAllManRanges(men);
    }

    /* ****************************
     *
     *   ENTITY Shapes
     *
     * ****************************/

    public void setMan(Point vertexCoorinate) {

        super.setMan(vertexCoorinate);

        mapEntities.add(new Man(this, vertexCoorinate);
        updateManRange(man);

        this.visualCoreController.updateStepPreviewsAndChoicePoints();
    }

    public void setLion(Point vertexCoorinate) {

        super.setLion(vertexCoorinate);

        mapEntities.add(new Lion(this, vertexCoorinate);
        updateLionRange(lion);

        this.visualCoreController.updateStepPreviewsAndChoicePoints();
    }

    public void relocateMan(Point manCoordinate, Point vertexCoordinate) {

        super.relocateMan(manCoordinate, vertexCoordinate);



        updateManRange(man);


        this.visualCoreController.updateStepPreviewsAndChoicePoints();
    }

    public void relocateLion(Point lionCoordinate, Point vertexCoordinate) {

        super.relocateLion(lionCoordinate, vertexCoordinate);

        mapEntities.stream().filter(entity -> entity.getPosition().equals(lionCoordinate)).forEach(entity -> entity.relocate(vertexCoordinate));
        updateLionRange(lion);

        this.visualCoreController.updateStepPreviewsAndChoicePoints();
    }

    public void removeMan(Point manCoordinate) {

        mapEntities.stream().filter(entity -> entity.getPosition().equals(manCoordinate)).forEach(entity -> entity.delete());
        mapEntities.removeIf(entity -> entity.getPosition().equals(manCoordinate));

        super.removeMan(manCoordinate);
        this.visualCoreController.updateStepPreviewsAndChoicePoints();
    }

    public void removeLion(Point lionCoordinate) {

        mapEntities.stream().filter(entity -> entity.getPosition().equals(lionCoordinate)).forEach(entity -> entity.delete());
        mapEntities.removeIf(entity -> entity.getPosition().equals(lionCoordinate));

        super.removeLion(lionCoordinate);
        this.visualCoreController.updateStepPreviewsAndChoicePoints();
    }

    @Override
    public void setManStrategy(Point manCoordinate, ManStrategy strategy) {
        super.setManStrategy(manCoordinate, strategy);

        visualCoreController.updateStepPreviewsAndChoicePoints();
    }

    @Override
    public void setLionStrategy(Point lionCoordinate, LionStrategy strategy) {
        super.setLionStrategy(lionCoordinate, strategy);

        visualCoreController.updateStepPreviewsAndChoicePoints();
    }

    @Override
    public void setAllManStrategy(ManStrategy strategy) {
        super.setAllManStrategy(strategy);

        visualCoreController.updateStepPreviewsAndChoicePoints();
    }

    @Override
    public void setAllLionStrategy(LionStrategy strategy) {
        super.setAllLionStrategy(strategy);

        visualCoreController.updateStepPreviewsAndChoicePoints();
    }

    @Override
    public void setManRange(Point manCoordinate, int range) {
        super.setManRange(manCoordinate, range);

        visualCoreController.updateManRange(man);
    }

    public void updateManRange(Man man) {
        ArrayList<Range> rangeVertices = mapManRange.get(man);
        if (rangeVertices == null) {
            rangeVertices = new ArrayList<>();
        }

//        rangeVertices.clear();
//        for(){
//            rangeVertices.add(new Range(coreController, new Point(0, 0), true));
//        }

        while (man.getRangeVertices().size() > rangeVertices.size()) {

        }
        while (man.getRangeVertices().size() < rangeVertices.size()) {
            rangeVertices.get(0).delete();
            rangeVertices.remove(0);
        }

        //update
        for (int i = 0; i < rangeVertices.size(); i++) {
            rangeVertices.get(i).relocate(man.getRangeVertices().get(i).getCoordinates());
        }

        mapManRange.put(man, rangeVertices);
    }

    public void updateAllManRanges(ArrayList<lions_on_graph.core.entities.Man> men) {
        for (lions_on_graph.core.entities.Man man : men) {
            updateManRange(man);
        }
    }

    @Override
    public void setLionRange(Point lionCoordinate, int range) {
        super.setLionRange(lionCoordinate, range);

        visualCoreController.updateLionRange(lion);
    }

    public void updateLionRange(lions_on_graph.core.entities.Lion lion) {
        ArrayList<Range> rangeVertices = mapLionRange.get(lion);
        if (rangeVertices == null) {
            rangeVertices = new ArrayList<>();
        }

        while (lion.getRangeVertices().size() > rangeVertices.size()) {
            rangeVertices.add(new Range(coreController, new Point(0, 0), false));
        }
        while (lion.getRangeVertices().size() < rangeVertices.size()) {
            rangeVertices.get(0).delete();
            rangeVertices.remove(0);
        }

        //update
        for (int i = 0; i < rangeVertices.size(); i++) {
            rangeVertices.get(i).relocate(lion.getRangeVertices().get(i).getCoordinates());
        }

        mapLionRange.put(lion, rangeVertices);
    }

    public void updateAllLionRanges(ArrayList<lions_on_graph.core.entities.Lion> lions) {
        for (lions_on_graph.core.entities.Lion lion : lions) {
            updateLionRange(lion);
        }
    }

    public void updateStepPreviewsAndChoicePoints() {
        // step previews
        for (Map.Entry<lions_on_graph.core.entities.Entity, Vertex> entry : mapStepPreviews.entrySet()) {
            lions_on_graph.core.entities.Entity entity = entry.getKey();
            Vertex shapedPreview = entry.getValue();

            shapedPreview.delete();
        }
        mapStepPreviews.clear();//TODO dont flush and create new

        for (lions_on_graph.core.entities.Man man : this.coreController.getMen()) {
            mapStepPreviews.put(man, new StepPreview(coreController, man.getNextPosition().getCoordinates()));
        }

        for (lions_on_graph.core.entities.Lion lion : this.coreController.getLions()) {
            mapStepPreviews.put(lion, new StepPreview(coreController, lion.getNextPosition().getCoordinates()));
        }

        // choice points
        // TODO: jens, meinst du die API ist irgendwie schön designt ist?


        ChoicePoint.clear();
        for (lions_on_graph.core.entities.Man man : coreController.getMenWithManualInput()) {
            new ChoicePoint(coreController, man, man.getCoordinates(), COLOR_MAN);
            for (Connection con : man.getCurrentPosition().getConnections()) {
                lions_on_graph.core.graph.Vertex choicePoint = con.getNeighbor(man.getCurrentPosition());
                new ChoicePoint(coreController, man, choicePoint.getCoordinates(), COLOR_CHOICEPOINT);
            }
        }

        for (lions_on_graph.core.entities.Lion lion : coreController.getLionsWithManualInput()) {
            new ChoicePoint(coreController, lion, lion.getCoordinates(), COLOR_LION);
            for (Connection con : lion.getCurrentPosition().getConnections()) {
                lions_on_graph.core.graph.Vertex choicePoint = con.getNeighbor(lion.getCurrentPosition());
                new ChoicePoint(coreController, lion, choicePoint.getCoordinates(), COLOR_CHOICEPOINT);
            }
        }
    }

    @Override
    public boolean simulateStep() {
        boolean lionsHaveWon = super.simulateStep();

        for (Man man : this.getMen()) {
            visualCoreController.relocateMan(man);
        }
        for (Lion lion : this.getLions()) {
            visualCoreController.relocateLion(lion);
        }
        this.visualCoreController.updateStepPreviewsAndChoicePoints();

        return lionsHaveWon;
    }
}
