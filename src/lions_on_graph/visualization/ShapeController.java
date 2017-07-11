package lions_on_graph.visualization;

import lions_on_graph.core.CoreController;
import lions_on_graph.core.entities.Entity;
import lions_on_graph.core.entities.Lion;
import lions_on_graph.core.entities.Man;
import lions_on_graph.core.graph.*;
import util.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShapeController {

    private Map<Vertex, ShapedVertex> mapVertices = new HashMap<>();
    private Map<Entity, ShapedEntity> mapEntities = new HashMap<>();
    private Map<Edge, ShapedEdge> mapEdges = new HashMap<>();
    private Map<Lion, ArrayList<ShapedRange>> mapLionRange = new HashMap<>();
    private Map<Entity, ShapedVertex> mapStepPreviews = new HashMap<>();
    private CoreController coreController;

    public ShapeController(CoreController coreController) {
        this.coreController = coreController;
    }

    public void removeAllShapes() {
        for (Map.Entry<Vertex, ShapedVertex> entry : mapVertices.entrySet()) {
            entry.getValue().delete();
        }
        mapVertices.clear();

        for (Map.Entry<Entity, ShapedEntity> entry : mapEntities.entrySet()) {
            entry.getValue().delete();
        }
        mapEntities.clear();

        for (Map.Entry<Edge, ShapedEdge> entry : mapEdges.entrySet()) {
            entry.getValue().delete();
        }
        mapEdges.clear();

        for (Map.Entry<Lion, ArrayList<ShapedRange>> entry : mapLionRange.entrySet()) {
            ArrayList<ShapedRange> shapeList = entry.getValue();
            if (shapeList == null) {
                shapeList = new ArrayList<>();
            }
            for (ShapedRange shape : shapeList) {
                shape.delete();
            }
        }
        mapLionRange.clear();

        for (Map.Entry<Entity, ShapedVertex> entry : mapStepPreviews.entrySet()) {
            entry.getValue().delete();
        }
        mapStepPreviews.clear();


    }

    /* ****************************
     *
     *   Graph Shapes
     *
     * ****************************/


    public void relocateVertex(BigVertex vertex, Point newCoordinate) {
        ShapedVertex shape = mapVertices.get(vertex);
        shape.relocate(newCoordinate);

        for (Edge edge : vertex.getEdges()) {
            ShapedEdge edgeShape = mapEdges.get(edge);
            edgeShape.relocate(edge.getVertices()[0].getCoordinates(), edge.getVertices()[1].getCoordinates());

            for (SmallVertex smallVertex : edge.getEdgeVertices()) {
                ShapedVertex smalLVertexShape = mapVertices.get(smallVertex);
                smalLVertexShape.relocate(smallVertex.getCoordinates());
            }
        }
        for (Map.Entry<Entity, ShapedEntity> entry : mapEntities.entrySet()) {
            Entity entity = entry.getKey();
            ShapedEntity shapedEntity = entry.getValue();

            shapedEntity.relocate(entity.getCoordinates());
        }
    }

    public void createVertex(Point coordinate) {
        ShapedBigVertex shape = new ShapedBigVertex(coreController, coordinate);
        mapVertices.put(coreController.getBigVertexByCoordinate(coordinate), shape);
    }

    public void deleteVertex(BigVertex vertex) {
        ShapedVertex shape = mapVertices.get(vertex);
        shape.delete();
        mapVertices.remove(vertex);

        for (Edge edge : vertex.getEdges()) {
            ShapedEdge edgeShape = mapEdges.get(edge);
            mapEdges.remove(edge);
            edgeShape.delete();

            for (SmallVertex smallVertex : edge.getEdgeVertices()) {
                ShapedVertex smallVertexShape = mapVertices.get(smallVertex);
                mapVertices.remove(smallVertex);
                smallVertexShape.delete();
            }
        }
    }

    public void createEdge(Edge edge) {
        ShapedEdge shape = new ShapedEdge(coreController, edge.getStartCoordinates(), edge.getEndCoordinates());
        mapEdges.put(edge, shape);

        for (SmallVertex smallVertex : edge.getEdgeVertices()) {
            ShapedSmallVertex smallVertexShape = new ShapedSmallVertex(coreController, smallVertex.getCoordinates());
            mapVertices.put(smallVertex, smallVertexShape);
        }
    }

    public void removeEdge(Edge edge) {
        ShapedEdge shape = mapEdges.get(edge);
        shape.delete();
        mapEdges.remove(edge);
        for (SmallVertex smallVertex : edge.getEdgeVertices()) {
            ShapedVertex smalLVertexShape = mapVertices.get(smallVertex);
            mapVertices.remove(smallVertex);
            smalLVertexShape.delete();
        }
    }


    /* ****************************
     *
     *   ENTITY Shapes
     *
     * ****************************/

    public void createMan(Man man) {
        ShapedMan shape = new ShapedMan(coreController, man.getCoordinates());
        mapEntities.put(man, shape);
    }

    public void createLion(Lion lion) {
        ShapedLion shape = new ShapedLion(coreController, lion.getCoordinates());
        mapEntities.put(lion, shape);
        updateLionRange(lion);
    }

    public void relocateMan(Man man) {
        ShapedEntity shape = mapEntities.get(man);
        shape.relocate(man.getCoordinates());
    }

    public void relocateLion(Lion lion) {
        ShapedEntity shape = mapEntities.get(lion);
        shape.relocate(lion.getCoordinates());
        updateLionRange(lion);
    }

    public void removeMan(Man man) {
        ShapedEntity shape = mapEntities.get(man);
        shape.delete();
    }

    public void removeLion(Lion lion) {
        ShapedEntity shape = mapEntities.get(lion);
        shape.delete();
    }

    public void updateLionRange(Lion lion) {
        ArrayList<ShapedRange> rangeVertices = mapLionRange.get(lion);
        if (rangeVertices == null) {
            rangeVertices = new ArrayList<>();
        }

        while (lion.getRangeVertices().size() > rangeVertices.size()) {
            rangeVertices.add(new ShapedRange(coreController, new Point(0, 0)));
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

    public void updateAllLionRanges(ArrayList<Lion> lions) {
        for (Lion lion : lions) {
            updateLionRange(lion);
        }
    }

    public void updateStepPreviewsAndChoicePoints() {
        // step previews
        for (Map.Entry<Entity, ShapedVertex> entry : mapStepPreviews.entrySet()) {
            Entity entity = entry.getKey();
            ShapedVertex shapedPreview = entry.getValue();

            shapedPreview.delete();
        }
        mapStepPreviews.clear();//TODO dont flush and create new

        for (Man man : this.coreController.getMen()) {
            mapStepPreviews.put(man, new ShapeStepPreview(coreController, man.getNextPosition().getCoordinates()));
        }

        for (Lion lion : this.coreController.getLions()) {
            mapStepPreviews.put(lion, new ShapeStepPreview(coreController, lion.getNextPosition().getCoordinates()));
        }

        // choice points
        // TODO: jens, meinst du die API ist irgendwie schön designt ist?


        ShapedChoicePoint.clear();
        for (Man man : coreController.getMenWithManualInput()) {
            for (Connection con : man.getCurrentPosition().getConnections()) {
                Vertex choicePoint = con.getNeighbor(man.getCurrentPosition());
                new ShapedChoicePoint(coreController, man, choicePoint.getCoordinates());
            }
        }

        for (Lion lion : coreController.getLionsWithManualInput()) {
            for (Connection con : lion.getCurrentPosition().getConnections()) {
                Vertex choicePoint = con.getNeighbor(lion.getCurrentPosition());
                new ShapedChoicePoint(coreController, lion, choicePoint.getCoordinates());
            }
        }
    }

}
