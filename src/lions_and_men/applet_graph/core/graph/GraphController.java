package lions_and_men.applet_graph.core.graph;

import lions_and_men.util.Point;

import java.util.ArrayList;

import static lions_and_men.applet_graph.visualization.Constants.BIG_VERTEX_RADIUS;
import static lions_and_men.applet_graph.visualization.Constants.SMALL_VERTEX_RADIUS;

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

    public void relocateVertex(BigVertex vertex, Point newCoordinate) {

        //check duplicate
        if (!validVertexPosition(newCoordinate)) {
            return;
        }

        vertex.setCoordinates(newCoordinate);

        updateEdgeVerticesPosition(vertex);

    }

    private void updateEdgeVerticesPosition(BigVertex vertex) {
        if (vertex == null) {
            return;
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
    }

    public void createVertex(Point coordinate) {

        //check duplicate and margin to other bigVertices
        if (!validVertexPosition(coordinate)) {
            return;
        }

        BigVertex vertex = new BigVertex(getIdCounter(), coordinate);
        bigVertices.add(vertex);
    }

    public void deleteVertex(BigVertex vertex) {
        bigVertices.remove(vertex);

        for (int i = vertex.getEdges().size() - 1; i >= 0; i--) {
            Edge edge = vertex.getEdges().get(i);
            removeEdge(edge);
        }
    }

    public void createEdge(BigVertex vertex1, BigVertex vertex2, int weight) {

        //check duplicate
        for (Edge edge : vertex1.getEdges()) {
            if (edge.getNeighbor(vertex1).equals(vertex2)) {
                return; //dublicate
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


        this.smallVertices.removeAll(edge.getEdgeVertices());

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

    private BigVertex getBigVertexByCoordinate(Point coordinate, double radius) {

        for (BigVertex vertex : bigVertices) {
            Point vector = new Point(vertex.getCoordinates().getX() - coordinate.getX(), vertex.getCoordinates().getY() - coordinate.getY());
            double vectorLength = vector.length();
            if (vectorLength <= radius) {
                return vertex;
            }
        }
        return null;
    }

    private SmallVertex getSmallVertexByCoordinate(Point coordinate) {

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
        return getBigVertexByCoordinate(coordinates, 2 * BIG_VERTEX_RADIUS) == null;
    }

    private Point calcSmallVertexCoordinates(BigVertex vertex1, BigVertex vertex2, int weight, int index) {
        Point vector = new Point(vertex2.getCoordinates().getX() - vertex1.getCoordinates().getX(), vertex2.getCoordinates().getY() - vertex1.getCoordinates().getY());
        double vectorLength = vector.length();
        double factor = (index + 1) * (vectorLength / weight) / vectorLength;
        Point addingVector = vector.mul(factor);
        return vertex1.getCoordinates().add(addingVector);
    }

    public void debugGraph() {

        System.out.println("debug graph....");

        StringBuilder str = new StringBuilder("\n#######################\n");

        str.append("bigVertices: ").append(this.bigVertices).append("\n");
        str.append("smallVertices: ").append(this.smallVertices).append("\n");
        str.append("edges: ").append(this.edges).append("\n");

        for (BigVertex vertex : bigVertices) {
            str.append("\n").append(vertex.getId()).append(" Coord: ").append(vertex.getCoordinates()).append(" (");
            for (Edge ver : vertex.getEdges()) {
                str.append(ver.toString()).append(" ' ");
            }
            str.append("), ");
            for (Edge edge : vertex.getEdges()) {
                str.append("\n - to: ").append(edge.getNeighbor(vertex).getId());
                for (SmallVertex smallVertex : edge.getEdgeVertices()) {
                    str.append("        \n --->   ").append(smallVertex.getId()).append(" Coord: ").append(smallVertex.getCoordinates()).append(" (");
                    for (Connection connection : smallVertex.getConnections()) {
                        str.append(connection.toString()).append(" ' ");
                    }
                    str.append("), ");
                }
            }
        }

        System.out.println(str);
    }

}
