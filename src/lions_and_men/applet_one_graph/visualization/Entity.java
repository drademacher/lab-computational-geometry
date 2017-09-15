package lions_and_men.applet_one_graph.visualization;

import lions_and_men.util.Point;

/**
 * Created by Jens on 29.06.2017.
 */
public interface Entity {

    void relocate(Point coordinates);

    void delete();

    Point getPosition();


}
