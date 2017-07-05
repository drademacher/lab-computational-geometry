package lions_on_graph.graph;

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
            if (coreController.isManOnVertex(current.getCoordinates())) {
                return map.get(current);
            }

            for (Connection connection : current.getConnections()) {
                Vertex nextVertex = connection.getNeighbor(current);

                if (!set.contains(nextVertex)) {
                    map.put(nextVertex, map.get(current) + 1);
                    set.add(nextVertex);

                    if (!coreController.isLionOnVertex(nextVertex.getCoordinates())) {
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
            if (coreController.isDangerOnVertex(current.getCoordinates())) {
                return map.get(current);
            }

            for (Connection connection : current.getConnections()) {
                Vertex nextVertex = connection.getNeighbor(current);

                if (!set.contains(nextVertex)) {
                    map.put(nextVertex, map.get(current) + 1);
                    set.add(nextVertex);

                    if (!coreController.isManOnVertex(nextVertex.getCoordinates())) {
                        queue.add(nextVertex);
                    }
                }
            }
        }

        return Integer.MAX_VALUE;

    }

    public ArrayList<Vertex> BFSgetAllVerticesTill(Vertex startVertex, int number) {

        ArrayList<Vertex> result = new ArrayList<>();

        Map<Vertex, Integer> map = new HashMap<>();
        Set<Vertex> set = new HashSet<>();
        Queue<Vertex> queue = new LinkedList<>();
        Vertex current = null;

        map.put(startVertex, 0);

        set.add(startVertex);
        queue.add(startVertex);

        while (!queue.isEmpty()) {
            current = queue.poll();

            // check break condition
            if (map.get(current) <= number && map.get(current) > 0) {
                result.add(current);
            }

            for (Connection connection : current.getConnections()) {
                Vertex nextVertex = connection.getNeighbor(current);

                if (!set.contains(nextVertex)) {
                    map.put(nextVertex, map.get(current) + 1);
                    set.add(nextVertex);

                    queue.add(nextVertex);
                }
            }
        }

        return result;
    }

    public int getDistanceBetween(Vertex vertex1, Vertex vertex2) {

        Map<Vertex, Integer> map = new HashMap<>();
        Set<Vertex> set = new HashSet<>();
        Queue<Vertex> queue = new LinkedList<>();
        Vertex current;

        map.put(vertex1, 0);

        set.add(vertex1);
        queue.add(vertex1);

        while (!queue.isEmpty()) {
            current = queue.poll();

            // check break condition
            if (current.equals(vertex2)) {
                return map.get(current);
            }

            for (Connection connection : current.getConnections()) {
                Vertex nextVertex = connection.getNeighbor(current);

                if (!set.contains(nextVertex)) {
                    map.put(nextVertex, map.get(current) + 1);
                    set.add(nextVertex);

                    queue.add(nextVertex);
                }
            }
        }

        return Integer.MAX_VALUE;
    }

}