package applet_two.core.strategies.man;


import applet_two.core.plane.Lion;
import applet_two.core.plane.Man;
import util.Point;

import java.util.ArrayList;

/**
 * Created by Jens on 20.07.2017.
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
