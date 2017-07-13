package lions_on_graph.core.strategies.ManStrategies;

import lions_on_graph.core.CoreController;
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
public class StrategyPaper extends StrategyMan {

    Queue<Vertex> queue = new LinkedList<>();

    public StrategyPaper(CoreController coreController) {
        super(coreController);
    }

    @Override
    protected ArrayList<Vertex> calculatePossibleSteps() {
        ArrayList<Vertex> result = new ArrayList<>();
        
        Vertex currentPosition = man.getCurrentPosition();
        System.out.println("### current: "+currentPosition);
        System.out.println("queue: "+queue);

        if (!queue.isEmpty()) {
            System.out.println("take from queue: "+queue);
            result.add(queue.poll());
            return result;
        }


        if (helper.isQuarter(currentPosition)) {
            System.out.println("on quarter");

            int distance = Integer.MAX_VALUE;

            // only two connections
            for (Connection connection : currentPosition.getConnections()) {

                //case 1: small vertex -> edge
                Vertex vertex = connection.getNeighbor(currentPosition);
                if (vertex.getClass() == SmallVertex.class) {

                    System.out.println("case 1");
                    if (distance > helper.BFSToLion(currentPosition, vertex)) {
                        distance = helper.BFSToLion(currentPosition, vertex);


                        Vertex quarterToGo = helper.getNeighborQuarters(currentPosition, vertex).get(0);

                        ArrayList<Vertex> path = helper.getPathBetween(currentPosition, quarterToGo);

                        Collections.reverse(path);
                        queue.clear();
                        for (Vertex pathVertex : path) {
                            System.out.println("add to queue "+pathVertex);
                            queue.add(pathVertex);
                        }
                    }
                }
                //case 2: big vertex -> two more edges
                else {
                    //TODO special case lion on big vertex

                    System.out.println("case 2");


                    for (Connection nextConection : vertex.getConnections()) {
                        if (!nextConection.getNeighbor(vertex).equals(currentPosition)) {
                            Vertex nextVertex = nextConection.getNeighbor(vertex);

                            if (distance > helper.BFSToLion(vertex, nextVertex) + 1) {
                                distance = helper.BFSToLion(vertex, nextVertex) + 1;


                                ArrayList<Vertex> path = helper.getPathBetween(currentPosition, nextVertex);

                                Collections.reverse(path);
                                queue.clear();
                                for (Vertex pathVertex : path) {
                                    System.out.println("add to queue "+pathVertex);
                                    queue.add(pathVertex);
                                }
                            }
                        }
                    }
                }
            }
        }
        //go to closest quarter
        else {
            System.out.println("go to quarter");
            ArrayList<Vertex> neighborQuarters;
            neighborQuarters = helper.getNeighborQuarters(currentPosition);

//            System.out.println("  neighborQuarters: "+neighborQuarters);

            int distance = Integer.MAX_VALUE;
            for (Vertex quarter : neighborQuarters) {
//                System.out.println("  distance to "+quarter+" : _"+helper.getDistanceBetween(currentPosition, quarter));
                if (distance > helper.getDistanceBetween(currentPosition, quarter)) {
                    distance = helper.getDistanceBetween(currentPosition, quarter);
//                    System.out.println("  new distance: "+distance);
                    ArrayList<Vertex> path = helper.getPathBetween(currentPosition, quarter);
//                    System.out.println("  path: "+path);
                    Collections.reverse(path);
                    queue.clear();
                    for (Vertex pathVertex : path) {
                        queue.add(pathVertex);
                    }
                }
            }
        }


        result.add(queue.poll());
        return result;
    }


}
