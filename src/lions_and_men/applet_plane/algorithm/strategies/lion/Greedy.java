package lions_and_men.applet_plane.algorithm.strategies.lion;

import lions_and_men.applet_plane.algorithm.plane.Lion;
import lions_and_men.applet_plane.algorithm.plane.Man;
import lions_and_men.util.Point;

import java.util.ArrayList;

/**
 * Lion Strategy, runs on shortest path to man
 */
public class Greedy extends Strategy {

    Greedy(StrategyEnumLion strategyEnumLion) {
        super(strategyEnumLion);
    }

    @Override
    public ArrayList<Point> getPath(Lion lion, Man man, ArrayList<Point> manPath, ArrayList<Point> prevPath) {

        Point curPosition;
        Point curMan;

        if (manPath != null && manPath.size() > 0) {
            curMan = manPath.get(manPath.size() - 1);
        } else {
            curMan = man.getPosition();
        }

        if (prevPath != null && prevPath.size() > 0) {
            curPosition = prevPath.get(prevPath.size() - 1);
        } else {
            curPosition = lion.getPosition();
            prevPath = new ArrayList<>();
            prevPath.add(lion.getPosition());
        }

        Point vector = new Point(curMan.getX() - curPosition.getX(), curMan.getY() - curPosition.getY());
        double vectorLength = vector.length();

        Point unitVector = vector.mul(1 / vectorLength);
        Point stepVector = unitVector.mul(lion.getSpeed());

        Point result = curPosition.add(stepVector);
        prevPath.add(result);

        return prevPath;
    }

}
