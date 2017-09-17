package lions_and_men.applet_one_graph.core.strategies;

import lions_and_men.applet_one_graph.core.CoreController;
import lions_and_men.applet_one_graph.core.entities.Entity;
import lions_and_men.applet_one_graph.core.entities.Lion;
import lions_and_men.applet_one_graph.core.graph.Connection;
import lions_and_men.applet_one_graph.core.graph.SmallVertex;
import lions_and_men.applet_one_graph.core.graph.Vertex;

import java.util.ArrayList;

/**
 * Created by Jens on 11.07.2017.
 */
public class PaperMan<T extends Entity> extends Strategy<T> {

    Vertex target = null;

    public PaperMan(CoreController coreController) {
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

//                        System.out.println("old distance: "+distance);
                        if (distance < helper.BFSToLion(currentPosition, vertex)) {
                            distance = helper.BFSToLion(currentPosition, vertex);
//                            System.out.println("updated distance: "+distance);


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
//                                    System.out.println("updated distance: "+distance);

                                        target = nextVertex;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //go to closest quarter
            else {
//                System.out.println("go to quarter");
                ArrayList<Vertex> neighborQuarters;
                neighborQuarters = helper.getNeighborQuarters(currentPosition);

//            System.out.println("  neighborQuarters: "+neighborQuarters);

                target = currentPosition; // fallback
                int distance = 0;
                for (Vertex quarter : neighborQuarters) {

//                    System.out.println("old distance: "+distance);
                    if (checkInvariant(quarter)) {
                        if (distance < helper.getDistanceBetween(currentPosition, quarter)) {
                            distance = helper.getDistanceBetween(currentPosition, quarter);
//                            System.out.println("updated distance: "+distance);

                            target = quarter;
//                            System.out.println("possible target: "+target);
                        }
                    }
                }
            }

        }


//        System.out.println("finished step... go to target : " + target);
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

//        System.out.println("invariant ok? -> "+(d_far >= 7 || d_near >= 3));
        return d_far >= 7 || d_near >= 3;
    }

}
