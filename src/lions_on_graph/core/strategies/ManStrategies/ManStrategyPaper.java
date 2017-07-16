package lions_on_graph.core.strategies.ManStrategies;

import lions_on_graph.core.CoreController;
import lions_on_graph.core.entities.Lion;
import lions_on_graph.core.graph.Connection;
import lions_on_graph.core.graph.SmallVertex;
import lions_on_graph.core.graph.Vertex;
import lions_on_graph.core.strategies.StrategyMan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Jens on 11.07.2017.
 */
public class ManStrategyPaper extends StrategyMan {

    Vertex target = null;

    public ManStrategyPaper(CoreController coreController, CoreController.ManStrategy strategyEnum) {
        super(coreController, strategyEnum);
    }

    @Override
    protected ArrayList<Vertex> calculatePossibleSteps() {
        ArrayList<Vertex> result = new ArrayList<>();

        Vertex currentPosition = man.getCurrentPosition();
//        System.out.println("###################");
//        System.out.println("current: " + currentPosition);

        if (target == null || target.equals(currentPosition)) {


            if (helper.isQuarter(currentPosition)) {
//                System.out.println("on quarter");

                int distance = 0;

                // only two connections
                for (Connection connection : currentPosition.getConnections()) {

                    //case 1: small vertex -> edge
                    Vertex vertex = connection.getNeighbor(currentPosition);
                    if (vertex.getClass() == SmallVertex.class) {

//                        System.out.println("case 1");
//                        System.out.println("old distance: "+distance);
                        if (distance < helper.BFSToLion(currentPosition, vertex)) {
                            distance = helper.BFSToLion(currentPosition, vertex);
//                            System.out.println("updated distance: "+distance);


                            target = helper.getNeighborQuarters(currentPosition, vertex).get(0);
//                            System.out.println("possible target: "+target);

                        }
                    }
                    //case 2: big vertex -> two more edges
                    else {
                        if(helper.BFSToLion(currentPosition, vertex )> 2) {

//                        System.out.println("case 2");

                            for (Connection nextConection : vertex.getConnections()) {
                                if (!nextConection.getNeighbor(vertex).equals(currentPosition)) {
                                    Vertex nextVertex = nextConection.getNeighbor(vertex);

//                                System.out.println("old distance: "+distance);
                                    if (distance < helper.BFSToLion(vertex, nextVertex) + 1) {
                                        distance = helper.BFSToLion(vertex, nextVertex) + 1;
//                                    System.out.println("updated distance: "+distance);

                                        target = nextVertex;
//                                    System.out.println("possible target: "+target);
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

        if (this.coreController.isLionOnVertex(vertex.getCoordinates())) {
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
            } else if(distance < d_far){
                d_far = distance;
            }
        }

//        System.out.println("invariant ok? -> "+(d_far >= 7 || d_near >= 3));
        return d_far >= 7 || d_near >= 3;
    }

}
