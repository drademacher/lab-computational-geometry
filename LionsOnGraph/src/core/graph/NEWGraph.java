package core.graph;

import core.util.Point;

import java.util.ArrayList;

import static core.graph.NEWGraphConstants.BIG_VERTEX_RADIUS;

/**
 * Created by Jens on 20.06.2017.
 */
public class NEWGraph {

    private static int idCounter = -1;

    private ArrayList<NEWBigVertex> bigVertices = new ArrayList<>();
    private ArrayList<NEWSmallVertex> smallVertices = new ArrayList<>();

    public NEWGraph(){

    }

    public boolean createVertex(Point coordinate){

        //check duplicate and margin to other bigVertices
        if(!validVertexPosition(coordinate)){
            return false;
        }

        NEWBigVertex vertex =  new NEWBigVertex(getIdCounter(), coordinate);
        bigVertices.add(vertex);
        return true;
    }

    public boolean relocateVertex(NEWBigVertex vertex, Point newCoordinate){

        //check duplicate
        if(!validVertexPosition(newCoordinate)){
            return false;
        }

        vertex.setCoordinates(newCoordinate);

        //update position of SmallVertices
        for(NEWBigVertex.EdgeVerticesObject edgeVerticesObject : vertex.getEdgeVerticesObjects()){
            NEWBigVertex neighbor = edgeVerticesObject.getNeighbor();
            int edgeWeight = edgeVerticesObject.getEdgeWeight();
            int i = 0;

            //check edge orientation, if there are (enough) edge bigVertices
            boolean reversedEdge = false;
            if(edgeWeight>2){
                reversedEdge = !verticesAreAdjacent(edgeVerticesObject.getEdgeVertices().get(0), vertex);
            }

            for(NEWSmallVertex smallVertex : edgeVerticesObject.getEdgeVertices()){
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

    public boolean deleteVertex(NEWBigVertex vertex){
        bigVertices.remove(vertex);
        return vertex.deleteVertex();
    }

    public boolean createEdge(NEWBigVertex vertex1, NEWBigVertex vertex2, int weight){

        //check duplicate
        for(NEWBigVertex.EdgeVerticesObject edgeVerticesObject : vertex1.getEdgeVerticesObjects()){
            if(edgeVerticesObject.getNeighbor().equals(vertex2)){
                return false; //dublicate
            }
        }

        ArrayList<NEWSmallVertex> edgeVertices = new ArrayList<>();
        for(int i = 0; i < weight-1; i++){

            NEWSmallVertex smallVertex = new NEWSmallVertex(getIdCounter(), calcSmallVertexCoordinates(vertex1, vertex2, weight, i));

            edgeVertices.add(smallVertex);
            this.smallVertices.add(smallVertex);

            //pointer to prev vertex
            if(i>0){
                smallVertex.registerAdjacentVertex(edgeVertices.get(i-1));
                edgeVertices.get(i-1).registerAdjacentVertex(smallVertex);
            }

            //pointer to the big bigVertices
            if(i==0){
                smallVertex.registerAdjacentVertex(vertex1);
                vertex1.registerAdjacentVertex(smallVertex);
            }
            if(i==weight-2){
                smallVertex.registerAdjacentVertex(vertex2);
                vertex2.registerAdjacentVertex(smallVertex);
            }
        }

        vertex1.registerEdgeVerticeObject(vertex2, edgeVertices, weight);
        vertex2.registerEdgeVerticeObject(vertex1, edgeVertices, weight);


        if(weight <= 1){
            vertex1.registerAdjacentVertex(vertex2);
            vertex2.registerAdjacentVertex(vertex1);
        }
        return true;
    }

    public boolean removeEdge(NEWBigVertex vertex1, NEWBigVertex vertex2){
        for(NEWBigVertex.EdgeVerticesObject edgeVerticesObject : vertex1.getEdgeVerticesObjects()){
            if(edgeVerticesObject.getNeighbor().equals(vertex2)){
                for(NEWSmallVertex smallVertex : edgeVerticesObject.getEdgeVertices()){
                    this.smallVertices.remove(smallVertex);
                }
            }
        }
        return vertex1.unregisterEdgeVerticeObject(vertex2) && vertex2.unregisterEdgeVerticeObject(vertex1);
    }

    public NEWBigVertex getVertexByCoordinate(Point coordinate) {
        return getVertexByCoordinate(coordinate, BIG_VERTEX_RADIUS);
    }
    private NEWBigVertex getVertexByCoordinate(Point coordinate, int radius) {

        for(NEWBigVertex vertex : bigVertices){
            Point vector = new Point(vertex.getCoordinates().getX() - coordinate.getX(), vertex.getCoordinates().getY() - coordinate.getY());
            double vectorLength = vector.length();
            if(vectorLength <= radius){
                return vertex;
            }
        }

        return null;
    }

    public NEWBigVertex getBigVertexById(int id){
        for(NEWBigVertex vertex : bigVertices){
            if(vertex.getId() == id){
                return vertex;
            }
        }
        return null;
    }

    public ArrayList<NEWBigVertex> getBigVertices() {
        return bigVertices;
    }

    public ArrayList<NEWSmallVertex> getSmallVertices() {
        return smallVertices;
    }

    /* ***********************************
     *  PRIVATE HELPER FUNCTIONS
     *********************************** */

    private boolean verticesAreAdjacent(NEWVertex vertex1, NEWVertex vertex2){
        for(NEWVertex vertex : vertex1.getAdjacentVertices()){
            if(vertex.equals(vertex2)){
                return true;
            }
        }
        return false;
    }

    private boolean validVertexPosition(Point coordinates){
        if(getVertexByCoordinate(coordinates, 2*BIG_VERTEX_RADIUS) != null){
            return false;
        }
        return true;
    }

    private static int getIdCounter() {
        idCounter++;
        return idCounter;
    }

    private Point calcSmallVertexCoordinates(NEWBigVertex vertex1, NEWBigVertex vertex2, int weight, int index){
        Point vector = new Point(vertex2.getCoordinates().getX() - vertex1.getCoordinates().getX(), vertex2.getCoordinates().getY() - vertex1.getCoordinates().getY());
        double vectorLength = vector.length();
        double factor = (index + 1) * (vectorLength/weight) /vectorLength;
        Point addingVector = vector.mul(factor);
        Point result = vertex1.getCoordinates().add(addingVector);
        return result;
    }

    public String debugGraph(){

        String str = "";

        for(NEWBigVertex vertex : bigVertices){
            str += "\n"+vertex.getId() + " Coord: "+vertex.getCoordinates()+ " (";
            for(NEWVertex ver : vertex.getAdjacentVertices()){
                str += ver.getId() + " ' ";
            }
            str += "), ";
            for(NEWBigVertex.EdgeVerticesObject evObject : vertex.getEdgeVerticesObjects()){
                str += "\n - to: " + evObject.getNeighbor().getId();
                for(NEWSmallVertex smallVertex : evObject.getEdgeVertices()){
                    str += "        \n --->   "+smallVertex.getId() + " Coord: "+smallVertex.getCoordinates() +" (";
                    for(NEWVertex ver : smallVertex.getAdjacentVertices()){
                        str += ver.getId() + " ' ";
                    }
                    str += "), ";
                }
            }
        }

        return str;
    }
}
