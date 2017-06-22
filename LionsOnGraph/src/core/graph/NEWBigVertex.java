package core.graph;

import core.util.Point;

import java.util.ArrayList;

/**
 * Created by Jens on 20.06.2017.
 */
public class NEWBigVertex extends NEWVertex {

    protected ArrayList<EdgeVerticesObject> edgeVerticesObjects = new ArrayList<>();

    public NEWBigVertex(int id, Point coordinates){
        super(id, coordinates);
    }

    public boolean deleteVertex() {
        for(EdgeVerticesObject edgeVertices : edgeVerticesObjects){
            edgeVertices.unregisterAll(this);
        }
        return false;
    }


    public boolean registerEdgeVerticeObject(NEWBigVertex neighbor, ArrayList<NEWSmallVertex> edgeVertices, int weight){
        return this.edgeVerticesObjects.add(new EdgeVerticesObject(neighbor, edgeVertices,weight));
    }

    public boolean unregisterEdgeVerticeObject(NEWBigVertex neighbor){
        for(EdgeVerticesObject edgeVertex : edgeVerticesObjects){
            if(edgeVertex.getNeighbor().equals(neighbor)){
                if(edgeVertex.getEdgeVertices().size() <= 0){
                    unregisterAdjacentVertex(neighbor);
                }
                for(NEWSmallVertex vertex : edgeVertex.getEdgeVertices()){
                    unregisterAdjacentVertex(vertex);
                }
                return edgeVerticesObjects.remove(edgeVertex);
            }
        }
        return false;
    }

    public ArrayList<EdgeVerticesObject> getEdgeVerticesObjects(){
        return edgeVerticesObjects;
    }


    public class EdgeVerticesObject {
        private NEWBigVertex neighbor;
        private ArrayList<NEWSmallVertex> edgeVertices = new ArrayList<>();
        private int weight;

        public EdgeVerticesObject(NEWBigVertex neighbor, ArrayList<NEWSmallVertex> edgeVertices, int weight){
            this.neighbor = neighbor;
            this.edgeVertices = edgeVertices;
            this.weight = weight;
        }

        public NEWBigVertex getNeighbor() {
            return neighbor;
        }

        public ArrayList<NEWSmallVertex> getEdgeVertices() {
            return edgeVertices;
        }

        public int getEdgeWeight(){
            return weight;
        }

        public boolean unregisterAll(NEWBigVertex vertex){
            for(NEWSmallVertex edgeVertex : edgeVertices){
                //TODO ?
            }
            return neighbor.unregisterEdgeVerticeObject(vertex);
        }
    }
}
