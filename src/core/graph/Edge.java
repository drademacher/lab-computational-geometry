package core.graph;


import core.util.Point;

import java.util.ArrayList;

/**
 * Created by Danny on 13.05.2017.
 */
public class Edge implements GraphEntity{

    private Vertex[] vertices = new Vertex[2];
    private int vertexWeight;
    private ArrayList<Position> edgePositions = new ArrayList<>();

    public static Edge createNewEdge(Vertex vertex1, Vertex vertex2){
        //TODO specify default vertexWeight
        return Edge.createNewEdge(vertex1, vertex2, 4);
    }
    public static Edge createNewEdge(Vertex vertex1, Vertex vertex2, int vertexWeight){
        Edge edge = new Edge(vertex1, vertex2, vertexWeight);
        edge.createPositions();
        edge.registerEdge();
        return edge;
    }

    private Edge(Vertex vertexStart, Vertex vertexEnd, int vertexWeight){
        this.vertices[0] = vertexStart;
        this.vertices[1] = vertexEnd;
        this.vertexWeight = vertexWeight;

    }

    private void createPositions(){
        //create Positions
        for(int i = 0; i < vertexWeight - 1; i++){
            edgePositions.add(new Position(this, i+1));
        }
        //link the inner positions
        for(int i = 0; i < edgePositions.size(); i++){
            if(i - 1 >= 0){
                edgePositions.get(i).registerNeighborPosition(edgePositions.get(i-1));
            }
            if(i + 1 < edgePositions.size()){
                edgePositions.get(i).registerNeighborPosition(edgePositions.get(i+1));
            }
        }

        //link first and last position to the vertex
        edgePositions.get(0).registerNeighborPosition(vertices[0].getPosition());
        edgePositions.get(edgePositions.size() - 1).registerNeighborPosition(vertices[1].getPosition());
    }

    private boolean registerEdge(){
        return vertices[0].registerEdge(this, edgePositions.get(0)) && vertices[1].registerEdge(this, edgePositions.get(edgePositions.size() - 1));
    }

    public boolean deleteEdge(){
        return vertices[0].unregisterEdge(this, edgePositions.get(0)) && vertices[1].unregisterEdge(this, edgePositions.get(edgePositions.size() - 1));
    }

    public Point getCoordStart(){
        return vertices[0].getCoord();
    }

    public Point getCoordEnd(){
        return vertices[1].getCoord();
    }

    public Vertex[] getVertices(){
        return vertices;
    }

    public int getVertexWeight() {
        return vertexWeight;
    }

    public ArrayList<Position> getPositions() {
        return edgePositions;
    }

    @Override
    public String getGraphEntityInfo() {
        return "Edge_"+vertices[0].getId() + " - " + vertices[1].getId();
    }

    @Override
    public String toString() {
        return getGraphEntityInfo();
    }
}
