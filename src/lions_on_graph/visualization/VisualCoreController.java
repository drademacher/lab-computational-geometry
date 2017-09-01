package lions_on_graph.visualization;

import lions_in_plane.visualization.VisualizedCoreController;
import lions_on_graph.core.CoreController;
import lions_on_graph.core.graph.Connection;
import util.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static lions_on_graph.visualization.Constants.*;

public class VisualCoreController extends CoreController {

    private Map<lions_on_graph.core.graph.Vertex, Vertex> mapVertices = new HashMap<>();
    private Map<lions_on_graph.core.entities.Entity, Entity> mapEntities = new HashMap<>();
    private Map<lions_on_graph.core.graph.Edge, Edge> mapEdges = new HashMap<>();
    private Map<lions_on_graph.core.entities.Man, ArrayList<Range>> mapManRange = new HashMap<>();
    private Map<lions_on_graph.core.entities.Lion, ArrayList<Range>> mapLionRange = new HashMap<>();
    private Map<lions_on_graph.core.entities.Entity, Vertex> mapStepPreviews = new HashMap<>();
//    private CoreController coreController;

    public VisualCoreController(/*CoreController coreController*/) {
        super();
//        this.coreController = coreController;
    }

    public void removeAllShapes() {
        for (Map.Entry<lions_on_graph.core.graph.Vertex, Vertex> entry : mapVertices.entrySet()) {
            entry.getValue().delete();
        }
        mapVertices.clear();

        for (Map.Entry<lions_on_graph.core.entities.Entity, Entity> entry : mapEntities.entrySet()) {
            entry.getValue().delete();
        }
        mapEntities.clear();

        for (Map.Entry<lions_on_graph.core.graph.Edge, Edge> entry : mapEdges.entrySet()) {
            entry.getValue().delete();
        }
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

        Vertex shape = mapVertices.get(vertex);
        shape.relocate(newCoordinate);

        for (lions_on_graph.core.graph.Edge edge : vertex.getEdges()) {
            Edge edgeShape = mapEdges.get(edge);
            edgeShape.relocate(edge.getVertices()[0].getCoordinates(), edge.getVertices()[1].getCoordinates());

            for (lions_on_graph.core.graph.SmallVertex smallVertex : edge.getEdgeVertices()) {
                Vertex smalLVertexShape = mapVertices.get(smallVertex);
                smalLVertexShape.relocate(smallVertex.getCoordinates());
            }
        }
        for (Map.Entry<lions_on_graph.core.entities.Entity, Entity> entry : mapEntities.entrySet()) {
            lions_on_graph.core.entities.Entity entity = entry.getKey();
            Entity shapedEntity = entry.getValue();

            shapedEntity.relocate(entity.getCoordinates());
        }
    }

    public void createVertex(Point coordinate) {
        super.createVertex(coordinate);
        BigVertex shape = new BigVertex(this, coordinate);
        mapVertices.put(getBigVertexByCoordinate(coordinate), shape);
    }

    public void deleteVertex(Point vertexCoordinates) {
        Vertex shape = mapVertices.get(vertex);
        shape.delete();
        mapVertices.remove(vertex);

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

        Edge shape = new Edge(this, edge.getStartCoordinates(), edge.getEndCoordinates());
        mapEdges.put(edge, shape);

        for (lions_on_graph.core.graph.SmallVertex smallVertex : edge.getEdgeVertices()) {
            SmallVertex smallVertexShape = new SmallVertex(this, smallVertex.getCoordinates());
            mapVertices.put(smallVertex, smallVertexShape);
        }

        this.visualCoreController.updateAllLionRanges(lions);
        this.visualCoreController.updateAllManRanges(men);
    }

    public void removeEdge(Point vertex1Coordinates, Point vertex2Coordinates) {

        super.removeEdge(vertex1Coordinates, vertex2Coordinates);

        Edge shape = mapEdges.get(edge);
        shape.delete();
        mapEdges.remove(edge);
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

        Man shape = new Man(coreController, man.getCoordinates());
        mapEntities.put(man, shape);
        updateManRange(man);

        this.visualCoreController.updateStepPreviewsAndChoicePoints();
    }

    public void setLion(Point vertexCoorinate) {

        super.setLion(vertexCoorinate);

        Lion shape = new Lion(coreController, lion.getCoordinates());
        mapEntities.put(lion, shape);
        updateLionRange(lion);

        this.visualCoreController.updateStepPreviewsAndChoicePoints();
    }

    public void relocateMan(Point manCoordinate, Point vertexCoordinate) {

        super.relocateMan(manCoordinate, vertexCoordinate);

        Entity shape = mapEntities.get(man);
        shape.relocate(man.getCoordinates());
        updateManRange(man);


        this.visualCoreController.updateStepPreviewsAndChoicePoints();
    }

    public void relocateLion(Point lionCoordinate, Point vertexCoordinate) {

        super.relocateLion(lionCoordinate, vertexCoordinate);

        Entity shape = mapEntities.get(lion);
        shape.relocate(lion.getCoordinates());
        updateLionRange(lion);

        this.visualCoreController.updateStepPreviewsAndChoicePoints();
    }

    public void removeMan(Point manCoordinate) {
        Entity shape = mapEntities.get(man);
        shape.delete();

        super.removeMan(manCoordinate);
        this.visualCoreController.updateStepPreviewsAndChoicePoints();
    }

    public void removeLion(Point lionCoordinate) {
        Entity shape = mapEntities.get(lion);
        shape.delete();

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

    public void updateManRange(lions_on_graph.core.entities.Man man) {
        ArrayList<Range> rangeVertices = mapManRange.get(man);
        if (rangeVertices == null) {
            rangeVertices = new ArrayList<>();
        }

        while (man.getRangeVertices().size() > rangeVertices.size()) {
            rangeVertices.add(new Range(coreController, new Point(0, 0), true));
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
