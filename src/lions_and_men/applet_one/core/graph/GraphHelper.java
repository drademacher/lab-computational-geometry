package lions_and_men.applet_one.core.graph;

import lions_and_men.applet_one.core.CoreController;

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


    public int BFSToMen(Vertex startVertex) {
        return BFSToMen(startVertex, null);
    }

    public int BFSToMen(Vertex startVertex, Vertex directionVertex) {

        Map<Vertex, Integer> map = new HashMap<>();
        Set<Vertex> set = new HashSet<>();
        Queue<Vertex> queue = new LinkedList<>();
        Vertex current = null;

        set.add(startVertex);

        if (directionVertex != null) {
            map.put(directionVertex, 1);
            set.add(directionVertex);
            queue.add(directionVertex);
        } else {
            map.put(startVertex, 0);
            queue.add(startVertex);
        }

        while (!queue.isEmpty()) {
            current = queue.poll();

            // check break condition
            if (coreController.isManRangeOnVertex(current.getCoordinates())) {
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
            if (coreController.isLionDangerOnVertex(current.getCoordinates())) {
                return map.get(current);
            }

            for (Connection connection : current.getConnections()) {
                Vertex nextVertex = connection.getNeighbor(current);

                if (!set.contains(nextVertex)) {
                    map.put(nextVertex, map.get(current) + 1);
                    set.add(nextVertex);

                    if (!coreController.isManRangeOnVertex(nextVertex.getCoordinates())) {
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

        if (vertex1 == null || vertex2 == null) {
            throw new IllegalArgumentException();
        }

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

    public ArrayList<Vertex> getPathBetween(Vertex vertex1, Vertex vertex2) {

        ArrayList<Vertex> result = new ArrayList<>();

        if (vertex1.equals(vertex2)) {
            result.add(vertex2);
            return result;
        }


        Map<Vertex, Vertex> map = new HashMap<>(); //  <vertex, ancestor>
        Set<Vertex> set = new HashSet<>();
        Queue<Vertex> queue = new LinkedList<>();
        Vertex current;

        map.put(vertex1, null);

        set.add(vertex1);
        queue.add(vertex1);

        while (!queue.isEmpty()) {
            current = queue.poll();

            // check break condition
            if (current.equals(vertex2)) {
                Vertex addToList = current;
                while (!addToList.equals(vertex1)) {
                    result.add(0, addToList);
                    addToList = map.get(addToList);
                }
                return result;
            }

            for (Connection connection : current.getConnections()) {
                Vertex nextVertex = connection.getNeighbor(current);

                if (!set.contains(nextVertex)) {
                    map.put(nextVertex, current);
                    set.add(nextVertex);

                    queue.add(nextVertex);
                }
            }
        }

        return result;
    }

    public ArrayList<Vertex> getNeighborBigVertices(Vertex vertex) {
        return getNeighborBigVertices(vertex, null);
    }

    public ArrayList<Vertex> getNeighborBigVertices(Vertex vertex, Vertex directionVertex) {

        ArrayList<Vertex> result = new ArrayList<>();

        Set<Vertex> set = new HashSet<>();
        Queue<Vertex> queue = new LinkedList<>();
        Vertex current = null;


        set.add(vertex);

        if (directionVertex != null) {
            queue.add(directionVertex);
            set.add(directionVertex);
        } else {
            queue.add(vertex);
        }

        while (!queue.isEmpty()) {
            current = queue.poll();

            // check break condition
            if (!vertex.equals(current) && coreController.getBigVertexByCoordinate(current.getCoordinates()) != null) {
                result.add(current);
            } else {

                for (Connection connection : current.getConnections()) {
                    Vertex nextVertex = connection.getNeighbor(current);

                    if (!set.contains(nextVertex)) {
                        set.add(nextVertex);

                        queue.add(nextVertex);
                    }
                }
            }
        }
        return result;
    }


    //paper strategy
    public ArrayList<Vertex> getNeighborQuarters(Vertex vertex) {
        return getNeighborQuarters(vertex, null);
    }

    public ArrayList<Vertex> getNeighborQuarters(Vertex vertex, Vertex directionVertex) {
        ArrayList<Vertex> result = new ArrayList<>();

        Set<Vertex> set = new HashSet<>();
        Queue<Vertex> queue = new LinkedList<>();
        Vertex current = null;


        set.add(vertex);

        if (directionVertex != null) {
            queue.add(directionVertex);
            set.add(directionVertex);
        } else {
            queue.add(vertex);
        }


        while (!queue.isEmpty()) {
            current = queue.poll();

            // check break condition
            if (isQuarter(current)) {
                result.add(current);
            } else {

                for (Connection connection : current.getConnections()) {
                    Vertex nextVertex = connection.getNeighbor(current);

                    if (!set.contains(nextVertex)) {
                        set.add(nextVertex);

                        queue.add(nextVertex);
                    }
                }
            }
        }

        return result;
    }

    public boolean isQuarter(Vertex vertex) {

        if (vertex.getClass() != SmallVertex.class) {
            return false;
        }

        for (Connection connection : vertex.getConnections()) {
            if (connection.getNeighbor(vertex).getClass() == BigVertex.class) {
                return true;
            }
        }

        return false;
    }

}