package lions_and_men.applet_two_plane.core.strategies.lion;

import lions_and_men.applet_two_plane.core.plane.Lion;
import lions_and_men.applet_two_plane.core.plane.Man;
import lions_and_men.util.Point;

import java.util.ArrayList;

public class Wait extends Strategy {

    public Wait(StrategyEnumLion strategyEnumLion) {
        super(strategyEnumLion);
    }

    @Override
    public ArrayList<Point> getPath(Lion lion, Man man, ArrayList<Point> manPath, ArrayList<Point> prevPath) {

        if (prevPath != null && prevPath.size() > 0) {
            prevPath.add(lion.getPosition());
        } else {
            prevPath = new ArrayList<>();
            prevPath.add(lion.getPosition());
            prevPath.add(lion.getPosition());
        }

        return prevPath;
    }
}
