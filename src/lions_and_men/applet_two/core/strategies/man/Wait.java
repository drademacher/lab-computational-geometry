package lions_and_men.applet_two.core.strategies.man;

import lions_and_men.applet_two.core.plane.Lion;
import lions_and_men.applet_two.core.plane.Man;
import lions_and_men.util.Point;

import java.util.ArrayList;

public class Wait extends Strategy {

    public Wait(StrategyEnumMan strategyEnumMan) {
        super(strategyEnumMan);
    }

    @Override
    public ArrayList<Point> getPath(Man man, ArrayList<Lion> lions, int index, ArrayList<Point> inductionPath) {
        ArrayList<Point> path = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            path.add(man.getPosition());
        }
        return path;
    }
}
