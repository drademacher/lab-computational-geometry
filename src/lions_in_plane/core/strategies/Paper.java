package lions_in_plane.core.strategies;


import lions_in_plane.core.plane.Lion;
import lions_in_plane.core.plane.Man;
import util.Point;

import java.util.ArrayList;

/**
 * Created by Jens on 20.07.2017.
 */
public class Paper implements Strategy{

    private double saveRadius;
    private double radiusMan;

    public Paper(){

    }

    @Override
    public ArrayList<Point> getPath(Man man, ArrayList<Lion> lions) {

       ArrayList<Point> result = new ArrayList<>();

        if(man == null || lions == null || lions.size() == 0){
            return result;
        }

        //TODO how to get goalposition?


        for(int i = 0; i < lions.size(); i++){

//            List<Lion> subLions = lions.subList(0, Math.min(lions.size(), i+1));
            result = doMove(man, lions.get(i), result);
        }

        //only 1 lion
//        result = doMove(man, lions.get(0), null);

        //System.out.println("RESULT......all "+result);
        //System.out.println("RESULT......size "+result.size());
        for(int i = 0; i < result.size(); i++){
            //System.out.println(result.get(i));
        }
        return result;
    }

    /*ASSUME
    *
    * delta * (1 + epsilon) ==  man.getSpeed()
    * delta                 ==  lion.getSpeed()
    * saveRadius            == ???   (for now 3* lion.getSpeed())
    *
    */
    private ArrayList<Point> doMove(Man man, Lion lion, ArrayList<Point> prevPath) {

        this.radiusMan = man.getSpeed();
        this.saveRadius = 3* lion.getSpeed();

        Point goalPosition = new Point(0, 0);//TODO

        ArrayList<Point> curPath = new ArrayList<>();
        curPath.add(man.getPosition());

        //init: only 1 lion -> no exiting prevPath
        if(prevPath == null || prevPath.size() == 0){
            System.out.println("CASE D");
            for(int i= 1; i <120; i++){
//                //System.out.println("curPath in calculation: "+curPath);
                curPath.add(goAwayFromLion(curPath.get(i-1), lion.getPosition()));
            }
        } else {

            for(int i= 1; i <120; i++) {
                Point cuPosition = curPath.get(curPath.size()-1);
//                Point goalPosition = p

                System.out.println("case B boolean:");
                System.out.println("a) "+!cuPosition.equals(goalPosition));
                System.out.println("b) "+ (cuPosition.distanceTo(lion.getPosition()) >= saveRadius - lion.getSpeed()));
                System.out.println("c) "+ (goInGoalDirection(cuPosition, goalPosition).distanceTo(lion.getPosition()) >= (lion.getSpeed() + cuPosition.distanceTo(lion.getPosition()))));

                if (cuPosition.distanceTo(lion.getPosition()) >= saveRadius + radiusMan) {
                    System.out.println("CASE A");
                    curPath.add(goInGoalDirection(cuPosition, goalPosition));
                    /*TODO parallel, instead of points??*/
                } else if (!cuPosition.equals(goalPosition) &&
                        (cuPosition.distanceTo(lion.getPosition()) >= saveRadius - lion.getSpeed()) &&
                        (goInGoalDirection(cuPosition, goalPosition).distanceTo(lion.getPosition()) >= (lion.getSpeed() + cuPosition.distanceTo(lion.getPosition())))) {
                    System.out.println("CASE B");
                    curPath.add(goInGoalDirection(cuPosition, goalPosition));
                } else {
                    System.out.println("CASE C");
                    curPath.add(doAvoidanceMove(cuPosition, lion.getPosition()));
                }
            }
        }

        //System.out.println("return curPath.."+curPath);
        return curPath;
    }

    private Point goAwayFromLion(Point curPosition, Point lionPosition){

        //System.out.println("##go away");
        //System.out.println("cur: "+curPosition);
        //System.out.println("lion: "+lionPosition);
        Point vector = new Point(lionPosition.getX() - curPosition.getX(), lionPosition.getY() - curPosition.getY());
        //System.out.println("vec: "+vector);
        double vectorLength = vector.length();

        //System.out.println("length: "+vectorLength);

        Point unitVector = vector.mul(1/vectorLength);

        //System.out.println("unit: "+unitVector);
        Point stepVector = unitVector.mul(-radiusMan);

        //System.out.println("stepVec: "+stepVector);

        Point result = curPosition.add(stepVector);
        //System.out.println("result: "+result);
        return result;
    }

    // free move and escape move
    private Point goInGoalDirection(Point curPosition, Point goalPosition){
//        System.out.println("## go to goal ");
//        System.out.println("cur: "+curPosition);
//        System.out.println("goal: "+goalPosition);
        Point vector = new Point(goalPosition.getX() - curPosition.getX(), goalPosition.getY() - curPosition.getY());
        double vectorLength = vector.length();

        Point unitVector = vector.mul(1/vectorLength);
        Point stepVector = unitVector.mul(radiusMan);

//        System.out.println("step" + stepVector);

        Point result = curPosition.add(stepVector);
//        System.out.println("result: "+result);
        return result;
    }

    // avoidance move
    private Point doAvoidanceMove(Point curPosition, Point lionPosition){
        Point [] intersections = getIntersectionPoints(curPosition, radiusMan, lionPosition, saveRadius);
        return intersections[0];//TODO need counterclockwise point
    }

    private Point[] getIntersectionPoints(Point m1, double radius1, Point m2, double radius2){
        Point[] intersectionPoints = new Point[2];

        Point vector = new Point(m2.getX() - m1.getX(), m2.getY() - m1.getY());
        double distance = vector.length();

        // check conditions
        if(distance > radius1 + radius2){
            //no intersection points
            return intersectionPoints;
        }
        if(distance < Math.abs(radius1 - radius2)){
            // no intersection points
            return intersectionPoints;
        }
        if(distance == 0 && radius1 == radius2){
            //coincident
            return intersectionPoints;
        }

        double a = (radius1*radius1 - radius2*radius2 + distance*distance)/(2*distance);
        double h = Math.sqrt(radius1*radius1 - a*a);

        Point middlePoint = m1.add((m2.sub(m1)).mul(a).mul(1 / distance));


        Point intersectionPoint1 = new Point(middlePoint.getX() + h*(m2.getY() - m1.getY()) / distance,
                middlePoint.getY() - h * (m2.getX() - m1.getX()) / distance);

        Point intersectionPoint2 = new Point(middlePoint.getX() - h*(m2.getY() - m1.getY()) / distance,
                middlePoint.getY() + h * (m2.getX() - m1.getX()) / distance);

        intersectionPoints[0] = intersectionPoint1;
        intersectionPoints[1] = intersectionPoint2;

        return intersectionPoints;
    }


    private double getAngleBetween(Point previous, Point center, Point next) {

        Point vector1 = new Point(previous.getX() - center.getX(), previous.getY() - center.getY());
        Point vector2 = new Point(next.getX() - center.getX(), next.getY() - center.getY());
        Point vector3 = new Point(previous.getX() - next.getX(), previous.getY() - next.getY());

        //need to normalize:
        Point vector1Normalized = vector1.mul(1/vector1.length());
        Point vector2Normalized = vector2.mul(1/vector2.length());

        double rad = Math.acos(vector1Normalized.getX() * vector2Normalized.getX() + vector1Normalized.getY() * vector2Normalized.getY() );

        //System.out.println("rad "+rad);

        double deg = Math.toDegrees(rad);

        //System.out.println("deg "+deg);

        return deg;
    }
}
