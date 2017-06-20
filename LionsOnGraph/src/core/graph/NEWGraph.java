package core.graph;

import core.util.Point;

import java.util.ArrayList;

/**
 * Created by Jens on 20.06.2017.
 */
public class NEWGraph {

    private static int idCounter = -1;

    private ArrayList<NEWBigVertex> vertices = new ArrayList<>();

    public NEWGraph(){

    }

    public NEWBigVertex createVertex(Point coordinate){
        //TODO check duplicate / margin to other vertices
        NEWBigVertex vertex =  new NEWBigVertex(getIdCounter(), coordinate);
        vertices.add(vertex);
        return vertex;
    }

    public boolean relocateVertex(NEWBigVertex vertex, Point newCoordinate){

        //TODO implement
        //TODO check duplicate / margin to other vertices
        return false;
    }

    public boolean deleteVertex(NEWBigVertex vertex){
        vertices.remove(vertex);
        return vertex.deleteVertex();
    }

    public boolean createEdge(NEWBigVertex vertex1, NEWBigVertex vertex2, int weight){

        //TODO check duplicate

        ArrayList<NEWSmallVertex> edgeVertices = new ArrayList<>();
        for(int i = 0; i < weight-1; i++){

            NEWSmallVertex smallVertex = new NEWSmallVertex(getIdCounter(), calcSmallVertexCoordinates(vertex1, vertex2, weight, i));

            edgeVertices.add(smallVertex);

            //pointer to prev vertex
            if(i>0){
                smallVertex.registerAdjacentVertex(edgeVertices.get(i-1));
                edgeVertices.get(i-1).registerAdjacentVertex(smallVertex);
            }

            //pointer to the big vertices
            if(i==0){
                smallVertex.registerAdjacentVertex(vertex1);
                vertex1.registerAdjacentVertex(smallVertex);
            }
            if(i==weight-2){
                smallVertex.registerAdjacentVertex(vertex2);
                vertex2.registerAdjacentVertex(smallVertex);
            }
        }

        vertex1.registerEdgeVerticeObject(vertex2, edgeVertices);
        vertex2.registerEdgeVerticeObject(vertex1, edgeVertices);


        if(weight <= 1){
            vertex1.registerAdjacentVertex(vertex2);
            vertex2.registerAdjacentVertex(vertex1);
        }
        return false;
    }

    public boolean removeEdge(NEWBigVertex vertex1, NEWBigVertex vertex2){
        return vertex1.unregisterEdgeVerticeObject(vertex2) && vertex2.unregisterEdgeVerticeObject(vertex1);
    }

    public NEWVertex getVertexByCoord(Point coordinate) {

        //TODO implement
//        for (NEWVertex vertex : vertices) {
//            if (vertex.getCoord().equals(coord)) {
//                return vertex;
//            }
//        }
        return null;
    }


    /* ***********************************
     *  PRIVATE HELPER FUNCTIONS
     *********************************** */

    private static int getIdCounter() {
        idCounter++;
        return idCounter;
    }

    private Point calcSmallVertexCoordinates(NEWBigVertex vertex1, NEWBigVertex vertex2, int weight, int index){
        Point vector = new Point(vertex1.getCoordinates().getX() - vertex2.getCoordinates().getX(), vertex1.getCoordinates().getY() - vertex2.getCoordinates().getY());
        double vectorLength = vector.length();
        return vertex1.getCoordinates().add(vector.mul((vectorLength / weight) * index + 1));
    }

    public String debugGraph(){

        String str = "";

        for(NEWBigVertex vertex : vertices){
            str += "\n"+vertex.getId() + " (";
            for(NEWVertex ver : vertex.getAdjacentVertices()){
                str += ver.getId() + " ' ";
            }
            str += "), ";
            for(NEWBigVertex.EdgeVerticesObject evObject : vertex.getEdgeVerticesObjects()){
                str += "\n - to: " + evObject.getNeighbor().getId();
                for(NEWSmallVertex smallVertex : evObject.getEdgeVertices()){
                    str += "        \n --->   "+smallVertex.getId()+" (";
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
