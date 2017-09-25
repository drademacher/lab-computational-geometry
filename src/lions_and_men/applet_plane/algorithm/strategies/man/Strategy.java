package lions_and_men.applet_plane.algorithm.strategies.man;


import lions_and_men.applet_plane.algorithm.plane.Lion;
import lions_and_men.applet_plane.algorithm.plane.Man;
import lions_and_men.util.Point;

import java.util.ArrayList;

/**
 * abstract strategy class
 */
public abstract class Strategy {


    private StrategyEnumMan strategyEnumMan;

    public Strategy(StrategyEnumMan strategyEnumMan) {
        this.strategyEnumMan = strategyEnumMan;
    }

    public abstract ArrayList<Point> getPath(Man man, ArrayList<Lion> lions, int index, ArrayList<Point> inductionPath);

    public String getName() {
        return strategyEnumMan.name();
    }
}
