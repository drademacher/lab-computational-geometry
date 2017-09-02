package lions_on_graph.visualization;

import util.Point;

/**
 * Created by Jens on 29.06.2017.
 */
public interface Entity {

    void relocate(Point coordinates);

    void delete();

    Point getPosition();
}
