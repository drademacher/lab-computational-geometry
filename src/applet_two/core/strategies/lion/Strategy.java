package applet_two.core.strategies.lion;

import applet_two.core.plane.Lion;
import applet_two.core.plane.Man;
import util.Point;

import java.util.ArrayList;

/**
 * Created by Jens on 21.07.2017.
 */
public abstract class Strategy {

    private StrategyEnumLion strategyEnumLion;

    public Strategy(StrategyEnumLion strategyEnumLion) {
        this.strategyEnumLion = strategyEnumLion;
    }

    public abstract ArrayList<Point> getPath(Lion lion, Man man, ArrayList<Point> manPath, ArrayList<Point> prevPath);

    public String getName() {
        return strategyEnumLion.name();
    }
}
