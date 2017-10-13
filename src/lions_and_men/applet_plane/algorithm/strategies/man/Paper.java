package lions_and_men.applet_plane.algorithm.strategies.man;

import lions_and_men.applet_plane.algorithm.plane.Lion;
import lions_and_men.applet_plane.algorithm.plane.Man;
import lions_and_men.util.Point;

import java.util.ArrayList;

/**
 * Man Strategy, runs away from lions (inductive)
 */
public class Paper extends Strategy {

    private double saveRadius;
    private double radiusMan;

    Paper(StrategyEnumMan strategyEnumMan) {
        super(strategyEnumMan);
    }

    @Override
    public ArrayList<Point> getPath(Man man, ArrayList<Lion> lions, int index, ArrayList<Point> inductionPath) {
        this.radiusMan = 1 + (man.getEpsilon() * (1 - (Math.pow(2, -(index + 1)))));

        ArrayList<Point> result;
        ArrayList<Point> curPath = man.getCalculatedPath();
        if (curPath != null && curPath.size() > 0) {
            result = curPath;
        } else {
            result = new ArrayList<>();
            result.add(man.getPosition());
        }

        result = doMove(lions.get(index), inductionPath, result);

        return result;
    }

    /*ASSUME
    *
    * delta * (1 + epsilon) ==  man.getSpeed()
    * delta                 ==  lion.getSpeed()
    * saveRadius            == ???   (lion.getRange()*2)
    *
    */
    private ArrayList<Point> doMove(Lion lion, ArrayList<Point> inductionPath, ArrayList<Point> curPath) {
        this.saveRadius = lion.getRange() * 2;

        Point curLionPosition = lion.getCalculatedPositionAtTime(curPath.size() - 2);

        //init: only 1 lion -> no exiting prevPath
        if (inductionPath == null || inductionPath.size() == 0) {

            curPath.add(goAwayFromLion(curPath.get(curPath.size() - 1), curLionPosition));
        } else {

            Point cuPosition = curPath.get(curPath.size() - 1);
            int indexGoal = (int) (curPath.size() * this.radiusMan);
            while (indexGoal > 0.5 * inductionPath.size()) {

                inductionPath = extendPath(inductionPath);
            }
            Point goalPosition = inductionPath.get(indexGoal);

            if (cuPosition.distanceTo(curLionPosition) >= saveRadius + radiusMan) {

                curPath.add(goInGoalDirection(cuPosition, goalPosition));
                    /*TODO parallel, instead of points??*/
            } else if (!cuPosition.equals(goalPosition) &&
                    (cuPosition.distanceTo(curLionPosition) >= saveRadius - lion.getSpeed()) &&
                    (goInGoalDirection(cuPosition, goalPosition).distanceTo(curLionPosition) >= (lion.getSpeed() + cuPosition.distanceTo(curLionPosition)))) {
                curPath.add(goInGoalDirection(cuPosition, goalPosition));
            } else {
                curPath.add(doAvoidanceMove(cuPosition, curLionPosition));
            }
        }
        return curPath;
    }

    private Point goAwayFromLion(Point curPosition, Point lionPosition) {

        Point vector = new Point(lionPosition.getX() - curPosition.getX(), lionPosition.getY() - curPosition.getY());
        double vectorLength = vector.length();
        Point unitVector = vector.mul(1 / vectorLength);
        Point stepVector = unitVector.mul(-radiusMan);
        return curPosition.add(stepVector);
    }

    // free move and escape move
    private Point goInGoalDirection(Point curPosition, Point goalPosition) {
        Point vector = new Point(goalPosition.getX() - curPosition.getX(), goalPosition.getY() - curPosition.getY());
        double vectorLength = vector.length();

        Point unitVector = vector.mul(1 / vectorLength);
        Point stepVector = unitVector.mul(radiusMan);

        return curPosition.add(stepVector);
    }

    // avoidance move
    private Point doAvoidanceMove(Point curPosition, Point lionPosition) {
        Point[] intersections = getIntersectionPoints(curPosition, radiusMan, lionPosition, saveRadius);
        return intersections[0];//TODO need counterclockwise point
    }

    private Point[] getIntersectionPoints(Point m1, double radius1, Point m2, double radius2) {
        Point[] intersectionPoints = new Point[2];

        Point vector = new Point(m2.getX() - m1.getX(), m2.getY() - m1.getY());
        double distance = vector.length();

        // check conditions
        if (distance > radius1 + radius2) {
            //no intersection points
            return intersectionPoints;
        }
        if (distance < Math.abs(radius1 - radius2)) {
            // no intersection points
            return intersectionPoints;
        }
        if (distance == 0 && radius1 == radius2) {
            //coincident
            return intersectionPoints;
        }

        double a = (radius1 * radius1 - radius2 * radius2 + distance * distance) / (2 * distance);
        double h = Math.sqrt(radius1 * radius1 - a * a);

        Point middlePoint = m1.add((m2.sub(m1)).mul(a).mul(1 / distance));

        Point intersectionPoint0 = new Point(middlePoint.getX() - h * (m2.getY() - m1.getY()) / distance,
                middlePoint.getY() + h * (m2.getX() - m1.getX()) / distance);

        Point intersectionPoint1 = new Point(middlePoint.getX() + h * (m2.getY() - m1.getY()) / distance,
                middlePoint.getY() - h * (m2.getX() - m1.getX()) / distance);

        intersectionPoints[0] = intersectionPoint0;
        intersectionPoints[1] = intersectionPoint1;

        return intersectionPoints;
    }

    private ArrayList<Point> extendPath(ArrayList<Point> path) {
        for (int i = 0; i < 150; i++) {
            Point vector = new Point(path.get(path.size() - 1).getX() - path.get(path.size() - 2).getX(), path.get(path.size() - 1).getY() - path.get(path.size() - 2).getY());
            double vectorLength = vector.length();

            Point unitVector = vector.mul(1 / vectorLength);
            Point stepVector = unitVector.mul(radiusMan);

            path.add(path.get(path.size() - 1).add(stepVector));
        }
        return path;

    }


}
