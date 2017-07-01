package graph;

import util.Point;

import java.util.ArrayList;

import static shapes.ShapeConstants.BIG_VERTEX_RADIUS;
import static shapes.ShapeConstants.SMALL_VERTEX_RADIUS;

class GraphController implements Api {

    private static int idCounter = -1;
    private CoreController coreController;

    //vertices
    private ArrayList<BigVertex> bigVertices = new ArrayList<>();
    private ArrayList<SmallVertex> smallVertices = new ArrayList<>();
    private ArrayList<Edge> NEWedges = new ArrayList<>();


    public GraphController(CoreController coreController) {
        this.coreController = coreController;
    }

    private static int getIdCounter() {
        idCounter++;
        return idCounter;
    }

    @Override
    public BigVertex relocateVertex(BigVertex vertex, Point newCoordinate) {

        //check duplicate
        if (!validVertexPosition(newCoordinate)) {
            return vertex;
        }

        vertex.setCoordinates(newCoordinate);

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
                //relocate shapes

                i++;
            }
        }
        return vertex;
    }

    @Override
    public BigVertex createVertex(Point coordinate) {

        //check duplicate and margin to other bigVertices
        if (!validVertexPosition(coordinate)) {
            return null;
        }

        BigVertex vertex = new BigVertex(getIdCounter(), coordinate);
        bigVertices.add(vertex);
        return vertex;
    }

    @Override
    public BigVertex deleteVertex(BigVertex vertex) {
        bigVertices.remove(vertex);

        for (int i = vertex.getEdges().size() - 1; i >= 0; i--) {
            Edge edge = vertex.getEdges().get(i);
            removeEdge(edge);
        }

        return vertex;
    }

    @Override
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
        NEWedges.add(edge);


        if (weight <= 1) {
            Connection connection = new Connection(vertex1, vertex2);
            vertex1.registerConnection(connection);
            vertex2.registerConnection(connection);
        }
        return edge;
    }

    @Override
    public Edge changeEdgeWeight(BigVertex vertex1, BigVertex vertex2, int weight) {

        for (int i = NEWedges.size() - 1; i >= 0; i--) {
            Edge edge = NEWedges.get(i);

            if (edge.contains(vertex1) && edge.contains(vertex2)) {

                //got the edge
                if (edge.getEdgeWeight() > weight) {
                    //TODO
                } else if (edge.getEdgeWeight() > weight) {
                    //TODO
                } else {
                    //do nothing, old weight == new weight
                }
            }
        }
        return null;
    }

    @Override
    public Edge removeEdge(BigVertex vertex1, BigVertex vertex2) {


        for (int i = NEWedges.size() - 1; i >= 0; i--) {
            Edge edge = NEWedges.get(i);

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

        this.NEWedges.remove(edge);

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

        for (Edge edge : NEWedges) {
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
        return NEWedges;
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
        str += "edges: " + this.NEWedges + "\n";

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
