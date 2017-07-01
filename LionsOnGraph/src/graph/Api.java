package graph;

import util.Point;

/**
 * Created by Jens on 28.06.2017.
 */
public interface Api {

    BigVertex relocateVertex(Point vertexCoordinates, Point newCoordinates);

    BigVertex createVertex(Point coordinates);

    BigVertex deleteVertex(Point vertexCoordinates);

    Edge createEdge(Point vertex1Coordinates, Point vertex2Coordinates, int weight);

    Edge removeEdge(Point vertex1Coordinates, Point vertex2Coordinates);

    Edge changeEdgeWeight(Point vertex1Coordinates, Point vertex2Coordinates, int weight);
}
