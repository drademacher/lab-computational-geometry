package graph;

import java.util.*;

/**
 * Created by Jens on 20.05.2017.
 */
public class GraphHelper {

    private static GraphHelper graphHelper;
    private GraphController graphController;

    private GraphHelper(GraphController graphController) {
        this.graphController = graphController;
    }

    public static GraphHelper createGraphHelper(GraphController graphController) {
        if (graphHelper == null) {
            graphHelper = new GraphHelper(graphController);
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
            if (graphController.isManOnVertex(current)) {
                return map.get(current);
            }

            for (Edge edge : current.getEdges()) {
                Vertex nextVertex = edge.getNeighbor(current);

                if (!set.contains(nextVertex)) {
                    map.put(nextVertex, map.get(current) +1);
                    set.add(nextVertex);

                    if (!graphController.isLionOnVertex(nextVertex)) {
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
            if (graphController.isLionOnVertex(current)) {
                return map.get(current);
            }

            for (Edge edge : current.getEdges()) {
                Vertex nextVertex = edge.getNeighbor(current);

                if (!set.contains(nextVertex)) {
                    map.put(nextVertex, map.get(current) +1);
                    set.add(nextVertex);

                    if (!graphController.isManOnVertex(nextVertex)) {
                        queue.add(nextVertex);
                    }
                }
            }
        }

        return Integer.MAX_VALUE;

    }
}