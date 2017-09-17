package lions_and_men.applet_plane.algorithm.strategies.lion;

import lions_and_men.applet_plane.algorithm.plane.Lion;
import lions_and_men.applet_plane.algorithm.plane.Man;
import lions_and_men.util.Point;

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
