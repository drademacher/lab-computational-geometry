package graph;

import shapes.ShapedSmallVertex;
import util.Point;

public class SmallVertex extends Vertex {

    private ShapedSmallVertex shape;

    public static SmallVertex createSmallVertex(GraphController graphController, int id, Point coordinates) {
        SmallVertex vertex = new SmallVertex(id, coordinates);
        vertex.shape = new ShapedSmallVertex(graphController, vertex);
        return vertex;
    }

    private SmallVertex(int id, Point coordinates) {
        super(id, coordinates);
    }

    public ShapedSmallVertex getShape() {
        return shape;
    }
}
