package applet_one.visualization;

import applet_two.visualization.VisualizedCoreController;
import applet_one.core.CoreController;
import applet_one.core.graph.Connection;
import util.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static applet_one.visualization.Constants.*;

public class VisualCoreController extends VisualizedCoreController {

    private Map<applet_one.core.graph.Vertex, Vertex> mapVertices = new HashMap<>();
    private Map<applet_one.core.entities.Entity, Entity> mapEntities = new HashMap<>();
    private Map<applet_one.core.graph.Edge, Edge> mapEdges = new HashMap<>();
    private Map<applet_one.core.entities.Man, ArrayList<Range>> mapManRange = new HashMap<>();
    private Map<applet_one.core.entities.Lion, ArrayList<Range>> mapLionRange = new HashMap<>();
    private Map<applet_one.core.entities.Entity, Vertex> mapStepPreviews = new HashMap<>();
    private CoreController coreController;

    public VisualCoreController(CoreController coreController) {
        this.coreController = coreController;
    }

    public void removeAllShapes() {
        for (Map.Entry<applet_one.core.graph.Vertex, Vertex> entry : mapVertices.entrySet()) {
            entry.getValue().delete();
        }
        mapVertices.clear();

        for (Map.Entry<applet_one.core.entities.Entity, Entity> entry : mapEntities.entrySet()) {
            entry.getValue().delete();
        }
        mapEntities.clear();

        for (Map.Entry<applet_one.core.graph.Edge, Edge> entry : mapEdges.entrySet()) {
            entry.getValue().delete();
        }
        mapEdges.clear();

        for (Map.Entry<applet_one.core.entities.Man, ArrayList<Range>> entry : mapManRange.entrySet()) {
            ArrayList<Range> shapeList = entry.getValue();
            if (shapeList == null) {
                shapeList = new ArrayList<>();
            }
            for (Range shape : shapeList) {
                shape.delete();
            }
        }
        mapLionRange.clear();

        for (Map.Entry<applet_one.core.entities.Lion, ArrayList<Range>> entry : mapLionRange.entrySet()) {
            ArrayList<Range> shapeList = entry.getValue();
            if (shapeList == null) {
                shapeList = new ArrayList<>();
            }
            for (Range shape : shapeList) {
                shape.delete();
            }
        }
        mapLionRange.clear();

        for (Map.Entry<applet_one.core.entities.Entity, Vertex> entry : mapStepPreviews.entrySet()) {
            entry.getValue().delete();
        }
        mapStepPreviews.clear();


    }

    /* ****************************
     *
     *   Graph Shapes
     *
     * ****************************/


    public void relocateVertex(applet_one.core.graph.BigVertex vertex, Point newCoordinate) {
        Vertex shape = mapVertices.get(vertex);
        shape.relocate(newCoordinate);

        for (applet_one.core.graph.Edge edge : vertex.getEdges()) {
            Edge edgeShape = mapEdges.get(edge);
            edgeShape.relocate(edge.getVertices()[0].getCoordinates(), edge.getVertices()[1].getCoordinates());

            for (applet_one.core.graph.SmallVertex smallVertex : edge.getEdgeVertices()) {
                Vertex smalLVertexShape = mapVertices.get(smallVertex);
                smalLVertexShape.relocate(smallVertex.getCoordinates());
            }
        }
        for (Map.Entry<applet_one.core.entities.Entity, Entity> entry : mapEntities.entrySet()) {
            applet_one.core.entities.Entity entity = entry.getKey();
            Entity shapedEntity = entry.getValue();

            shapedEntity.relocate(entity.getCoordinates());
        }
    }

    public void createVertex(Point coordinate) {
        BigVertex shape = new BigVertex(coreController, coordinate);
        mapVertices.put(coreController.getBigVertexByCoordinate(coordinate), shape);
    }

    public void deleteVertex(applet_one.core.graph.BigVertex vertex) {
        Vertex shape = mapVertices.get(vertex);
        shape.delete();
        mapVertices.remove(vertex);

        for (applet_one.core.graph.Edge edge : vertex.getEdges()) {
            Edge edgeShape = mapEdges.get(edge);
            mapEdges.remove(edge);
            edgeShape.delete();

            for (applet_one.core.graph.SmallVertex smallVertex : edge.getEdgeVertices()) {
                Vertex smallVertexShape = mapVertices.get(smallVertex);
                mapVertices.remove(smallVertex);
                smallVertexShape.delete();
            }
        }
    }

    public void createEdge(applet_one.core.graph.Edge edge) {
        Edge shape = new Edge(coreController, edge.getStartCoordinates(), edge.getEndCoordinates());
        mapEdges.put(edge, shape);

        for (applet_one.core.graph.SmallVertex smallVertex : edge.getEdgeVertices()) {
            SmallVertex smallVertexShape = new SmallVertex(coreController, smallVertex.getCoordinates());
            mapVertices.put(smallVertex, smallVertexShape);
        }
    }

    public void removeEdge(applet_one.core.graph.Edge edge) {
        Edge shape = mapEdges.get(edge);
        shape.delete();
        mapEdges.remove(edge);
        for (applet_one.core.graph.SmallVertex smallVertex : edge.getEdgeVertices()) {
            Vertex smalLVertexShape = mapVertices.get(smallVertex);
            mapVertices.remove(smallVertex);
            smalLVertexShape.delete();
        }
    }


    /* ****************************
     *
     *   ENTITY Shapes
     *
     * ****************************/

    public void createMan(applet_one.core.entities.Man man) {
        Man shape = new Man(coreController, man.getCoordinates());
        mapEntities.put(man, shape);
        updateManRange(man);
    }

    public void createLion(applet_one.core.entities.Lion lion) {
        Lion shape = new Lion(coreController, lion.getCoordinates());
        mapEntities.put(lion, shape);
        updateLionRange(lion);
    }

    public void relocateMan(applet_one.core.entities.Man man) {
        Entity shape = mapEntities.get(man);
        shape.relocate(man.getCoordinates());
        updateManRange(man);
    }

    public void relocateLion(applet_one.core.entities.Lion lion) {
        Entity shape = mapEntities.get(lion);
        shape.relocate(lion.getCoordinates());
        updateLionRange(lion);
    }

    public void removeMan(applet_one.core.entities.Man man) {
        Entity shape = mapEntities.get(man);
        shape.delete();
    }

    public void removeLion(applet_one.core.entities.Lion lion) {
        Entity shape = mapEntities.get(lion);
        shape.delete();
    }

    public void updateManRange(applet_one.core.entities.Man man) {
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

    public void updateAllManRanges(ArrayList<applet_one.core.entities.Man> men) {
        for (applet_one.core.entities.Man man : men) {
            updateManRange(man);
        }
    }

    public void updateLionRange(applet_one.core.entities.Lion lion) {
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

    public void updateAllLionRanges(ArrayList<applet_one.core.entities.Lion> lions) {
        for (applet_one.core.entities.Lion lion : lions) {
            updateLionRange(lion);
        }
    }

    public void updateStepPreviewsAndChoicePoints() {
        // step previews
        for (Map.Entry<applet_one.core.entities.Entity, Vertex> entry : mapStepPreviews.entrySet()) {
            applet_one.core.entities.Entity entity = entry.getKey();
            Vertex shapedPreview = entry.getValue();

            shapedPreview.delete();
        }
        mapStepPreviews.clear();//TODO dont flush and create new

        for (applet_one.core.entities.Man man : this.coreController.getMen()) {
            mapStepPreviews.put(man, new StepPreview(coreController, man.getNextPosition().getCoordinates()));
        }

        for (applet_one.core.entities.Lion lion : this.coreController.getLions()) {
            mapStepPreviews.put(lion, new StepPreview(coreController, lion.getNextPosition().getCoordinates()));
        }

        // choice points
        // TODO: jens, meinst du die API ist irgendwie schön designt ist?


        ChoicePoint.clear();
        for (applet_one.core.entities.Man man : coreController.getMenWithManualInput()) {
            new ChoicePoint(coreController, man, man.getCoordinates(), COLOR_MAN);
            for (Connection con : man.getCurrentPosition().getConnections()) {
                applet_one.core.graph.Vertex choicePoint = con.getNeighbor(man.getCurrentPosition());
                new ChoicePoint(coreController, man, choicePoint.getCoordinates(), COLOR_CHOICEPOINT);
            }
        }

        for (applet_one.core.entities.Lion lion : coreController.getLionsWithManualInput()) {
            new ChoicePoint(coreController, lion, lion.getCoordinates(), COLOR_LION);
            for (Connection con : lion.getCurrentPosition().getConnections()) {
                applet_one.core.graph.Vertex choicePoint = con.getNeighbor(lion.getCurrentPosition());
                new ChoicePoint(coreController, lion, choicePoint.getCoordinates(), COLOR_CHOICEPOINT);
            }
        }
    }

}
