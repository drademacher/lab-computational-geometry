package lions_and_men.applet_two_plane.core.strategies.man;

import lions_and_men.applet_two_plane.core.plane.Lion;
import lions_and_men.applet_two_plane.core.plane.Man;
import lions_and_men.util.Point;

import java.util.ArrayList;

public class Wait extends Strategy {

    public Wait(StrategyEnumMan strategyEnumMan) {
        super(strategyEnumMan);
    }

    @Override
    public ArrayList<Point> getPath(Man man, ArrayList<Lion> lions, int index, ArrayList<Point> inductionPath) {

        ArrayList<Point> curPath = man.getCalculatedPath();

        if (curPath != null && curPath.size() > 0) {
            curPath.add(man.getPosition());
        } else {
            curPath = new ArrayList<>();
            curPath.add(man.getPosition());
            curPath.add(man.getPosition());
        }

        return curPath;
    }
}
