package lions_on_graph.core.graph;

import util.Point;

import java.util.ArrayList;

import static lions_on_graph.visualization.Constants.BIG_VERTEX_RADIUS;
import static lions_on_graph.visualization.Constants.SMALL_VERTEX_RADIUS;

public class GraphController {

    private static int idCounter = -1;
    private static int defaultEdgeWeight = 4;

    //vertices
    private ArrayList<BigVertex> bigVertices = new ArrayList<>();
    private ArrayList<SmallVertex> smallVertices = new ArrayList<>();
    private ArrayList<Edge> edges = new ArrayList<>();


    public GraphController() {
    }

    private static int getIdCounter() {
        idCounter++;
        return idCounter;
    }

    public static int getDefaultEdgeWeight() {
        return GraphController.defaultEdgeWeight;
    }

    public static void setDefaultEdgeWeight(int defaultEdgeWeight) {
        if (defaultEdgeWeight < 0) {
            return;
        }
        GraphController.defaultEdgeWeight = defaultEdgeWeight;
    }

    public BigVertex relocateVertex(BigVertex vertex, Point newCoordinate) {

        //check duplicate
        if (!validVertexPosition(newCoordinate)) {
            return vertex;
        }

        vertex.setCoordinates(newCoordinate);

        return updateEdgeVerticesPosition(vertex);

    }

    public BigVertex updateEdgeVerticesPosition(BigVertex vertex) {
        if (vertex == null) {
            return null;
        }


        //update position of SmallVertices
        for (Edge edge : vertex.getEdges()) {
            BigVertex neighbor = edge.getNeighbor(vertex);
            int edgeWeight = edge.getEdgeWeight();
            int i = 0;

            //check edge orientation, if there are (enough) edge bigVertices
            boolean reversedEdge = false;
            if (edgeWeight > 2) {
                reversedEdge = !verticesAreAdjacent(edge.getEdgeVertices().get(0), vertex);
            }

            for (SmallVertex smallVertex : edge.getEdgeVertices()) {
                if (reversedEdge) {
                    Point coordinates = calcSmallVertexCoordinates(neighbor, vertex, edgeWeight, i);
                    smallVertex.setCoordinates(coordinates);

                } else {
                    Point coordinates = calcSmallVertexCoordinates(vertex, neighbor, edgeWeight, i);
                    smallVertex.setCoordinates(coordinates);
                }
                //relocate visualization

                i++;
            }
        }
        return vertex;
    }

    public BigVertex createVertex(Point coordinate) {

        //check duplicate and margin to other bigVertices
        if (!validVertexPosition(coordinate)) {
            return null;
        }

        BigVertex vertex = new BigVertex(getIdCounter(), coordinate);
        bigVertices.add(vertex);
        return vertex;
    }

    public BigVertex deleteVertex(BigVertex vertex) {
        bigVertices.remove(vertex);

        for (int i = vertex.getEdges().size() - 1; i >= 0; i--) {
            Edge edge = vertex.getEdges().get(i);
            removeEdge(edge);
        }

        return vertex;
    }

    public Edge createEdge(BigVertex vertex1, BigVertex vertex2) {
        return createEdge(vertex1, vertex2, GraphController.defaultEdgeWeight);
    }

    public Edge createEdge(BigVertex vertex1, BigVertex vertex2, int weight) {

        //check duplicate
        for (Edge edge : vertex1.getEdges()) {
            if (edge.getNeighbor(vertex1).equals(vertex2)) {
                return null; //dublicate
            }
        }

        ArrayList<SmallVertex> edgeVertices = new ArrayList<>();
        for (int i = 0; i < weight - 1; i++) {

            SmallVertex smallVertex = new SmallVertex(getIdCounter(), calcSmallVertexCoordinates(vertex1, vertex2, weight, i));

            edgeVertices.add(smallVertex);
            this.smallVertices.add(smallVertex);

            //pointer to prev vertex
            if (i > 0) {
                Connection connection = new Connection(smallVertex, edgeVertices.get(i - 1));
                smallVertex.registerConnection(connection);
                edgeVertices.get(i - 1).registerConnection(connection);
            }

            //pointer to the big bigVertices
            if (i == 0) {
                Connection connection = new Connection(smallVertex, vertex1);
                smallVertex.registerConnection(connection);
                vertex1.registerConnection(connection);
            }
            if (i == weight - 2) {
                Connection connection = new Connection(smallVertex, vertex2);
                smallVertex.registerConnection(connection);
                vertex2.registerConnection(connection);
            }
        }

        Edge edge = new Edge(vertex1, vertex2, edgeVertices, weight);
        vertex1.registerEdge(edge);
        vertex2.registerEdge(edge);
        edges.add(edge);


        if (weight <= 1) {
            Connection connection = new Connection(vertex1, vertex2);
            vertex1.registerConnection(connection);
            vertex2.registerConnection(connection);
        }
        return edge;
    }

    private void changeAllEdgeWeightsToDefault() {
        for (Edge edge : edges) {
            changeEdgeWeight(edge.getVertices()[0], edge.getVertices()[1]);
        }
    }

    public void changeEdgeWeight(BigVertex vertex1, BigVertex vertex2) {
        changeEdgeWeight(vertex1, vertex2, GraphController.defaultEdgeWeight);
    }

    public void changeEdgeWeight(BigVertex vertex1, BigVertex vertex2, int weight) {

        for (Edge edge : edges) {

            if (edge.contains(vertex1) && edge.contains(vertex2)) {
                //got the edge

                // deleting
                while (edge.getEdgeWeight() > weight) {

                    SmallVertex smallVertex = edge.getEdgeVertices().get(0);

                    if (smallVertex.getConnections().size() > 2) {
                        throw new Error("Unexpected: smallVertex has more then 2 connections");
                    }

                    Vertex vertexLeft = smallVertex.getConnections().get(0).getNeighbor(smallVertex);
                    Vertex vertexRight = smallVertex.getConnections().get(1).getNeighbor(smallVertex);

                    for (int j = vertexLeft.getConnections().size() - 1; j >= 0; j--) {
                        Connection connection = vertexLeft.getConnections().get(j);
                        if (connection.contains(smallVertex)) {
                            vertexLeft.unregisterConnection(connection);
                        }
                    }
                    for (int j = vertexRight.getConnections().size() - 1; j >= 0; j--) {
                        Connection connection = vertexRight.getConnections().get(j);
                        if (connection.contains(smallVertex)) {
                            vertexRight.unregisterConnection(connection);
                        }
                    }

                    Connection newConnextion = new Connection(vertexLeft, vertexRight);
                    vertexLeft.registerConnection(newConnextion);
                    vertexRight.registerConnection(newConnextion);

                    edge.unregisterEdgeVertex(smallVertex);
                    smallVertices.remove(smallVertex);


                }
                //creating
                while (edge.getEdgeWeight() < weight) {
                    for (int j = edge.getEdgeVertices().size() - 1; j >= 0; j--) {
                        SmallVertex smallVertex = edge.getEdgeVertices().get(j);

                        for (int i = smallVertex.getConnections().size() - 1; i >= 0; i--) {
                            Connection connection = smallVertex.getConnections().get(i);

                            if (connection.contains(vertex2)) {

                                smallVertex.unregisterConnection(connection);
                                vertex2.unregisterConnection(connection);


                                SmallVertex newSmallVertex = new SmallVertex(getIdCounter(), new Point(0, 0));


                                Connection newConnextion1 = new Connection(smallVertex, newSmallVertex);
                                smallVertex.registerConnection(newConnextion1);
                                newSmallVertex.registerConnection(newConnextion1);

                                Connection newConnextion2 = new Connection(vertex2, newSmallVertex);
                                vertex2.registerConnection(newConnextion2);
                                newSmallVertex.registerConnection(newConnextion2);

                                edge.registerEdgeVertex(newSmallVertex);
                                smallVertices.add(newSmallVertex);

                            }

                        }
                    }
                    if (edge.getEdgeVertices().size() == 0) {
                        SmallVertex newSmallVertex = new SmallVertex(getIdCounter(), new Point(0, 0));


                        Connection newConnextion1 = new Connection(vertex1, newSmallVertex);
                        vertex1.registerConnection(newConnextion1);
                        newSmallVertex.registerConnection(newConnextion1);

                        Connection newConnextion2 = new Connection(vertex2, newSmallVertex);
                        vertex2.registerConnection(newConnextion2);
                        newSmallVertex.registerConnection(newConnextion2);

                        edge.registerEdgeVertex(newSmallVertex);
                        smallVertices.add(newSmallVertex);
                    }
                }
            }
        }
        updateEdgeVerticesPosition(vertex1);
    }

    public Edge removeEdge(BigVertex vertex1, BigVertex vertex2) {


        for (int i = edges.size() - 1; i >= 0; i--) {
            Edge edge = edges.get(i);

            if (edge.contains(vertex1) && edge.contains(vertex2)) {
                return removeEdge(edge);
            }
        }
        return null;
    }

    private Edge removeEdge(Edge edge) {

        BigVertex vertex1 = edge.getVertices()[0];
        BigVertex vertex2 = edge.getVertices()[1];


        for (SmallVertex smallVertex : edge.getEdgeVertices()) {
            this.smallVertices.remove(smallVertex);
        }

        this.edges.remove(edge);

        for (SmallVertex smallVertex : edge.getEdgeVertices()) {

            this.smallVertices.remove(smallVertex);

            for (int i = vertex1.getConnections().size() - 1; i >= 0; i--) {
                Connection connection = vertex1.getConnections().get(i);
                if (connection.contains(smallVertex)) {
                    vertex1.unregisterConnection(connection);
                }
            }

            for (int i = vertex2.getConnections().size() - 1; i >= 0; i--) {
                Connection connection = vertex2.getConnections().get(i);
                if (connection.contains(smallVertex)) {
                    vertex2.unregisterConnection(connection);
                }
            }
        }

        if (edge.getEdgeVertices().size() == 0) {
            for (Connection connection : vertex1.getConnections()) {
                if (connection.contains(vertex1) && connection.contains(vertex2)) {
                    vertex1.unregisterConnection(connection);
                    vertex2.unregisterConnection(connection);
                }
            }
        }

        edge.getVertices()[0].NEWunregisterEdge(edge);
        edge.getVertices()[1].NEWunregisterEdge(edge);

        return edge;
    }

    public BigVertex getBigVertexByCoordinate(Point coordinate) {
        return getBigVertexByCoordinate(coordinate, BIG_VERTEX_RADIUS);
    }

    public BigVertex getBigVertexByCoordinate(Point coordinate, double radius) {

        for (BigVertex vertex : bigVertices) {
            Point vector = new Point(vertex.getCoordinates().getX() - coordinate.getX(), vertex.getCoordinates().getY() - coordinate.getY());
            double vectorLength = vector.length();
            if (vectorLength <= radius) {
                return vertex;
            }
        }
        return null;
    }

    public SmallVertex getSmallVertexByCoordinate(Point coordinate) {

        for (SmallVertex vertex : smallVertices) {
            Point vector = new Point(vertex.getCoordinates().getX() - coordinate.getX(), vertex.getCoordinates().getY() - coordinate.getY());
            double vectorLength = vector.length();
            if (vectorLength <= SMALL_VERTEX_RADIUS) {
                return vertex;
            }
        }
        return null;
    }

    public Vertex getVertexByCoordinate(Point coordinate) {

        Vertex vertex = getBigVertexByCoordinate(coordinate);
        if (vertex == null) {
            vertex = getSmallVertexByCoordinate(coordinate);
        }
        return vertex;
    }

    public BigVertex getBigVertexById(int id) {
        for (BigVertex vertex : bigVertices) {
            if (vertex.getId() == id) {
                return vertex;
            }
        }
        return null;
    }

    public Edge getEdgeByVertices(BigVertex vertex1, BigVertex vertex2) {

        for (Edge edge : edges) {
            if (edge.contains(vertex1) && edge.contains(vertex2)) {
                return edge;
            }
        }
        return null;
    }

    public ArrayList<BigVertex> getBigVertices() {
        return bigVertices;
    }

    public ArrayList<SmallVertex> getSmallVertices() {
        return smallVertices;
    }

    /* ***********************************
     *  PRIVATE HELPER FUNCTIONS
     *********************************** */

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    private boolean verticesAreAdjacent(Vertex vertex1, Vertex vertex2) {
        for (Connection connection : vertex1.getConnections()) {
            if (connection.contains(vertex2)) {
                return true;
            }
        }
        return false;
    }

    private boolean validVertexPosition(Point coordinates) {
        if (getBigVertexByCoordinate(coordinates, 2 * BIG_VERTEX_RADIUS) != null) {
            return false;
        }
        return true;
    }

    private Point calcSmallVertexCoordinates(BigVertex vertex1, BigVertex vertex2, int weight, int index) {
        Point vector = new Point(vertex2.getCoordinates().getX() - vertex1.getCoordinates().getX(), vertex2.getCoordinates().getY() - vertex1.getCoordinates().getY());
        double vectorLength = vector.length();
        double factor = (index + 1) * (vectorLength / weight) / vectorLength;
        Point addingVector = vector.mul(factor);
        Point result = vertex1.getCoordinates().add(addingVector);
        return result;
    }

    public void debugGraph() {

        System.out.println("debug graph....");

        String str = "\n#######################\n";

        str += "bigVertices: " + this.bigVertices + "\n";
        str += "smallVertices: " + this.smallVertices + "\n";
        str += "edges: " + this.edges + "\n";

        for (BigVertex vertex : bigVertices) {
            str += "\n" + vertex.getId() + " Coord: " + vertex.getCoordinates() + " (";
            for (Edge ver : vertex.getEdges()) {
                str += ver.toString() + " ' ";
            }
            str += "), ";
            for (Edge edge : vertex.getEdges()) {
                str += "\n - to: " + edge.getNeighbor(vertex).getId();
                for (SmallVertex smallVertex : edge.getEdgeVertices()) {
                    str += "        \n --->   " + smallVertex.getId() + " Coord: " + smallVertex.getCoordinates() + " (";
                    for (Connection connection : smallVertex.getConnections()) {
                        str += connection.toString() + " ' ";
                    }
                    str += "), ";
                }
            }
        }

        System.out.println(str);
    }

}
