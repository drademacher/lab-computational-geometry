package lions_and_men.applet_graph.visualization;

import lions_and_men.util.Point;


/**
 * Interface to describe an entity (man or lion)
 */
public interface Entity {

    void relocate(Point coordinates);

    void delete();

    Point getPosition();


}
