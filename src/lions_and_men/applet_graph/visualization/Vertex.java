package lions_and_men.applet_graph.visualization;

import lions_and_men.util.Point;


/**
 * interface to describe a vertex (big or small vertex)
 */
public interface Vertex {

    void relocate(Point coordinates);

    void delete();

    Point getPosition();
}
