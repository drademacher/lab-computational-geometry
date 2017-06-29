package shapes;

import util.Point;

/**
 * Created by Jens on 29.06.2017.
 */
public interface ShapedEntity {

    void relocate(Point coordinates);

    void delete();
}
