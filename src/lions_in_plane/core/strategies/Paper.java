package lions_in_plane.core.strategies;


import lions_in_plane.core.plane.Lion;
import lions_in_plane.core.plane.Man;
import util.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jens on 20.07.2017.
 */
public class Paper implements Strategy{

    private double saveRadius;
    private double radiusMan;

    public Paper(){

    }

    @Override
    public ArrayList<Point[]> getPath(Man man, ArrayList<Lion> lions) {

        ArrayList<Point[]> result = new ArrayList<>();

        //TODO how to get goalposition?

        System.out.println("all lions: "+lions);

        for(int i = 0; i < lions.size(); i++){

            List<Lion> subLions = lions.subList(0, Math.min(lions.size(), i));
            System.out.println("subLions : "+subLions);
//            result.add( >> new path << );
        }
        return result;
    }

    /*ASSUME
    *
    * delta * 1 + epsilon)  ==  man.getSpeed()
    * delta                 ==  lion.getSpeed()
    * saveRadius            == ???   (for now 3* lion.getSpeed())
    *
    */
    private Point doMove(Man man, Lion lion, Point goalPosition){
        this.radiusMan = man.getSpeed();
        this.saveRadius = 3* lion.getSpeed();

        if(man.getPosition().distanceTo(lion.getPosition()) >= saveRadius + radiusMan){
            return goInGoalDirection(man.getPosition(), goalPosition);
        } else if(!man.getPosition().equals(goalPosition) &&
                (man.getPosition().distanceTo(lion.getPosition()) >= saveRadius - lion.getSpeed()) &&
                (goInGoalDirection(man.getPosition(), lion.getPosition()).distanceTo(lion.getPosition()) >= (lion.getSpeed() + man.getPosition().distanceTo(lion.getPosition())))){
            return goInGoalDirection(man.getPosition(), goalPosition);
        } else{
            return doAvoidanceMove(man.getPosition(), lion.getPosition());
        }
    }

    // free move and escape move
    private Point goInGoalDirection(Point manPosition, Point goalPosition){
        Point vector = new Point(goalPosition.getX() - manPosition.getX(), goalPosition.getY() - manPosition.getY());
        double vectorLength = vector.length();

        Point unitVector = vector.mul(1/vectorLength);
        Point stepVector = unitVector.mul(radiusMan);

        return manPosition.add(stepVector);
    }

    // avoidance move
    private Point doAvoidanceMove(Point manPosition, Point lionPosition){
        Point [] intersections = getIntersectionPoints(manPosition, radiusMan, lionPosition, saveRadius);
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

        System.out.println("rad "+rad);

        double deg = Math.toDegrees(rad);

        System.out.println("deg "+deg);

        return deg;
    }
}
