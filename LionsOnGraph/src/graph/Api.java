package graph;

import util.Point;

/**
 * Created by Jens on 28.06.2017.
 */
public interface Api {

    BigVertex relocateVertex(BigVertex vertex, Point newCoordinate);

    BigVertex createVertex(Point coordinate);

    BigVertex deleteVertex(BigVertex vertex);

    Edge createEdge(BigVertex vertex1, BigVertex vertex2, int weight);

    Edge removeEdge(BigVertex vertex1, BigVertex vertex2);

    Edge changeEdgeWeight(BigVertex vertex1, BigVertex vertex2, int weight);
}
