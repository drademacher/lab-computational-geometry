package graph;

import shapes.ShapedBigVertex;
import util.Point;

import java.util.ArrayList;

/**
 * Created by Jens on 20.06.2017.
 */
public class BigVertex extends Vertex {

    protected ArrayList<EdgeVerticesObject> edgeVerticesObjects = new ArrayList<>();
    private ShapedBigVertex shape;

    public static BigVertex createBigVertex(GraphController graphController, int id, Point coordinates) {
        BigVertex vertex = new BigVertex(id, coordinates);
        vertex.shape = new ShapedBigVertex(graphController, vertex);
        return vertex;
    }

    private BigVertex(int id, Point coordinates) {
        super(id, coordinates);
    }

    public boolean deleteVertex() {
        for (EdgeVerticesObject edgeVertices : edgeVerticesObjects) {
            edgeVertices.unregisterAll(this);
        }
        return false;
    }


    public boolean registerEdgeVerticeObject(BigVertex neighbor, ArrayList<SmallVertex> edgeVertices, int weight) {
        return this.edgeVerticesObjects.add(new EdgeVerticesObject(neighbor, edgeVertices, weight));
    }

    public boolean unregisterEdgeVerticeObject(BigVertex neighbor) {
        for (EdgeVerticesObject edgeVertex : edgeVerticesObjects) {
            if (edgeVertex.getNeighbor().equals(neighbor)) {

                if (edgeVertex.getEdgeVertices().size() <= 0) {
                    for (int i = edges.size() - 1; i >= 0; i--) {
                        if (edges.get(i).contains(neighbor)) {
                            edges.get(i).getShape().delete();
                            unregisterEdge(edges.get(i));
                        }
                    }
                }
                for (SmallVertex vertex : edgeVertex.getEdgeVertices()) {
                    for (int i = vertex.getEdges().size() - 1; i >= 0; i--) {
                        Edge edge = vertex.getEdges().get(i);
                        vertex.getShape().delete();
                        edge.getShape().delete();
                        vertex.unregisterEdge(edge);
                        if (edge.contains(this)) {
                            unregisterEdge(edge);
                        }
                        if (edge.contains(neighbor)) {
                            neighbor.unregisterEdge(edge);
                        }
                    }
                }
                return edgeVerticesObjects.remove(edgeVertex);
            }
        }
        return false;
    }

    public ArrayList<EdgeVerticesObject> getEdgeVerticesObjects() {
        return edgeVerticesObjects;
    }

    public ShapedBigVertex getShape() {
        return shape;
    }

    class EdgeVerticesObject {
        private BigVertex neighbor;
        private ArrayList<SmallVertex> edgeVertices = new ArrayList<>();
        private int weight;

        public EdgeVerticesObject(BigVertex neighbor, ArrayList<SmallVertex> edgeVertices, int weight) {
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

        public int getEdgeWeight() {
            return weight;
        }

        public boolean unregisterAll(BigVertex vertex) {
            for (SmallVertex edgeVertex : edgeVertices) {
                //TODO ?
            }
            return neighbor.unregisterEdgeVerticeObject(vertex);
        }
    }
}
