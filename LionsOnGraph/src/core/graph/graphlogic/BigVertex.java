package core.graph.graphlogic;

import core.util.Point;

import java.util.ArrayList;

/**
 * Created by Jens on 20.06.2017.
 */
public class BigVertex extends Vertex {

    protected ArrayList<EdgeVerticesObject> edgeVerticesObjects = new ArrayList<>();

    public BigVertex(int id, Point coordinates){
        super(id, coordinates);
    }

    public boolean deleteVertex() {
        for(EdgeVerticesObject edgeVertices : edgeVerticesObjects){
            edgeVertices.unregisterAll(this);
        }
        return false;
    }


    public boolean registerEdgeVerticeObject(BigVertex neighbor, ArrayList<SmallVertex> edgeVertices, int weight){
        return this.edgeVerticesObjects.add(new EdgeVerticesObject(neighbor, edgeVertices,weight));
    }

    public boolean unregisterEdgeVerticeObject(BigVertex neighbor){
        for(EdgeVerticesObject edgeVertex : edgeVerticesObjects){
            if(edgeVertex.getNeighbor().equals(neighbor)){
                if(edgeVertex.getEdgeVertices().size() <= 0){
                    for(Edge edge : edges){
                        if(edge.contains(neighbor)){
                            unregisterEdge(edge);
                        }
                    }
                }
                for(SmallVertex vertex : edgeVertex.getEdgeVertices()){
                    for(Edge edge : edges){
                        if(edge.contains(vertex)){
                            unregisterEdge(edge);
                        }
                    }
                }
                return edgeVerticesObjects.remove(edgeVertex);
            }
        }
        return false;
    }

    public ArrayList<EdgeVerticesObject> getEdgeVerticesObjects(){
        return edgeVerticesObjects;
    }


    class EdgeVerticesObject {
        private BigVertex neighbor;
        private ArrayList<SmallVertex> edgeVertices = new ArrayList<>();
        private int weight;

        public EdgeVerticesObject(BigVertex neighbor, ArrayList<SmallVertex> edgeVertices, int weight){
            this.neighbor = neighbor;
            this.edgeVertices = edgeVertices;
            this.weight = weight;
        }

        public BigVertex getNeighbor() {
            return neighbor;
        }

        public ArrayList<SmallVertex> getEdgeVertices() {
            return edgeVertices;
        }

        public int getEdgeWeight(){
            return weight;
        }

        public boolean unregisterAll(BigVertex vertex){
            for(SmallVertex edgeVertex : edgeVertices){
                //TODO ?
            }
            return neighbor.unregisterEdgeVerticeObject(vertex);
        }
    }
}
