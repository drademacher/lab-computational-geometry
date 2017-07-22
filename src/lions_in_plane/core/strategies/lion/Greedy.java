package lions_in_plane.core.strategies.lion;

import lions_in_plane.core.plane.Lion;
import lions_in_plane.core.plane.Man;
import util.Point;

import java.util.ArrayList;

/**
 * Created by Jens on 21.07.2017.
 */
public class Greedy implements Strategy {
    @Override
    public ArrayList<Point> getPath(Lion lion, Man man, ArrayList<Point> manPath, ArrayList<Point> prevPath) {

        Point curPosition;
        Point curMan;

        if (manPath != null &&  manPath.size() > 0) {
            curMan = manPath.get(manPath.size() - 1);
        } else {
            curMan = man.getPosition();
            System.out.println("RESET");
        }

        if (prevPath != null &&  prevPath.size() > 0) {
            curPosition = prevPath.get(prevPath.size() - 1);
        } else {
            curPosition = lion.getPosition();
            prevPath = new ArrayList<>();
        }
        //        System.out.println("## go to goal ");
//        System.out.println("cur: "+curPosition);
//        System.out.println("goal: "+goalPosition);
        Point vector = new Point(curMan.getX() - curPosition.getX(), curMan.getY() - curPosition.getY());
        double vectorLength = vector.length();

        Point unitVector = vector.mul(1 / vectorLength);
        Point stepVector = unitVector.mul(lion.getSpeed());

//        System.out.println("step" + stepVector);

        Point result = curPosition.add(stepVector);
//        System.out.println("result: "+result);
        prevPath.add(result);

        return prevPath;
    }

    private boolean lionCatchedMan(Point lionCoord, Point manCoord, Lion lion) {

        if (lionCoord == null || manCoord == null || lion == null) {
            return false;
        }

        if (new Point(manCoord.getX() - lionCoord.getX(), manCoord.getY() - lionCoord.getY()).length() < lion.getSpeed()) {
            return true;
        }

        System.out.println("false");
        System.out.println(new Point(manCoord.getX() - lionCoord.getX(), manCoord.getY() - lionCoord.getY()).length());
        System.out.println(lion.getSpeed());
        return false;
    }
}
