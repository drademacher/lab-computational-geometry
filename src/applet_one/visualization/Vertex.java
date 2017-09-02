package applet_one.visualization;

import util.Point;

/**
 * Created by Jens on 29.06.2017.
 */
public interface Vertex {

    void relocate(Point coordinates);

    void delete();
}
