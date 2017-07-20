package lions_in_plane.core.strategies;


import lions_in_plane.core.plane.Lion;
import lions_in_plane.core.plane.Man;
import util.Point;

import java.util.ArrayList;

/**
 * Created by Jens on 20.07.2017.
 */
public class Paper implements Strategy{

    public Paper(){

    }

    @Override
    public Point[] getPath(Man man, ArrayList<Lion> lions) {
        return new Point[0];
    }
}
