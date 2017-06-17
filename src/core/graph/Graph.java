package core.graph;

import core.util.Point;

import java.util.ArrayList;

/**
 * Created by Danny on 13.05.2017.
 */
public class Graph {

    private static int idCounter = -1;
    private ArrayList<Edge> edges = new ArrayList<>();
    private ArrayList<Vertex> vertices = new ArrayList<>();
    private ArrayList<Position> positions = new ArrayList<>();

    private int xRange = 0;
    private int yRange = 0;
    private int defaultEdgeWeight = 4;

    public Graph(){

    }


    /**
     * Create new Vertex
     * to create a new vertex, chose a baseVertex and new coordinate for the new vertex,
     * because every new vertex needs a edge connection to a existing vertex (only one component!)
     *
     * */
    public boolean registerVertex(Point newVertexCoord){

        Vertex newVertex = new Vertex(getIdCounter(), newVertexCoord);

        if(getVertexByCoord(newVertexCoord) != null){
            return false;
        }

        calculateXRange(newVertexCoord);
        calculateYRange(newVertexCoord);

        return this.vertices.add(newVertex);
    }

    public boolean relocateVertex(Vertex vertex, Point newVertexPoint){
        if(vertex == null){
            return false;
        }

        if(getVertexByCoord(newVertexPoint) != null){
            return false;
        }

        ArrayList<Vertex> neighborVertices = new ArrayList<>();
        for(Edge edge : vertex.getEdges()){
            if(!edge.getVertices()[0].getCoord().equals(vertex.getCoord())){
                neighborVertices.add(edge.getVertices()[0]);
            }
            if(!edge.getVertices()[1].getCoord().equals(vertex.getCoord())){
                neighborVertices.add(edge.getVertices()[1]);
            }
        }

//        vertex.setCoord(newVertexPoint);
        deleteVertex(vertex);
        registerVertex(newVertexPoint);
        for(Vertex neighborVertex : neighborVertices){
            registerEdge(newVertexPoint, neighborVertex.getCoord());
        }

        calculateXRange(newVertexPoint);
        calculateYRange(newVertexPoint);

        return true;
    }

    /**
     * Delete Vertex
     * delete a vertex and all incident edges
     *
     * */
    public boolean deleteVertex(Point point){
        return deleteVertex(getVertexByCoord(point));
    }
    public boolean deleteVertex(Vertex vertex){
        if(vertex == null){
            return false;
        }

        calculateXRange(null);
        calculateYRange(null);

        for(int i = vertex.getEdges().size() - 1; i >= 0; i--){
            Edge edge = vertex.getEdges().get(i);
            deleteEdge(edge);
        }

        return vertices.remove(vertex);
    }

    /**
     * Create new Edge
     * specify two vertices between the new edge should be created
     *
     * */
    public boolean registerEdge(Point pointBaseVertex, Point pointTargetVertex){
        return registerEdge(getVertexByCoord(pointBaseVertex), getVertexByCoord(pointTargetVertex));
    }
    public boolean registerEdge(Vertex baseVertex, Vertex targetVertex){
        return registerEdge(baseVertex, targetVertex,defaultEdgeWeight);
    }
    public boolean registerEdge(Point pointBaseVertex, Point pointTargetVertex, int edgeWeight){
        return registerEdge(getVertexByCoord(pointBaseVertex), getVertexByCoord(pointTargetVertex), edgeWeight);
    }
    public boolean registerEdge(Vertex baseVertex, Vertex targetVertex, int edgeWeight){

        if(baseVertex == null || targetVertex == null){
            return false;
        }
        if(edgeExists(baseVertex, targetVertex)){
            return false;
        }

        Edge newEdge = Edge.createNewEdge(baseVertex, targetVertex, edgeWeight);

        return this.edges.add(newEdge);
    }


    /**
     * Delete Edge
     * unregister and delete edge
     *
     * */
    public boolean deleteEdge(Point coordStart, Point coordEnd){
        return deleteEdge(getEdgeByCoord(coordStart, coordEnd));
    }
    public boolean deleteEdge(Edge edge){

        if(edge == null){
            return false;
        }

        if(edge.deleteEdge()){
            return edges.remove(edge);
        }
        return false;
    }

    /**
     * get Vertex by Coord
     *
     * */
    public Vertex getVertexByCoord(Point coord){
        for(Vertex vertex : vertices){
            if (vertex.getCoord().equals(coord)) {
                return vertex;
            }
        }
        return null;
    }

    public Boolean isVertex(Point coord) {
        // TODO: Jens, mach das mal ordentlich :D
        return getVertexByCoord(coord) != null;
    }

    /**
     * get Edge by Coord
     *
     * */
    public Edge getEdgeByCoord(Point coordStart, Point coordEnd){
        for(Edge edge : edges){
            //both possibilities
            if(edge.getCoordStart().equals(coordStart) && edge.getCoordEnd().equals(coordEnd)){
                return edge;
            }
            if(edge.getCoordStart().equals(coordEnd) && edge.getCoordEnd().equals(coordStart)){
                return edge;
            }
        }
        return null;
    }

    /**
     * Check if edge exists already
     *
     * */
    private boolean edgeExists(Vertex baseVertex, Vertex targetVertex){
        Edge edge = getEdgeByCoord(baseVertex.getCoord(), targetVertex.getCoord());
        if(edge == null){
            return false;
        }
        return true;
    }

    /**
     *  calculate xRand and yRange
     *
     */
    private void calculateXRange(Point changedValue){
        if(changedValue == null){
            changedValue = new Point(0, 0);
        }
        if(changedValue.getX() > xRange){
            xRange = changedValue.getX() + 1;
        } else {
            xRange = 0;
            for (Vertex vertex : vertices) {
                xRange = Math.max(xRange, vertex.getCoord().getX() + 1);
            }
        }
    }
    private void calculateYRange(Point changedValue){
        if(changedValue == null){
            changedValue = new Point(0, 0);
        }
        if(changedValue.getY() > yRange){
            yRange = changedValue.getY() + 1;
        } else {
            yRange = 0;
            for (Vertex vertex : vertices) {
                yRange = Math.max(yRange, vertex.getCoord().getY() + 1);
            }
        }
    }



    public ArrayList<Edge> getEdges(){
        return edges;
    }

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }


    public int getXRange() {
        return xRange;
    }

    public int getYRange() {
        return yRange;
    }

    public void outputGraph(){
        positions.clear();
        System.out.println("##### all vertices");
        for(Vertex vertex : vertices){
            System.out.println(vertex);
            positions.add(vertex.getPosition());
        }
        System.out.println("##### all edges");
        for(Edge edge : edges){
            System.out.println(edge);
            for(Position position : edge.getPositions()){
                positions.add(position);
            }
        }
        System.out.println("##### all positions");
        for(Position position : positions){
            System.out.println(position);
        }
    }

    private static int getIdCounter(){
        idCounter++;
        return  idCounter;
    }

}
