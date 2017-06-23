package core.graph;

import core.util.Point;

import java.util.ArrayList;

import static core.graph.GraphConstants.BIG_VERTEX_RADIUS;

/**
 * Created by Jens on 20.06.2017.
 */
class Graph {

    private static int idCounter = -1;

    private ArrayList<BigVertex> bigVertices = new ArrayList<>();
    private ArrayList<SmallVertex> smallVertices = new ArrayList<>();

    public Graph(){

    }

    public boolean createVertex(Point coordinate){

        //check duplicate and margin to other bigVertices
        if(!validVertexPosition(coordinate)){
            return false;
        }

        BigVertex vertex =  new BigVertex(getIdCounter(), coordinate);
        bigVertices.add(vertex);
        return true;
    }

    public boolean relocateVertex(BigVertex vertex, Point newCoordinate){

        //check duplicate
        if(!validVertexPosition(newCoordinate)){
            return false;
        }

        vertex.setCoordinates(newCoordinate);

        //update position of SmallVertices
        for(BigVertex.EdgeVerticesObject edgeVerticesObject : vertex.getEdgeVerticesObjects()){
            BigVertex neighbor = edgeVerticesObject.getNeighbor();
            int edgeWeight = edgeVerticesObject.getEdgeWeight();
            int i = 0;

            //check edge orientation, if there are (enough) edge bigVertices
            boolean reversedEdge = false;
            if(edgeWeight>2){
                reversedEdge = !verticesAreAdjacent(edgeVerticesObject.getEdgeVertices().get(0), vertex);
            }

            for(SmallVertex smallVertex : edgeVerticesObject.getEdgeVertices()){
                if(reversedEdge){
                    smallVertex.setCoordinates(calcSmallVertexCoordinates(neighbor, vertex, edgeWeight, i));
                }else{
                    smallVertex.setCoordinates(calcSmallVertexCoordinates(vertex, neighbor, edgeWeight, i));
                }
                i++;
            }
        }

        return false;
    }

    public boolean deleteVertex(BigVertex vertex){
        bigVertices.remove(vertex);
        return vertex.deleteVertex();
    }

    public boolean createEdge(BigVertex vertex1, BigVertex vertex2, int weight){

        //check duplicate
        for(BigVertex.EdgeVerticesObject edgeVerticesObject : vertex1.getEdgeVerticesObjects()){
            if(edgeVerticesObject.getNeighbor().equals(vertex2)){
                return false; //dublicate
            }
        }

        ArrayList<SmallVertex> edgeVertices = new ArrayList<>();
        for(int i = 0; i < weight-1; i++){

            SmallVertex smallVertex = new SmallVertex(getIdCounter(), calcSmallVertexCoordinates(vertex1, vertex2, weight, i));

            edgeVertices.add(smallVertex);
            this.smallVertices.add(smallVertex);

            //pointer to prev vertex
            if(i>0){
                Edge edge = new Edge(smallVertex, edgeVertices.get(i-1));
                smallVertex.registerEdge(edge);
                edgeVertices.get(i-1).registerEdge(edge);
            }

            //pointer to the big bigVertices
            if(i==0){
                Edge edge = new Edge(smallVertex, vertex1);
                smallVertex.registerEdge(edge);
                vertex1.registerEdge(edge);
            }
            if(i==weight-2){
                Edge edge = new Edge(smallVertex, vertex2);
                smallVertex.registerEdge(edge);
                vertex2.registerEdge(edge);
            }
        }

        vertex1.registerEdgeVerticeObject(vertex2, edgeVertices, weight);
        vertex2.registerEdgeVerticeObject(vertex1, edgeVertices, weight);


        if(weight <= 1){
            Edge edge = new Edge(vertex1, vertex2);
            vertex1.registerEdge(edge);
            vertex2.registerEdge(edge);
        }
        return true;
    }

    public boolean removeEdge(BigVertex vertex1, BigVertex vertex2){
        for(BigVertex.EdgeVerticesObject edgeVerticesObject : vertex1.getEdgeVerticesObjects()){
            if(edgeVerticesObject.getNeighbor().equals(vertex2)){
                for(SmallVertex smallVertex : edgeVerticesObject.getEdgeVertices()){
                    this.smallVertices.remove(smallVertex);
                }
            }
        }
        return vertex1.unregisterEdgeVerticeObject(vertex2) && vertex2.unregisterEdgeVerticeObject(vertex1);
    }

    public BigVertex getBigVertexByCoordinate(Point coordinate) {
        return getBigVertexByCoordinate(coordinate, BIG_VERTEX_RADIUS);
    }
    private BigVertex getBigVertexByCoordinate(Point coordinate, int radius) {

        for(BigVertex vertex : bigVertices){
            Point vector = new Point(vertex.getCoordinates().getX() - coordinate.getX(), vertex.getCoordinates().getY() - coordinate.getY());
            double vectorLength = vector.length();
            if(vectorLength <= radius){
                return vertex;
            }
        }
        return null;
    }

    public SmallVertex getSmallVertexByCoordinate(Point coordinate) {
        return getSmallVertexByCoordinate(coordinate, BIG_VERTEX_RADIUS);
    }
    private SmallVertex getSmallVertexByCoordinate(Point coordinate, int radius) {

        for(SmallVertex vertex : smallVertices){
            Point vector = new Point(vertex.getCoordinates().getX() - coordinate.getX(), vertex.getCoordinates().getY() - coordinate.getY());
            double vectorLength = vector.length();
            if(vectorLength <= radius){
                return vertex;
            }
        }
        return null;
    }

    public BigVertex getBigVertexById(int id){
        for(BigVertex vertex : bigVertices){
            if(vertex.getId() == id){
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

    private boolean verticesAreAdjacent(Vertex vertex1, Vertex vertex2){
        for(Edge edge : vertex1.getEdges()){
            if(edge.contains(vertex2)){
                return true;
            }
        }
        return false;
    }

    private boolean validVertexPosition(Point coordinates){
        if(getBigVertexByCoordinate(coordinates, 2*BIG_VERTEX_RADIUS) != null){
            return false;
        }
        return true;
    }

    private static int getIdCounter() {
        idCounter++;
        return idCounter;
    }

    private Point calcSmallVertexCoordinates(BigVertex vertex1, BigVertex vertex2, int weight, int index){
        Point vector = new Point(vertex2.getCoordinates().getX() - vertex1.getCoordinates().getX(), vertex2.getCoordinates().getY() - vertex1.getCoordinates().getY());
        double vectorLength = vector.length();
        double factor = (index + 1) * (vectorLength/weight) /vectorLength;
        Point addingVector = vector.mul(factor);
        Point result = vertex1.getCoordinates().add(addingVector);
        return result;
    }

    public String debugGraph(){

        String str = "";

        for(BigVertex vertex : bigVertices){
            str += "\n"+vertex.getId() + " Coord: "+vertex.getCoordinates()+ " (";
            for(Edge ver : vertex.getEdges()){
                str += ver.toString() + " ' ";
            }
            str += "), ";
            for(BigVertex.EdgeVerticesObject evObject : vertex.getEdgeVerticesObjects()){
                str += "\n - to: " + evObject.getNeighbor().getId();
                for(SmallVertex smallVertex : evObject.getEdgeVertices()){
                    str += "        \n --->   "+smallVertex.getId() + " Coord: "+smallVertex.getCoordinates() +" (";
                    for(Edge ver : smallVertex.getEdges()){
                        str += ver.toString() + " ' ";
                    }
                    str += "), ";
                }
            }
        }

        return str;
    }
}
