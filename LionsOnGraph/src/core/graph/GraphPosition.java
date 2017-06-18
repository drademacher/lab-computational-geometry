package core.graph;

/**
 * Created by Jens on 25.05.2017.
 */
public class GraphPosition {

    private Vertex vertexStart;
    private Vertex vertexEnd;
    private int stepsOnEdge;

    public GraphPosition(Vertex vertexStart, Vertex vertexEnd, int stepsOnEdge) {
        this.vertexStart = vertexStart;
        this.vertexEnd = vertexEnd;
        this.stepsOnEdge = stepsOnEdge;
    }

    public Vertex getVertexStart() {
        return vertexStart;
    }

    public Vertex getVertexEnd() {
        return vertexEnd;
    }

    public int getStepsOnEdge() {
        return stepsOnEdge;
    }

    @Override
    public String toString() {
        return vertexStart.getGraphEntityInfo() + " - " + vertexEnd.getGraphEntityInfo() + " | " + stepsOnEdge;
    }
}
