package lions_in_plane.core.strategies.man;

import lions_in_plane.core.plane.Lion;
import lions_in_plane.core.plane.Man;
import util.Point;

import java.util.ArrayList;

public class Wait implements Strategy{

    public Wait(){

    }

    @Override
    public ArrayList<Point> getPath(Man man, Lion lion, ArrayList<Point> inductionPath, ArrayList<Point> curPath) {
        return null;
    }
}
