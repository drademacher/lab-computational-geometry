package graph;

import util.Point;

import java.util.ArrayList;

import static shapes.ShapeConstants.BIG_VERTEX_RADIUS;

class Graph {

    private static int idCounter = -1;
    private GraphController graphController;

    //vertices
    private ArrayList<BigVertex> bigVertices = new ArrayList<>();
    private ArrayList<SmallVertex> smallVertices = new ArrayList<>();
    private ArrayList<Edge> edges = new ArrayList<>();


    public Graph(GraphController graphController) {
        this.graphController = graphController;
    }

    private static int getIdCounter() {
        idCounter++;
        return idCounter;
    }

    public boolean relocateVertex(BigVertex vertex, Point newCoordinate) {

        //check duplicate
        if (!validVertexPosition(newCoordinate)) {
            return false;
        }

        vertex.setCoordinates(newCoordinate);
        vertex.getShape().relocate();

        //update position of SmallVertices
        for (BigVertex.EdgeVerticesObject edgeVerticesObject : vertex.getEdgeVerticesObjects()) {
            BigVertex neighbor = edgeVerticesObject.getNeighbor();
            int edgeWeight = edgeVerticesObject.getEdgeWeight();
            int i = 0;

            //check edge orientation, if there are (enough) edge bigVertices
            boolean reversedEdge = false;
            if (edgeWeight > 2) {
                reversedEdge = !verticesAreAdjacent(edgeVerticesObject.getEdgeVertices().get(0), vertex);
            }

            for (SmallVertex smallVertex : edgeVerticesObject.getEdgeVertices()) {
                if (reversedEdge) {
                    Point coordinates = calcSmallVertexCoordinates(neighbor, vertex, edgeWeight, i);
                    smallVertex.setCoordinates(coordinates);

                } else {
                    Point coordinates = calcSmallVertexCoordinates(vertex, neighbor, edgeWeight, i);
                    smallVertex.setCoordinates(coordinates);
                }
                //relocate shapes
                smallVertex.getShape().relocate();
                for (Edge edge : smallVertex.getEdges()) {
                    edge.getShape().relocate();
                }
                i++;
            }
        }
        return false;
    }

    public boolean createVertex(Point coordinate) {

        //check duplicate and margin to other bigVertices
        if (!validVertexPosition(coordinate)) {
            return false;
        }

        BigVertex vertex = BigVertex.createBigVertex(graphController, getIdCounter(), coordinate);
        bigVertices.add(vertex);
        return true;
    }

    public boolean deleteVertex(BigVertex vertex) {
        bigVertices.remove(vertex);

        vertex.getShape().delete();

        vertex.deleteVertex();

        for (BigVertex.EdgeVerticesObject edgeVerticesObject : vertex.getEdgeVerticesObjects()) {

            for (SmallVertex smallVertex : edgeVerticesObject.getEdgeVertices()) {
                this.smallVertices.remove(smallVertex);
                for (int i = edges.size() - 1; i >= 0; i--) {
                    Edge edge = edges.get(i);
                    if (edge.contains(smallVertex)) {
                        edges.remove(edge);
                    }
                }
            }
        }

        //TODO
        return true;
    }

    public boolean createEdge(BigVertex vertex1, BigVertex vertex2, int weight) {

        //check duplicate
        for (BigVertex.EdgeVerticesObject edgeVerticesObject : vertex1.getEdgeVerticesObjects()) {
            if (edgeVerticesObject.getNeighbor().equals(vertex2)) {
                return false; //dublicate
            }
        }

        ArrayList<SmallVertex> edgeVertices = new ArrayList<>();
        for (int i = 0; i < weight - 1; i++) {

            SmallVertex smallVertex = SmallVertex.createSmallVertex(graphController, getIdCounter(), calcSmallVertexCoordinates(vertex1, vertex2, weight, i));

            edgeVertices.add(smallVertex);
            this.smallVertices.add(smallVertex);

            //pointer to prev vertex
            if (i > 0) {
                Edge edge = Edge.createEdge(graphController, smallVertex, edgeVertices.get(i - 1));
                smallVertex.registerEdge(edge);
                edgeVertices.get(i - 1).registerEdge(edge);
                this.edges.add(edge);
            }

            //pointer to the big bigVertices
            if (i == 0) {
                Edge edge = Edge.createEdge(graphController, smallVertex, vertex1);
                smallVertex.registerEdge(edge);
                vertex1.registerEdge(edge);
                this.edges.add(edge);
            }
            if (i == weight - 2) {
                Edge edge = Edge.createEdge(graphController, smallVertex, vertex2);
                smallVertex.registerEdge(edge);
                vertex2.registerEdge(edge);
                this.edges.add(edge);
            }
        }

        vertex1.registerEdgeVerticeObject(vertex2, edgeVertices, weight);
        vertex2.registerEdgeVerticeObject(vertex1, edgeVertices, weight);


        if (weight <= 1) {
            Edge edge = Edge.createEdge(graphController, vertex1, vertex2);
            vertex1.registerEdge(edge);
            vertex2.registerEdge(edge);
            this.edges.add(edge);
        }
        return true;
    }

    public boolean removeEdge(BigVertex vertex1, BigVertex vertex2) {

        for (BigVertex.EdgeVerticesObject edgeVerticesObject : vertex1.getEdgeVerticesObjects()) {
            if (edgeVerticesObject.getNeighbor().equals(vertex2)) {

                for (SmallVertex smallVertex : edgeVerticesObject.getEdgeVertices()) {
                    this.smallVertices.remove(smallVertex);
                    for (int i = edges.size() - 1; i >= 0; i--) {
                        Edge edge = edges.get(i);
                        if (edge.contains(smallVertex)) {
                            edges.remove(edge);
                        }
                    }
                }
            }
        }
        boolean bool1 = vertex1.unregisterEdgeVerticeObject(vertex2);
        boolean bool2 = vertex2.unregisterEdgeVerticeObject(vertex1);

        return bool1 && bool2;
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

    public SmallVertex getSmallVertexByCoordinate(Point coordinate) {
        return getSmallVertexByCoordinate(coordinate, BIG_VERTEX_RADIUS);
    }

    private SmallVertex getSmallVertexByCoordinate(Point coordinate, double radius) {

        for (SmallVertex vertex : smallVertices) {
            Point vector = new Point(vertex.getCoordinates().getX() - coordinate.getX(), vertex.getCoordinates().getY() - coordinate.getY());
            double vectorLength = vector.length();
            if (vectorLength <= radius) {
                return vertex;
            }
        }
        return null;
    }

    public BigVertex getBigVertexById(int id) {
        for (BigVertex vertex : bigVertices) {
            if (vertex.getId() == id) {
                return vertex;
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
        for (Edge edge : vertex1.getEdges()) {
            if (edge.contains(vertex2)) {
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

    public String debugGraph() {


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
            for (BigVertex.EdgeVerticesObject evObject : vertex.getEdgeVerticesObjects()) {
                str += "\n - to: " + evObject.getNeighbor().getId();
                for (SmallVertex smallVertex : evObject.getEdgeVertices()) {
                    str += "        \n --->   " + smallVertex.getId() + " Coord: " + smallVertex.getCoordinates() + " (";
                    for (Edge ver : smallVertex.getEdges()) {
                        str += ver.toString() + " ' ";
                    }
                    str += "), ";
                }
            }
        }

        return str;
    }
}
