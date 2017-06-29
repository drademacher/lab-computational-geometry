package graph;

import java.util.*;

/**
 * Created by Jens on 20.05.2017.
 */
public class GraphHelper {

    private static GraphHelper graphHelper;
    private CoreController coreController;

    private GraphHelper(CoreController coreController) {
        this.coreController = coreController;
    }

    public static GraphHelper createGraphHelper(CoreController coreController) {
        if (graphHelper == null) {
            graphHelper = new GraphHelper(coreController);
        }
        return graphHelper;
    }


    public int BFSToMen(Vertex startVertex, Vertex directionVertex) {

        Map<Vertex, Integer> map = new HashMap<>();
        Set<Vertex> set = new HashSet<>();
        Queue<Vertex> queue = new LinkedList<>();
        Vertex current = null;

        map.put(directionVertex, 1);

        set.add(startVertex);
        set.add(directionVertex);
        queue.add(directionVertex);

        while (!queue.isEmpty()) {
            current = queue.poll();

            // check break condition
            if (coreController.isManOnVertex(current)) {
                return map.get(current);
            }

            for (Connection connection : current.getConnections()) {
                Vertex nextVertex = connection.getNeighbor(current);

                if (!set.contains(nextVertex)) {
                    map.put(nextVertex, map.get(current) + 1);
                    set.add(nextVertex);

                    if (!coreController.isLionOnVertex(nextVertex)) {
                        queue.add(nextVertex);
                    }
                }
            }
        }

        return Integer.MAX_VALUE;

    }

    public int BFSToLion(Vertex startVertex, Vertex directionVertex) {

        Map<Vertex, Integer> map = new HashMap<>();
        Set<Vertex> set = new HashSet<>();
        Queue<Vertex> queue = new LinkedList<>();
        Vertex current = null;

        map.put(directionVertex, 1);

        set.add(startVertex);
        set.add(directionVertex);
        queue.add(directionVertex);

        while (!queue.isEmpty()) {
            current = queue.poll();

            // check break condition
            if (coreController.isLionOnVertex(current)) {
                return map.get(current);
            }

            for (Connection connection : current.getConnections()) {
                Vertex nextVertex = connection.getNeighbor(current);

                if (!set.contains(nextVertex)) {
                    map.put(nextVertex, map.get(current) + 1);
                    set.add(nextVertex);

                    if (!coreController.isManOnVertex(nextVertex)) {
                        queue.add(nextVertex);
                    }
                }
            }
        }

        return Integer.MAX_VALUE;

    }
}