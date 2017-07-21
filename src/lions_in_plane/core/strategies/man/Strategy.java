package lions_in_plane.core.strategies.man;


import lions_in_plane.core.plane.Lion;
import lions_in_plane.core.plane.Man;
import util.Point;

import java.util.ArrayList;

/**
 * Created by Jens on 20.07.2017.
 */
public interface Strategy {

    ArrayList<Point> getPath(Man man, ArrayList<Lion> lions, ArrayList<Point> prevPath);
}
