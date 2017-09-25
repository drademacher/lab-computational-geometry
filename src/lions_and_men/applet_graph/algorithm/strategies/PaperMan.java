package lions_and_men.applet_graph.algorithm.strategies;

import lions_and_men.applet_graph.algorithm.CoreController;
import lions_and_men.applet_graph.algorithm.entities.Lion;
import lions_and_men.applet_graph.algorithm.entities.Man;
import lions_and_men.applet_graph.algorithm.graph.Connection;
import lions_and_men.applet_graph.algorithm.graph.SmallVertex;
import lions_and_men.applet_graph.algorithm.graph.Vertex;

import java.util.ArrayList;

public class PaperMan extends Strategy<Man> {

    private Vertex target = null;

    PaperMan(CoreController coreController) {
        super(coreController);
    }

    @Override
    protected ArrayList<Vertex> calculatePossibleSteps() {
        ArrayList<Vertex> result = new ArrayList<>();

        Vertex currentPosition = entity.getCurrentPosition();

        if (target == null || target.equals(currentPosition)) {


            if (helper.isQuarter(currentPosition)) {
                int distance = 0;

                // only two connections
                for (Connection connection : currentPosition.getConnections()) {

                    //case 1: small vertex -> edge
                    Vertex vertex = connection.getNeighbor(currentPosition);
                    if (vertex.getClass() == SmallVertex.class) {

                        if (distance < helper.BFSToLion(currentPosition, vertex)) {
                            distance = helper.BFSToLion(currentPosition, vertex);

                            target = helper.getNeighborQuarters(currentPosition, vertex).get(0);

                        }
                    }
                    //case 2: big vertex -> two more edges
                    else {
                        if (helper.BFSToLion(currentPosition, vertex) > 2) {

                            for (Connection nextConection : vertex.getConnections()) {
                                if (!nextConection.getNeighbor(vertex).equals(currentPosition)) {
                                    Vertex nextVertex = nextConection.getNeighbor(vertex);

                                    if (distance < helper.BFSToLion(vertex, nextVertex) + 1) {
                                        distance = helper.BFSToLion(vertex, nextVertex) + 1;

                                        target = nextVertex;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //go to best quarter
            else {
                ArrayList<Vertex> neighborQuarters;
                neighborQuarters = helper.getNeighborQuarters(currentPosition);

                target = currentPosition; // fallback
                int distanceToLion = 0;
//                int distance = 0;
                for (Vertex quarter : neighborQuarters) {

                    if (checkInvariant(quarter)) {
                        ArrayList<Vertex> pathToQuater = helper.getPathBetween(currentPosition, quarter);
                        if (pathToQuater != null && pathToQuater.size() > 0) {
                            Vertex directionVertex = pathToQuater.get(0);

                            if (distanceToLion < helper.BFSToLion(currentPosition, directionVertex)) {
                                distanceToLion = helper.BFSToLion(currentPosition, directionVertex);
                                target = quarter;
                            }
                        }
                    }
                }
            }

        }

        result.add(helper.getPathBetween(currentPosition, target).get(0));
        return result;
    }

    private boolean checkInvariant(Vertex vertex) {

        if (!helper.isQuarter(vertex)) {
            return false;
        }

        if (this.coreController.isLionDangerOnVertex(vertex.getCoordinates())) {
            return false;
        }

        if (this.coreController.getLions().size() < 2) {
            return true;
        }

        int d_near = Integer.MAX_VALUE;
        int d_far = Integer.MAX_VALUE;

        for (Lion lion : this.coreController.getLions()) {
            int distance = helper.getDistanceBetween(vertex, lion.getCurrentPosition());
            if (distance < d_near) {
                d_far = d_near;
                d_near = distance;
            } else if (distance < d_far) {
                d_far = distance;
            }
        }

        return d_far >= 7 || d_near >= 3;
    }

    @Override
    public void reset() {
        target = null;
    }
}
